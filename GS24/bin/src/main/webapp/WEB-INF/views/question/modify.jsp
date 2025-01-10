<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${questionVO.questionTitle }</title>
</head>
<body>
	<h2>글 수정 페이지</h2>
	<form action="modify" method="POST">
		<div>
			<p>번호 : </p>
			<input type="text" name="questionId" value="${questionVO.questionId }" readonly>
		</div>
		<div>
			<p>제목 : </p>
			<input type="text" name="questionTitle" placeholder="제목 입력" 
maxlength="20" value="${questionVO.questionTitle }" required>
		</div>
		<div>
			<p>작성자 : ${questionVO.memberId} </p>
			
		</div>
		<div>
			<p>내용 : </p>
			<textarea rows="20" cols="120" name="questionContent" placeholder="내용 입력" 
maxlength="300" required>${questionVO.questionContent }</textarea>
		</div>
		<div>
			<input type="submit" value="등록">
		</div>
	</form>
</body>
</html>