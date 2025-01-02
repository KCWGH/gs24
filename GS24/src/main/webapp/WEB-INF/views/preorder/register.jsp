<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.7.1.js">
</script>
<title>편의점 식품 예약</title>
</head>
<body>
	<h1>식품 예약 페이지</h1>
	<%	
	 //<p>${foodVO.imgFoodVO.imgFoodPath }</p>
	%>
	<p>식품 이름 : ${foodVO.foodName }</p>
	<p>식품 가격 : ${foodVO.foodPrice }원</p>
	<p>총 재고량 : ${foodVO.foodStock }개</p>
	<br>
	<form action="register" method="POST">
		<input type="hidden" name="foodId" value="${foodVO.foodId}">
		<input type="hidden" name="memberId" value="${sessionScope.memberId}">
		<p>예약 일</p>
		<input type="date" name="pickupDate" id="pickupDate" required="required" min=""><br>
		<p>예약 개수</p>
		<input type="number" name="preorderAmount" id="preorderAmount" value="1" required="required">	
		<input type="submit" value="예약하기">
	</form>
	<br>
	<p id="buyPrice"></p>
	
	<button onclick="location.href='../food/list'">돌아가기</button>
	
	<script type="text/javascript">
		$(document).ready(function() {
			
			checkStrockAndNowPrice();
			isNowDate();
			
			function checkStrockAndNowPrice(){
				let foodPrice = ${foodVO.foodPrice};
				let minStock = 1;
				let maxStock = ${foodVO.foodStock};
				let buyAmount = $('#preorderAmount');			
			
				$('#buyPrice').html("예상 금액 : " + foodPrice * buyAmount.val() + "원");
				
				$('#preorderAmount').change(function() {
					let currentAmount = parseInt(buyAmount.val());	
					
					if (currentAmount < minStock) {
						console.log("is minStock.");
						buyAmount.val(minStock);
						currentAmount = minStock;
					} else if (currentAmount > maxStock) {
						console.log("is maxStock.");
						buyAmount.val(maxStock);
						currentAmount = maxStock;
					}
	
					// 예상 금액 표시
					$('#buyPrice').html("예상 금액 : " + foodPrice * currentAmount + "원");
				});
			}
			
			function isNowDate(){
				
				let today = new Date();
				let yyyy = today.getFullYear();
				let mm = today.getMonth() + 1;
				let dd = today.getDate();

				if (mm < 10) mm = '0' + mm;
				if (dd < 10) dd = '0' + dd;

			    var todayString = yyyy + '-' + mm + '-' + dd;

			    $('#pickupDate').attr('min', todayString);
			}
		});
	</script>
</body>
</html>
