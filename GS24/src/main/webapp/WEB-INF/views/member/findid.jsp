<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 찾기</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    $(document).ready(function() {
        $("form").on("submit", function(event) {
            event.preventDefault();
            let email = $("#email").val();
            if(email) {
                $.ajax({
                    url: "findid",
                    type: "POST",
                    data: { email: email },
                    success: function(response) {
                        if (response) {
                            $("#memberIdArea").text("찾은 아이디: " + response).show();
                            
                        } else {
                            $("#memberIdArea").text("해당 이메일로 등록된 아이디가 없습니다.").show();
                        }
                    }
                });
            } else {
                alert("이메일을 입력해주세요.");
            }
        });
    });
</script>
</head>
<body>
    <h2>아이디 찾기</h2>
    <form action="findid" method="POST">
        <span>이메일: </span><input type="email" id="email" name="email" required>
        <input type="submit" value="아이디 찾기">
    </form>
    <p id="memberIdArea"></p> 
    <a href="findpw"><button type="button">비밀번호 찾기</button></a>
    <a href="login"><button type="button">로그인 창으로 돌아가기</button></a>
</body>
</html>
