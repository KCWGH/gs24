<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>비밀번호 찾기 - 인증번호 입력</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <h2>인증번호 입력</h2>
    <form id="verifyCodeForm">
        <label for="memberId">회원 ID:</label>
        <input type="text" id="memberId" name="memberId" value="" required><br><br>
        
        <label for="verificationCode">인증번호:</label>
        <input type="text" id="verificationCode" name="verificationCode" required><br><br>
        
        <input type="submit" value="비밀번호 확인">
        <a href="login"><button type="button">로그인 창으로 돌아가기</button></a>
    </form>
    <div id="message"></div>

    <script>
        $(document).ready(function() {
            $("#verifyCodeForm").submit(function(e) {
                e.preventDefault(); // 폼 제출을 막고 Ajax로 처리
                let memberId = $("#memberId").val();
                let verificationCode = $("#verificationCode").val();
                $("#message").html('인증번호가 이메일로 전송되었습니다. 이메일을 확인해주세요');
                
                $.ajax({
                    url: 'findpw/verifycode', // 인증번호 검증 및 비밀번호 확인 URL
                    type: 'POST',
                    data: { memberId: memberId, verificationCode: verificationCode },
                    dataType: 'json', // 서버에서 반환된 데이터를 JSON으로 파싱
                    success: function(response) {
                        if (response.success === true) { // 성공 여부 확인
                            let messageContent = '<p>당신의 비밀번호는 아래와 같습니다.</p>' +
                                                 '<input type="password" id="passwordField" value="' + response.password + '" readonly>' +
                                                 '<button id="togglePassword">비밀번호 보기</button>';
                            $("#message").html(messageContent);

                            $("#togglePassword").click(function() {
                                let passwordField = $("#passwordField");
                                if (passwordField.attr("type") === "password") {
                                    passwordField.attr("type", "text");
                                    $(this).text("비밀번호 숨기기");
                                } else {
                                    passwordField.attr("type", "password");
                                    $(this).text("비밀번호 보기");
                                }
                            });
                        } else {
                            $("#message").html('<p>인증번호가 잘못되었습니다.</p>');
                        }
                    },
                    error: function(xhr, status, error) {
                        $("#message").html('<p>서버 오류가 발생했습니다. 다시 시도해주세요.</p>');
                        console.error("Error: ", error);
                    }
                });
            });
        });
    </script>
</body>
</html>
