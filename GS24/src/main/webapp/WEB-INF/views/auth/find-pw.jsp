<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>
    <title>비밀번호 찾기</title>
    <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
    <script type="text/javascript">
    $(document).ajaxSend(function(e, xhr, opt){
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        
        xhr.setRequestHeader(header, token);
     });
        $(document).ready(function() {
        	const pwRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
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
                
                if (!pwRegex.test(password)) {
                    $('#passwordMatchMessage').text("비밀번호는 최소 8자, 대문자, 소문자, 숫자, 특수문자를 각각 하나 이상 포함해야 합니다.").css('color', 'red');
                    isPasswordMatched = false;
                    return;
                }
                
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

            $("#btnSendVerificationCode").click(function(event) {
                event.preventDefault();
                let memberId = $("#memberId").val();
                let email = $("#email").val();

                if (email === "" || memberId === "") {
                    alert("아이디와 이메일을 모두 기입해주세요.");
                    return;
                }

                // 인증번호 전송 버튼 비활성화
                $("#btnSendVerificationCode").prop("disabled", true);

                // 기존 타이머를 초기화
                if (timerInterval) {
                    clearInterval(timerInterval); // 기존 타이머 정지
                }
                remainingTime = 2 * 60; // 타이머를 2분으로 초기화
                
                $("#verificationCode").prop("disabled", false);

                $.ajax({
                    url: "find-pw",
                    type: "POST",
                    data: { memberId: memberId, email: email },
                    success: function(response) {
                        $("#sendResult").html("해당 이메일로 인증 코드를 보냈습니다.");
                        $("#sendResult, #verificationText, #verificationCode, #btnVerifyCode").show();
                        startTimer(); // 타이머 시작
                        $("#timer").show();
                    },
                    error: function(xhr, status, error) {
                        let responseText = xhr.responseText;
                        if (responseText === "do not exist") {
                            $("#sendResult").html("해당 아이디에 등록된 이메일이 아닙니다. 다시 확인해주세요.").show();
                        } else {
                            $("#sendResult").html("이메일 전송에 실패했습니다. 다시 시도해 주세요.").show();
                        }
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
                        $("#btnSendVerificationCode").prop("disabled", false);
                    }
                }, 1000);
            }
        });
    </script>
</head>
<body>
    <h2>비밀번호 찾기</h2>
    <p>회원가입 시 등록된 이메일로 인증번호를 전송합니다.</p>
    
    <table>
        <tr>
            <th>아이디</th>
            <td>
                <input type="text" id="memberId" name="memberId" required>
            </td>
        </tr>
        <tr>
            <th>이메일</th>
            <td>
                <input type="email" id="email" name="email" required>
                <button id="btnSendVerificationCode">인증번호 전송</button>
            </td>
        </tr>
        <tr>
        	<th></th>
        	<td>
        		<div id="sendResult" hidden="hidden"></div>
        	</td>
        </tr>
        <tr>
        	<th><span id="verificationText" hidden="hidden">인증번호</span></th>
        	<td>
        	<input type="text" id="verificationCode" name="verificationCode" required hidden="hidden">
        	<button id="btnVerifyCode" hidden="hidden">인증번호 확인</button>
        	</td>
        </tr>
        <tr>
        	<th></th>
        	<td>
        		<div id="timer" hidden="hidden"></div>
    			<div id="findResult" hidden="hidden"></div>
        	</td>
        </tr>
    </table>
    
    <div id="divPassword" hidden="hidden">
    	<p>비밀번호를 재설정합니다.</p>
    	<table>
    		<tr>
    			<th>비밀번호</th>
    			<td>
    				<input type="password" id="password" name="password" required>
    			</td>
    		</tr>
    		<tr>
    			<th>비밀번호 확인</th>
    			<td>
    				<input type="password" id="passwordConfirm" name="passwordConfirm" required >
    				<button type="button" onclick="checkPw()" hidden="hidden">비밀번호 확인</button>
    				<button id="btnUpdatePw" disabled>비밀번호 변경</button>
    			</td>
    		</tr>
    		<tr>
    			<th></th>
    			<td>
    				<span id="passwordMatchMessage"></span>
    				<span id="updateResult"></span>
    			</td>
    		</tr>
    	</table>     
    </div>
	<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
    <a href="find-id"><button type="button">아이디 찾기</button></a>
    <a href="login"><button type="button">로그인 창으로 돌아가기</button></a>

    <div id="message"></div>
</body>
</html>
