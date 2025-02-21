<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>
    <title>계정 재활성화</title>
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
        gap: 10px; /* Optional: Add some space between the input and button */
        align-items: center; /* Vertically align elements in the center */
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
        	let timerInterval; // 타이머 인터벌 변수
            let remainingTime = 2 * 60; // 2분(120초)
        	
                let username = "${memberId}";
                let email = "${email}";

                // 기존 타이머를 초기화
                if (timerInterval) {
                    clearInterval(timerInterval); // 기존 타이머 정지
                }
                remainingTime = 2 * 60; // 타이머를 2분으로 초기화
                
                $("#verificationCode").prop("disabled", false);

                $.ajax({
                    url: "../user/find-pw",
                    type: "POST",
                    data: { username: username, email: email },
                    success: function(response) {
                        $("#sendResult").html("해당 계정에 등록된 이메일로 인증 코드를 보냈습니다.");
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


            $("#btnUpdatePw").prop('disabled', true);

            $("#btnVerifyCode").click(function(event){
            	event.preventDefault();
    			let code = $("#verificationCode").val();
    			code = code.replace(/\s+/g, '').replace(/\D/g, '');
    			
    			$.ajax({
    			    url: '../user/verifyCode-PW',
    			    type: 'POST',
    			    contentType: 'application/json',
    			    data: JSON.stringify({
    			        email: email,
    			        code: code
    			    }),
    			    success: function (response) {
    			    	$.ajax({
    			    	    url: 'activate',
    			    	    type: 'POST',
    			    	    success: function(response) {
    			    	    	alert("비활성화 해제 완료. 다시 로그인해주세요.");
    			    	        window.location.href = '../auth/login';
    			    	    },
    			    	    error: function(xhr, status, error) {
    			    	        alert("계정 활성화에 실패했습니다. 다시 시도해주세요.");
    			    	    }
    			    	});
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
<c:if test="${not empty message}">
    <script type="text/javascript">
        alert("${message}");
    </script>
</c:if>
<div class="card">
    <h2>계정 재활성화</h2>
    <table>
        <tr>
        	<th></th>
        	<td>
        		<div id="sendResult" hidden="hidden"></div>
        	</td>
        </tr>
        <tr>
        	<th><span id="verificationText" hidden="hidden">인증번호</span></th>
        	<td>
        	<div class="verification-container">
        	<input type="text" id="verificationCode" name="verificationCode" required hidden="hidden">
        	<form action="../member/activate" method="POST">
        	<button id="btnVerifyCode" hidden="hidden">인증번호 확인</button>
        	</form>
        	</div>
        	</td>
        </tr>
        <tr>
        	<th></th>
        	<td>
        		<div id="timer" hidden="hidden"></div>
        	</td>
        </tr>
        <tr>
        	<th></th>
        	<td>
    			<a href="../auth/login"><button type="button">로그인 창으로 돌아가기</button></a>
        	</td>
        </tr>
    </table>
	<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
</div>
</body>
</html>
