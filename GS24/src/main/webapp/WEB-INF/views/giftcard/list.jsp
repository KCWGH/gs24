<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>
    <title>기프트카드 리스트</title>
    <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
    <script type="text/javascript">
    $(document).ajaxSend(function(e, xhr, opt){
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        
        xhr.setRequestHeader(header, token);
     });
    $(document).ready(function() {
        let currentPage = 1;

        $("input[name='choice']").change(function() {
            const selectedChoice = $("input[name='choice']:checked").val();
            fetchGiftCardList(selectedChoice, 1);
        });

        $(document).on("click", ".pagination a", function(event) {
            event.preventDefault();
            const pageNum = $(this).data("page");
            currentPage = pageNum;
            const selectedChoice = $("input[name='choice']:checked").val();
            fetchGiftCardList(selectedChoice, pageNum);
        });

        function formatDate(timestamp) {
            const date = new Date(timestamp);
            const year = date.getFullYear().toString().slice(-2);
            const month = ('0' + (date.getMonth() + 1)).slice(-2);
            const day = ('0' + date.getDate()).slice(-2);
            let hours = date.getHours();
            const minutes = ('0' + date.getMinutes()).slice(-2);
            const period = hours >= 12 ? '오후' : '오전';
            hours = hours % 12 || 12;
            return year + '.' + month + '.' + day + ' ' + period + ' ' + hours + ':' + minutes;
        }

        function fetchGiftCardList(choice, pageNum) {
            $.ajax({
                url: 'list-' + choice,
                type: 'GET',
                data: { pageNum: pageNum },
                dataType: 'json',
                success: function(response) {
                    let now = new Date();
                    let giftCardList = response.giftCardList;
                    let pageMaker = response.pageMaker;
                    let giftCardHtml = '';
                    
                    giftCardList.forEach(function(giftCardVO) {
                        let imagePath = '';
                        let statusText = '';
                        if (now < giftCardVO.giftCardExpiredDate && giftCardVO.isUsed == 0) {
                            imagePath = '../resources/images/giftCard/giftCard.png';
                            statusText = '미사용 기프트카드';
                        } else if (now < giftCardVO.giftCardExpiredDate && giftCardVO.isUsed == 1 && giftCardVO.balance != 0) {
                            imagePath = '../resources/images/giftCard/usedGiftCard.png';
                            statusText = '사용중인 기프트카드';
                        } else if (now >= giftCardVO.giftCardExpiredDate || giftCardVO.balance == 0){
                            imagePath = '../resources/images/giftCard/expiredGiftCard.png';
                            statusText = '만료됐거나 잔액이 없는 기프트카드';
                        }

                        giftCardHtml += '<div class="giftCard-item" style="display: flex; justify-content: center; align-items: center;">';
                        giftCardHtml += '<table style="width: 100%; text-align: center; vertical-align: middle;">';
                        giftCardHtml += '<tr>';
                        giftCardHtml += '<td style="width: 40%; text-align: center;"><a href="detail?giftCardId=' + giftCardVO.giftCardId + '"><img src="' + imagePath + '" alt="GiftCard" /></a></td>';
                        giftCardHtml += '<td style="width: 60%; vertical-align: middle; text-align: center;">';
                        giftCardHtml += '<strong>' + giftCardVO.giftCardName + '<br><span style="color:green">잔액 '+ giftCardVO.balance + ' 원' + '</strong><br>';
                        giftCardHtml += formatDate(giftCardVO.giftCardGrantDate) + '<br>~ <strong style="color:red">' + formatDate(giftCardVO.giftCardExpiredDate) + '</strong><br>';
                        giftCardHtml += '<strong>' + statusText + '</strong></td>';
                        giftCardHtml += '</tr>';
                        giftCardHtml += '</table>';
                        giftCardHtml += '</div>';
                    });
                    
                    if (giftCardList.length == 0){
                    	giftCardHtml += '해당 상태의 기프트카드가 없습니다.<br><br>';
                    }

                    $(".giftCard-list").html(giftCardHtml);
                    updatePagination(pageMaker, choice);
                },
                error: function(xhr, status, error) {
                    console.error('AJAX request failed: ' + error);
                }
            });
        }

        function updatePagination(pageMaker, choice) {
            let paginationHtml = '';
            if (pageMaker.isPrev) {
                paginationHtml += '<li><a href="#" data-page="' + (pageMaker.startNum - 1) + '">이전</a></li>';
            }
            for (let num = pageMaker.startNum; num <= pageMaker.endNum; num++) {
                const activeClass = num === currentPage ? 'active' : '';
                paginationHtml += '<li class="' + activeClass + '"><a href="#" data-page="' + num + '">' + num + '</a></li>';
            }
            if (pageMaker.isNext) {
                paginationHtml += '<li><a href="#" data-page="' + (pageMaker.endNum + 1) + '">다음</a></li>';
            }
            $(".pagination").html(paginationHtml);
        }

        fetchGiftCardList('used', 1);

        $('#btnGrantGiftCard').click(function() {
            window.location.href = 'grant';
        });
        
        $('#btnPresentGiftCard').click(function() {
        });
    });
</script>

<style>
    body {
        font-family: Arial, sans-serif;
    }

    .giftCard-list {
        margin-top: 20px;
    }

    .giftCard-item {
        border: 2px solid #ccc;
        border-radius: 5px;
        padding: 10px;
        margin-bottom: 10px;
        transition: border-color 0.3s;
        display: block;
    }

    .giftCard-item:hover {
        border-color: #ccc;
    }

    .giftCard-item table {
        width: 100%;
        border-spacing: 0;
        table-layout: fixed;
    }

    .giftCard-item table td {
        padding: 8px;
    }

    .giftCard-item img {
        max-width: 100%;
        height: auto;
        border-radius: 5px;
    }

    ul {
    	position: fixed;
    	bottom: 10px;
    	left: 50%;
    	transform: translateX(-50%);
    	z-index: 100;
        list-style-type: none;
        padding: 0;
        display: flex;
        justify-content: center;
        gap: 10px;
        margin-top: 20px;
    }

    ul li {
        display: inline-block;
        padding: 5px 10px;
        cursor: pointer;
    }

    ul li.active {
        font-weight: bold;
        color: #007bff;
    }

    ul li.disabled {
        color: #ccc;
        cursor: not-allowed;
    }

    ul li a {
        text-decoration: none;
        color: inherit;
    }

    ul li a:hover {
        color: inherit;
    }

    
</style>
</head>
<body>

    <h2>${memberVO.memberId}님의 기프트카드함</h2>
    <p>만료된 이후 30일이 경과한 기프트카드는 자동 삭제됩니다.</p>

    <label><input type="radio" name="choice" value="used" checked>사용중</label>
    <label><input type="radio" name="choice" value="unused">미사용</label>
    <label><input type="radio" name="choice" value="all">전체</label>
    <label><input type="radio" name="choice" value="expired">만료됨</label>

    <div class="giftCard-list"></div>

    <div class="fixed-buttons">
        <c:if test="${memberVO.memberRole == 2}"> 
            <button id="btnGrantGiftCard">개별 기프트카드 제공</button><br>
        </c:if>
        <c:if test="${memberVO.memberRole == 1}"> 
            <button id="btnPresentGiftCard">내 기프트카드 선물하기</button><br>
        </c:if>
        <a href="../member/mypage"><button>마이페이지</button></a>
    </div>
    <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
    <ul class="pagination"></ul>

</body>
</html>
