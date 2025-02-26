<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<title>Insert title here</title>
</head>
<body>
	<script type="text/javascript">
	$(document).ready(function() {
    	$('#loginForm')[0].submit();
	});
	</script>
	
	<form action="login" method="POST" id="loginForm">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
		<input type="hidden" id="username" name="username" value="${nickname }" required>
		<input type="hidden" id="password" name="password" value="${nickname }">
	</form>
	<h1>로그인 중입니다. 잠시 기다려 주십시오</h1>
</body>
</html>