<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 Console</title>
<style>
    /* 콘솔 페이지 스타일 */
    .console-page {
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

    .console-container {
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

    button, #logout {
        width: 100%;
        padding: 15px;
        margin: 10px 0;
        border: 1px solid #ccc;
        border-radius: 5px;
        font-size: 16px;
        cursor: pointer;
        background-color: transparent;
        transition: color 0.3s;
    }

    button, #logout:hover {
        filter: brightness(0.9);
    }

    .button-activate {
        color: #4CAF50;
    }

    .button-remain {
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
<body class="console-page">
    <div class="console-container">
        <h1>관리자 Console</h1>
        <button class="button-activate" type="button" onclick='location.href="../foodlist/list"'>식품 창고</button>
        <button class="button-remain" type="button" onclick="window.open('../coupon/publish', '_blank', 'width=500,height=700,top=100,left=200')">쿠폰 발행</button>
        <button class="button-activate" type="button" onclick='location.href="../orders/list"'>발주 승인</button>
        <button class="button-remain" type="button" onclick='location.href="../admin/activate"'>비활성화 해제 승인</button>
        <form action="../auth/logout" method="post">
        	<input id="logout" type="submit" value="로그아웃">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        </form>
    </div>
</body>
</html>
