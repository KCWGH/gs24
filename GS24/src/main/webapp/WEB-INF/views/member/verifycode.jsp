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
        
        <input type="submit" value="비밀번호 재설정">
        <a href="login"><button type="button">로그인 창으로 돌아가기</button></a>
    </form>
    <div id="message"></div>

    <script>
        $(document).ready(function() {
            $("#verifyCodeForm").submit(function(e) {
                e.preventDefault();
                let memberId = $("#memberId").val();
                let verificationCode = $("#verificationCode").val();
                $("#message").html('인증번호가 이메일로 전송되었습니다. 이메일을 확인해주세요');
                
                $.ajax({
                    url: 'findpw/verifycode', 
                    type: 'POST',
                    data: { memberId: memberId, verificationCode: verificationCode },
                    dataType: 'json',
                    success: function(response) {
                        if (response.success === true) {
                            
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
