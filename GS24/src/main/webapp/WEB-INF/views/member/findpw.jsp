<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>비밀번호 찾기</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <h2>비밀번호 찾기</h2>
<<<<<<< HEAD
    <form id="forgotPasswordForm">
        <label for="memberId">회원 ID:</label>
        <input type="text" id="memberId" name="memberId" required><br>
        
        <label for="email">이메일 주소:</label>
        <input type="email" id="email" name="email" required>
        
        <input type="submit" value="인증번호 전송"><br>
        <a href="login"><button type="button">로그인 창으로 돌아가기</button></a>
=======
    <form action="findid" method="POST">
    	아이디: <input type="text" id="memberId" name="memberId" required><br>
        이메일: <input type="email" id="email" name="email" required>
        <input type="submit" value="비밀번호 찾기">
>>>>>>> c366c08dc1ff87280f5da0a1dbabf6a230862cb9
    </form>
    <div id="message"></div>

    <script>
        $(document).ready(function() {
            $("#forgotPasswordForm").submit(function(e) {
                e.preventDefault(); // 폼 제출을 막고 Ajax로 처리
                let memberId = $("#memberId").val();
                let email = $("#email").val();

                $.ajax({
                    url: 'findpw/sendcode', // 인증번호 전송 URL
                    type: 'POST',
                    data: { memberId: memberId, email: email },
                    dataType: 'json', // 서버에서 반환된 데이터를 JSON으로 파싱
                    success: function(response) {
                        // JSON 데이터 처리
                        if (response.success === true) {
                            $("#message").html('<p>인증번호가 이메일로 전송되었습니다.</p>');
                            window.location.href = 'verifycode'; // 인증번호 입력 페이지로 리디렉션
                        } else {
                            $("#message").html('<p>회원 ID 또는 이메일이 잘못되었습니다.</p>');
                        }
                    },
                    error: function(xhr, status, error) {
                        // 오류 처리
                        $("#message").html('<p>서버 오류가 발생했습니다. 다시 시도해주세요.</p>');
                        console.error("Error: ", error);
                    }
                });
            });
        });
    </script>
</body>
</html>
