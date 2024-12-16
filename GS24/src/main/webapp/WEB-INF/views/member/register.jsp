<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>회원가입</title>
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
	$(document).ready(function() {
		let isIdChecked = false;
		let isEmailChecked = false;
		let isPhoneChecked = false;

		// 아이디 중복체크
		$("#btnDupCheckId").click(function() {
			let memberId = $("#memberId").val();

			if (!memberId) {
				alert("아이디를 입력해주세요.");
				return;
			}

			$.ajax({
				url : "dupcheckid",
				type : "POST",
				data : {
					memberId : memberId
				},
				success : function(response) {
					if (response === "1") {
						alert("이미 사용 중인 아이디입니다.");
						isIdChecked = false;
					} else if (response === "0") {
						alert("사용 가능한 아이디입니다.");
						isIdChecked = true;
					} else {
						alert("알 수 없는 응답입니다.");
						isIdChecked = false;
					}
				},
				error : function() {
					alert("아이디 중복체크 중 오류가 발생했습니다.");
					isIdChecked = false;
				}
			});
		});

		// 이메일 중복체크
		$("#btnDupCheckEmail").click(function() {
			let email = $("#email").val();

			if (!email) {
				alert("이메일을 입력해주세요.");
				return;
			}

			$.ajax({
				url : "dupcheckemail",
				type : "POST",
				data : {
					email : email
				},
				success : function(response) {
					if (response === "1") {
						alert("이미 사용 중인 이메일입니다.");
						isEmailChecked = false;
					} else if (response === "0") {
						alert("사용 가능한 이메일입니다.");
						isEmailChecked = true;
					} else {
						alert("알 수 없는 응답입니다.");
						isEmailChecked = false;
					}
				},
				error : function() {
					alert("이메일 중복체크 중 오류가 발생했습니다.");
					isEmailChecked = false;
				}
			});
		});

		// 폰 중복체크
		$("#btnDupCheckPhone").click(function() {
			let phone = $("#phone").val();

			if (!phone) {
				alert("전화번호를 입력해주세요.");
				return;
			}

			$.ajax({
				url : "dupcheckphone",
				type : "POST",
				data : {
					phone : phone
				},
				success : function(response) {
					if (response === "1") {
						alert("이미 사용 중인 전화번호입니다.");
						isPhoneChecked = false;
					} else if (response === "0") {
						alert("사용 가능한 전화번호입니다.");
						isPhoneChecked = true;
					} else {
						alert("알 수 없는 응답입니다.");
						isPhoneChecked = false;
					}
				},
				error : function() {
					alert("전화번호 중복체크 중 오류가 발생했습니다.");
					isPhoneChecked = false;
				}
			});
		});
		
		// 건들면 초기화
		$("#memberId").on("input", function() {
			isIdChecked = false;
		});
		$("#email").on("input", function() {
			isEmailChecked = false;
		});
		$("#phone").on("input", function() {
			isPhoneChecked = false;
		});
		$("form").submit(function(event) {
			if (!isIdChecked) {
				alert("아이디 중복체크를 완료해주세요.");
				event.preventDefault();
				return;
			}
			if (!isEmailChecked) {
				alert("이메일 중복체크를 완료해주세요.");
				event.preventDefault();
				return;
			}
			if (!isPhoneChecked) {
				alert("전화번호 중복체크를 완료해주세요.");
				event.preventDefault();
				return;
			}
			alert("회원가입을 진행합니다.");
		});
	});
</script>

<body>
	<h2>회원가입</h2>

	<form action="register" method="POST">
		<p>아이디 및 생일은 가입 이후에 변경이 불가능합니다. 정확하게 입력해주세요.</p>
		<div>
			<label for="memberId">아이디</label> <input type="text" id="memberId"
				name="memberId" required>
			<button type="button" id="btnDupCheckId">중복체크</button>
		</div>

		<div>
			<label for="password">비밀번호</label> <input type="password"
				id="password" name="password" required>
		</div>

		<div>
			<label for="email">이메일</label> <input type="email" id="email"
				name="email" required>
			<button type="button" id="btnDupCheckEmail">중복체크</button>
		</div>

		<div>
			<label for="phone">휴대폰</label> <input type="text" id="phone"
				name="phone" required>
			<button type="button" id="btnDupCheckPhone">중복체크</button>
		</div>

		<div>
			<label for="birthday">생일</label> <input type="date" id="birthday"
				name="birthday">
		</div>

		<div>
			<label>계정 유형</label><br> <input type="radio" id="memberRole1"
				name="memberRole" value="1" required> <label
				for="memberRole1">일반회원</label><br> <input type="radio"
				id="memberRole2" name="memberRole" value="2"> <label
				for="memberRole2">점주</label>
		</div>

		<div>
			<button type="submit">회원가입</button>
		</div>

		<div>
			<a href="login"><button type="button">로그인 창으로 돌아가기</button></a>
		</div>
	</form>
</body>
</html>
