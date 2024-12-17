<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<<<<<<< HEAD

=======
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>음식 리스트</title>
<<<<<<< Updated upstream
</head>
<body>
	<span>환영합니다, ${memberId }님</span>
	<c:if test="${not empty memberVO}">
	<a href="../member/mypage"><button>마이페이지</button></a>
	<a href="../member/logout"><button>로그아웃</button></a>
	<a href="../notice/list"><button>공지사항</button></a>
	<a href="../question/list"><button>Q&A게시판</button></a>
	</c:if>
	<c:if test="${empty memberVO}">
	<a href="../member/login"><button>로그인</button></a>
	<a href="../notice/list"><button>공지사항</button></a>
	<a href="../question/list"><button>Q&A게시판</button></a>
	</c:if>
	<hr>
=======
<!DOCTYPE html>
>>>>>>> c366c08dc1ff87280f5da0a1dbabf6a230862cb9
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<title>식품 리스트</title>
</head>
<script type="text/javascript">
$("#myButton").click(function() {
	  window.open("https://www.example.com", "_blank");
	});
</script>
<body>

	<c:if test="${not empty memberVO}">
		<span>환영합니다, ${memberId}님</span>
		<a href="../member/mypage"><button>마이페이지</button></a>
		<a href="../member/logout"><button>로그아웃</button></a>
		<a href="../notice/list"><button>공지사항</button></a>
		<a href="../question/list"><button>문의사항(QnA)</button></a>
	</c:if>

	<c:if test="${empty memberVO}">
		<a href="../member/login"><button>로그인</button></a>
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
