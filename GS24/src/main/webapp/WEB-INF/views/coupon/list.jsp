<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>쿠폰 리스트</title>
    <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
    <script type="text/javascript">
    $(document).ready(function() {
        let currentPage = 1;

        $("input[name='choice']").change(function() {
            const selectedChoice = $("input[name='choice']:checked").val();
            fetchCouponList(selectedChoice, 1);
        });

        $(document).on("click", ".pagination a", function(event) {
            event.preventDefault();
            const pageNum = $(this).data("page");
            currentPage = pageNum;
            const selectedChoice = $("input[name='choice']:checked").val();
            fetchCouponList(selectedChoice, pageNum);
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

        function fetchCouponList(choice, pageNum) {
            $.ajax({
                url: 'list-' + choice,
                type: 'GET',
                data: { pageNum: pageNum },
                dataType: 'json',
                success: function(response) {
                    let now = new Date();
                    let couponList = response.couponList;
                    let pageMaker = response.pageMaker;
                    let couponHtml = '';

                    couponList.forEach(function(couponVO) {
                        let imagePath = '';
                        let statusText = '';
                        if (now < couponVO.couponExpiredDate && couponVO.isUsed == 0) {
                            imagePath = '../resources/images/coupon/coupon.png';
                            statusText = '사용 가능';
                        } else if (couponVO.isUsed == 1) {
                            imagePath = '../resources/images/coupon/usedCoupon.png';
                            statusText = '사용 불가(이미 사용된 쿠폰)';
                        } else {
                            imagePath = '../resources/images/coupon/expiredCoupon.png';
                            statusText = '사용 불가(만료된 쿠폰)';
                        }

                        couponHtml += '<div class="coupon-item" style="display: flex; justify-content: center; align-items: center;">';
                        couponHtml += '<table style="width: 100%; text-align: center; vertical-align: middle;">';
                        couponHtml += '<tr>';
                        couponHtml += '<td style="width: 40%; text-align: center;"><a href="detail?couponId=' + couponVO.couponId + '"><img src="' + imagePath + '" alt="Coupon" /></a></td>';
                        couponHtml += '<td style="width: 60%; vertical-align: middle; text-align: center;">';
                        couponHtml += '<strong>' + couponVO.couponName + '</strong><br>';
                        couponHtml += formatDate(couponVO.couponGrantDate) + '<br>~ <strong>' + formatDate(couponVO.couponExpiredDate) + '</strong><br>';
                        couponHtml += '<strong>' + statusText + '</strong></td>';
                        couponHtml += '</tr>';
                        couponHtml += '</table>';
                        couponHtml += '</div>';
                    });

                    $(".coupon-list").html(couponHtml);
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

        fetchCouponList('available', 1);

        $('#btnGrantCoupon').click(function() {
            window.location.href = 'grant';
        });
    });
</script>

<style>
    body {
        font-family: Arial, sans-serif;
    }

    .coupon-list {
        margin-top: 20px;
    }

    .coupon-item {
        border: 2px solid #ccc;
        border-radius: 5px;
        padding: 10px;
        margin-bottom: 10px;
        transition: border-color 0.3s;
        display: block;
    }

    .coupon-item:hover {
        border-color: #ccc;
    }

    .coupon-item table {
        width: 100%;
        border-spacing: 0;
        table-layout: fixed;
    }

    .coupon-item table td {
        padding: 8px;
    }

    .coupon-item img {
        max-width: 100%;
        height: auto;
        border-radius: 5px;
    }

    ul {
    	position: fixed; /* 화면 하단에 고정 */
    	bottom: 20px; /* 하단에서의 거리 설정 */
    	left: 50%; /* 화면 가로 중앙 정렬 */
    	transform: translateX(-50%); /* 중앙 정렬 보정 */
    	z-index: 100; /* 다른 요소보다 위에 표시 */
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

    body {
        overflow: hidden;
    }
    .fixed-buttons {
    position: sticky; /* 스크롤 시 기본 위치 고정 */
    bottom: 0; /* 기본 위치에 따라 고정 */
    z-index: 110; /* 페이징보다 위에 표시 */
}
}
</style>
</head>
<body>

    <h2>${memberVO.memberId}님의 쿠폰함</h2>
    <p>만료일 이후 일주일이 경과한 쿠폰은 자동 삭제됩니다.</p>

    <label><input type="radio" name="choice" value="available" checked>사용 가능 쿠폰</label>
    <label><input type="radio" name="choice" value="all">전체</label>
    <label><input type="radio" name="choice" value="expired">만료된 쿠폰</label>
    <label><input type="radio" name="choice" value="used">사용된 쿠폰</label>

    <div class="coupon-list"></div>
	<div class="fixed-buttons">
    <c:if test="${memberVO.memberRole == 2}"> 
        <button id="btnGrantCoupon">개별 쿠폰 제공</button> 
    </c:if>
    <a href="../member/mypage"><button>마이페이지</button></a>
    </div>
    <ul class="pagination"></ul>

</body>
</html>