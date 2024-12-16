<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>마이페이지</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
	$(document).ready(function() {
		$("#btnUpdate").click(function() {
			$("#password, #email, #phone").prop("disabled", false);
			$("#btnUpdateConfirm, #btnUpdateCancel").prop("hidden", false);
			$("#updateResultArea").hide();
		});
		$("#btnUpdateConfirm").click(function(event) {
			event.preventDefault();
			let memberId = $("#memberId").val();
			let password = $("#password").val();
			let email = $("#email").val();
			let phone = $("#phone").val();
			if (password && email && phone && memberId) {
				$.ajax({
					url : "update",
					type : "POST",
					contentType : "application/json",
					data : JSON.stringify({
						memberId : $("#memberId").val(),
						password : $("#password").val(),
						email : $("#email").val(),
						phone : $("#phone").val()
					}),
					success : function(response) {
						$("#updateResultArea").text("정보 수정 완료!").show();
						$("#btnUpdateConfirm, #btnUpdateCancel").prop("hidden", true);
						$("#password, #email, #phone").prop("disabled", true);
					},
					error : function(xhr, status, error) {
						$("#updateResultArea").text("정보 수정 실패..").show();
					}
				});
			} else {
				alert("수정할 정보를 입력하세요");
			}
		});
		$("#btnUpdateCancel").click(function() {
			$("#btnUpdateConfirm, #btnUpdateCancel").prop("hidden", true);
			$("#password, #email, #phone").prop("disabled", true);
		});
		$("#btnDelete").click(function() {
			$("#confirmDelete, #btnDeleteConfirm, #btnDeleteCancel").prop("hidden", false);
		});
		$("#btnDeleteCancel").click(function() {
			$("#confirmDelete, #btnDeleteConfirm, #btnDeleteCancel").prop("hidden", true);
		});
		$("#btnDeleteConfirm").click(function(event) {
			event.preventDefault();
			let memberId = $("#memberId").val();
			if (memberId) {
				$.ajax({	
					url : "delete",
					type : "POST",
					data : {
						memberId : $("#memberId").val()
					},
					success : function(response=1) {
						alert('회원 삭제 완료');
					},
					error : function(xhr, status, error) {
						$("#deleteResultArea").text("회원 삭제 실패..").show();
					}
				});
				$.ajax({
				    url: "logout",
				    type: "GET",
				    success: function(response) {
				    	window.location.href = "login";
				    },
				    error: function(xhr, status, error) {
				        alert("로그아웃 실패!");
				    }
				});
			} else {
				alert("수정할 정보를 입력하세요");
			}
		});
	});
</script>
</head>
<body>
	<h2>마이페이지</h2>
	<c:if test="${empty memberVO}">
		<h3>마이페이지 조회를 위해서 먼저 로그인하세요</h3>
		<button onclick="window.location.href='login'">로그인</button>
	</c:if>
	<c:if test="${not empty memberVO}">
		<form>
			<table>
				<tr>
					<th>아이디</th>
					<td><input id="memberId" type="hidden" name="memberId"
						value="${memberVO.memberId}"> <span>${memberVO.memberId}</span>
					</td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td><input id="password" type="password" name="password"
						value="${memberVO.password}" disabled></td>
				</tr>

				<tr>
					<th>이메일</th>
					<td><input id="email" type="email" name="email"
						value="${memberVO.email}" disabled></td>
				</tr>
				<tr>
					<th>휴대폰</th>
					<td><input id="phone" type="text" name="phone"
						value="${memberVO.phone}" disabled></td>
				</tr>
				<tr>
					<th>생일</th>
					<td><span>${memberVO.birthday}</span></td>
				</tr>
				<tr>
					<th>계정 유형</th>
					<td><c:choose>
							<c:when test="${memberVO.memberRole == 1}">일반회원</c:when>
							<c:when test="${memberVO.memberRole == 2}">점주</c:when>
						</c:choose></td>
				</tr>
			</table>
		</form>
		<br>
		<button id="btnUpdate">회원정보 수정</button>
		<button type="button" id="btnUpdateConfirm" hidden="hidden">저장</button>
		<button type="button" id="btnUpdateCancel" hidden="hidden">취소</button>
		<p id="updateResultArea" style="display: none; color: blue;"></p><br>
		<button id="btnDelete">회원 탈퇴</button>
		<span id="confirmDelete" hidden="hidden">정말 탈퇴하시겠습니까? 사용자의 활동
			내역(게시글, 댓글 등)은 사라지지 않습니다.</span>
		<button type="button" id="btnDeleteConfirm" hidden="hidden">예</button>
		<button type="button" id="btnDeleteCancel" hidden="hidden">아니오</button>
		<br>
		<p id="deleteResultArea" style="display: none; color: red;"></p>
		<button onclick="window.location.href='../food/list'">음식 리스트로 돌아가기</button>
		<button onclick="window.location.href='logout'">로그아웃</button>
	</c:if>
</body>
</html>
