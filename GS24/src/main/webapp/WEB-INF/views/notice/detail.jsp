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
<title>${noticeVO.noticeTitle }</title>
</head>
<body>
	<h2>글 보기</h2>
	<div>
		<p>글 번호 : ${noticeVO.noticeId }</p>
	</div>
	<div>
		<p>제목 : </p>
		<p>${noticeVO.noticeTitle }</p>
	</div>
	<div>
		<p>작성자 : ${noticeVO.memberId }</p>
		<!-- boardDateCreated 데이터 포멧 변경 -->
		<fmt:formatDate value="${noticeVO.noticeDateCreated }"
					pattern="yyyy-MM-dd HH:mm:ss" var="noticeDateCreated"/>
		<p>작성일 : ${noticeDateCreated }</p>
	</div>
	<div>
		<textarea rows="20" cols="120" readonly>${noticeVO.noticeContent }</textarea>
	</div>
	
	<button onclick="location.href='list'">글 목록</button>
	<c:if test="${ memberVO.memberRole == 2}">
	<button onclick="location.href='modify?noticeId=${noticeVO.noticeId}'">글 수정</button>
	<button id="deletenotice">글 삭제</button>
	</c:if>
	<c:if test="${ memberVO.memberRole != 2}">
		
		</c:if>
	<form id="deleteForm" action="delete" method="POST">
		<input type="hidden" name="noticeId" value="${noticeVO.noticeId }">
	</form>
	
	<script type="text/javascript">
		$(document).ready(function(){
			$('#deleteNotice').click(function(){
				if(confirm('삭제하시겠습니까?')){
					$('#deleteForm').submit(); // form 데이터 전송
				}
			});
		}); // end document
	</script>

</body>
</html>




