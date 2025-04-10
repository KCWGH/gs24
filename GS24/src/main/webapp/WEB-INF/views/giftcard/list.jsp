<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="../resources/css/fonts.css">
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
/* 전체 페이지 스타일 */
body {
	font-family: 'Pretendard-Regular', sans-serif;
    margin: 0;
    padding: 15px;
    background-color: #f8f9fa;
    text-align: center;
}

/* 제목 스타일 */
h2 {
    color: #333;
    margin-bottom: 5px;
}

/* 안내 문구 */
p {
    color: #888;
    margin-bottom: 10px;
}

/* 선택 옵션 스타일 */
label {
    margin: 0 5px;
    cursor: pointer;
}

input[type="radio"] {
    margin-right: 3px;
}

/* 기프트카드 리스트 스타일 */
.giftCard-list {
    margin-top: 15px;
}
/* 
/* 개별 기프트카드 */
.giftCard-item {
    background: #fff;
    border-radius: 10px;
    padding: 10px;
    margin: 8px 0;
    box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1);
    display: flex;
    align-items: center;
    text-align: left;
}

.giftCard-item img {
    width: 150px;
    height: auto;
    border-radius: 5px;
}

.giftCard-item table {
    width: 100%;
    text-align: left;
}

.giftCard-item td {
	line-height: 1.4;
    padding: 5px;
}

.giftCard-item strong {
    color: #333;
} */

/* 상태별 텍스트 스타일 */
.status-text {
    font-size: 13px;
    font-weight: bold;
    display: inline-block;
    padding: 3px 8px;
    border-radius: 5px;
}
/* 페이지네이션 */
.pagination {
    list-style: none;
    padding: 0;
    display: flex;
    justify-content: center;
    margin-top: 10px;
}

.pagination li {
    display: inline;
    margin: 0 3px;
}

.pagination a {
    text-decoration: none;
    color: black;
    padding: 3px 6px;
    border-radius: 5px;
    transition: background 0.3s;
}

.pagination .active a {
    background: #555;
    color: white;
}


.fixed-buttons {
    display: flex;
    justify-content: space-between;
    margin-top: 15px;
}

.fixed-buttons button {
	font-family: 'Pretendard-Regular', sans-serif;
    flex: 1;
    margin: 0 5px;
    padding: 8px;
    border: none;
    background: #ddd;
    border-radius: 5px;
    cursor: pointer;
}

.fixed-buttons button:hover {
    background: #bbb;
}

.fixed-buttons {
    display: flex;
    justify-content: space-between;
    margin-top: 15px;
}

/* 왼쪽 정렬 버튼 */
.left-buttons {
    display: flex;
    gap: 10px; /* 버튼 간격 */
}

.right-button {
    margin-left: auto;
}
.radio-group {
    display: flex;
    justify-content: space-between;
    width: 100%;
    max-width: 500px;
    margin: 10px auto;
}

.radio-group label {
    flex: 1;
    text-align: center;
}
</style>
</head>
<body>

    <h2>${memberVO.memberId}님의 기프트카드함</h2>
    <p>만료된 이후 30일이 경과한 기프트카드는 자동 삭제됩니다.</p>
	<div class="radio-group">
    <label><input type="radio" name="choice" value="used" checked>사용중</label>
    <label><input type="radio" name="choice" value="unused">미사용</label>
    <label><input type="radio" name="choice" value="all">전체</label>
    <label><input type="radio" name="choice" value="expired">만료됨</label>
	</div>
    <div class="giftCard-list"></div>

    <ul class="pagination"></ul>
    <div class="fixed-buttons">
    <div class="left-buttons">
        <a href="../giftcard/purchase"><button>기프트카드 구매</button></a>
        <a href="../giftcard/grant"><button>기프트카드 선물하기</button></a>
    </div>
    <div class="right-button">
        <a href="../user/mypage"><button>마이페이지</button></a>
    </div>
	</div>

    <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">

</body>
</html>
