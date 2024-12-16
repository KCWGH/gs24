<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>마이페이지</title>
<!-- jQuery 포함 -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
	$(document).ready(function() {
		// "회원정보 수정" 버튼 클릭 시
		$("#btnEdit").click(function() {
			// 이메일, 휴대폰 입력란 활성화
			$("#email, #phone").prop("disabled", false);
			// "정보 수정 저장" 버튼을 보이게 함
			$("#btnSave").prop("hidden", false);
		});

		// "정보 수정 저장" 버튼 클릭 시
		$("#btnSave").click(function(event) {
			event.preventDefault();
			let email = $("#email").val();
			let phone = $("#phone").val();
			
			if (email && phone) {
				// AJAX 요청 보내기
				$.ajax({
					url: "update",  // 요청을 보낼 URL (서버에 설정된 URL)
					type: "POST",   // 요청 방식
					data: {
						email: email,
						phone: phone
					},
					success: function(response) {
						if (response) {
							$("#updateResultArea").text("정보 수정 완료!").show();
						} else {
							$("#updateResultArea").text("정보 수정 실패..").show();
						}
					},
					error: function(xhr, status, error) {
						$("#updateResultArea").text("오류가 발생했습니다: " + error).show();
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

	<!-- 로그인된 사용자가 아닐 경우, 로그인 유도 메시지 -->
	<c:if test="${empty memberVO}">
		<h3>마이페이지 조회를 위해서 먼저 로그인하세요</h3>
		<a href="login">로그인</a>
	</c:if>

	<!-- 로그인된 사용자 정보 표시 -->
	<c:if test="${not empty memberVO}">
		<form>
			<table>
				<tr>
					<th>아이디</th>
					<td><span>${memberVO.memberId}</span></td>
				</tr>
				<tr>
					<th>이메일</th>
					<td><input id="email" type="email" name="email" value="${memberVO.email}" disabled></td>
				</tr>
				<tr>
					<th>휴대폰</th>
					<td><input id="phone" type="text" name="phone" value="${memberVO.phone}" disabled></td>
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
			<br>
			<!-- 회원정보 수정 버튼 -->
			<a href="#" id="btnEdit">회원정보 수정</a>
			<!-- "정보 수정 저장" 버튼, 처음에는 hidden 속성으로 숨김 -->
			<button type="button" id="btnSave" hidden="hidden">정보 수정 저장</button>
		</form>
		<p id="updateResultArea" style="display:none;"></p>
		<a href="delete">회원 탈퇴</a>
		<br>
		<a href="../food/list">음식 리스트로 돌아가기</a>
		<a href="logout">로그아웃</a>
	</c:if>
</body>
</html>
