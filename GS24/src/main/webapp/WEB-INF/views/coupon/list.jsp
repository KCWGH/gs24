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
        $("input[name='choice']").change(function() {
            const selectedChoice = $("input[name='choice']:checked").val();
            fetchCouponList(selectedChoice, 1);
        });

        $(document).on("click", ".pagination a", function(event) {
            event.preventDefault();
            const pageNum = $(this).data("page");
            const selectedChoice = $("input[name='choice']:checked").val();
            fetchCouponList(selectedChoice, pageNum);
        });

        function fetchCouponList(choice, pageNum) {
            $.ajax({
                url: 'list-' + choice,
                type: 'GET',
                data: { pageNum: pageNum },
                dataType: 'json',
                success: function(response) {
                    let couponList = response.couponList;
                    let pageMaker = response.pageMaker;

                    let couponHtml = '';
                    couponList.forEach(function(couponVO) {
                        let imageSrc = '';
                        if (couponVO.couponGrantDate < couponVO.couponExpiredDate && couponVO.isUsed == 0) {
                            imageSrc = '../resources/images/coupon/coupon.png';
                        } else if (couponVO.isUsed == 1) {
                            imageSrc = '../resources/images/coupon/usedCoupon.png';
                        } else {
                            imageSrc = '../resources/images/coupon/expiredCoupon.png';
                        }
                        couponHtml += '<div class="coupon-item">';
                        couponHtml += '<a href="detail?couponId=' + couponVO.couponId + '">';
                        couponHtml += '<img src="' + imageSrc + '" alt="Coupon" />';
                        couponHtml += '</a>';
                        couponHtml += '</div>';
                    });

                    $(".coupon-grid").html(couponHtml);
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
                paginationHtml += '<li><a href="#" data-page="' + num + '">' + num + '</a></li>';
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

    .coupon-grid {
        display: grid;
        grid-template-columns: repeat(2, 1fr);
        gap: 10px;
        margin-top: 20px;
    }

    .coupon-grid div {
        border: 2px solid #ccc;
        border-radius: 5px;
        padding: 10px;
        text-align: center;
        transition: border-color 0.3s;
    }

    .coupon-grid div:hover {
        border-color: #007bff;
    }

    .coupon-grid div.is-used {
        filter: grayscale(100%);
    }

    .coupon-grid div.is-expired {
        border-color: #ccc;
    }

    .coupon-grid div.is-expired:hover {
        border-color: red;
    }

    .coupon-grid img {
        max-width: 100%;
        height: auto;
        border-radius: 5px;
    }

    ul {
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
    </style>
</head>
<body>

    <h2>${memberVO.memberId}님의 쿠폰함</h2>

    

    <label><input type="radio" name="choice" value="available" checked>사용 가능 쿠폰</label>
    <label><input type="radio" name="choice" value="all">전체</label>
    <label><input type="radio" name="choice" value="expired">만료된 쿠폰</label>
    <label><input type="radio" name="choice" value="used">사용된 쿠폰</label>

    <div class="coupon-grid"></div>

    
    <c:if test="${memberVO.memberRole == 2}">
        <br><button id="btnGrantCoupon">쿠폰 제공</button>
    </c:if>
    <ul class="pagination"></ul>

</body>
</html>
