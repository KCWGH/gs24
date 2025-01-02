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
img {
   width: 200px;
   height: 150px;
}
</style>

</head>
<body>
   <c:if test="${not empty memberVO}">
      <span>환영합니다, ${memberId}님</span>
      <button onclick="window.open('../member/mypage', '_blank', 'width=500,height=700')">마이페이지</button>
      <a href="../member/logout"><button>로그아웃</button></a>
      <button onclick='location.href="../preorder/list"'>예약 식품 목록</button>
      <button onclick="window.open('../coupon/list', '_blank', 'width=500,height=700')">쿠폰 목록</button>
   </c:if>

   <c:if test="${empty memberVO}">
      <button onclick="window.open('../member/login', '_blank', 'width=550,height=400')">로그인</button>
   </c:if>
      <a href="../notice/list"><button>공지사항</button></a>
      <a href="../question/list"><button>문의사항(QnA)</button></a>
   <hr>

   <h1>식품 리스트</h1>
   <c:if test="${memberVO.memberRole == 2 }">
      <button onclick='location.href="register"'>식품등록</button>
   </c:if>

   <ul class="food_box">
      <c:forEach var="FoodVO" items="${FoodList}">
         <li class="List">
            <img src="../ImgFood?foodId=${FoodVO.foodId }">
            <p>${FoodVO.foodType}</p>
            <p>${FoodVO.foodName}</p>
            <p>${FoodVO.foodStock}개</p>
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
</body>
</html>
