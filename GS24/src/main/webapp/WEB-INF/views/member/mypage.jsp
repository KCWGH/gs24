<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>마이페이지</title>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<script>
$(document).ready(function() {
	$("#btnUpdate").click(function(event){
		$("#btnUpdatePassword, #btnUpdateEmail, #btnUpdatePhone, #btnUpdateCancel").prop("hidden", false);
		$("#btnUpdate").prop("hidden", true);
	});
	$("#btnUpdateCancel").click(function(event){
		$("#btnUpdatePassword, #btnUpdateEmail, #btnUpdatePhone, #btnUpdateCancel").prop("hidden", true);
		$("#btnUpdate").prop("hidden", false);
	});
	$("#btnUpdatePassword").click(function(event){
		event.preventDefault();
		$("#password").prop("disabled", false);
		$("#btnUpdatePassword").prop("hidden", true);
		$("#btnUpdatePasswordConfirm, #btnUpdatePasswordCancel").prop("hidden", false);
		$("#updatePasswordResult").hide();
	});
	$("#btnUpdatePasswordCancel").click(function(event){
		event.preventDefault();
		$("#password").prop("disabled", true);
		$("#btnUpdatePassword").prop("hidden", false);
		$("#btnUpdatePasswordConfirm, #btnUpdatePasswordCancel").prop("hidden", true);
	});
	$("#btnUpdatePasswordConfirm").click(function(event){
		event.preventDefault();
		let password = $("#password").val();
		let memberId = "${memberVO.memberId}";
		let data = { password: password, memberId: memberId };
		$.ajax({
			url: "update-pw",
			type: "POST",
			contentType: "application/json",
			data: JSON.stringify(data),
			success: function(response) {
				if (response === "Update Success") {
					$("#updatePasswordResult").text("비밀번호 수정 완료 :)").show();
					$("#password").prop("disabled", true);
					$("#btnUpdatePassword").prop("hidden", false);
					$("#btnUpdatePasswordConfirm, #btnUpdatePasswordCancel").prop("hidden", true);
				} else {
					$("#updatePasswordResult").text("비밀번호 수정 실패 :(").show();
				}
			}
		});
	});
	$("#btnUpdateEmail").click(function(event){
		event.preventDefault();
		$("#email").prop("disabled", false);
		$("#btnUpdateEmail").prop("hidden", true);
		$("#btnUpdateEmailConfirm, #btnUpdateEmailCancel").prop("hidden", false);
		$("#updateEmailResult").hide();
	});
	$("#btnUpdateEmailCancel").click(function(event){
		event.preventDefault();
		$("#email").prop("disabled", true);
		$("#btnUpdateEmail").prop("hidden", false);
		$("#btnUpdateEmailConfirm, #btnUpdateEmailCancel").prop("hidden", true);
	});
	$("#btnUpdateEmailConfirm").click(function(event){
		event.preventDefault();
		let email = $("#email").val();
		let memberId = "${memberVO.memberId}";
		let data = { email: email, memberId: memberId };
		$.ajax({
			url: "update-email",
			type: "POST",
			contentType: "application/json",
			data: JSON.stringify(data),
			success: function(response) {
				if (response === "Update Success") {
					$("#updateEmailResult").text("이메일 수정 완료 :)").show();
					$("#email").prop("disabled", true);
					$("#btnUpdateEmail").prop("hidden", false);
					$("#btnUpdateEmailConfirm, #btnUpdateEmailCancel").prop("hidden", true);
				} else if(response === "Update Fail - Same Email"){
					$("#updateEmailResult").text("동일한 이메일입니다.").show();
				} else if(response === "Update Fail - Duplicated Email"){
					$("#updateEmailResult").text("중복된 이메일입니다.").show();
				}
			}
		});
	});
	$("#btnUpdatePhone").click(function(event){
		event.preventDefault();
		$("#phone").prop("disabled", false);
		$("#btnUpdatePhone").prop("hidden", true);
		$("#btnUpdatePhoneConfirm, #btnUpdatePhoneCancel").prop("hidden", false);
		$("#updatePhoneResult").hide();
	});
	$("#btnUpdatePhoneCancel").click(function(event){
		event.preventDefault();
		$("#phone").prop("disabled", true);
		$("#btnUpdatePhone").prop("hidden", false);
		$("#btnUpdatePhoneConfirm, #btnUpdatePhoneCancel").prop("hidden", true);
	});
	$("#btnUpdatePhoneConfirm").click(function(event){
		event.preventDefault();
		let phone = $("#phone").val();
		let memberId = "${memberVO.memberId}";
		let data = { phone: phone, memberId: memberId };
		$.ajax({
			url: "update-phone",
			type: "POST",
			contentType: "application/json",
			data: JSON.stringify(data),
			success: function(response) {
				if (response === "Update Success") {
					$("#updatePhoneResult").text("휴대폰 번호 수정 완료 :)").show();
					$("#phone").prop("disabled", true);
					$("#btnUpdatePhone").prop("hidden", false);
					$("#btnUpdatePhoneConfirm, #btnUpdatePhoneCancel").prop("hidden", true);
				} else if(response === "Update Fail - Same Phone"){
					$("#updatePhoneResult").text("동일한 휴대폰 번호입니다.").show();
				} else if(response === "Update Fail - Duplicated Phone"){
					$("#updatePhoneResult").text("중복된 휴대폰 번호입니다.").show();
				}
			}
		});
	});
	$("#btnDelete").click(function(event){
		event.preventDefault();
		$("#textDelete, #btnDeleteConfirm, #btnDeleteCancel").prop("hidden", false);
	});
	$("#btnDeleteCancel").click(function(event){
		event.preventDefault();
		$("#textDelete, #btnDeleteConfirm, #btnDeleteCancel").prop("hidden", true);
	});
	$("#btnDeleteConfirm").click(function(event){
		event.preventDefault();
		let memberId = "${memberVO.memberId}";
		let data = { memberId: memberId };
		$.ajax({
			url: "delete",
			type: "POST",
			data: data,
			success: function(response) {
				if (response === "Delete Success") {
					alert('아이디 삭제 완료');
					if (window.opener) {
						window.opener.location.reload();
					}
					window.close();
				} else {
					$("#updatePhoneResult").text("아이디 삭제 실패").show();
				}
			}
		});
	});
	$("#logout").click(function(event){
		window.location.href = 'logout';
	});
	
});
</script>
</head>
<body>
<div class="container">
	<h2>마이페이지</h2>
	<c:if test="${empty memberVO}">
		<h3>로그인 필요</h3>
		<a href="login">로그인</a>
	</c:if>
	<c:if test="${not empty memberVO}">
		<form>
			<table>
				<tr>
					<th>아이디</th>
					<td>${memberVO.memberId}</td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td><input id="password" type="password" value="${memberVO.password}" disabled></td>
					<td><button id="btnUpdatePassword" hidden="hidden">수정</button><button id="btnUpdatePasswordConfirm" hidden="hidden">저장</button></td>
					<td><button id="btnUpdatePasswordCancel" hidden="hidden">취소</button></td>
				</tr>
				<tr>
					<td></td>
					<td id="updatePasswordResult" hidden="hidden">수정 결과창</td>
				</tr>
				<tr>
					<th>이메일</th>
					<td><input id="email" type="email" value="${memberVO.email}" disabled></td>
					<td><button id="btnUpdateEmail" hidden="hidden">수정</button><button id="btnUpdateEmailConfirm" hidden="hidden">저장</button></td>
					<td><button id="btnUpdateEmailCancel" hidden="hidden">취소</button></td>
				</tr>
				<tr>
					<td></td>
					<td id="updateEmailResult" hidden="hidden">수정 결과창</td>
				</tr>
				<tr>
					<th>휴대폰</th>
					<td><input id="phone" type="text" value="${memberVO.phone}" disabled></td>
					<td><button id="btnUpdatePhone" hidden="hidden">수정</button><button id="btnUpdatePhoneConfirm" hidden="hidden">저장</button></td>
					<td><button id="btnUpdatePhoneCancel" hidden="hidden">취소</button></td>
				</tr>
				<tr>
					<td></td>
					<td id="updatePhoneResult" hidden="hidden">수정 결과창</td>
				</tr>
				<tr>
					<th>생일</th>
					<td>${memberVO.birthday}</td>
				</tr>
				<tr>
					<th>계정 유형</th>
					<td>
						<c:choose>
							<c:when test="${memberVO.memberRole == 1}">일반회원</c:when>
							<c:when test="${memberVO.memberRole == 2}">점주</c:when>
						</c:choose>
					</td>
				</tr>
			</table>
		</form>
		<div class="btn-container">
			<button id="btnUpdate">회원정보 수정</button>
			<button id="btnUpdateCancel" hidden="hidden">수정 마치기</button>
			<button id="btnDelete">회원 탈퇴</button>
			<p id="textDelete" hidden="hidden">탈퇴 시 사용자의 활동 내역(작성 게시글 및 댓글)은 삭제되지 않습니다. 정말 탈퇴하시겠습니까?</p>
			<button id="btnDeleteConfirm" hidden="hidden">네</button>
			<button id="btnDeleteCancel" hidden="hidden">아니오</button>
		</div>
		<p id="deleteResultArea"></p>
		<div class="btn-container">
			<button onclick='location.href="../coupon/list"'>쿠폰함</button>
			<button onclick='location.href="myhistory"'>내 활동</button>
			<button onclick='location.href="../auth/logout"'>로그아웃</button>
		</div>
	</c:if>
</div>
</body>
</html>
