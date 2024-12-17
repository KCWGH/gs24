<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
			$("#email, #phone, #password").prop("disabled", false);
			$("#btnUpdateConfirm, #btnUpdateCancel").prop("hidden", false);
			$("#updateResultArea").hide();
<<<<<<< HEAD
		});
		$("#btnUpdateConfirm").click(function(event) {
		    event.preventDefault();
		    
		    let email = $("#email").val();
		    let phone = $("#phone").val();
		    let password = $("#password").val();
		    
		    if (email && phone && password) {
		        let data = {
		            email: email,
		            phone: phone,
		            password: password,
		            memberId: "${memberVO.memberId}"
		        };

		        let emailCheck = false;
		        let phoneCheck = false;
		        
		        $.ajax({
		            url: 'dupcheckemail',
		            type: 'POST',
		            data: { email: email },
		            success: function(response) {
		                if (response == 1) {
		                    console.log("중복된 이메일");
		                    emailCheck = false; // 이메일 중복 처리
		                    $("#updateResultArea").text("중복된 이메일입니다.").show();
		                } else {
		                    console.log("사용 가능한 이메일");
		                    emailCheck = true; // 이메일 사용 가능 처리
		                }
		                // 전화번호 중복 체크
		                $.ajax({
		                    url: 'dupcheckphone',
		                    type: 'POST',
		                    data: { phone: phone },
		                    success: function(response) {
		                        if (response == 1) {
		                            console.log("중복된 전화번호");
		                            phoneCheck = false; // 전화번호 중복 처리
		                            $("#updateResultArea").text("중복된 전화번호입니다.").show();
		                        } else {
		                            console.log("사용 가능한 전화번호");
		                            phoneCheck = true; // 전화번호 사용 가능 처리
		                        }

		                        // 이메일과 전화번호 모두 중복되지 않으면 정보 업데이트
		                        if (emailCheck && phoneCheck) {
		                            $.ajax({
		                                url: "update",
		                                type: "POST",
		                                contentType: "application/json",
		                                data: JSON.stringify(data),
		                                success: function(response) {
		                                    if (response === "Update Success") {
		                                        $("#updateResultArea").text("정보 수정 완료!").show();
		                                        $("#email, #phone, #password").prop("disabled", true);
		                                        $("#btnUpdateConfirm, #btnUpdateCancel").prop("hidden", true);
		                                    } else {
		                                        $("#updateResultArea").text("정보 수정 실패..").show();
		                                    }
		                                },
		                                error: function(xhr, status, error) {
		                                    $("#updateResultArea").text("오류가 발생했습니다: " + error).show();
		                                }
		                            });
		                        }
		                    },
		                    error: function(xhr, status, error) {
		                        $("#updateResultArea").text("오류가 발생했습니다: " + error).show();
		                    }
		                });
		            },
		            error: function(xhr, status, error) {
		                $("#updateResultArea").text("오류가 발생했습니다: " + error).show();
		            }
		        });
		    } else {
		        $("#updateResultArea").text("모든 필드를 입력해주세요.").show();
		    }
		});


		$("#btnUpdateCancel").click(function() {
			$("#email, #phone, #password").prop("disabled", true);
			$("#btnUpdateConfirm, #btnUpdateCancel").prop("hidden", true);
			$("#updateResultArea").hide();
		});
=======
		});
		$("#btnUpdateConfirm").click(function(event) {
			event.preventDefault();
			let email = $("#email").val();
			let phone = $("#phone").val();
			let password = $("#password").val();
			
			if (email && phone && password) {
				let data = {
					email: email,
					phone: phone,
					password: password,
					memberId: "${memberVO.memberId}"
				};
				
				$.ajax({
					url: "update",
					type: "POST",
					contentType: "application/json",
					data: JSON.stringify(data),
					success: function(response) {
						if (response === "Update Success") {
							$("#updateResultArea").text("정보 수정 완료!").show();
							$("#email, #phone, #password").prop("disabled", true);
							$("#btnUpdateConfirm, #btnUpdateCancel").prop("hidden", true);
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
		$("#btnUpdateCancel").click(function() {
			$("#email, #phone, #password").prop("disabled", true);
			$("#btnUpdateConfirm, #btnUpdateCancel").prop("hidden", true);
			$("#updateResultArea").hide();
		});
>>>>>>> c366c08dc1ff87280f5da0a1dbabf6a230862cb9
		$("#btnDelete").click(function() {
			$("#textDelete, #btnDeleteConfirm, #btnDeleteCancel").prop("hidden", false);
		});
		$("#btnDeleteConfirm").click(function(event) {
		    event.preventDefault();
		    let memberId = "${memberVO.memberId}";

		    if (memberId) {
		        $.ajax({
		            url: "delete",
		            type: "POST",
		            data: { memberId: memberId },
		            success: function(response) {
		                if (response === "Delete Success") {
		                    alert('회원 삭제 완료');
		                    window.location.href = 'login';
		                } else {
		                    $("#deleteResultArea").text("정보 수정 실패..").show();
		                }
		            },
		            error: function(xhr, status, error) {
		                $("#deleteResultArea").text("오류가 발생했습니다: " + error).show();
		            }
		        });
		    } else {
		        alert("수정할 정보를 입력하세요");
		    }
		});

		$("#btnDeleteCancel").click(function() {
			$("#textDelete, #btnDeleteConfirm, #btnDeleteCancel").prop("hidden", true);
		});
<<<<<<< HEAD
		$("#couponList").click(function() {
			  window.open("coupon", "_blank", "width=800,height=600");
			});
=======
>>>>>>> c366c08dc1ff87280f5da0a1dbabf6a230862cb9
	});
</script>
</head>
<body>
	<h2>마이페이지</h2>

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
					<th>비밀번호</th>
					<td><input id="password" type="password" name="password" value="${memberVO.password}" disabled></td>
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
			</form>
			<br>
			<a href="#" id="btnUpdate">회원정보 수정</a>
			<button type="button" id="btnUpdateConfirm" hidden="hidden">저장</button>
			<button type="button" id="btnUpdateCancel" hidden="hidden">취소</button>
		
		<p id="updateResultArea" style="display:none;"></p>
		<a href="#" id="btnDelete">회원 탈퇴</a>
		<p id=textDelete hidden="hidden">회원을 탈퇴하시면 더 이상 로그인할 수 없지만, 사용자의 활동(게시글, 댓글)은 그대로 남습니다. 정말 탈퇴할까요?</p>
		<button type="button" id="btnDeleteConfirm" hidden="hidden">네</button>
		<button type="button" id="btnDeleteCancel" hidden="hidden">아니오</button><br>
<<<<<<< HEAD
		<c:if test="${memberVO.memberRole == 1}">
		<a href="#" id="couponList">쿠폰함</a><br>
		</c:if>
=======
>>>>>>> c366c08dc1ff87280f5da0a1dbabf6a230862cb9
		<p id="deleteResultArea" style="display:none;"></p>
		<a href="../food/list">음식 리스트로 돌아가기</a>
		<a href="logout">로그아웃</a>
	</c:if>
</body>
</html>
