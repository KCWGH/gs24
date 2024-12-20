<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 찾기</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
	$(document).ready(function() {
		$("#btnSendVerificationCode").click(function(event){
			event.preventDefault();
			let email = $("#email").val();
			let data = {email: email};
			$.ajax({
				url: "findid",
	            type: "POST",
	            data: data,
	            success: function(response) {
	            	if (response === "do not exist") {
	            		$("#result").text("해당 이메일로 등록된 아이디가 없습니다").show();
	            	} else if (response === "exist"){
	            		$.ajax({
	            			url: "sendVerificationCode",
	        	            type: "POST",
	        	            data: data,
	        	            success: function(success){
	        	            	if (success == true){
	        	            		$("#result").text("해당 이메일로 인증번호를 전송했습니다. 인증번호를 입력해주세요.").show();
	        	            		$("#verificationText, #verificationCode, #btnFindId").prop("hidden", false);
	        	            	}
	        	            }
	            		});
	                	
	                } else {
	                	$("#result").text("이메일을 입력해주세요.").show();
	                }
	            }
	        });
		});
	});
</script>
</head>
<body>
    <h2>아이디 찾기</h2>
    <p>회원가입 시 등록된 이메일로 인증번호를 전송합니다.</p>
    이메일: <input type="email" id="email" name="email" required>
    <button id="btnSendVerificationCode">인증번호 전송</button><br>
    <p id="result" hidden="hidden"></p> 
    <span id="verificationText" hidden="hidden">인증번호: </span><input type="text" id="verificationCode" name="verficationCode" required hidden="hidden">
    <button id="btnFindId" hidden="hidden">아이디 찾기</button><br><br>
    <a href="findpw"><button type="button">비밀번호 찾기</button></a>
    <a href="login"><button type="button">로그인 창으로 돌아가기</button></a>
</body>
</html>
