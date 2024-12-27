<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${noticeVO.noticeTitle }</title>
</head>
<body>
	<h2>글 수정 페이지</h2>
	<form action="modify" method="POST">
		<div>
			<p>번호 : </p>
			<input type="text" name="noticeId" value="${noticeVO.noticeId }" readonly>
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
	</form>
</body>
</html>