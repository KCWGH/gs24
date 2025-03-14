<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>식품 예약</title>
    <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
    <link rel="stylesheet" href="../resources/css/fonts.css">
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
    <script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/i18n/datepicker-ko.js"></script>
    
    <style>
    	body {
    		padding-top: 0px;
    		margin-top: 0px;
			font-family: 'Pretendard-Regular', sans-serif;
			padding: 5px;
			font-size: 16px;
			background-color: #f4f4f4;
		}
		
		.card {
			font-family: 'Pretendard-Regular', sans-serif;
    		max-width: 100%;
    		padding: 10px;
    		background: white;
    		border-radius: 10px;
    		box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1);
    		text-align: center;
        }

		h1 {
			color: #333;
			text-align: center;
		}
    	
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
        	font-size: 14px;
            background-color: #fefefe;
            margin: 15% auto;
            padding: 20px;
            width: 80%;
            max-width: 600px;
            max-height: 80%;
            overflow-y: auto;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            border-radius: 10px;
        }

        .modal-table {
            width: 100%;
            border-collapse: collapse;
        }

        .modal-table td, .modal-table th {
            padding: 10px;
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
        
        #pickupDateContainer {
    		justify-self: center;
		}
		
		.main th {
    		width: 23%;
    		padding: 10px;
		}
		
		.main td {
    		text-align: center;
    		vertical-align: middle;
		}

        button {
        	font-family: 'Pretendard-Regular', sans-serif;
    		padding: 7px 10px;
    		border: none;
    		background: #ddd;
    		border-radius: 5px;
    		cursor: pointer;
    		margin: 5px;
        }
        
        button:hover {
    		background: #bbb;
		}
        
        input[type="number"] {
        	font-family: 'Pretendard-Regular', sans-serif;
    		width: 15%;
    		padding: 7px;
    		border: 1px solid #ddd;
    		border-radius: 5px;
    		text-align: center;
        }
        
        input[type="submit"] {
        	font-family: 'Pretendard-Regular', sans-serif;
    		padding: 10px 20px;
    		border: transparent;
    		border-radius: 5px;
    		text-align: center;
    		background: #4CAF50;
    		color: white;
    		cursor: pointer;
        }
        
        input[type="submit"]:hover {
        	background: #388E3C;
        }
        
        #selectCoupon {
        	color: white;
        	background: #4CAF50;
        }
        
        #selectCoupon:hover {
        	background: #388E3C;
        }
        
        #doNotApplyCoupon {
        	color: white;
        	background: #ff6666;
        }
        
        #doNotApplyCoupon:hover {
			background: #ff4d4d;
        }
        
        #buyPrice, #latestBalance {
        	font-size: 20px;
        }
        
        #return {
   			display: block;
    		margin-left: auto;
    		margin-botton: 0px;
		}
		
		#reset {
			color: white;
        	background: #ff6666;
			padding: 10px 5px;
		}
		
		#reset:hover {
			background: #ff4d4d;
		}
    </style>
</head>
<body>
<c:if test="${not empty message}">
    <script type="text/javascript">
        alert("${message}");
        if (window.opener && window.opener.location.href.includes("/convenienceFood/detail")) {
            window.opener.location.reload();
        }
    </script>
</c:if>
    <h1>${foodVO.foodName}</h1>
    <div class="card">
    <form action="create" method="POST">
        <table class="main">
        	<tr>
                <th>수령 장소</th>
                <td>${address}</td>
            </tr>
            <tr>
                <th>식품 종류</th>
                <td>${foodVO.foodType}</td>
            </tr>
            <tr>
                <th>재고 수량</th>
                <td>${foodVO.foodAmount}개</td>
            </tr>
            <tr>
                <th>식품 가격</th>
                <td>${foodVO.foodPrice}원</td>
            </tr>
            <tr>
                <th>예약 일자</th>
                <td><div id="pickupDateContainer"></div>
                <span style="color:gray">※ 2일 후부터 2주 이내까지 선택 가능합니다.</span></td>
            </tr>
            <tr>
                <th>예약 수량</th>
                <td><input type="number" name="preorderAmount" id="preorderAmount" value="1" required> 개<br>
                <span style="color:gray">※ 변경시 적용된 쿠폰과 기프트카드가 초기화됩니다.</span></td>
            </tr>
            <tr>
                <th>적용된<br>쿠폰</th>
                <td><button type="button" id="openCouponModal">쿠폰 변경</button> <span id="selectedCoupon">없음</span> <button type="button" id="doNotApplyCoupon" >쿠폰 사용하지 않기</button><button type="button" id="selectCoupon" hidden="hidden">선택하기</button></td>
            </tr>
            <tr id="giftCardToggle" hidden="hidden">
                <th>사용할<br>기프트카드</th>
                <td><span id="selectedGiftCard">없음</span> <button type="button" id="openGiftCardModal">기프트카드 변경</button></td>
            </tr>
            <tr id="latestBalanceToggle" hidden="hidden">
                <th>기프트카드<br>잔액</th>
                <td><strong id="latestBalance"></strong> 원</td>
            </tr>
            <tr id="priceToggle" hidden="hidden">
                <th>별도<br>결제금액</th>
                <td><strong id="buyPrice">${foodVO.foodPrice}</strong> 원</td>
            </tr>
            <tr>
            	<th></th>
            	<td><input type="submit" id="createPreorder" value="예약하기" hidden="hidden" disabled><button type="button" id=reset hidden="hidden">쿠폰, 기프트카드 초기화</button></td>
            </tr>
        </table>
        <input type="hidden" name="convenienceId" id="convenienceId">
        <input type="hidden" name="foodId" id="foodId">
        <input type="hidden" name="memberId" id="memberId">
        <input type="hidden" name="pickupDate" id="pickupDate" required>
        <input type="hidden" name="couponId" id="couponId">
        <input type="hidden" name="giftCardId" id="giftCardId">
        <input type="hidden" name="totalPrice" id="totalPrice">
		<input type="hidden" name="refundVal" id="refundVal">
		
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
    										<c:choose>
        										<c:when test="${couponVO.couponAmount == 0}">
            										<span style="color: red;">적용불가</span>
        										</c:when>
        										<c:otherwise>
            										<button class="applyCoupon"
                    										data-coupon-id="${couponVO.couponId}"
                    										data-coupon-name="${couponVO.couponName}"
                    										data-discount-type="${couponVO.discountType}"
                    										data-percentage="${couponVO.percentage}"
                    										data-amount="${couponVO.amount}"
                    										data-coupon-amount="${couponVO.couponAmount}"
                    										data-is-duplicate-allowed="${couponVO.isDuplicateAllowed}">
                										적용하기
            										</button>
        										</c:otherwise>
    										</c:choose>
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
                                            <strong>${giftCardVO.giftCardName}</strong><br>
                                            <fmt:formatDate value="${giftCardVO.giftCardExpiredDate}" pattern="yyyy-MM-dd HH:mm"/><br>까지 사용가능
                                        </td>
                                        <td><span style="color:green">잔액<br>${giftCardVO.balance}원</span></td>
										<td>
    										<c:choose>
        										<c:when test="${giftCardVO.balance == 0}">
            										<span style="color: red;">사용불가</span>
        										</c:when>
        										<c:otherwise>
            										<button class="applyGiftCard"
                    										data-giftCard-id="${giftCardVO.giftCardId}"
                    										data-giftCard-name="${giftCardVO.giftCardName}"
                    										data-balance="${giftCardVO.balance}"
                    										data-giftCard-expired-date="${giftCardVO.giftCardExpiredDate}">
                										사용하기
            										</button>
        										</c:otherwise>
    										</c:choose>
										</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
    </form>
	</div>
	<button id="return" onclick='location.href="../member/myhistory"'>내 찜 목록</button>
    <script>
        $(document).ready(function() {
        	
        	$('#createPreorder').click(function(e) {
        	    e.preventDefault();
        	    
        	    let refundVal = $('#refundVal').val();
        	    console.log(refundVal);
        	    if (!refundVal) {
        	    	$('#refundVal').val(0);
        	    }
        	    $(this).closest('form').submit();
        	});

        	
        	let convenienceId = '${foodVO.convenienceId}';
        	let foodId = '${foodVO.foodId}';
        	let memberId = '${memberId}';
        	let totalPrice = '${foodVO.foodPrice}';
        	
        	$('#convenienceId').val(convenienceId);
        	$('#foodId').val(foodId);
        	$('#memberId').val(memberId);
        	$('#totalPrice').val(totalPrice);
        	
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
                    let balance = parseInt($(this).attr("data-balance"));
                    let giftCardExpiredDate = $(this).attr("data-giftCard-expired-date");
                    let preorderAmount = $('#preorderAmount').val();  // 예약 수량 가져오기
                    let appliedPrice = parseInt($('#totalPrice').val());  // 쿠폰이 적용된 후의 totalPrice 사용
                    let originalPrice = parseInt(${foodVO.foodPrice} * preorderAmount); // 쿠폰 적용 전 가격
                    let couponId = parseInt($('#couponId').val(), 10); // couponId 값 가져오기

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
                        $('#buyPrice').text(appliedPrice); // 초기 가격으로 리셋
                        $('#totalPrice').val(appliedPrice);  // 초기 가격으로 리셋
                    }

                    currentlyAppliedGiftCardId = giftCardId;
                    $('#giftCardId').val(giftCardId);
                    $('#selectedGiftCard').text(giftCardName);
                    
                    if (!isNaN(couponId)) { // 쿠폰을 이미 선택했다면
                    	if (balance >= appliedPrice) { // 쿠폰, 기프트카드 적용이고 잔고가 더 많다면
                    		$('#totalPrice').val(appliedPrice);
                    		$('#buyPrice').text(0);
                    		$('#latestBalance').text(balance - appliedPrice);
                    		$('#latestBalanceToggle').show();
                    		$('#refundVal').val(appliedPrice);
                    		$('#totalPrice').val(0);
                    	} else { // 잔고보다 결제 금액이 더 많다면
                    		$('#totalPrice').val(balance);
                    		$('#latestBalance').text(0);
                    		$('#buyPrice').text(appliedPrice - balance);
                    		$('#latestBalanceToggle').show();
                    		$('#refundVal').val(balance);
                    		$('#totalPrice').val(appliedPrice - balance);
                    	}
                    } else { // 쿠폰을 선택하지 않고 기프트카드만 선택했다면
                    	if (balance >= originalPrice) { // 기프트카드만이고 잔고가 가격보다 더 많다면
                    		$('#totalPrice').val(originalPrice);
                    		$('#buyPrice').text(0);
                    		$('#latestBalance').text(balance - originalPrice);
                    		$('#latestBalanceToggle').show();
                    		$('#totalPrice').val(0);
                    		$('#refundVal').val(originalPrice);
                    	} else { // 잔고보다 결제 금액이 더 많다면
                    		$('#totalPrice').val(balance);
                    		$('#latestBalance').text(0);
                    		$('#buyPrice').text(originalPrice - balance);
                    		$('#latestBalanceToggle').show();
                    		$('#totalPrice').val(originalPrice - balance);
                    		$('#refundVal').val(balance);
                    	}
                    }

                    $(this).text("적용중");
                    $(this).prop("disabled", true);

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

                let currentlyAppliedGiftCardId = null;
                let currentlyAppliedCouponId = null;

                $(".applyCoupon").click(function(event) {
                    event.preventDefault();
                    let couponId = $(this).data("coupon-id");
                    let couponName = $(this).data("coupon-name");
                    let discountType = $(this).data("discount-type");
                    let discountValue;
                    if (discountType == 'A') {
                    	discountValue = $(this).data("amount");
                    } else if (discountType == 'P') {
                    	discountValue = $(this).data("percentage");
                    }
                    let couponAmount = $(this).data("coupon-amount");
                    let isDuplicateAllowed = $(this).data("is-duplicate-allowed");
                    let currentPrice = ${foodVO.foodPrice} * $('#preorderAmount').val(); // 식품 가격에 예약 수량 곱하기
                    let discountedPrice = calculateDiscount(currentPrice, discountType, discountValue);
                    
                    if (couponAmount <= 0) {
                        alert('모두 소진된 쿠폰입니다.');
                        return;
                    }
                    
                    let giftCardId = $(".applyGiftCard").val();
                    if (isDuplicateAllowed == 0) { // 쿠폰이 기프트카드와 중복 불가라면
                    	 $('#giftCardId').val(""); // 기존 기프트카드 ID 제거
                         $('#selectedGiftCard').text("없음"); // 선택된 기프트카드 표시 초기화
                         currentlyAppliedGiftCardId = null;
                         $('#buyPrice').text(currentPrice); // 초기 가격으로 리셋
                         $('#totalPrice').val(0);  // 초기 가격으로 리셋
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
                    $('#buyPrice').text(discountedPrice);
                    $('#totalPrice').val(discountedPrice);  // 쿠폰 적용 후 가격을 totalPrice에 설정

                    $(this).text("적용중");
                    $(this).prop("disabled", true);
                    
                    $('#selectCoupon').show();
                    $('#doNotApplyCoupon').hide();
                    $('#priceToggle').show();
                    
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
                    let amount = Math.max(1, Math.min(${foodVO.foodAmount}, $('#preorderAmount').val()));
                    $('#preorderAmount').val(amount);
                    let originalPrice = ${foodVO.foodPrice} * amount;
                    $('#buyPrice').text(originalPrice);
                    $('#totalPrice').val(originalPrice);

                    // 기프트카드와 쿠폰 초기화
                    currentlyAppliedCouponId = null;
                    currentlyAppliedGiftCardId = null;
                    $('#couponId').val("");
                    $('#giftCardId').val("");
                    $('#selectedCoupon').text("없음");
                    $('#selectedGiftCard').text("없음");
                    $('#giftCardToggle').hide();
                    $('#openCouponModal').show();
                    $('#createPreorder').prop("disabled", true);
                    $('#doNotApplyCoupon').show();
                    $('#priceToggle').hide();
                    $('#createPreorder').hide();
                    $('#reset').hide();
                    $('#selectCoupon').hide();

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
            
            $('#doNotApplyCoupon').on('click', function(event) {
            	$(this).hide();
            	$('#openCouponModal').hide();
            	$('#priceToggle').show();
            	$('#createPreorder').show();
            	$('#reset').show();
            	$('#giftCardToggle').show();
            	$('#createPreorder').prop("disabled", false);
            });
            
            $('#selectCoupon').on('click', function(event) {
            	let selectedCouponButton = $(".applyCoupon[data-coupon-id='" + $('#couponId').val() + "']");
                let isDuplicateAllowed = selectedCouponButton.data("is-duplicate-allowed");
            	console.log(isDuplicateAllowed)
            	if (isDuplicateAllowed == 0) { // 중복 불가능이면
                    $('#giftCardToggle').hide(); // 기프트카드 행 숨기기
                    
                } else {
                    $('#giftCardToggle').show(); // 기프트카드 행 보이기
                }
                $('#openCouponModal').hide();
                $(this).hide();
                $('#createPreorder').prop("disabled", false);
                $('#createPreorder').show();
                $('#reset').show();
            });
        });
    </script>
</body>
</html>
