<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
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
img {
	width: 200px;
	height: 150px;
}
</style>
</head>
<body>
	<c:if test="${not empty memberVO}">
		<span>환영합니다, ${memberId}님</span>
		<a href="../member/mypage" id="mypage"><button>마이페이지</button></a>
		<a href="../member/logout"><button>로그아웃</button></a>
		<a href="../notice/list"><button>공지사항</button></a>
		<a href="../question/list"><button>문의사항(QnA)</button></a>
	</c:if>

	<c:if test="${empty memberVO}">
		<a href="../member/login" id="login"><button>로그인</button></a>
		<a href="../notice/list"><button>공지사항</button></a>
		<a href="../question/list"><button>문의사항(QnA)</button></a>
	</c:if>
	
	<button onclick='location.href="../preorder/list"'>예약 식품 목록</button>
	
	<hr>

	<h1>식품 리스트</h1>
	<c:if test="${memberVO.memberRole == 2 }">
		<button onclick='location.href="register"'>식품등록</button>
	</c:if>

	<ul class="food_box">
		<c:forEach var="FoodVO" items="${FoodList}">
			<li class="List">
				<% 
				 //<img src="<spring:url value ='/resources/${ FoodVO.imgFoodVO.path }'/>"/>
				%>
				<img src="/website/ImgFood?foodId=${FoodVO.foodId }" alt="${FoodVO.foodName }">
				<p>${FoodVO.foodName}</p>
				<p>${FoodVO.foodPrice}원</p>
				<p>${FoodVO.foodAvgRating }점</p>
				<p>리뷰 ${FoodVO.foodReviewCnt }개</P>
				<button onclick="location.href='detail?foodId=${FoodVO.foodId}'">상세 보기</button><br>
				<button onclick='location.href="../preorder/register?foodId=${FoodVO.foodId }"'>예약하기</button><br>
				<c:if test="${memberVO.memberRole == 2 }">
					<button onclick="location.href='update?foodId=${FoodVO.foodId}'">식품 수정</button><br>
					<button onclick="location.href='delete?foodId=${FoodVO.foodId}'">식품 삭제</button>
				</c:if>
			</li>
		</c:forEach>
	</ul>
	
	<script type="text/javascript">
		
	</script>
</body>
</html>
