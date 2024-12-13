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
	<form action="register" method="post"  enctype="multipart/form-data">
		<input type="file" name="file" id="file">
		<input type="submit" value="사진 등록">
	</form>
</body>
</html>