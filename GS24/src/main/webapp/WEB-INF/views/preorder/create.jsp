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

        /* 모달 안의 특정 테이블에만 스타일 적용 */
        .modal-table {
            width: 100%;
            border-collapse: collapse;
        }

        .modal-table td, .modal-table th {
            padding: 10px;
            text-align: left;
        }

        .modal-table th {
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
    <form action="create" method="POST">
        <table>
            <tr>
                <th>식품 종류</th>
                <td>${foodVO.foodType}</td>
            </tr>
            <tr>
                <th>식품 이름</th>
                <td>${foodVO.foodName}</td>
            </tr>
            <tr>
                <th>식품 가격</th>
                <td>${foodVO.foodPrice}원</td>
            </tr>
            <tr>
                <th>총 재고량</th>
                <td>${foodVO.foodStock}개</td>
            </tr>
            <tr>
                <th>예약 일자</th>
                <td><div id="pickupDateContainer"></div></td>
            </tr>
            <tr>
                <td colspan="2" style="color:gray">※ 오늘을 기준으로 2일 후부터 2주 이내까지 가능합니다.</td>
            </tr>
            <tr>
                <th>예약 수량</th>
                <td><input type="number" name="preorderAmount" id="preorderAmount" value="1" required> 개 <button type="button" id=reset>쿠폰, 기프트카드 초기화</button></td>
            </tr>
            <tr>
                <th>적용된 쿠폰</th>
                <td><span id="selectedCoupon">없음</span> <button type="button" id="openCouponModal">쿠폰 변경</button></td>
            </tr>
            <tr>
                <td colspan="2" style="color:gray">※ 예약 수량을 변경하면 적용된 쿠폰이 초기화됩니다.</td>
            </tr>
            <tr id="giftCardToggle">
                <th>사용할 기프트카드</th>
                <td><span id="selectedGiftCard">없음</span> <button type="button" id="openGiftCardModal">기프트카드 변경</button></td>
            </tr>
            <tr>
                <th>별도 결제 금액</th>
                <td><span id="buyPrice">${foodVO.foodPrice}원</span> <input type="submit" value="예약하기"></td>
            </tr>
            <tr id="latestBalanceToggle" hidden="hidden">
                <th>기프트카드 잔액</th>
                <td><span id="latestBalance"></span></td>
            </tr>
        </table>

        <input type="hidden" name="foodId" value="${foodVO.foodId}">
        <input type="hidden" name="memberId" value="${sessionScope.memberId}">
        <input type="hidden" name="pickupDate" id="pickupDate" required>
        <input type="hidden" name="couponId" id="couponId">
        <input type="hidden" name="giftCardId" id="giftCardId">
        <input type="hidden" name="totalPrice" id="totalPrice" value="${foodVO.foodPrice}">

        <div id="couponModal" class="modal">
            <div class="modal-content">
                <span class="close" id="closeCouponModal">&times;</span>
                <h3>사용 가능한 쿠폰</h3>
                <c:choose>
                    <c:when test="${empty couponList}">
                        <p>사용할 수 있는 쿠폰이 없습니다.</p>
                    </c:when>
                    <c:otherwise>
                        <table class="modal-table">
                            <tbody>
                                <c:forEach var="couponVO" items="${couponList}">
                                    <tr>
                                        <td hidden="hidden">${couponVO.couponId}</td>
                                        <td>
                                            ${couponVO.couponName}<br>
                                            <fmt:formatDate value="${couponVO.couponExpiredDate}" pattern="yyyy-MM-dd HH:mm" />까지 사용<br>
                                            <c:choose>
                                                <c:when test="${couponVO.isDuplicateAllowed == 1}">기프트카드로 잔액 결제 가능</c:when>
                                                <c:otherwise>기프트카드로 잔액 결제 불가</c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>${couponVO.couponAmount}개 남음</td>
                                        <td>
                                            <button class="applyCoupon"
                                                    data-coupon-id="${couponVO.couponId}"
                                                    data-coupon-name="${couponVO.couponName}"
                                                    data-discount-type="${couponVO.discountType}"
                                                    data-discount-value="${couponVO.discountValue}"
                                                    data-coupon-amount="${couponVO.couponAmount}"
                                                    data-is-duplicate-allowed="${couponVO.isDuplicateAllowed}">
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

        <div id="giftCardModal" class="modal">
            <div class="modal-content">
                <span class="close" id="closeGiftCardModal">&times;</span>
                <h3>보유한 기프트카드</h3>
                <c:choose>
                    <c:when test="${empty giftCardList}">
                        <p>보유한 기프트카드가 없습니다.</p>
                    </c:when>
                    <c:otherwise>
                        <table class="modal-table">
                            <tbody>
                                <c:forEach var="giftCardVO" items="${giftCardList}">
                                    <tr>
                                        <td hidden="hidden">${giftCardVO.giftCardId}</td>
                                        <td>
                                            ${giftCardVO.giftCardName}<br>
                                            <fmt:formatDate value="${giftCardVO.giftCardExpiredDate}" pattern="yyyy-MM-dd HH:mm" />까지 사용가능
                                        </td>
                                        <td><span style="color:green">잔액: ${giftCardVO.balance}원</span></td>
                                        <td>
                                            <button class="applyGiftCard"
                                                    data-giftCard-id="${giftCardVO.giftCardId}"
                                                    data-giftCard-name="${giftCardVO.giftCardName}"
                                                    data-balance="${giftCardVO.balance}"
                                                    data-giftCard-expired-date="${giftCardVO.giftCardExpiredDate}">
                                                사용하기
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
    </form>

    <button onclick="location.href='../food/list'">돌아가기</button>

    <script>
        $(document).ready(function() {
            initializeDatePicker();
            initializeCouponModals();
            initializeGiftCardModals();
            updatePrice();

            function initializeDatePicker() {
                $("#pickupDateContainer").datepicker({
                    dateFormat: "yy-mm-dd",
                    changeMonth: true,
                    showMonthAfterYear: true,
                    minDate: +2,
                    maxDate: "+14",
                    onSelect: function(dateText) {
                        $('#pickupDate').val(dateText);
                    }
                }).datepicker("setDate", +2);

                let initialDate = $("#pickupDateContainer").datepicker("getDate");
                $('#pickupDate').val($.datepicker.formatDate("yy-mm-dd", initialDate));
            }

            function initializeGiftCardModals() {
                $("#openGiftCardModal").click(function() {
                    $("#giftCardModal").fadeIn();
                });
                $("#closeGiftCardModal").click(function() {
                    $("#giftCardModal").fadeOut();
                });
                $(window).click(function(event) {
                    if ($(event.target).is("#giftCardModal")) {
                        $("#giftCardModal").fadeOut();
                    }
                });

                let currentlyAppliedGiftCardId = null;
                $(".applyGiftCard").click(function(event) {
                    event.preventDefault();
                    let giftCardId = $(this).attr("data-giftCard-id");
                    let giftCardName = $(this).attr("data-giftCard-name");
                    let balance = $(this).attr("data-balance");
                    let giftCardExpiredDate = $(this).attr("data-giftCard-expired-date");
                    let preorderAmount = $('#preorderAmount').val();  // 예약 수량 가져오기
                    let appliedPrice = $('#totalPrice').val();  // 쿠폰이 적용된 후의 totalPrice 사용
                    let originalPrice = ${foodVO.foodPrice} * preorderAmount; // 쿠폰 적용 전 가격

                    console.log('appliedPrice : '+appliedPrice);
                    console.log('originalPrice : '+originalPrice);

                    if (balance <= 0) {
                        alert('잔액이 없는 기프트카드입니다.');
                        return;
                    }

                    // 기존에 적용된 기프트카드를 초기화
                    if (currentlyAppliedGiftCardId !== null && currentlyAppliedGiftCardId !== giftCardId) {
                        let previousGiftCardButton = $("button").filter(function() {
                            return $(this).attr("data-giftCard-id") === currentlyAppliedGiftCardId;
                        });

                        previousGiftCardButton.text("사용하기");
                        previousGiftCardButton.prop("disabled", false);
                        $('#giftCardId').val(""); // 기존 기프트카드 ID 제거
                        $('#selectedGiftCard').text("없음"); // 선택된 기프트카드 표시 초기화
                        currentlyAppliedGiftCardId = null;
                        $('#buyPrice').text(appliedPrice + '원'); // 초기 가격으로 리셋
                        $('#totalPrice').val(appliedPrice);  // 초기 가격으로 리셋
                    }

                    // 새로운 기프트카드 적용
                    currentlyAppliedGiftCardId = giftCardId;
                    $('#giftCardId').val(giftCardId);
                    $('#selectedGiftCard').text(giftCardName);
                    
                    if ($('#couponId').val() !== "") { // 쿠폰을 이미 선택했다면
                    	if (balance >= appliedPrice) { // 쿠폰, 기프트카드 적용이고 잔고가 더 많다면
                    		$('#totalPrice').val(appliedPrice);
                    		$('#latestBalance').text((balance - appliedPrice) + '원');
                    		$('#latestBalanceToggle').show();
                    	} else { // 잔고보다 결제 금액이 더 많다면
                    		$('#totalPrice').val(balance);
                    		$('#latestBalance').text(0 + '원');
                    		$('#buyPrice').text((appliedPrice - balance) + '원');
                    		$('#latestBalanceToggle').show();
                    	}
                    } else { // 쿠폰을 선택하지 않고 기프트카드만 선택했다면
                    	if (balance >= appliedPrice) { // 기프트카드만이고 잔고가 더 많다면
                    		$('#totalPrice').val(appliedPrice);
                    		$('#latestBalance').text((balance - appliedPrice) + '원');
                    		$('#latestBalanceToggle').show();
                    	} else { // 잔고보다 결제 금액이 더 많다면
                    		$('#totalPrice').val(balance);
                    		$('#latestBalance').text(0 + '원');
                    		$('#buyPrice').text((appliedPrice - balance) + '원');
                    		$('#latestBalanceToggle').show();
                    	}
                    }

                    $(this).text("적용중");
                    $(this).prop("disabled", true);

                    // 모달 닫기
                    $("#giftCardModal").fadeOut();
                });

            }

            function initializeCouponModals() {
                $("#openCouponModal").click(function() {
                    $("#couponModal").fadeIn();
                });

                $("#closeCouponModal").click(function() {
                    $("#couponModal").fadeOut();
                });
                $(window).click(function(event) {
                    if ($(event.target).is("#couponModal")) {
                        $("#couponModal").fadeOut();
                    }
                });

                let currentlyAppliedCouponId = null;

                $(".applyCoupon").click(function(event) {
                    event.preventDefault();
                    let couponId = $(this).data("coupon-id");
                    let couponName = $(this).data("coupon-name");
                    let discountValue = $(this).data("discount-value");
                    let discountType = $(this).data("discount-type");
                    let couponAmount = $(this).data("coupon-amount");
                    let isDuplicateAllowed = $(this).data("is-duplicate-allowed");
                    let currentPrice = ${foodVO.foodPrice} * $('#preorderAmount').val(); // 식품 가격에 예약 수량 곱하기
                    let discountedPrice = calculateDiscount(currentPrice, discountType, discountValue);
                    
                    if (couponAmount <= 0) {
                        alert('모두 소진된 쿠폰입니다.');
                        return;
                    }

                    // 다른 쿠폰이 이미 적용되어 있는 경우 초기화
                    if (currentlyAppliedCouponId !== null && currentlyAppliedCouponId !== couponId) {
                        let previousCouponButton = $("button[data-coupon-id='" + currentlyAppliedCouponId + "']");
                        previousCouponButton.text("적용하기");
                        previousCouponButton.prop("disabled", false);
                        $('#couponId').val(""); // 기존 쿠폰 ID 제거
                        $('#selectedCoupon').text("없음"); // 선택된 쿠폰 표시 초기화
                    }

                    // 새로운 쿠폰 적용
                    currentlyAppliedCouponId = couponId;
                    $('#couponId').val(couponId);
                    $('#selectedCoupon').text(couponName);
                    $('#buyPrice').text(discountedPrice + "원");
                    $('#totalPrice').val(discountedPrice);  // 쿠폰 적용 후 가격을 totalPrice에 설정

                    $(this).text("적용중");
                    $(this).prop("disabled", true);
                    
                    if (!isDuplicateAllowed) {
                        $('#giftCardToggle').hide(); // 기프트카드 행 숨기기
                    } else {
                        $('#giftCardToggle').show(); // 기프트카드 행 보이기
                    }

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
                // 수량 입력 시 동작
                $('#preorderAmount').on('input', function() {
                    updatePriceFunction();
                });

                // reset 버튼 클릭 시 동작
                $('#reset').on('click', function() {
                    updatePriceFunction();
                });

                // 가격 업데이트 기능을 별도의 함수로 분리
                function updatePriceFunction() {
                    let amount = Math.max(1, Math.min(${foodVO.foodStock}, $('#preorderAmount').val()));
                    $('#preorderAmount').val(amount);
                    let originalPrice = ${foodVO.foodPrice} * amount;
                    $('#buyPrice').text(originalPrice + "원");
                    $('#totalPrice').val(originalPrice);

                    // 기프트카드와 쿠폰 초기화
                    currentlyAppliedCouponId = null;
                    currentlyAppliedGiftCardId = null;
                    $('#couponId').val("");
                    $('#giftCardId').val("");
                    $('#selectedCoupon').text("없음");
                    $('#selectedGiftCard').text("없음");
                    $('#giftCardToggle').show();

                    $(".applyCoupon").each(function() {
                        $(this).text("적용하기");
                        $(this).prop("disabled", false);
                    });

                    $(".applyGiftCard").each(function() {
                        $(this).text("사용하기");
                        $(this).prop("disabled", false);
                    });

                    $('#latestBalanceToggle').hide();
                }
            }

        });
    </script>
</body>
</html>
