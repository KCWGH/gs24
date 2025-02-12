<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<meta charset="UTF-8">
<title>${noticeVO.noticeTitle }</title>
<%@ include file="../common/header.jsp" %>
</head>
<body>
	<h2>글 수정 페이지</h2>
	<form action="modify" method="POST">
		<div>
			<p>글 번호 : ${noticeVO.noticeId }</p>			
		</div>
		<div>
			<p>제목 : </p>
			<input type="text" name="noticeTitle" placeholder="제목 입력" 
maxlength="20" value="${noticeVO.noticeTitle }" required>
		</div>
		<div>
			<p>작성자 : ${noticeVO.memberId} </p>	
		</div>
		<div>
			<p>내용 : </p>
			<textarea rows="20" cols="120" name="noticeContent" placeholder="내용 입력" 
maxlength="300" required>${noticeVO.noticeContent }</textarea>
		</div>
		<div>
			<input type="submit" value="등록">
		</div>
		<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }"><br>
	</form>
</body>
</html>