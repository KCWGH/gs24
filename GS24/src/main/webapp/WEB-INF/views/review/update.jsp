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
<title>리뷰 수정</title>
</head>
<body>
	<input type="number" name="reviewId" value="${reviewVO.reviewId }">
	<input type="number" name="foodId" value="${reviewVO.foodId }">
	<p>회원 아이디<p>
	<input type="text" value="${reviewVO.memberId }" readonly="readonly"><br>
	<div class="img">
		<img src='../Img/Review?reviewId=${reviewVO.reviewId }'>
	</div>
	<p>리뷰 제목 : </p><input type="text" name="reviewContent" value="${reviewVO.reviewTitle }">
	<p>리뷰 내용 : </p><textarea rows="5" cols="30" name="reviewContent">${reviewVO.reviewContent }</textarea>
	<p>리뷰 별점 : </p><input type="number" name="reviewRating" value="${reviewVO.reviewRating }">
	
	
	<button>수정</button>
</body>
</html>