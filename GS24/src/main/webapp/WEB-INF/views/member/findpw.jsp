<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 찾기</title>
</head>
<body>
<body>
    <h2>비밀번호 찾기</h2>
    <form action="findid" method="POST">
    	<span>아이디: </span><input type="text" id="memberId" name="memberId" required><br>
        <span>이메일: </span><input type="email" id="email" name="email" required>
        <input type="submit" value="인증번호 전송">
    </form>
    <a href="login"><button type="button">로그인 창으로 돌아가기</button></a>
</body>
</body>
</html>