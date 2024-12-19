<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>비밀번호 찾기</title>
    <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            function checkPw() {
                let password = $('#password').val();
                let passwordConfirm = $('#passwordConfirm').val();

                if (password !== passwordConfirm) {
                    $('#passwordMatchMessage').text("비밀번호가 일치하지 않습니다.").css('color','red');
                    $('#btnUpdatePw').prop('disabled', true);
                } else {
                    $('#passwordMatchMessage').text("비밀번호가 일치합니다.").css('color','green');
                    $('#btnUpdatePw').prop('disabled', false);
                }
            }

            $("#btnSendVerificationCode").click(function(event){
                event.preventDefault();
                let memberId = $("#memberId").val();
                let email = $("#email").val();
                let verificationCode = $("#verificationCode").val();
                
                $.ajax({
                    url: "account-exist",
                    type: "POST",
                    data: { memberId: memberId, email: email },
                    success: function(response) {
                        if (response === "do not exist") {
                            $("#sendResult").text("해당 회원정보가 없습니다.").show();
                        } else if (response === "exist") {
                            $.ajax({
                                url: "send-verification-code",
                                type: "POST",
                                data: { email: email },
                                success: function(response) {
                                    if (response === "Sending Success") {
                                        $("#sendResult").text("해당 이메일로 인증번호를 전송했습니다. 인증번호를 입력해주세요.").show();
                                        $("#verificationText, #verificationCode, #btnVerifyCode").prop("hidden", false);
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
            $("#btnUpdatePw").prop('disabled', true);
            $('#passwordConfirm').on('input', checkPw);
            $("#btnVerifyCode").click(function(event){
            	event.preventDefault();
            	let memberId = $("#memberId").val();
    			let email = $("#email").val();
    			let verificationCode = $("#verificationCode").val();
    			$.ajax({
        			url: "find-pw",
    	            type: "POST",
    	            data: {
    	            	memberId: memberId,
    	            	email: email,
    	            	verificationCode : verificationCode
    	            },
    	            success: function (response) {
    	            	$('#findResult').prop("hidden", false);
                        if (response === null || response === "0") {
                        	$('#findResult').text('인증번호가 잘못되었습니다.');
                        } else {
                        	$('#divPassword').prop('hidden', false);
                        }
                    },
        		});   	
            });
            $("#btnUpdatePw").click(function(event){
            	event.preventDefault();
            	let memberId = $("#memberId").val();
            	let password = $("#password").val();
            	let email = $("#email").val();
            	let data = {memberId: memberId, email: email, password: password};
            	$.ajax({
        			url: "find-update-pw",
    	            type: "POST",
    	            data: data,
    	            success: function (response) {
    	            	$('#findResult').prop("hidden", false);
                        if (response === "Update Success") {
                        	window.location.href = "login";
                        	alert('비밀번호가 변경되었습니다. 해당 아이디와 비밀번호로 로그인하세요.');
                        } else {
                        	$('#updateResult').text('비밀번호 변경 실패. 해당 이메일로 등록된 아이디가 아닙니다.');
                        }
                    },
        		}); 
            });
        });
    </script>
</head>
<body>
    <h2>비밀번호 찾기</h2>
    <p>회원가입 시 등록된 이메일로 인증번호를 전송합니다.</p>

    <label for="memberId">아이디: </label>
    <input type="text" id="memberId" name="memberId" required><br>

    <label for="email">이메일: </label>
    <input type="email" id="email" name="email" required>
    <button id="btnSendVerificationCode">인증번호 전송</button><br>

    <div id="sendResult" hidden="hidden"></div>
    <span id="verificationText" hidden="hidden">인증번호: </span>
    <input type="text" id="verificationCode" name="verficationCode" required hidden="hidden"><button id="btnVerifyCode" hidden="hidden">인증번호 확인</button><br>
    <div id="findResult" hidden="hidden"></div>

    <div id="divPassword" hidden="hidden">
    	<span>비밀번호를 재설정합니다.</span><br>
        <label for="password">비밀번호: </label>
        <input type="password" id="password" name="password" required><br>
        
        <label for="passwordConfirm">비밀번호 확인: </label>
        <input type="password" id="passwordConfirm" name="passwordConfirm" required >
        
        <button type="button" onclick="checkPw()" hidden="hidden">비밀번호 확인</button><br>
        <span id="passwordMatchMessage"></span>
        <button id="btnUpdatePw" disabled>비밀번호 변경</button><br>
        <span id="updateResult"></span>
    </div>

    <a href="find-id"><button type="button">아이디 찾기</button></a>
    <a href="login"><button type="button">로그인 창으로 돌아가기</button></a>

    <div id="message"></div>
</body>
</html>
