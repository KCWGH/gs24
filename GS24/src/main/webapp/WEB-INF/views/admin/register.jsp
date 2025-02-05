<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 계정 생성</title>
</head>
<body>
<h2>계정 생성</h2>
        <form action="register" method="POST">
            <div>
                <label for="adminId">아이디</label> <input type="text" id="adminId" name="adminId" required>
            </div>
			<div>
                <label for="password">비밀번호</label> <input type="password" id="password" name="password" required>
            </div>

            <button type="submit">가입하기</button>
            <input type="hidden" name="recaptchaToken" id="recaptchaToken">
            <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
        </form>
</body>
</html>