<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>편의점 식품 등록</title>
</head>
<body>
	<h1>편의점 식품 등록</h1>
	<!-- 나중에 여기에 식품 이미지도 같이 DB에 저장해야 한다. -->
	<form action="register" method="post" enctype="multipart/form-data">
		<div><input type="file" name="file"></div>
		<div><input type="text" name="foodType" placeholder="식품 유형 입력" required="required"></div>
		<div><input type="text" name="foodName" placeholder="식품명 입력" required="required"></div>
		<div><input type="number" name="foodStock" placeholder="재고량 입력" required="required"></div>
		<div><input type="number" name="foodPrice" placeholder="식품 가격 입력" required="required"></div>
		<div><input type="number" name="foodFat" placeholder="지방 영양소 입력" required="required"></div>
		<div><input type="number" name="foodProtein" placeholder="단백질 영양소 입력" required="required"></div>
		<div><input type="number" name="foodCarb" placeholder="탄수화물 영양소 입력" required="required"></div>
		<div><input type="submit" value="등록"></div>
	</form>
</body>
</html>