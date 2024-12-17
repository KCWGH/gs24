<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        // 중복 확인 상태
        let isIdChecked = false;
        let isEmailChecked = false;
        let isPhoneChecked = false;

        // 아이디 중복 확인
        function checkId() {
            let memberId = $('#memberId').val();
            if (!memberId) {
                alert("아이디를 입력해주세요.");
                return;
            }

            $.ajax({
                url: 'dupcheckid',
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
                    updateSubmitButton();  // 중복 확인 상태에 따라 버튼 활성화 여부 갱신
                },
                error: function() {
                    alert("중복 확인 중 오류가 발생했습니다.");
                }
            });
        }

        // 이메일 중복 확인
        function checkEmail() {
            let email = $('#email').val();
            if (!email) {
                alert("이메일을 입력해주세요.");
                return;
            }

            $.ajax({
                url: 'dupcheckemail',
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
                    updateSubmitButton();  // 중복 확인 상태에 따라 버튼 활성화 여부 갱신
                },
                error: function() {
                    alert("중복 확인 중 오류가 발생했습니다.");
                }
            });
        }

        // 전화번호 중복 확인
        function checkPhone() {
            let phone = $('#phone').val();
            if (!phone) {
                alert("전화번호를 입력해주세요.");
                return;
            }

            $.ajax({
                url: 'dupcheckphone',
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
                    updateSubmitButton();  // 중복 확인 상태에 따라 버튼 활성화 여부 갱신
                },
                error: function() {
                    alert("중복 확인 중 오류가 발생했습니다.");
                }
            });
        }

        // 중복 체크가 모두 완료되었을 때 회원 가입 버튼을 활성화
        function updateSubmitButton() {
            if (isIdChecked && isEmailChecked && isPhoneChecked) {
                $('#registerBtn').prop('disabled', false);  // 버튼 활성화
            } else {
                $('#registerBtn').prop('disabled', true);  // 버튼 비활성화
            }
        }

        // 값이 수정되면 중복 체크를 다시 요구
        $('#memberId').on('input', function() {
            isIdChecked = false;
            updateSubmitButton();
        });

        $('#email').on('input', function() {
            isEmailChecked = false;
            updateSubmitButton();
        });

        $('#phone').on('input', function() {
            isPhoneChecked = false;
            updateSubmitButton();
        });
    </script>
</head>
<body>
    <h2>회원가입</h2>

    <form action="register" method="POST">
    	아이디와 생일은 변경할 수 없으니, 신중하게 선택해주세요.
        <!-- ID -->
        <div>
            <label for="memberId">아이디</label>
            <input type="text" id="memberId" name="memberId" required>
            <button type="button" onclick="checkId()">아이디 중복 확인</button>
        </div>

        <!-- 비밀번호 -->
        <div>
            <label for="password">비밀번호</label>
            <input type="password" id="password" name="password" required>
        </div>

        <!-- 이메일 -->
        <div>
            <label for="email">이메일</label>
            <input type="email" id="email" name="email" required>
            <button type="button" onclick="checkEmail()">이메일 중복 확인</button>
        </div>

        <!-- 휴대폰 -->
        <div>
            <label for="phone">휴대폰</label>
            <input type="text" id="phone" name="phone" required>
            <button type="button" onclick="checkPhone()">전화번호 중복 확인</button>
        </div>

        <!-- 생일 -->
        <div>
            <label for="birthday">생일</label>
            <input type="date" id="birthday" name="birthday">
        </div>

        <!-- 계정 유형 (라디오 버튼) -->
        <div>
            <label>계정 유형</label><br>
            <input type="radio" id="memberRole1" name="memberRole" value="1" required>
            <label for="memberRole1">일반회원</label><br>
            <input type="radio" id="memberRole2" name="memberRole" value="2">
            <label for="memberRole2">점주</label>
        </div>

        <div>
            <button type="submit" id="registerBtn" disabled>회원가입</button> <!-- 기본적으로 비활성화 -->
            <a href="login"><button type="button">로그인 창으로 돌아가기</button></a>
        </div>
    </form>
</body>
</html>
