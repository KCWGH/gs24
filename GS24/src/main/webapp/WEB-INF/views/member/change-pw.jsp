<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<title>비밀번호 변경</title>
</head>
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
<body>
<c:if test="${not empty message}">
        <script type="text/javascript">
            alert("${message}");
        </script>
    </c:if>
    <h2>비밀번호 변경</h2>
    <h3>새로운 비밀번호를 입력해주세요.</h3>
    <form action="change-pw" method="POST">
    <div>
            <input type="hidden" id="memberId" name="memberId" value="${memberVO.memberId}" readonly>
        </div>
    <table>
		<tr>
			<th>비밀번호</th>
			<td><input type="password" id="password" name="password" required></td>
		</tr>
		<tr>
			<th>비밀번호 확인</th>
			<td><input type="password" id="passwordConfirm" name="passwordConfirm" required></td>
		</tr>
		<tr>
			<td></td>
			<td><span id="passwordMatchMessage"></span></td>
		</tr>
	</table>
        <br>
        <button type="submit" id="btnUpdatePw" disabled>비밀번호 변경</button> 
        <button type="button" onclick='location.href="mypage"'>마이페이지로 돌아가기</button>
        <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
        <input type="hidden" name="recaptchaToken" id="recaptchaToken">
    </form>
</body>
</html>
