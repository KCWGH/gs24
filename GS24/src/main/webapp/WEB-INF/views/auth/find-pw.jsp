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
        	
        	const urlParams = new URLSearchParams(window.location.search);
            const memberId = urlParams.get('memberId');
            
            if (memberId) {
                $("#memberId").val(memberId);
            }
            
            let timerInterval; // 타이머 인터벌 변수
            let remainingTime = 2 * 60; // 2분(120초)
        	
            function checkPw() {
                let password = $('#password').val();
                let passwordConfirm = $('#passwordConfirm').val();
                if (password === "" || passwordConfirm === "") {
                    $('#passwordMatchMessage').text("비밀번호를 입력해주세요.").css('color', 'red');
                    $('#btnUpdatePw').prop('disabled', true);
                    return;
                }
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
            	if (email === "" || memberId === "") {
                    alert("아이디와 이메일을 모두 기입해주세요.");
                    return;
                }
            	$("#btnSendVerificationCode").prop("disabled", true);
                $.ajax({
                    url: "find-pw",
                    type: "POST",
                    data: { memberId: memberId, email: email },
                    success: function(response) {
                    	$("#sendResult").html("해당 이메일로 인증 코드를 보냈습니다.");
                        $("#sendResult,#verificationText,#verificationCode,#btnVerifyCode").show();
                        startTimer();
                        $("#timer").show();
                    },
                    error: function(xhr, status, error) {
                        let responseText = xhr.responseText;
                        if (responseText === "do not exist") {
                        	$("#sendResult").html("해당 아이디에 등록된 이메일이 아닙니다. 다시 확인해주세요.");
                        	$("#sendResult").show();
                        } else {
                        	$("#sendResult").html("이메일 전송에 실패했습니다. 다시 시도해 주세요.");
                        	$("#sendResult").show();
                        }
                    },
                    complete: function() {
                        $("#btnSendVerificationCode").prop("disabled", false);
                    }
                });
            });

            $("#btnUpdatePw").prop('disabled', true);
            $('#password').on('input', checkPw);
            $('#passwordConfirm').on('input', checkPw);

            $("#btnVerifyCode").click(function(event){
            	event.preventDefault();
            	let memberId = $("#memberId").val();
    			let email = $("#email").val();
    			let code = $("#verificationCode").val();
    			$.ajax({
    			    url: 'verifyCode-PW',
    			    type: 'POST',
    			    contentType: 'application/json',  // 데이터를 JSON 형식으로 전송
    			    data: JSON.stringify({
    			        email: email,
    			        code: code
    			    }),
    			    success: function (response) {
    			        // 인증 성공 시 메시지 출력 및 비밀번호 입력 섹션 표시
    			        $("#findResult").html("인증번호가 일치합니다.");
    			        $("#findResult").show();
    			        $("#divPassword").show();
    			        $("#timer").hide();
    			    },
    			    error: function (xhr, status, error) {
    			    	if (xhr.status === 400) {
    			        $("#findResult").html("인증번호가 일치하지 않습니다.");
    			        $("#findResult").show();
    			        } else {
    			            alert("서버와의 통신 중 오류가 발생했습니다. 다시 시도해주세요.");
    			        }
    			    }
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
            
         // 타이머 시작 함수
            function startTimer() {
                // 2분 타이머를 1초마다 갱신
                timerInterval = setInterval(function() {
                    let minutes = Math.floor(remainingTime / 60);
                    let seconds = remainingTime % 60;
                    $("#timer").text("남은 시간: " + minutes + "분 " + seconds + "초");
                    remainingTime--;

                    if (remainingTime < 0) {
                        clearInterval(timerInterval);  // 타이머 멈추기
                        $("#timer").text("인증번호가 만료되었습니다.");
                        $("#verificationCode").prop("disabled", true);  // 인증번호 입력 불가
                    }
                }, 1000);
            }
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
    <input type="text" id="verificationCode" name="verificationCode" required hidden="hidden">
    <button id="btnVerifyCode" hidden="hidden">인증번호 확인</button><br>
    <div id="timer" hidden="hidden"></div>
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
