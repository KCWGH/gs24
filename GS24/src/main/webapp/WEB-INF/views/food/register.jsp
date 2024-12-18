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
	<form action="register" method="post" id="foodForm" enctype="multipart/form-data">
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
	
	<script>
		$(document).ready(function() {
			// 업로드를 허용할 확장자를 정의한 정규표현식
			var accessExtensions = /\.(png|jsp|jpeg)$/i; 
				
			// 파일 전송 form validation
			$("#foodForm").submit(function(event) {
				var fileInput = $("input[name='file']"); // File input 요소 참조
				var file = fileInput.prop('files')[0]; // file 객체 참조
				var fileName = fileInput.val();	
				
				if (!file) { // file이 없는 경우
					alert("파일을 선택하세요.");
					event.preventDefault();
					return;
				}
				
				if (!blockedExtensions.test(fileName)) { // 허용된 확장자인 경우
					alert("이 확장자의 파일은 첨부할 수 없습니다.");
					event.preventDefault();
					return;
				}

				var maxSize = 10 * 1024 * 1024; // 10 MB 
				if (file.size > maxSize) {
					alert("파일 크기가 너무 큽니다. 최대 크기는 10MB입니다.");
					event.preventDefault();
				}
			});
		});
	</script>
</body>
</html>