<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>로그인</title>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<style>
    body {
        filter: grayscale(100%);
        font-family: Arial, sans-serif;
        background-color: #f4f4f4;
        color: #333;
        margin: 0;
        padding: 0;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
    }

    .container {
        background-color: white;
        padding: 30px;
        border-radius: 8px;
        box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.1);
        width: 300px;
        text-align: center;
    }

    h2 {
        color: #333;
        font-size: 24px;
        margin-bottom: 20px;
    }

    label {
        font-size: 14px;
        margin-bottom: 8px;
        display: block;
        text-align: left;
    }

    input[type="text"],
    input[type="password"] {
        width: 100%;
        padding: 10px;
        margin-bottom: 15px;
        border: 1px solid #ccc;
        border-radius: 4px;
        box-sizing: border-box;
    }

    button {
        width: 100%;
        padding: 10px;
        background-color: #4CAF50;
        color: white;
        border: none;
        border-radius: 4px;
        font-size: 16px;
        cursor: pointer;
    }

    button:hover {
        background-color: #45a049;
    }

    a {
        display: inline-block;
        margin: 10px 5px;
        color: #007BFF;
        text-decoration: none;
    }

    a:hover {
        text-decoration: underline;
    }

    button, a {
        filter: grayscale(50%);
        transition: filter 0.3s ease;
    }

    button:hover, a:hover {
        filter: grayscale(0%);
    }
</style>
</head>
<body>
    <div class="container">
        <h2>로그인</h2>
        <form action="login" method="POST">
            <div>
                <label for="memberId">아이디</label> <input type="text" id="memberId"
                    name="memberId" required>
            </div>

            <div>
                <label for="password">비밀번호</label> <input type="password"
                    id="password" name="password" required>
            </div>

            <button type="submit">로그인</button>

            <div>
                <a href="find-id">아이디 찾기</a>
                <a href="find-pw">비밀번호 찾기</a>
                <a href="register">회원가입</a>
            </div>
        </form>
    </div>
</body>
</html>
