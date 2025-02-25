<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>회원유형 선택</title>
<style>
    body {
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

    h1 {
        font-size: 24px;
        margin-bottom: 20px;
        color: #333;
    }

    button {
        width: 100%;
        padding: 15px;
        margin: 10px 0;
        border: 1px solid #ccc; /* 테두리 추가 */
        border-radius: 5px;
        font-size: 16px;
        cursor: pointer;
        background-color: transparent; /* 배경색 제거 */
        transition: color 0.3s; /* 글씨 색 변화 애니메이션 */
    }

    button:hover {
        filter: brightness(0.9); /* 호버 시 어두운 효과 */
    }

    .button-general {
        color: #4CAF50; /* 일반회원 연두색 글씨 */
    }

    .button-owner {
        color: #D84B16; /* 점주 주홍색 글씨 */
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
