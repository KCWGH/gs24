<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="../resources/css/fonts.css">
<title>회원유형 선택</title>
<style>
    body {
        font-family: 'Pretendard-Regular', sans-serif;
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

    h1 {
        margin-bottom: 20px;
        font-size: 25px;
        color: #333;
    }

    button {
    	font-family: 'Pretendard-Regular', sans-serif;
        width: 100%;
        padding: 15px;
        margin: 10px 0;
        border: 1px solid #ccc;
        border-radius: 5px;
        font-size: 18px;
        cursor: pointer;
        background-color: transparent;
        transition: color 0.3s;
    }

    button:hover {
        filter: brightness(0.9);
    }

    .button-general {
        color: #4CAF50;
    }

    .button-owner {
        color: #D84B16;
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
</style>
</head>
<body>
    <div class="container">
        <h1>회원유형 선택</h1>
        <button class="button-general" onclick='location.href="../member/register"'>일반회원</button>
        <button class="button-owner" onclick='location.href="../owner/register"'>점주</button>
    </div>
</body>
</html>
