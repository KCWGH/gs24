<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<title>식품 리스트</title>
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

	<hr>

	<h1>식품 리스트</h1>
	<a href="register">식품 등록</a>

	<ul class="food_box">
		<c:forEach var="FoodVO" items="${FoodList}">
			<li class="List">
				<div class="FoodBox">
					<p>${FoodVO.foodType}</p>
					<p>${FoodVO.foodName}</p>
					<p>${FoodVO.foodStock}개</p>
					<p>${FoodVO.foodPrice}원</p>
					<a href="detail?foodId=${FoodVO.foodId}">자세히 보기</a> <a
						href="update?foodId=${FoodVO.foodId}">식품 정보 수정</a> <a
						href="delete?foodId=${FoodVO.foodId}">식품 삭제</a>
				</div>
			</li>
		</c:forEach>
	</ul>
</body>
</html>
