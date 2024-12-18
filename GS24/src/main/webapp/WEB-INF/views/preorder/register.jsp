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
	
	<p>${ImgPath }</p>
	<p>식품 이름 : ${foodVO.foodName }</p>
	<p>식품 가격 : ${foodVO.foodPrice }원</p>
	<p>총 재고량 : ${foodVO.foodStock }개</p>
	<br>
	<p>예약 개수</p>
	<input type="number" id="buyAmount" value="1" required="required" min="1" max="${foodVO.foodStock}">
	<br>
	<p id="buyPrice"></p>
	
	<script type="text/javascript">
		$(document).ready(function() {
			let foodPrice = ${foodVO.foodPrice};
			let minStock = 1;
			let maxStock = ${foodVO.foodStock};
			let buyAmount = $('#buyAmount');
			
			// 초기 예상 금액 설정
			$('#buyPrice').html("예상 금액 : " + foodPrice * buyAmount.val() + "원");
			
			// input 값 변경 시
			$('#buyAmount').on('input', function() {
				let currentAmount = parseInt(buyAmount.val());  // 현재 입력된 값

				// 최소값과 최대값을 체크
				if (currentAmount < minStock) {
					console.log("is minStock.");
					buyAmount.val(minStock);  // 최소값 1로 설정
					currentAmount = minStock;
				} else if (currentAmount > maxStock) {
					console.log("is maxStock.");
					buyAmount.val(maxStock);  // 재고량으로 설정
					currentAmount = maxStock;
				}

				// 예상 금액 업데이트
				$('#buyPrice').html("예상 금액 : " + foodPrice * currentAmount + "원");
			});
		});
	</script>
</body>
</html>
