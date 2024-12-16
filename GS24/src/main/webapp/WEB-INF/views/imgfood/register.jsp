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
<title>사진 등록</title>
</head>
<body>
	<form id="ImgFoodForm" action="register" method="post"  enctype="multipart/form-data">
		<input type="text" name="foodId" value="7" readonly="readonly">
		<input type="file" name="file" id="file">
		<input type="submit" value="사진 등록">
	</form>
	
	<script>
		$(document).ready(function() {
			// 업로드를 허용할 확장자를 정의한 정규표현식
			var blockedExtensions = /\.(png)$/i; 
				
			// 파일 전송 form validation
			$("#ImgFoodForm").submit(function(event) {
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