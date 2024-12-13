<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입</title>
</head>
<body>
    <h2>회원가입</h2>

    <form action="register" method="POST">
        <!-- ID -->
        <div>
            <label for="memberId">아이디</label>
            <input type="text" id="memberId" name="memberId" required>
        </div>

        <!-- 비밀번호 -->
        <div>
            <label for="password">비밀번호</label>
            <input type="password" id="password" name="password" required>
        </div>

        <!-- 이메일 -->
        <div>
            <label for="email">이메일</label>
            <input type="email" id="email" name="email" required>
        </div>

        <!-- 휴대폰 -->
        <div>
            <label for="phone">휴대폰</label>
            <input type="text" id="phone" name="phone" required>
        </div>

        <!-- 생일 -->
        <div>
            <label for="birthday">생일</label>
            <input type="date" id="birthday" name="birthday">
        </div>

        <!-- 계정 유형 (라디오 버튼) -->
        <div>
            <label>계정 유형</label><br>
            <input type="radio" id="memberRole1" name="memberRole" value="1" required>
            <label for="memberRole1">일반회원</label><br>
            <input type="radio" id="memberRole2" name="memberRole" value="2">
            <label for="memberRole2">점주</label>
        </div>

        <div>
            <button type="submit">회원가입</button>
        </div>

        <!-- 로그인 페이지로 돌아가기 -->
        <div>
            <a href="login"><button type="button">로그인 창으로 돌아가기</button></a>
        </div>
    </form>
</body>
</html>
