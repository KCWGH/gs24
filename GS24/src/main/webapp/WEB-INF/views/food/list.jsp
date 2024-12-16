<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>음식 리스트</title>
</head>
<style>
ul {
	list-style-type: none;
}

li {
	display: inline-block;
}
</style>
</head>
<body>
	<span>환영합니다, ${memberId }님</span>
	<c:if test="${not empty memberVO}">
		<a href="../member/mypage"><button>마이페이지</button></a>
		<a href="../member/logout"><button>로그아웃</button></a>
	</c:if>
	<c:if test="${empty memberVO}">
		<a href="../member/login"><button>로그인</button></a>
	</c:if>
	<hr>
	<h1>식품 리스트</h1>
	<a href=register><p>식품 등록</p></a>
	<ul class="food_box">
		<c:forEach var="FoodVO" items="${FoodList }">
			<!-- 나중에 식품 이미지도 같이 나오게 해야한다. -->
			<li class='List'>
				<div class="FoodBox">
					<p>${FoodVO.foodType }</p>
					<p>${FoodVO.foodName }</p>
					<p>${FoodVO.foodStock }개</p>
					<p>${FoodVO.foodPrice }원</p>
					<a href="detail?foodId=${FoodVO.foodId}">자세히 보기</a> <a
						href="update?foodId=${FoodVO.foodId }"><p>식품 정보 수정</p></a>
				</div>
			</li>
		</c:forEach>
	</ul>





</body>
</html>