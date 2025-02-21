<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<title>비밀번호 변경</title>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<script src="https://www.google.com/recaptcha/api.js?render=6LfrNrAqAAAAANk1TA-pg2iX6Zi9mEDxF1l1kZgR"></script>
<script type="text/javascript">
$(document).ajaxSend(function(e, xhr, opt){
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    xhr.setRequestHeader(header, token);
});

$(document).ready(function() {
    $("#btnUpdatePw").prop('disabled', true);
    $('#password').on('input', checkPw);
    $('#passwordConfirm').on('input', checkPw);
    
    $('form').submit(function (event) {
        event.preventDefault();
        grecaptcha.ready(function() {
            grecaptcha.execute('6LfrNrAqAAAAANk1TA-pg2iX6Zi9mEDxF1l1kZgR', { action: 'password_change' }).then(function(token) {
                $('#recaptchaToken').val(token);
                $('form')[0].submit();
            });
        });
    });
});

function checkPw() {
    let password = $('#password').val();
    let passwordConfirm = $('#passwordConfirm').val();
    
    if (password === "" || passwordConfirm === "") {
        $('#passwordMatchMessage').text("비밀번호를 입력해주세요.").css('color', 'red');
        $('#btnUpdatePw').prop('disabled', true);
        return;
    }

    if (password !== passwordConfirm) {
        $('#passwordMatchMessage').text("비밀번호가 일치하지 않습니다.").css('color', 'red');
        $('#btnUpdatePw').prop('disabled', true);
    } else {
        $('#passwordMatchMessage').text("비밀번호가 일치합니다.").css('color', 'green');
    }

    const pwRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
    if (!pwRegex.test(password)) {
        $('#passwordMatchMessage').html("비밀번호는 최소 8자 이상,<br>대소문자, 숫자, 특수문자를 포함해야 합니다.").css('color', 'red').show();
        $('#btnUpdatePw').prop('disabled', true);
    } else {
        if (password === passwordConfirm) {
            $('#passwordMatchMessage').text("비밀번호가 강력하고 일치합니다.").css('color', 'green');
            $('#btnUpdatePw').prop('disabled', false);
        }
    }
}
</script>

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
        width: 400px;
        text-align: center;
    }

    h2, h3 {
        color: #333;
        margin-bottom: 20px;
    }

    label {
        font-size: 14px;
        margin-bottom: 8px;
        display: block;
        text-align: left;
    }

    input[type="password"],
    input[type="text"] {
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
        background-color: #999;
        color: white;
        border: none;
        border-radius: 4px;
        font-size: 16px;
        cursor: pointer;
        margin-top: 10px;
    }

    button:hover {
        background-color: #777;
    }

    .btn-container {
        display: flex;
        justify-content: space-between;
        margin-top: 10px;
    }

    .btn-container button {
        width: 48%;
    }

    #passwordMatchMessage {
        margin-top: 10px;
    }
</style>

<body>
    <c:if test="${not empty message}">
        <script type="text/javascript">
            alert("${message}");
        </script>
    </c:if>

    <div class="container">
        <h2>비밀번호 변경</h2>
        <h3>새로운 비밀번호를 입력해주세요.</h3>
        
        <form action="change-pw" method="POST">
            <div>
                <sec:authorize access="hasRole('ROLE_MEMBER')">
                    <input type="text" name="memberId" value="${userInfo.memberId}" readonly hidden="hidden">
                </sec:authorize>
                <sec:authorize access="hasRole('ROLE_OWNER')">
                    <input type="text" name="ownerId" value="${userInfo.ownerId}" readonly hidden="hidden">
                </sec:authorize>
            </div>

            <div>
                <label for="password">비밀번호</label>
                <input type="password" id="password" name="password" required>
            </div>

            <div>
                <label for="passwordConfirm">비밀번호 확인</label>
                <input type="password" id="passwordConfirm" name="passwordConfirm" required>
            </div>

            <div>
                <span id="passwordMatchMessage"></span>
            </div>

            <div class="btn-container">
                <button type="submit" id="btnUpdatePw" disabled>비밀번호 변경</button>
                <button type="button" onclick='location.href="mypage"'>마이페이지로 돌아가기</button>
            </div>

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <input type="hidden" name="recaptchaToken" id="recaptchaToken">
        </form>
    </div>
</body>
</html>
