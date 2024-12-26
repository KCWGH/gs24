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
<title>식품 정보 수정</title>
</head>
<body>
	<h1>식품 정보 수정</h1>
	<h2>${FoodVO.foodName }</h2>
	<form action="update" method="post"enctype="multipart/form-data">
		<div><input type="file" name="file" id="file" required="required"></div>
		<img id="img" style="width: 200px; height: 150px;">
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
	
	<script type="text/javascript">
		$(document).ready(function(){
			
			// 업로드를 허용할 확장자를 정의한 정규표현식
			var accessExtensions = /\.(png|jpg|jpeg)$/i; 
					
			// 파일 전송 form validation
			$("#file").change(function(event) {
				var fileInput = $("input[name='file']"); // File input 요소 참조
				var file = fileInput.prop('files')[0]; // file 객체 참조
				var fileName = fileInput.val();	
					
				if (!file) { // file이 없는 경우
					alert("파일을 선택하세요.");
					event.preventDefault();
					return;
				}
					
				if (!accessExtensions.test(fileName)) { // 허용된 확장자가 아닌 경우
					alert("이 확장자의 파일은 등록할 수 없습니다.");
					event.preventDefault();
					$(this).val("");
					$("#img").html("");
					return;
				}
					
				var maxSize = 1 * 1024 * 1024; // 1 MB 
				if (file.size > maxSize) {
					alert("파일 크기가 너무 큽니다. 최대 크기는 1MB입니다.");
					event.preventDefault();
					$(this).val("");
					$("#img").html("");
					return;
				}
				
				showImg(this)
			});
			
			function showImg(input){
				console.log(input.files[0]);
				let fileReader = new FileReader();
				fileReader.onload = function(event){
					console.log(event.target.result);
					$("#img").attr('src',event.target.result);
				}
				
				fileReader.readAsDataURL(input.files[0]);
			}
		});
	</script>
</body>
</html>