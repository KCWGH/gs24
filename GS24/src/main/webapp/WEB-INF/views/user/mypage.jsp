<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<link rel="stylesheet" href="../resources/css/fonts.css">
<title>마이페이지</title>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<style type="text/css">
body {
	font-family: 'Pretendard-Regular', sans-serif;
    margin: 0;
    padding: 60px 15px 15px 15px;
    background-color: #f8f9fa;
    text-align: center;
}

.container {
	font-family: 'Pretendard-Regular', sans-serif;
    max-width: 100%;
    padding: 10px;
    background: white;
    border-radius: 10px;
    box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1);
    text-align: center;
}

h2 {
    color: #333;
    margin-bottom: 10px;
}

table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 10px;
}

th, td {
    padding: 8px;
    text-align: left;
    border-bottom: 1px solid #ddd;
    font-size: 18px;
}

th {
    background-color: #f1f1f1;
    color: #333;
    text-align: center;
}

input[type="text"], input[type="email"] {
	font-family: 'Pretendard-Regular', sans-serif;
    width: 60%;
    padding: 5px;
    border: 1px solid #ddd;
    border-radius: 5px;
    text-align: center;
    font-size: 13px;
}

button {
	font-family: 'Pretendard-Regular', sans-serif;
    padding: 7px 10px;
    font-size: 15px;
    border: none;
    background: #ddd;
    border-radius: 5px;
    cursor: pointer;
    margin: 5px;
}

button:hover {
    background: #bbb;
}

.btn-container button {
    width: 120px;
    padding: 10px;
    font-size: 15px;
    text-align: center;
}

#textDelete {
    font-size: 16px;
    color: red;
    margin-bottom: 5px;
}

#updateEmailResult, #updatePhoneResult, #deleteResult {
    font-size: 16px;
    margin-top: 5px;
    color: red;
}
#btnDelete {
    background: #ff6666;
    color: white;
}

#btnDelete:hover {
    background: #ff4d4d;
}
</style>
<script>
$(document).ajaxSend(function(e, xhr, opt){
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    
    xhr.setRequestHeader(header, token);
 });
$(document).ready(function() {
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    const phoneRegex = /^01[016789]-\d{4}-\d{4}$/;
    
    $("#btnUpdate").click(function(event) {
        $("#btnUpdateEmail, #btnUpdatePhone, #btnUpdateEnd").prop("hidden", false);
        $("#btnUpdate").prop("hidden", true);
    });
    
    $("#btnUpdateEnd").click(function(event) {
    	location.reload();
    });

    $("#btnUpdateEmail").click(function(event) {
        event.preventDefault();
        $("#email").prop("disabled", false);
        $("#btnUpdateEmail").prop("hidden", true);
        $("#btnUpdateMemberEmailConfirm, #btnUpdateEmailCancel").prop("hidden", false);
        $("#updateEmailResult").hide();
    });

    $("#btnUpdateEmailCancel").click(function(event) {
        event.preventDefault();
        location.reload();
    });
   
    let userRole = "${userRole}";
    let username = $("#username").attr("data-username");
    
    $("#btnUpdateMemberEmailConfirm").click(function(event) {
        event.preventDefault();
        let email = $("#email").val();
        
        if (!emailRegex.test(email)) {
            $("#updateEmailResult").text("유효하지 않은 이메일 형식입니다.").show();
            return;
        }
        
        let url;
        let data;
        if (userRole === 'member') {
        	url = 'member';
        	data = { email: email, memberId: username };
        } else if (userRole === 'owner') {
        	url = 'owner';
        	data = { email: email, ownerId: username };
        }
        $.ajax({
            url: "update-" + url + "-email",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(data),
            success: function(response) {
                if (response === "Update Success") {
                    $("#updateEmailResult").text("이메일 수정 완료 :)").css('color', 'green').show();
                    $("#email").prop("disabled", true);
                    $("#btnUpdateEmail").prop("hidden", false);
                    $("#btnUpdateMemberEmailConfirm, #btnUpdateEmailCancel").prop("hidden", true);
                } else if (response === "Update Fail - Same Email") {
                    $("#updateEmailResult").text("동일한 이메일입니다.").css('color', 'red').show();
                } else if (response === "Update Fail - Duplicated Email") {
                    $("#updateEmailResult").text("중복된 이메일입니다.").css('color', 'red').show();
                }
            }
        });
    });
    
   

    $("#btnUpdatePhone").click(function(event) {
        event.preventDefault();
        $("#phone").prop("disabled", false);
        $("#btnUpdatePhone").prop("hidden", true);
        $("#btnUpdatePhoneConfirm, #btnUpdatePhoneCancel").prop("hidden", false);
        $("#updatePhoneResult").hide();
    });

    $("#btnUpdatePhoneCancel").click(function(event) {
        event.preventDefault();
        location.reload();
    });

    $("#btnUpdatePhoneConfirm").click(function(event) {
        event.preventDefault();
        let phone = $("#phone").val();
        
        if (!phoneRegex.test(phone)) {
        	$("#updatePhoneResult").html('휴대폰 번호 형식이 올바르지 않습니다. <br> <span style="color: gray;">(예: 010-1234-5678)</span>')
            .css('color', 'red')
            .show();
            return;
        }
        let url;
        let data;
        if (userRole === 'member') {
        	url = 'member';
        	data = { phone: phone, memberId: username };
        } else if (userRole === 'owner') {
        	url = 'owner';
        	data = { phone: phone, ownerId: username };
        }
        $.ajax({
            url: "update-" + url + "-phone",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(data),
            success: function(response) {
                if (response === "Update Success") {
                    $("#updatePhoneResult").text("휴대폰 번호 수정 완료 :)").css('color', 'green').show();
                    $("#phone").prop("disabled", true);
                    $("#btnUpdatePhone").prop("hidden", false);
                    $("#btnUpdatePhoneConfirm, #btnUpdatePhoneCancel").prop("hidden", true);
                } else if (response === "Update Fail - Same Phone") {
                    $("#updatePhoneResult").text("동일한 휴대폰 번호입니다.").css('color', 'red').show();
                } else if (response === "Update Fail - Duplicated Phone") {
                    $("#updatePhoneResult").text("중복된 휴대폰 번호입니다.").css('color', 'red').show();
                }
            }
        });
    });

    $("#btnDelete").click(function(event) {
        event.preventDefault();
        $("#textDelete, #btnDeleteConfirm, #btnDeleteCancel").prop("hidden", false);
    });

    $("#btnDeleteCancel").click(function(event) {
    	location.reload();
    });

    $("#btnDeleteConfirm").click(function(event) {
        event.preventDefault();
        let url;
        let data;
        if (userRole === 'member') {
        	url = 'member';
        	data = { memberId: username };
        } else if (userRole === 'owner') {
        	url = 'owner';
        	data = { ownerId: username };
        }
        $.ajax({
            url: "delete-" + url,
            type: "POST",
            data: data,
            success: function(response) {
                if (response === "Delete Success") {
                    alert('아이디 삭제 완료');
                    if (window.opener) {
                        window.opener.location.reload();
                    }
                    window.close();
                } else if (response === "Delete Fail - Remaining Giftcard Balances"){
                	$("#deleteResult").text("아이디 삭제 실패 - 잔액이 남은 기프트카드가 있습니다.").show();
                } else if (response === "Delete Fail - Remaining Preorders") {
                	$("#deleteResult").text("아이디 삭제 실패 - 미수령 예약이 있습니다.").show();
                } else {
                    $("#deleteResult").text("아이디 삭제 실패").show();
                }
            }
        });
    });
});
</script>

</head>
<body>
<c:if test="${not empty message}">
        <script type="text/javascript">
            alert("${message}");
        </script>
    </c:if>
<div class="container">
	<h2>마이페이지</h2>
		<c:if test="${not empty memberGrade }">
			<span>당신의 이번 달 멤버십 등급은 </span>
			<c:choose>
				<c:when test="${memberGrade == 1}">
					<strong style="color: brown;">'브론즈'</strong>
				</c:when>
				<c:when test="${memberGrade == 2}">
					<strong style="color: silver;">'실버'</strong>
				</c:when>
				<c:when test="${memberGrade == 3}">
					<strong style="color: gold;">'골드'</strong>
				</c:when>
			</c:choose>
			<span> 입니다.</span><br><br>
		</c:if>
			<table>
				<tr>
					<th>아이디</th>
					<sec:authorize access="hasRole('ROLE_MEMBER')">
					<td id="username" data-username="${userInfo.memberId}">${userInfo.memberId}</td>
					</sec:authorize>
					<sec:authorize access="hasRole('ROLE_OWNER')">
					<td id="username" data-username="${userInfo.ownerId}">${userInfo.ownerId}</td>
					</sec:authorize>
				</tr>
				<tr>
					<th>계정유형</th>
					<td>
						<sec:authorize access="hasRole('ROLE_MEMBER')">일반회원</sec:authorize>
						<sec:authorize access="hasRole('ROLE_OWNER')">점주</sec:authorize>
					</td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td><button type="button" onclick='location.href="../user/verify"'>비밀번호 변경</button></td>
				</tr>
			<tr>
				<th>이메일</th>
				<td><input id="email" type="email" value="${userInfo.email}"
					disabled>
					<button id="btnUpdateEmail" hidden="hidden">수정</button>
						<button id="btnUpdateMemberEmailConfirm" hidden="hidden">저장</button>
					<button id="btnUpdateEmailCancel" hidden="hidden">취소</button><br>
					<span id="updateEmailResult" hidden="hidden">수정 결과창</span></td>
			</tr>
				<tr>
					<th>휴대폰</th>
					<td><input id="phone" type="text" value="${userInfo.phone}" disabled>
					<button id="btnUpdatePhone" hidden="hidden">수정</button><button id="btnUpdatePhoneConfirm" hidden="hidden">저장</button>
					<button id="btnUpdatePhoneCancel" hidden="hidden">취소</button><br>
					<span id="updatePhoneResult" hidden="hidden">수정 결과창</span></td>
				</tr>
				<sec:authorize access="hasRole('ROLE_MEMBER')">
				<tr>
					<th>생일</th>
					<td>${userInfo.birthday}</td>
				</tr>
				</sec:authorize>
			</table>
			<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }"><br>
		<div class="btn-container">
			<button id="btnUpdate">회원정보 수정</button><button id="btnUpdateEnd" hidden="hidden">수정 마치기</button><button id="btnDelete">회원 탈퇴</button>
			<sec:authorize access="hasRole('ROLE_MEMBER')">
			<p id="textDelete" hidden="hidden">탈퇴 시 회원님의 활동 내역(작성 게시글 및 댓글)은<br>삭제되지 않습니다. 정말 탈퇴하시겠습니까?</p></sec:authorize>
			<sec:authorize access="hasRole('ROLE_OWNER')">
			<p id="textDelete" hidden="hidden">탈퇴 시 점주님의 활동 내역(등록된 공지사항 및 답변 내역)은<br>삭제되지 않습니다. 정말 탈퇴하시겠습니까?</p></sec:authorize>
			<button id="btnDeleteConfirm" hidden="hidden">네</button><button id="btnDeleteCancel" hidden="hidden">아니오</button>
			<p id="deleteResult" hidden="hidden"></p>
		</div>
		<div class="btn-container">
			<sec:authorize access="hasRole('ROLE_MEMBER')">
			<button onclick='location.href="../giftcard/list"'>기프트카드함</button><button onclick='location.href="../member/myhistory"'>내 활동</button>
			</sec:authorize>
		</div>
</div>
</body>
</html>
