<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>로그인</title>

</head>

<body>
	<div class="container">
		<h2>로그인</h2>
		<form action="login" method="POST">
			<!-- 아이디 입력 -->
			<div>
				<label for="memberId">아이디</label> <input type="text" id="memberId"
					name="memberId" required>
			</div>

			<!-- 비밀번호 입력 -->
			<div>
				<label for="password">비밀번호</label> <input type="password"
					id="password" name="password" required>
			</div>

			<!-- 로그인 버튼 -->
			<button type="submit">로그인</button>

			<!-- 아이디 찾기 링크 -->
			<a href="findid">아이디 찾기</a>
			<!-- 비밀번호 찾기 링크 -->
			<a href="findpw">비밀번호 찾기</a>

			<!-- 회원가입 버튼 -->
			<a href="register"><button type="button">회원가입</button></a>
		</form>
	</div>
</body>
</html>
