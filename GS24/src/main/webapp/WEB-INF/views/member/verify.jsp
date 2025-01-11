<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 변경</title>
</head>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<script src="https://www.google.com/recaptcha/api.js?render=6LfrNrAqAAAAANk1TA-pg2iX6Zi9mEDxF1l1kZgR"></script>
<script type="text/javascript">
$(document).ready(function() {
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
</script>
<body>
	<c:if test="${not empty message}">
        <script type="text/javascript">
            alert("${message}");
        </script>
    </c:if>
	<h2>비밀번호 확인</h2>
	<h3>비밀번호를 다시 한번 입력해 주세요.</h3>
	<form action="check-pw" method="POST" >
	<table>
		<tr>
			<th>아이디</th>
			<td><input type="text" id="memberId" name="memberId" value="${memberVO.memberId}" readonly></td>
		</tr>
		<tr>
			<th>비밀번호</th>
			<td><input type="password" id="password" name="password" required></td>
		</tr>
	</table>
	<br>
	<button type="submit">확인</button> <button type="button" onclick='location.href="mypage"'>마이페이지로 돌아가기</button>
	<input type="hidden" name="recaptchaToken" id="recaptchaToken">

	</form>
</body>
</html>