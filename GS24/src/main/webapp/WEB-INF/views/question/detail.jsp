<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<!-- jquery 라이브러리 import -->
<script src="https://code.jquery.com/jquery-3.7.1.js">
</script>
<meta charset="UTF-8">
<title>${questionVO.questionTitle }</title>
</head>
<body>
	<h2>글 보기</h2>
	<div>
		<p>글 번호 : ${questionVO.questionId }</p>
	</div>
	<div>
		<p>제목 : </p>
		<p>${questionVO.questionTitle }</p>
	</div>
	<div>
		<p>작성자 : ${questionVO.memberId }</p>
		<!-- boardDateCreated 데이터 포멧 변경 -->
		<fmt:formatDate value="${questionVO.questionDateCreated }"
					pattern="yyyy-MM-dd HH:mm:ss" var="questionDateCreated"/>
		<p>작성일 : ${questionDateCreated }</p>
	</div>
	<div>
		<textarea rows="20" cols="120" readonly>${questionVO.questionContent }</textarea>
	</div>
	
	<button onclick="location.href='list'">글 목록</button>
	<button onclick="location.href='modify?questionId=${questionVO.questionId}'">글 수정</button>
	<button id="deletequestion">글 삭제</button>
	<form id="deleteForm" action="delete" method="POST">
		<input type="hidden" name="questionId" value="${questionVO.questionId }">
	</form>
	
	<script type="text/javascript">
		$(document).ready(function(){
			$('#deletequestion').click(function(){
				if(confirm('삭제하시겠습니까?')){
					$('#deleteForm').submit(); // form 데이터 전송
				}
			});
		}); // end document
	</script>

</body>
</html>




