<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>식품 정보 수정</title>
</head>
<body>
	<h1>식품 정보 수정</h1>
	<h2>${FoodVO.foodName }</h2>
	<form action="update" method="post"enctype="multipart/form-data">
		<div><input type="file" name="file" required="required"></div>
		<input type="hidden" name="foodId" value="${FoodVO.foodId }"><br>
		<p>재고량</p>
		<input type="number" name="foodStock" value="${FoodVO.foodStock }" required="required"><br>
		<p>가격</p>
		<input type="number" name="foodPrice" value="${FoodVO.foodPrice }" required="required"><br>	
		<p>단백질 함유량</p>
		<input type="number" name="foodProtein" value="${FoodVO.foodProtein }" required="required"><br>
		<p>지방 함유량</p>
		<input type="number" name="foodFat" value="${FoodVO.foodFat }" required="required"><br>
		<p>탄수화물 함유량</p>
		<input type="number" name="foodCarb" value= "${FoodVO.foodCarb }" required="required"><br>
		<input type="submit" value="수정">
	</form>
</body>
</html>