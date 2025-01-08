<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>선착순 쿠폰 발행</title>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<script type="text/javascript">
	function setAmount(value) {
		$("input[name='earlyBirdCouponAmount']").val(value);
	}
	function setDiscountValue(value) {
		  $("input[name='discountValue']").val(value);
		}
	function setExpirationDate(days) {
		  let currentDate = new Date();
		  currentDate.setDate(currentDate.getDate() + days);
		  let year = currentDate.getFullYear();
		  let month = String(currentDate.getMonth() + 1).padStart(2, '0');
		  let day = String(currentDate.getDate()).padStart(2, '0');
		  let hours = String(currentDate.getHours()).padStart(2, '0');
		  let minutes = String(currentDate.getMinutes()).padStart(2, '0');
		  let formattedDate = year + '-' + month + '-' + day + 'T' + hours + ':' + minutes;
		  if (!isNaN(currentDate.getTime())) {
		    $('#earlyBirdCouponExpiredDate').val(formattedDate);
		  } else {
		    console.error('잘못된 날짜입니다.');
		  }
		}
	$(document).ready(function() {
		$("input[name='discountType']").change(function() {
		    const selectedChoice = $("input[name='discountType']:checked").val();
		    let discountFormHTML = '';
		    if (selectedChoice == 'P') {
		      discountFormHTML += '<div><label for="discountValue">할인율: </label>';
		      discountFormHTML += '<input type="number" name="discountValue" required> %<br>';
		      discountFormHTML += '<button type="button" onclick="setDiscountValue(3)">3%</button>';
		      discountFormHTML += '<button type="button" onclick="setDiscountValue(5)">5%</button>';
		      discountFormHTML += '<button type="button" onclick="setDiscountValue(10)">10%</button>';
		      discountFormHTML += '<button type="button" onclick="setDiscountValue(25)">25%</button>';
		      discountFormHTML += '<button type="button" onclick="setDiscountValue(50)">50%</button></div>';
		    } else if (selectedChoice == 'A') {
		      discountFormHTML += '<div><label for="discountValue">할인액: </label>';
		      discountFormHTML += '<input type="number" name="discountValue" required> 원<br>';
		      discountFormHTML += '<button type="button" onclick="setDiscountValue(1000)">1000원</button>';
		      discountFormHTML += '<button type="button" onclick="setDiscountValue(3000)">3000원</button>';
		      discountFormHTML += '<button type="button" onclick="setDiscountValue(5000)">5000원</button>';
		      discountFormHTML += '<button type="button" onclick="setDiscountValue(10000)">10000원</button></div>';
		    }
		    $('#discountForm').html(discountFormHTML);
		  });
	});
</script>
</head>
<body>

	<form action="publish" method="POST">
	<div>
    <label for="couponName">쿠폰 이름: </label>
    <input type="text" id="earlyBirdCouponName" name="earlyBirdCouponName"><br>
    <span style="color:gray">쿠폰 이름을 적지 않으면 기본 쿠폰 이름으로 제공됩니다.</span><br>
    <span>예) 음료 1000원 할인권</span>
  </div>
  <br>
	<div>
	
		<label for="foodType">음식 종류: </label> <select id="foodType" name="foodType" required>
			<option value="" selected disabled>선택하세요</option>
			<c:forEach var="food" items="${foodTypeList}">
				<option value="${food}">${food}</option>
			</c:forEach>
		</select><br><span style="color:gray">현재 존재하는 음식 종류만 표시됩니다.</span>
	</div>
	<br>
	<div>
    <label for="discountType">쿠폰 종류: </label>
    <label><input type="radio" name="discountType" value="P" required> 비율권</label>
    <label><input type="radio" name="discountType" value="A"> 정액권</label>
  </div>
  <div id="discountForm"></div>
	<br>
	<div>
		<label for="earlyBirdCouponAmount">수량: </label> <input type="number" name="earlyBirdCouponAmount" required> 매<br>
		<button type="button" onclick="setAmount(5)">5매</button>
		<button type="button" onclick="setAmount(10)">10매</button>
		<button type="button" onclick="setAmount(50)">50매</button>
		<button type="button" onclick="setAmount(100)">100매</button>
		<button type="button" onclick="setAmount(500)">500매</button>
	</div>
	<br>
	<div>
    <label for="earlyBirdCouponExpiredDate">유효기간: </label>
    <input type="datetime-local" id="earlyBirdCouponExpiredDate" name="earlyBirdCouponExpiredDate" ><br>
    
  </div>
  <div>
    <button type="button" onclick="setExpirationDate(1)">하루</button>
    <button type="button" onclick="setExpirationDate(3)">3일</button>
    <button type="button" onclick="setExpirationDate(7)">1주일</button>
    <button type="button" onclick="setExpirationDate(30)">1개월</button>
    <button type="button" onclick="setExpirationDate(365)">1년</button><br>
    <span style="color:gray">유효기간을 지정하지 않으면 쿠폰이 다 소진될 때까지 제공됩니다.</span>
  </div>
  <br>
  <div>
    <label for="isDuplicateAllowed">개별 쿠폰과 중복 사용 가능 여부: </label>
    <label><input type="radio" name="isDuplicateAllowed" value="1" required> 예</label>
    <label><input type="radio" name="isDuplicateAllowed" value="0"> 아니오</label>
  </div>
  <br>
	<button type="submit" id="btnPublish" >쿠폰 발행</button></form>
</body>
</html>