<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입</title>
    <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
    <script>
    let isIdChecked = false;
    let isEmailChecked = false;
    let isPhoneChecked = false;
    let isPasswordMatched = false;

    function checkId() {
        let memberId = $('#memberId').val();
        if (!memberId) {
            alert("아이디를 입력해주세요.");
            return;
        }

        $.ajax({
            url: 'dup-check-id',
            type: 'POST',
            data: { memberId: memberId },
            success: function(response) {
                if (response == 1) {
                    alert("이미 사용 중인 아이디입니다.");
                    isIdChecked = false;
                } else {
                    alert("사용 가능한 아이디입니다.");
                    isIdChecked = true;
                }
                updateSubmitButton();
            },
            error: function() {
                alert("중복 확인 중 오류가 발생했습니다.");
            }
        });
    }

    function checkEmail() {
        let email = $('#email').val();
        if (!email) {
            alert("이메일을 입력해주세요.");
            return;
        }

        $.ajax({
            url: 'dup-check-email',
            type: 'POST',
            data: { email: email },
            success: function(response) {
                if (response == 1) {
                    alert("이미 사용 중인 이메일입니다.");
                    isEmailChecked = false;
                } else {
                    alert("사용 가능한 이메일입니다.");
                    isEmailChecked = true;
                }
                updateSubmitButton();
            },
            error: function() {
                alert("중복 확인 중 오류가 발생했습니다.");
            }
        });
    }

    function checkPhone() {
        let phone = $('#phone').val();
        if (!phone) {
            alert("전화번호를 입력해주세요.");
            return;
        }

        $.ajax({
            url: 'dup-check-phone',
            type: 'POST',
            data: { phone: phone },
            success: function(response) {
                if (response == 1) {
                    alert("이미 사용 중인 전화번호입니다.");
                    isPhoneChecked = false;
                } else {
                    alert("사용 가능한 전화번호입니다.");
                    isPhoneChecked = true;
                }
                updateSubmitButton();
            },
            error: function() {
                alert("중복 확인 중 오류가 발생했습니다.");
            }
        });
    }

    function checkPw() {
        let password = $('#password').val();
        let passwordConfirm = $('#passwordConfirm').val();

        if (password !== passwordConfirm) {
            $('#passwordMatchMessage').text("비밀번호가 일치하지 않습니다.").css('color', 'red');
            isPasswordMatched = false;
        } else {
            $('#passwordMatchMessage').text("비밀번호가 일치합니다.").css('color', 'green');
            isPasswordMatched = true;
        }
        updateSubmitButton();
    }

<<<<<<< Updated upstream
    function updateSubmitButton() {
        if (isIdChecked && isEmailChecked && isPhoneChecked && isPasswordMatched) {
            $('#btnRegister').prop('disabled', false);
        } else {
            $('#btnRegister').prop('disabled', true);
        }
    }

    // 모든 필드에서 입력 값이 변경되면 상태를 갱신
    $('#memberId, #email, #phone, #password, #passwordConfirm').on('input', function() {
        if (this.id === 'memberId') {
            isIdChecked = false;
        } else if (this.id === 'email') {
            isEmailChecked = false;
        } else if (this.id === 'phone') {
            isPhoneChecked = false;
        } else if (this.id === 'password' || this.id === 'passwordConfirm') {
            isPasswordMatched = false;
        }

        updateSubmitButton();  // 버튼 갱신
    });

    // 비밀번호 확인
    $('#password, #passwordConfirm').on('input', function() {
        checkPw();
    });

=======
        function updateSubmitButton() {

            if (isIdChecked && isEmailChecked && isPhoneChecked && isPasswordMatched) {
                $('#registerBtn').prop('disabled', false);
            } else {
                $('#registerBtn').prop('disabled', true);
            }
        }

        $('#memberId').on('input', function() {
            isIdChecked = false;
            updateSubmitButton();
            // 모든 조건이 만족되면 버튼을 활성화
            if (isIdChecked && isEmailChecked && isPhoneChecked && isPasswordMatched) {
                $('#btnRegister').prop('disabled', false);
            } else {
                $('#btnRegister').prop('disabled', true);
            }
        }
            // 모든 조건이 만족되면 버튼을 활성화
            if (isIdChecked && isEmailChecked && isPhoneChecked && isPasswordMatched) {
                $('#btnRegister').prop('disabled', false);
            } else {
                $('#btnRegister').prop('disabled', true);
            }
        }

        // 입력 필드에 값이 변경되면 회원가입 버튼 비활성화
        $('#memberId, #email, #phone, #password, #passwordConfirm').on('input', function() {
            // 필드 값이 수정되면 해당 필드에 맞는 상태를 false로 초기화
            if (this.id === 'memberId') {
                isIdChecked = false;
            } else if (this.id === 'email') {
                isEmailChecked = false;
            } else if (this.id === 'phone') {
                isPhoneChecked = false;
            } else if (this.id === 'password' || this.id === 'passwordConfirm') {
                isPasswordMatched = false;
            }

            // 버튼을 비활성화
            $('#btnRegister').prop('disabled', true);
        });

        // 비밀번호 확인
        $('#password, #passwordConfirm').on('input', function() {
            checkPw();
        });

        $('#password, #passwordConfirm').on('input', function() {
            checkPw();
        });
>>>>>>> Stashed changes
    </script>
</head>
<body>
    <h2>회원가입</h2>

    <form action="register" method="POST">
    	아이디와 생일은 변경할 수 없으니, 신중하게 선택해주세요.
        <div>
            <label for="memberId">아이디</label>
            <input type="text" id="memberId" name="memberId" required>
            <button type="button" onclick="checkId()">아이디 중복 확인</button>
        </div>

        <div>
            <label for="password">비밀번호</label>
            <input type="password" id="password" name="password" required>
        </div>
        
        <div>
            <label for="passwordConfirm">비밀번호 확인</label>
            <input type="password" id="passwordConfirm" name="passwordConfirm" required>
            <button type="button" onclick="checkPw()">비밀번호 확인</button>
            <span id="passwordMatchMessage"></span>
        </div>
        
        <div>
            <label for="email">이메일</label>
            <input type="email" id="email" name="email" required>
            <button type="button" onclick="checkEmail()">이메일 중복 확인</button>
        </div>

        <div>
            <label for="phone">휴대폰</label>
            <input type="text" id="phone" name="phone" required>
            <button type="button" onclick="checkPhone()">전화번호 중복 확인</button>
        </div>

        <div>
            <label for="birthday">생일</label>
            <input type="date" id="birthday" name="birthday" required>
        </div>

        <div>
            <label>계정 유형</label><br>
            <input type="radio" id="memberRole1" name="memberRole" value="1" required>
            <label for="memberRole1">일반회원</label><br>
            <input type="radio" id="memberRole2" name="memberRole" value="2">
            <label for="memberRole2">점주</label>
        </div>

        <div>
<<<<<<< Updated upstream
            <button type="submit" id="btnRegister" disabled>회원가입</button>
=======
            <button type="submit" id="registerBtn" disabled>회원가입</button>

>>>>>>> Stashed changes
            <a href="login"><button type="button">로그인 창으로 돌아가기</button></a>
        </div>
    </form>
</body>
</html>
