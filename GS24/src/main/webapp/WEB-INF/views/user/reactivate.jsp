<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비활성화된 계정입니다</title>
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
<body>
    <div class="container">
        <h1>비활성화된 계정입니다.</h1>
        <sec:authorize access="hasRole('ROLE_DEACTIVATED_MEMBER')">
        <button class="button-activate" type="button" onclick='location.href="../member/activate"'>비활성화 해제</button>
        </sec:authorize>
        <sec:authorize access="hasRole('ROLE_DEACTIVATED_OWNER')">
        <form action="../owner/request-activation" method="POST">
        <button class="button-activate" type="submit">비활성화 해제</button>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        </form>
        </sec:authorize>
		<form action="../auth/logout" method="POST">
        <button class="button-remain" type="submit">비활성화 유지하고 돌아가기</button>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        </form>
    </div>
</body>
</html>
