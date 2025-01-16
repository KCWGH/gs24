<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>
    <title>아이디 찾기</title>
    <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
    <script>
    $(document).ajaxSend(function(e, xhr, opt){
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        
        xhr.setRequestHeader(header, token);
     });
        $(document).ready(function() {
            let timerInterval;
            let remainingTime = 2 * 60;

            $("#btnSendVerificationCode").click(function() {
                let email = $("#email").val();
                if (email === "") {
                    alert("이메일을 입력해주세요.");
                    return;
                }
                $("#btnSendVerificationCode").prop("disabled", true);
                
                if (timerInterval) {
                    clearInterval(timerInterval); // 기존 타이머 정지
                }
                remainingTime = 2 * 60; // 타이머를 2분으로 초기화
                
                $("#verificationCode").prop("disabled", false);
                
                $.ajax({
                    url: 'find-id',
                    type: 'POST',
                    data: { email: email },
                    success: function(response) {
                        $("#sendResult").html("해당 이메일로 인증 코드를 보냈습니다.");
                        $("#sendResult").show();
                        $("#verificationText").show();
                        $("#verificationCode").show();
                        $("#btnFindId").show();
                        startTimer();
                        $("#timer").show();
                    },
                    error: function(xhr, status, error) {
                        let responseText = xhr.responseText;
                        if (responseText === "do not exist") {
                            $("#sendResult").html("등록된 이메일이 존재하지 않습니다. 다시 확인해주세요.");
                        } else {
                            $("#sendResult").html("이메일 전송에 실패했습니다. 다시 시도해 주세요.");
                        }
                        $("#sendResult").show();
                    }
                });
            });

            $("#btnFindId").click(function() {
                let email = $("#email").val();
                let code = $("#verificationCode").val();
                $.ajax({
                    url: 'verifyCode-ID',
                    type: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify({ email: email, code: code }),
                    success: function(response) {
                        $("#findResult").html("인증번호가 일치합니다. 아이디는 " + response + " 입니다.");
                        $("#findResult").show();
                        let findPwUrl = 'find-pw?memberId=' + encodeURIComponent(response);
                        $("#btnFindPw").attr("onclick", "location.href='" + findPwUrl + "'");
                        $("#timer").hide();
                    },
                    error: function(xhr, status, error) {
                        if (xhr.status == 400) {
                            $("#findResult").html("인증번호가 일치하지 않거나 만료되었습니다.");
                            $("#findResult").show();
                        } else {
                            alert("서버와의 통신 중 오류가 발생했습니다. 다시 시도해주세요.");
                        }
                    }
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
    <h2>아이디 찾기</h2>
    <p>회원가입 시 등록된 이메일로 인증번호를 전송합니다.</p>
    <table>
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
                <button id="btnFindId" hidden="hidden">아이디 찾기</button>
            </td>
        </tr>
        <tr>
            <th></th>
            <td>
                <div id="findResult" hidden="hidden"></div>
                <div id="timer" hidden="hidden"></div>
            </td>
        </tr>
    </table>
    <br>
    <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
    <button id="btnFindPw" onclick='location.href="find-pw"'>비밀번호 찾기</button>
    <a href="login"><button type="button">로그인 창으로 돌아가기</button></a>
</body>
</html>
