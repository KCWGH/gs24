<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 찾기</title>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<script>
    $(document).ready(function() {
        $("#btnSendVerificationCode").click(function() {
            let email = $("#email").val();
            if (email === "") {
                alert("이메일을 입력해주세요.");
                return;
            }
            $("#btnSendVerificationCode").prop("disabled", true);
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
                },
                error: function(xhr, status, error) {
                    let responseText = xhr.responseText;
                    if (responseText === "do not exist") {
                        $("#sendResult").html("등록된 이메일이 존재하지 않습니다. 다시 확인해주세요.");
                    } else {
                        $("#sendResult").html("이메일 전송에 실패했습니다. 다시 시도해 주세요.");
                    }
                    $("#sendResult").show();
                },
                complete: function() {
                    $("#btnSendVerificationCode").prop("disabled", false);
                }
            });
        });

        $("#btnFindId").click(function() {
            let email = $("#email").val();
            let code = $("#verificationCode").val();
            $.ajax({
                url: 'verifyCode-ID',
                type: 'POST',
                data: { email: email, code: code },
                success: function (response) {
                    $("#findResult").html("인증번호가 일치합니다. 아이디는 " + response + "입니다.");
                    $("#findResult").show();
                },
                error: function (xhr, status, error) {
                    if (xhr.status === 400) {
                        $("#findResult").html("인증번호가 일치하지 않습니다.");
                    } else {
                        alert("서버와의 통신 중 오류가 발생했습니다. 다시 시도해주세요.");
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
    
    <div id="sendResult" hidden="hidden"><br><br></div>
    
    <span id="verificationText" hidden="hidden">인증번호: </span>
    <input type="text" id="verificationCode" name="verificationCode" required hidden="hidden">
    
    <button id="btnFindId" hidden="hidden">아이디 찾기</button><br>
    
    <div id="findResult" hidden="hidden"><br><br></div>
    
    <a href="find-pw"><button type="button">비밀번호 찾기</button></a>
    <a href="login"><button type="button">로그인 창으로 돌아가기</button></a>
</body>
</html>
