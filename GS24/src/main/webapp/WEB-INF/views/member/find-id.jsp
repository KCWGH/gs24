<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 찾기</title>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<script>
	$(document).ready(function() {
		$("#btnSendVerificationCode").click(function(event){
			event.preventDefault();
			let email = $("#email").val();
			let verificationCode = $("#verificationCode").val();
			$.ajax({
				url: "email-exist",
	            type: "POST",
	            data: {email: email},
	            success: function(response) {
	            	if (response === "do not exist") {
	            		$("#sendResult").text("해당 이메일로 등록된 아이디가 없습니다.").show();
	            	} else if (response === "exist"){
	            		$.ajax({
	            			url: "send-verification-code",
	        	            type: "POST",
	        	            data: {email: email},
	        	            success: function(response){
	        	            	if (response === "Sending Success"){
	        	            		$("#sendResult").text("해당 이메일로 인증번호를 전송했습니다. 인증번호를 입력해주세요.").show();
	        	            		$("#verificationText, #verificationCode, #btnFindId").prop("hidden", false);
	        	            		
	        	            	} else {
	        	            		$("#sendResult").text("네트워크 오류로 이메일이 전송되지 않았습니다.").show();
	        	            	}
	        	            }
	            		});
	                	
	                } else {
	                	$("#sendResult").text("이메일을 입력해주세요.").show();
	                }
	            }
	        });
		});
		$("#btnFindId").click(function(event){
			event.preventDefault();
			let email = $("#email").val();
			let verificationCode = $("#verificationCode").val();
			$.ajax({
    			url: "find-id",
	            type: "POST",
	            data: {email: email,
	            	verificationCode : verificationCode
	            },
	            success: function (response) {
	            	$('#findResult').prop("hidden", false);
                    if (response === null || response === "") {
                        $('#findResult').text('인증번호가 잘못되었습니다.');
                    } else {
                        $('#findResult').text('회원 ID: ' + response);
                    }
                },
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
    <div id="sendResult" hidden="hidden"><br><br></div>
    <span id="verificationText" hidden="hidden">인증번호: </span><input type="text" id="verificationCode" name="verficationCode" required hidden="hidden">
    <button id="btnFindId" hidden="hidden">아이디 찾기</button>
    <div id="findResult" hidden="hidden"><br><br></div>
    <a href="find-pw"><button type="button">비밀번호 찾기</button></a>
    <a href="login"><button type="button">로그인 창으로 돌아가기</button></a>
</body>
</html>
