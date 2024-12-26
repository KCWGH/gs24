<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<title>편의점 식품 등록</title>
<style>
	img{
		width: 200px;
		height: 150px;
	}
</style>
</head>
<body>
	<h1>편의점 식품 등록</h1>
	<!-- 나중에 여기에 식품 이미지도 같이 DB에 저장해야 한다. -->
	<form action="register" method="post" id="foodForm" enctype="multipart/form-data">
		<div><input type="file" name="file" id="file" required="required"></div>
		<div id="img"></div>
		<div><input type="text" name="foodType" placeholder="식품 유형 입력" required="required"></div>
		<div><input type="text" name="foodName" placeholder="식품명 입력" required="required"></div>
		<div><input type="number" name="foodStock" placeholder="재고량 입력" required="required"></div>
		<div><input type="number" name="foodPrice" placeholder="식품 가격 입력" required="required"></div>
		<div><input type="number" name="foodFat" placeholder="지방 영양소 입력" required="required"></div>
		<div><input type="number" name="foodProtein" placeholder="단백질 영양소 입력" required="required"></div>
		<div><input type="number" name="foodCarb" placeholder="탄수화물 영양소 입력" required="required"></div>
		<div><input type="submit" value="등록"></div>
	</form>
	
	<script>
		$(document).ready(function() {
			
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
				
				showImg(this);
			});
			
			function showImg(input){
				console.log(input.files[0]);
				let fileReader = new FileReader();
				fileReader.onload = function(event){
					console.log(event.target.result);
					$("#img").html("<img src="+ event.target.result +">");
				}
				
				fileReader.readAsDataURL(input.files[0]);
			}
			
		});
	</script>
</body>
</html>