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
    <link rel="stylesheet" href="../resources/css/fonts.css">
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
        	const today = new Date();
            const sevenYearsAgo = new Date();
            sevenYearsAgo.setFullYear(today.getFullYear() - 7);

            $("#birthdayContainer").datepicker({
                dateFormat: "yy-mm-dd",
                changeMonth: true,
                inline: true,
                changeYear: true,
                showMonthAfterYear: true,
                yearRange: "1900:2025",
                maxDate: sevenYearsAgo,
                onSelect: function (dateText) {
                    $('#birthdayMessage').text("");
                    $('#birthday').val(dateText);
                }
            });

            $('form').submit(function (event) {
                let birthday = $('#birthday').val();
                if (!birthday) {
                    event.preventDefault();
                	$('#birthdayMessage').text("생일을 선택해주세요.").css('color', 'red');
                    return;
                }
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
            let memberId = $('#memberId').val();
            console.log('파라미터값은 ' + memberId);
            if (!memberId) {
                $('#memberIdMessage').text("아이디를 입력해주세요.").css('color', 'red');
                return;
            }

            if (!idRegex.test(memberId)) {
                $('#memberIdMessage').text("아이디는 5~15자, 알파벳, 숫자, _ 또는 -만 사용할 수 있습니다.").css('color', 'red');
                return;
            }

            $.ajax({
                url: '../user/dup-check-id',
                type: 'POST',
                data: { memberId: memberId },
                success: function (response) {
                    if (response == 1) {
                        $('#memberIdMessage').text("이미 사용 중인 아이디입니다.").css('color', 'red');
                        isIdChecked = false;
                    } else {
                        $('#memberIdMessage').text("사용 가능한 아이디입니다.").css('color', 'green');
                        isIdChecked = true;
                    }
                    updateSubmitButton();
                },
                error: function () {
                    $('#memberIdMessage').text("중복 확인 중 오류가 발생했습니다.").css('color', 'red');
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
            
            phone = phone.replace(/[^0-9]/g, '');
            if (phone.length === 11) {
                phone = phone.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
            } else if (phone.length === 10) {
                phone = phone.replace(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3');
            }
            
            $('#phone').val(phone);
            
            if (!phone) {
                $('#phoneMessage').text("전화번호를 입력해주세요.").css('color', 'red');
                return;
            }

            if (!phoneRegex.test(phone)) {
                $('#phoneMessage').text("전화번호는 010, 011, 016, 017, 018, 019 형식이어야 합니다.").css('color', 'red');
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

        $('#memberId, #email, #phone, #password, #passwordConfirm').on('input', function () {
            if (this.id === 'memberId') {
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
    <style>
        /* Body의 전체를 중앙 정렬 */
        body {
        	font-family: 'Pretendard-Regular', sans-serif;
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
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }

        th, td {
        	font-size: 16px;
            padding: 8px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #f1f1f1;
            color: #333;
            text-align: center;
        }
        input[type="text"], input[type="password"], input[type="email"] {
        	font-family: 'Pretendard-Regular', sans-serif;
            width: 50%;
            padding: 5px;
            border: 1px solid #ddd;
            border-radius: 5px;
            text-align: center;
        }
        button {
        	font-family: 'Pretendard-Regular', sans-serif;
            padding: 7px 10px;
            border: none;
            background: #ddd;
            border-radius: 5px;
            cursor: pointer;
            margin: 5px;
        }

        button:hover {
            background: #bbb;
        }
    </style>
</head>

<body>
<c:if test="${not empty message}">
    <script type="text/javascript">
        alert("${message}");
    </script>
</c:if>

<div class="card">
    <h2>회원가입</h2>
    <form action="register" method="POST">
        <table>
        	<tr>
        		<td colspan="2">※ 아이디와 생일은 이후에 변경할 수 없으니, 신중하게 선택해주세요.</td>
        	</tr>
            <tr>
                <th><label for="memberId">아이디</label></th>
                <td><input type="text" id="memberId" name="memberId" required>
                    <button type="button" onclick="checkId()">아이디 중복 확인</button> <br>
                    <span id="memberIdMessage"></span></td>
            </tr>
        	<tr>
                <th><label for="nickname">닉네임</label></th>
                <td>
                    <input type="text" id="nickname" name="nickname" required>
                </td>
            </tr>
            <tr>
                <th><label for="password">비밀번호</label></th>
                <td>
                    <input type="password" id="password" name="password" required>
                    <br>
                </td>
            </tr>
            <tr>
                <th><label for="passwordConfirm">비밀번호<br>확인</label></th>
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
            <tr id="birthdayRow">
                <th><label for="birthday">생일</label></th>
                <td>
                    <div id="birthdayContainer"></div>
                    <input type="hidden" id="birthday" name="birthday" required>
                    <span id="birthdayMessage" style="color:gray">※ 7세 이상만 회원가입이 가능합니다.</span>
                </td>
            </tr>
        </table>
                    <button type="submit" id="btnRegister" disabled>회원가입</button>
                    <a href="../auth/login"><button type="button">로그인 창으로 돌아가기</button></a>
        <input type="hidden" name="recaptchaToken" id="recaptchaToken">
        <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
    </form>
</div>

</body>

</html>
