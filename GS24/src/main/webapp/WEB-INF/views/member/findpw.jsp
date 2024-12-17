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
    	아이디: <input type="text" id="memberId" name="memberId" required><br>
        이메일: <input type="email" id="email" name="email" required>
        <input type="submit" value="비밀번호 찾기">
    </form>
    <a href="login"><button type="button">로그인 창으로 돌아가기</button></a>
</body>
</body>
</html>