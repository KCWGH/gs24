<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<title>식품 리스트</title>
<style>
ul {
	list-style-type: none;
}

li {
	display: inline-block;
}
</style>
</head>
<script type="text/javascript">
	$("#myButton").click(function() {
		window.open("https://www.example.com", "_blank");
	});
</script>
<body>

	<h1>식품 리스트</h1>
	<a href="register">식품 등록</a>

	<ul class="food_box">
		<c:forEach var="FoodVO" items="${FoodList}">
			<li class="List">
				<p>${ImgList.get(FoodList.indexOf(FoodVO)).getImgFoodPath()}</p>
				<p>${FoodVO.foodType}</p>
				<p>${FoodVO.foodName}</p>
				<p>${FoodVO.foodStock}개</p>
				<p>${FoodVO.foodPrice}원</p>
				<button onclick="location.href='detail?foodId=${FoodVO.foodId}'">상세 보기</button><br>
				<a href="../preorder/register?foodId=${FoodVO.foodId }">예약하기</a><br>
				<button onclick="location.href='update?foodId=${FoodVO.foodId}'">식품 수정</button><br>
				<button onclick="locatioin.href='delete?foodId=${FoodVO.foodId}'">식품 삭제</button>
			</li>
		</c:forEach>
	</ul>
</body>
</html>
