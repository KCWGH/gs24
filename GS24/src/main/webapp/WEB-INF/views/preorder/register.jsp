<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>편의점 식품 예약</title>

    <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>

    <style>
        /* 모달 기본 스타일 */
        .modal {
            display: none;
            position: fixed;
            z-index: 1;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0, 0, 0, 0.4);
        }

        .modal-content {
            background-color: #fefefe;
            margin: 15% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 50%;
            max-width: 600px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }

        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
        }

        .close:hover,
        .close:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }
    </style>
</head>
<body>
    <h1>식품 예약 페이지</h1>

    <p>식품 종류 : ${foodVO.foodType}</p>
    <p>식품 이름 : ${foodVO.foodName}</p>
    <p>식품 가격 : ${foodVO.foodPrice}원</p>
    <p>총 재고량 : ${foodVO.foodStock}개</p>
    <br>

    <form action="register" method="POST">
        <input type="hidden" name="foodId" value="${foodVO.foodId}">
        <input type="hidden" name="memberId" value="${sessionScope.memberId}">
        <span style="color:gray">예약일로부터 2일 후부터 2주 후 이내로 예약이 가능합니다.</span><br>
        <div id="pickupDateContainer"></div>
        <input type="hidden" name="pickupDate" id="pickupDate" required><br>

		<span id=selectedEarlyBirdCoupon>적용된 선착순 쿠폰 : 없음 </span>
        <button type="button" id="openModal">선착순 쿠폰 변경</button><br>

        <!-- 모달 구조 -->
        <div id="couponModal" class="modal">
            <div class="modal-content">
                <span class="close" id="closeModal">&times;</span>
                <h3>사용 가능한 쿠폰</h3>
                <table>
                    <tbody>
                        <c:forEach var="earlyBirdCouponVO" items="${couponList}">
                            <tr>
                                <td hidden="hidden">${earlyBirdCouponVO.earlyBirdCouponId}</td>
                                <td>
                                    ${earlyBirdCouponVO.earlyBirdCouponName} 
                                    ${earlyBirdCouponVO.earlyBirdCouponAmount}개<br>
                                    ${earlyBirdCouponVO.earlyBirdCouponExpiredDate}까지<br>
                                    <c:choose>
                                        <c:when test="${earlyBirdCouponVO.isDuplicateAllowed == 1}">
                                            중복 사용 가능
                                        </c:when>
                                        <c:otherwise>
                                            중복 사용 불가
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <button 
                                        class="applyEarlyBirdCoupon" 
                                        data-coupon-id="${earlyBirdCouponVO.earlyBirdCouponId}" 
                                        data-coupon-name="${earlyBirdCouponVO.earlyBirdCouponName}" 
                                        data-coupon-amount="${earlyBirdCouponVO.earlyBirdCouponAmount}"
                                        data-expired-date="${earlyBirdCouponVO.earlyBirdCouponExpiredDate}"
                                        data-discount-type="${earlyBirdCouponVO.discountType}"
                                        data-discount-value="${earlyBirdCouponVO.discountValue}">
                                        적용하기
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <span>예약 개수 : </span>
        <input type="number" name="preorderAmount" id="preorderAmount" value="1" required>개<br>
        <span id="buyPrice"></span> <input type="submit" value="예약하기">
    </form><br>
    
    <button onclick="location.href='../food/list'">돌아가기</button>

    <script>
        $(document).ready(function() {
            $("#pickupDateContainer").datepicker({
        		dateFormat: "yy-mm-dd",
        		changeMonth: true,
        		minDate: +2,
        		maxDate: "+14",
        		inline: true, // 달력을 항상 화면에 표시
        		onSelect: function(dateText) {
            $('#pickupDate').val(dateText);
            console.log(dateText);
        }
    });

            // 쿠폰 적용 버튼 클릭 이벤트
            $(".applyEarlyBirdCoupon").click(function(event) {
                event.preventDefault();
                let discountType = $(this).data("discount-type");
                let discountValue = $(this).data("discount-value");
                let couponName = $(this).data("coupon-name");
                let buyPrice = parseInt($('#buyPrice').text().replace(/[^0-9]/g, ''), 10);
                let discountedPrice;
                
                if (discountType === 'A') {
                    discountedPrice = buyPrice - discountValue;
                } else if (discountType === 'P') {
                    discountedPrice = buyPrice - (buyPrice * discountValue / 100);
                }
                
                $('#selectedEarlyBirdCoupon').html("적용된 선착순 쿠폰 : " + couponName + " ");
                $('#buyPrice').html("결제 금액 : " + discountedPrice + "원");
                $("#couponModal").fadeOut();
            });

            checkStockAndUpdatePrice();
            setupDateLimits();

            // 모달 열기 및 닫기
            $("#openModal").click(function() {
            	$('#buyPrice').html("결제 금액 : " + ${foodVO.foodPrice} * $('#preorderAmount').val() + "원");
                $("#couponModal").fadeIn();
            });

            $("#closeModal").click(function() {
                $("#couponModal").fadeOut();
            });

            $(window).click(function(event) {
                if ($(event.target).is("#couponModal")) {
                    $("#couponModal").fadeOut();
                }
            });

            // 예약 개수와 금액 확인
            function checkStockAndUpdatePrice() {
                var foodPrice = ${foodVO.foodPrice};
                var maxStock = ${foodVO.foodStock};
                var buyAmount = $('#preorderAmount');

                $('#buyPrice').html("결제 금액 : " + foodPrice * buyAmount.val() + "원");

                $('#preorderAmount').change(function() {
                    var currentAmount = parseInt(buyAmount.val());

                    if (currentAmount < 1) {
                        currentAmount = 1;
                    } else if (currentAmount > maxStock) {
                        currentAmount = maxStock;
                    }

                    buyAmount.val(currentAmount);
                    $('#buyPrice').html("결제 금액 : " + foodPrice * currentAmount + "원");
                });
            }

            // 현재 날짜로 제한 설정
            function setupDateLimits() {
                var today = new Date();
                var yyyy = today.getFullYear();
                var mm = today.getMonth() + 1;
                var dd = today.getDate();

                if (mm < 10) mm = '0' + mm;
                if (dd < 10) dd = '0' + dd;

                var todayString = yyyy + '-' + mm + '-' + dd;
                $('#pickupDate').attr('min', todayString);
            }
        });
    </script>
</body>
</html>
