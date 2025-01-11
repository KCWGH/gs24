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
        .modal {
            display: none;
            position: fixed;
            z-index: 1;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.4);
        }
        .modal-content {
            background-color: #fefefe;
            margin: 15% auto;
            padding: 20px;
            width: 50%;
            max-width: 600px;
            max-height: 80%;
            overflow-y: auto;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        table td, table th {
            padding: 10px;
            text-align: left;
        }
        table th {
            background-color: #f2f2f2;
        }
        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            cursor: pointer;
        }
        .close:hover {
            color: black;
        }
    </style>
</head>
<body>
    <h1>식품 예약 페이지</h1>
    <p>식품 종류: ${foodVO.foodType}</p>
    <p>식품 이름: ${foodVO.foodName}</p>
    <p>식품 가격: ${foodVO.foodPrice}원</p>
    <p>총 재고량: ${foodVO.foodStock}개</p>

    <form action="create" method="POST">
        <input type="hidden" name="foodId" value="${foodVO.foodId}">
        <input type="hidden" name="memberId" value="${sessionScope.memberId}">
        <div id="pickupDateContainer"></div>
        <input type="hidden" name="pickupDate" id="pickupDate" required>
        <input type="hidden" name="couponId" id="couponId">

        <label>예약 수량:
            <input type="number" name="preorderAmount" id="preorderAmount" value="1" required> 개
        </label>
        <br>
        <span id="selectedCoupon">적용된 쿠폰: 없음</span>
        <button type="button" id="openModal">쿠폰 변경</button><br>
        <span style="color:gray">※ 예약 수량을 변경하면 적용된 쿠폰이 초기화됩니다.</span><br>
        <div id="selectedGiftCard" hidden="hidden">
            <span>기프트카드 사용: 없음</span>
            <button type="button" id="selectGiftCard">기프트카드 변경</button>
        </div>

        <div id="couponModal" class="modal">
            <div class="modal-content">
                <span class="close" id="closeModal">&times;</span>
                <h3>사용 가능한 쿠폰</h3>
                <c:choose>
                    <c:when test="${empty couponList}">
                        <p>사용할 수 있는 쿠폰이 없습니다.</p>
                    </c:when>
                    <c:otherwise>
                        <table>
                            <tbody>
                                <c:forEach var="couponVO" items="${couponList}">
                                    <tr>
                                        <td hidden="hidden">${couponVO.couponId}</td>
                                        <td>
                                            ${couponVO.couponName}<br>
                                            <fmt:formatDate value="${couponVO.couponExpiredDate}" pattern="yyyy-MM-dd HH:mm" />까지 사용<br>
                                            <c:choose>
                                                <c:when test="${couponVO.isDuplicateAllowed == 1}">기프트카드 중복 사용 가능</c:when>
                                                <c:otherwise>기프트카드 중복 사용 불가</c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>${couponVO.couponAmount}개 남음</td>
                                        <td>
                                            <button class="applyCoupon"
                                                    data-coupon-id="${couponVO.couponId}"
                                                    data-coupon-name="${couponVO.couponName}"
                                                    data-discount-type="${couponVO.discountType}"
                                                    data-discount-value="${couponVO.discountValue}"
                                                    data-coupon-amount="${couponVO.couponAmount}">
                                                적용하기
                                            </button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <br>
        <span id="buyPrice">결제 금액: ${foodVO.foodPrice}원</span>
        <input type="submit" value="예약하기">
    </form>

    <button onclick="location.href='../food/list'">돌아가기</button>

    <script>
        $(document).ready(function() {
            initializeDatePicker();
            initializeCouponModal();
            updatePrice();

            function initializeDatePicker() {
                $("#pickupDateContainer").datepicker({
                    dateFormat: "yy-mm-dd",
                    changeMonth: true,
                    minDate: +2,
                    maxDate: "+14",
                    onSelect: function(dateText) {
                        $('#pickupDate').val(dateText);
                    }
                }).datepicker("setDate", +2);

                const initialDate = $("#pickupDateContainer").datepicker("getDate");
                $('#pickupDate').val($.datepicker.formatDate("yy-mm-dd", initialDate));
            }

            function initializeCouponModal() {
                $("#openModal").click(function() {
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

                $(".applyCoupon").click(function(event) {
                    event.preventDefault();
                    const couponId = $(this).data("coupon-id");
                    const couponName = $(this).data("coupon-name");
                    const discountValue = $(this).data("discount-value");
                    const discountType = $(this).data("discount-type");
                    const couponAmount = $(this).data("coupon-amount");
                    const currentPrice = ${foodVO.foodPrice} * $('#preorderAmount').val();
                    const discountedPrice = calculateDiscount(currentPrice, discountType, discountValue);

                    if (couponAmount <= 0) {
                        alert('모두 소진된 쿠폰입니다.');
                        return;
                    }

                    $('#couponId').val(couponId);
                    $('#selectedCoupon').text("적용된 쿠폰: " + couponName);
                    $('#buyPrice').text("결제 금액: " + discountedPrice + "원");
                    $("#couponModal").fadeOut();
                });
            }

            function calculateDiscount(price, type, value) {
                if (type === 'A') {
                    return Math.max(0, price - value);
                } else if (type === 'P') {
                    return Math.max(0, price - (price * value / 100));
                }
                return price;
            }

            function updatePrice() {
                $('#preorderAmount').on('input', function() {
                	$('#selectedCoupon').text("적용된 쿠폰: 없음");
                    const amount = Math.max(1, Math.min(${foodVO.foodStock}, $(this).val()));
                    $(this).val(amount);
                    const totalPrice = ${foodVO.foodPrice} * amount;
                    $('#buyPrice').text("결제 금액: " + totalPrice + "원");
                });
            }
        });
    </script>
</body>
</html>
