<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>
    
    <title>회원가입</title>
    <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
    <script src="https://www.google.com/recaptcha/api.js?render=6LfrNrAqAAAAANk1TA-pg2iX6Zi9mEDxF1l1kZgR"></script>
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
    <script>
    $(document).ajaxSend(function(e, xhr, opt){
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        
        xhr.setRequestHeader(header, token);
     });

        let isIdChecked = false;
        let isEmailChecked = false;
        let isPhoneChecked = false;
        let isPasswordMatched = false;
        let csrfToken = $('#csrfToken').val();

        // 정규 표현식
        const idRegex = /^[a-zA-Z0-9_-]{5,15}$/;
        const pwRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
        const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        const phoneRegex = /^01[016789]-\d{4}-\d{4}$/;

        $(document).ready(function () {
            $('form').submit(function (event) {
                event.preventDefault();
                grecaptcha.ready(function() {
                    grecaptcha.execute('6LfrNrAqAAAAANk1TA-pg2iX6Zi9mEDxF1l1kZgR', { action: 'signup' }).then(function(token) {
                        // 토큰을 hidden input에 설정
                        $('#recaptchaToken').val(token);
                        // 폼 제출
                        $('form')[0].submit();
                    });
                });
            });
        });

        function checkId() {
            let ownerId = $('#ownerId').val();
            if (!ownerId) {
                $('#ownerIdMessage').text("아이디를 입력해주세요.").css('color', 'red');
                return;
            }

            if (!idRegex.test(ownerId)) {
                $('#ownerIdMessage').text("아이디는 5~15자, 알파벳, 숫자, _ 또는 -만 사용할 수 있습니다.").css('color', 'red');
                return;
            }

            $.ajax({
                url: '../user/dup-check-id',
                type: 'POST',
                data: { ownerId: ownerId },
                success: function (response) {
                    if (response == 1) {
                        $('#ownerIdMessage').text("이미 사용 중인 아이디입니다.").css('color', 'red');
                        isIdChecked = false;
                    } else {
                        $('#ownerIdMessage').text("사용 가능한 아이디입니다.").css('color', 'green');
                        isIdChecked = true;
                    }
                    updateSubmitButton();
                },
                error: function () {
                    $('#ownerIdMessage').text("중복 확인 중 오류가 발생했습니다.").css('color', 'red');
                }
            });
        }

        function checkEmail() {
            let email = $('#email').val();
            if (!email) {
                $('#emailMessage').text("이메일을 입력해주세요.").css('color', 'red');
                return;
            }

            if (!emailRegex.test(email)) {
                $('#emailMessage').text("유효하지 않은 이메일 형식입니다.").css('color', 'red');
                return;
            }

            $.ajax({
                url: '../user/dup-check-email',
                type: 'POST',
                data: { email: email },
                success: function (response) {
                    if (response == 1) {
                        $('#emailMessage').text("이미 사용 중인 이메일입니다.").css('color', 'red');
                        isEmailChecked = false;
                    } else {
                        $('#emailMessage').text("사용 가능한 이메일입니다.").css('color', 'green');
                        isEmailChecked = true;
                    }
                    updateSubmitButton();
                },
                error: function () {
                    $('#emailMessage').text("중복 확인 중 오류가 발생했습니다.").css('color', 'red');
                }
            });
        }

        function checkPhone() {
            let phone = $('#phone').val();
            if (!phone) {
                $('#phoneMessage').text("전화번호를 입력해주세요.").css('color', 'red');
                return;
            }

            if (!phoneRegex.test(phone)) {
                $('#phoneMessage').text("전화번호는 하이픈(-)을 포함한 010, 011, 016, 017, 018, 019 형식이어야 합니다.").css('color', 'red');
                return;
            }

            $.ajax({
                url: '../user/dup-check-phone',
                type: 'POST',
                data: { phone: phone },
                success: function (response) {
                    if (response == 1) {
                        $('#phoneMessage').text("이미 사용 중인 전화번호입니다.").css('color', 'red');
                        isPhoneChecked = false;
                    } else {
                        $('#phoneMessage').text("사용 가능한 전화번호입니다.").css('color', 'green');
                        isPhoneChecked = true;
                    }
                    updateSubmitButton();
                },
                error: function () {
                    $('#phoneMessage').text("중복 확인 중 오류가 발생했습니다.").css('color', 'red');
                }
            });
        }

        function checkPw() {
            let password = $('#password').val();
            let passwordConfirm = $('#passwordConfirm').val();
            
            if (!password || !passwordConfirm) {
                $('#passwordMatchMessage').text("비밀번호를 입력해주세요.").css('color', 'red');
                return;
            }

            if (!pwRegex.test(password)) {
                $('#passwordMatchMessage').text("비밀번호는 최소 8자, 대문자, 소문자, 숫자, 특수문자를 각각 하나 이상 포함해야 합니다.").css('color', 'red');
                isPasswordMatched = false;
                return;
            }

            if (password !== passwordConfirm) {
                $('#passwordMatchMessage').text("비밀번호가 일치하지 않습니다.").css('color', 'red');
                isPasswordMatched = false;
            } else {
                $('#passwordMatchMessage').text("비밀번호가 일치합니다.").css('color', 'green');
                isPasswordMatched = true;
            }
            updateSubmitButton();
        }

        function updateSubmitButton() {
            if (isIdChecked && isEmailChecked && isPhoneChecked && isPasswordMatched) {
                $('#btnRegister').prop('disabled', false);
            } else {
                $('#btnRegister').prop('disabled', true);
            }
        }

        $('#ownerId, #email, #phone, #password, #passwordConfirm').on('input', function () {
            if (this.id === 'ownerId') {
                isIdChecked = false;
            } else if (this.id === 'email') {
                isEmailChecked = false;
            } else if (this.id === 'phone') {
                isPhoneChecked = false;
            } else if (this.id === 'password' || this.id === 'passwordConfirm') {
                isPasswordMatched = false;
            }

            updateSubmitButton();
        });
    </script>
</head>

<body>
<c:if test="${not empty message}">
        <script type="text/javascript">
            alert("${message}");
        </script>
    </c:if>
    <h2>회원가입</h2>
        <p>※ 아이디는 이후에 변경할 수 없으니, 신중하게 선택해주세요.</p>
	<form action="register" method="POST">
        <table>
			<tr>
				<th><label for="ownerId">아이디</label></th>
				<td><input type="text" id="ownerId" name="ownerId" required>
					<button type="button" onclick="checkId()">아이디 중복 확인</button> <br>
					<span id="ownerIdMessage"></span></td>
			</tr>
			<tr>
                <th><label for="password">비밀번호</label></th>
                <td>
                    <input type="password" id="password" name="password" required>
                    <br>
                </td>
            </tr>
            <tr>
                <th><label for="passwordConfirm">비밀번호 확인</label></th>
                <td>
                    <input type="password" id="passwordConfirm" name="passwordConfirm" required>
                    <button type="button" onclick="checkPw()">비밀번호 확인</button>
                    <br>
                    <span id="passwordMatchMessage"></span>
                </td>
            </tr>
            <tr>
                <th><label for="email">이메일</label></th>
                <td>
                    <input type="email" id="email" name="email" required>
                    <button type="button" onclick="checkEmail()">이메일 중복 확인</button>
                    <br>
                    <span id="emailMessage"></span>
                </td>
            </tr>
            <tr>
                <th><label for="phone">휴대폰</label></th>
                <td>
                    <input type="text" id="phone" name="phone" required>
                    <button type="button" onclick="checkPhone()">전화번호 중복 확인</button>
                    <br>
                    <span id="phoneMessage"></span>
                </td>
            </tr>
            <tr>
            	<th><label for="address">주소</label></th>
            	<td>
            		<input type="text" id="address" name="address" required>
            	</td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <button type="submit" id="btnRegister" disabled>회원가입</button>
                    <a href="../auth/login"><button type="button">로그인 창으로 돌아가기</button></a>
                </td>
            </tr>
        </table>
        <input type="hidden" name="recaptchaToken" id="recaptchaToken">
        <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
    </form>
</body>

</html>
