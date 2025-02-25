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
    <style>
        /* Body의 전체를 중앙 정렬 */
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            margin: 0;
            background-color: #f4f4f4;
        }

        /* Card 스타일 */
        .card {
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            width: 80%;
            max-width: 600px;
            padding: 20px;
            background-color: #fff;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
        }
        .verification-container {
        display: flex;
        gap: 10px;
        align-items: center;
    	}
    	table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }

        th, td {
            padding: 8px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #f1f1f1;
            color: #333;
            font-size: 14px;
            text-align: center;
        }

        td {
            font-size: 14px;
        }
        input[type="text"], input[type="password"], input[type="email"] {
            width: 50%;
            padding: 5px;
            border: 1px solid #ddd;
            border-radius: 5px;
            text-align: center;
            font-size: 13px;
        }
        button {
            padding: 7px 10px;
            font-size: 13px;
            border: none;
            background: #ddd;
            border-radius: 5px;
            cursor: pointer;
            margin: 5px;
        }

        button:hover {
            background: #bbb;
        }
    </style>
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
            const username = urlParams.get('username');
            
            if (username) {
                $("#username").val(username);
            }
            
            let timerInterval;
            let remainingTime = 2 * 60;
        	
            function checkPw() {
                let password = $('#password').val();
                let passwordConfirm = $('#passwordConfirm').val();
                
                if (!pwRegex.test(password)) {
                	$('#passwordMatchMessage').html("비밀번호는 최소 8자, 대문자, 소문자, 숫자, 특수문자를<br>각각 하나 이상 포함해야 합니다.").css('color', 'red');
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
                let username = $("#username").val();
                let email = $("#email").val();

                if (email === "" || username === "") {
                    alert("아이디와 이메일을 모두 기입해주세요.");
                    return;
                }

                $("#btnSendVerificationCode").prop("disabled", true);

                if (timerInterval) {
                    clearInterval(timerInterval);
                }
                remainingTime = 2 * 60;

                $("#verificationCode").prop("disabled", false);

                $.ajax({
                    url: "find-pw",
                    type: "POST",
                    data: { username: username, email: email },
                    success: function(response) {
                        $("#sendResult").html("해당 이메일로 인증 코드를 보냈습니다.");
                        $("#sendResult").show();

                        var verificationRow = 
                            '<tr id="verificationRow">' +
                                '<th>인증번호</th>' +
                                '<td>' +
                                    '<div class="verification-container">' +
                                        '<input type="text" id="verificationCode" name="verificationCode" required>' +
                                        '<button id="btnVerifyCode">인증번호 확인</button>' +
                                    '</div>' +
                                        '<div id="timer"></div>' +
                                        '<div id="findResult"></div>' +
                                '</td>' +
                            '</tr>';

                        $("table").append(verificationRow);

                        startTimer();
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
            $(document).on('input', '#password, #passwordConfirm', checkPw);

            $(document).on("click", "#btnVerifyCode", function() {
                event.preventDefault();
                let username = $("#username").val();
                let email = $("#email").val();
                let code = $("#verificationCode").val();
                code = code.replace(/\s+/g, '').replace(/\D/g, '');

                $.ajax({
                    url: 'verifyCode-PW',
                    type: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify({
                        email: email,
                        code: code
                    }),
                    success: function (response) {
                        var passwordFormHtml = 
                                    '<tr>' +
                                        '<th>비밀번호</th>' +
                                        '<td>' +
                                            '<span style="color:green;">비밀번호를 재설정합니다.</span><br><input type="password" id="password" name="password" required>' +
                                        '</td>' +
                                    '</tr>' +
                                    '<tr>' +
                                        '<th>비밀번호<br>확인</th>' +
                                        '<td>' +
                                            '<input type="password" id="passwordConfirm" name="passwordConfirm" required>' +
                                            '<button type="button" onclick="checkPw()" hidden="hidden">비밀번호 확인</button>' +
                                            '<button id="btnUpdatePw" disabled>비밀번호 변경</button><br><span id="passwordMatchMessage"></span> <span id="updateResult"></span><br><div id="message"></div>' +
                                        '</td>' +
                                    '</tr>' +
                            '</div>';
                        $("table").append(passwordFormHtml);
                        $("#findResult").html("인증번호가 일치합니다.");
                        $("#findResult").show();
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

            $(document).on("click", "#btnUpdatePw", function() {
            	event.preventDefault();
            	let username = $("#username").val();
            	let password = $("#password").val();
            	let email = $("#email").val();
            	let data = {username: username, email: email, password: password};
            	$.ajax({
        			url: "find-update-pw",
    	            type: "POST",
    	            data: data,
    	            success: function (response) {
    	            	$('#findResult').prop("hidden", false);
                        if (response === "Update Success") {
                        	window.location.href = "../auth/login";
                        	alert('비밀번호가 변경되었습니다. 해당 아이디와 비밀번호로 로그인하세요.');
                        } else {
                        	$('#updateResult').text('비밀번호 변경 실패. 해당 이메일로 등록된 아이디가 아닙니다.');
                        }
                    },
        		}); 
            });
            
            function startTimer() {
                timerInterval = setInterval(function() {
                    let minutes = Math.floor(remainingTime / 60);
                    let seconds = remainingTime % 60;
                    $("#timer").text("남은 시간: " + minutes + "분 " + seconds + "초");
                    remainingTime--;

                    if (remainingTime < 0) {
                        clearInterval(timerInterval);
                        $("#timer").text("인증번호가 만료되었습니다.");
                        $("#verificationCode").prop("disabled", true);
                        $("#btnSendVerificationCode").prop("disabled", false);
                    }
                }, 1000);
            }
        });
    </script>
</head>
<body>
<div class="card">
    <h2>비밀번호 찾기</h2>
    <p>회원가입 시 등록된 이메일로 인증번호를 전송합니다.</p>
    
    <table>
        <tr>
            <th>아이디</th>
            <td>
                <input type="text" id="username" name="username" required>
            </td>
        </tr>
        <tr>
            <th>이메일</th>
            <td>
                <input type="email" id="email" name="email" required>
                <button id="btnSendVerificationCode">인증번호 전송</button><br>
                <div id="sendResult" hidden="hidden"></div>
            </td>
        </tr>
    </table>
	<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
    <div style="display: flex; gap: 10px; justify-content: center; margin-top: 20px;">
        <a href="find-id"><button type="button">아이디 찾기</button></a>
    	<a href="../auth/login"><button type="button">로그인 창으로 돌아가기</button></a>
    </div>
</div>
</body>
</html>
