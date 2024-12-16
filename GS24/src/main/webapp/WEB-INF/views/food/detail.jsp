<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>식품 상세 정보</title>
</head>
<body>
	<!-- 상품 이미지도 같이 넣어줘야 한다. -->
	<p>식품 유형 : ${FoodVO.foodType }</p>
	<p>식품 이름 : ${FoodVO.foodName }</p>
	<p>재고량 : ${FoodVO.foodStock }개</p>
	<p>가격 : ${FoodVO.foodPrice }원</p>
	<p>단백질 함유량 : ${FoodVO.foodProtein }</p>
	<p>지방 함유량 : ${FoodVO.foodFat }</p>
	<p>탄수호물 함유량 : ${FoodVO.foodCarb }</p>
	<p>입고 날짜 : ${FoodVO.registeredDate }</p>
	
	<button onclick="location.href='list'">돌아가기</button>
	
	<div style="text-align: center">
		<div id="reply"></div>	
	</div>
	
	<script type="text/javascript">
			$(document).reay(function(){
				function getReplies(){
					var foodId = ${FoodVO.foodId};
					var url = '../food/detail/' + foodId;
					
					
				}
			});
	</script>
</body>
</html>