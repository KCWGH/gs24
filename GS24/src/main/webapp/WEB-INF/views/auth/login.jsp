<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>GS24</title>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<script src="https://www.google.com/recaptcha/api.js?render=6LfrNrAqAAAAANk1TA-pg2iX6Zi9mEDxF1l1kZgR"></script>

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
<script>
$(document).ready(function() {
    // 로그인 폼 제출 시 처리
    $('#loginForm').on('submit', function(event) {
        event.preventDefault();  // 폼의 기본 제출 동작을 막음

        // reCAPTCHA 토큰을 가져와서 hidden input에 설정
        grecaptcha.ready(function() {
            grecaptcha.execute('6LfrNrAqAAAAANk1TA-pg2iX6Zi9mEDxF1l1kZgR', { action: 'login' }).then(function(token) {
                // 토큰을 hidden input에 설정
                $('#recaptchaToken').val(token);
                // 폼 제출
                $('#loginForm')[0].submit();
            });
        });
    });
});
</script>
<body>
<c:if test="${not empty message}">
        <script type="text/javascript">
            alert("${message}");
        </script>
    </c:if>
    <c:if test="${not empty errorMsg}">
        <script type="text/javascript">
            alert("${errorMsg}");
        </script>
    </c:if>
    <c:if test="${not empty expiredMsg}">
        <script type="text/javascript">
            alert("${expiredMsg}");
        </script>
    </c:if>
    <div class="container">
        <h1><a href="../convenience/list">GS24</a></h1>
        <form action="login" method="POST" id="loginForm">
            <div>
                <label for="username">아이디</label> <input type="text" id="username"
                    name="username" required>
            </div>

            <div>
                <label for="password">비밀번호</label> <input type="password"
                    id="password" name="password" required>
            </div>

            <button type="submit">로그인</button>

            <div>
                <a href="../user/find-id">아이디 찾기</a>
                <a href="../user/find-pw">비밀번호 찾기</a>
                <a href="../user/register">회원가입</a>
            </div>
            <input type="hidden" name="recaptchaToken" id="recaptchaToken">
            <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
        </form>
    </div>
</body>
</html>
