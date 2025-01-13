<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- css 파일 불러오기 -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/css/questionAttach.css">
<!-- jquery 라이브러리 import -->
<script src="https://code.jquery.com/jquery-3.7.1.js">
</script>
<title>${questionDTO.questionTitle }</title>
</head>
<body>
	<h2>글 수정 페이지</h2>
	<form id="modifyForm" action="modify" method="POST">
		<div>
			<p>번호 : ${questionDTO.questionId }</p>		
		</div>
		<div>
			<p>제목 : <input type="text" name="questionTitle" placeholder="제목 입력" maxlength="20" value="${questionDTO.questionTitle }" required></p>		
		</div>
		<div>
            <p>식품 : <input type="text" name="foodName" placeholder="식품 입력" maxlength="20" value="${questionDTO.foodName }" required></p>
            
        </div>
		<div>
			<p>작성자 : ${questionDTO.memberId} </p>	
		</div>
		<div>
			<p>내용 : </p>
			<textarea rows="20" cols="120" name="questionContent" placeholder="내용 입력" maxlength="300" required>${questionDTO.questionContent }</textarea>
		</div>
		<!-- 기존 첨부 파일 리스트 데이터 구성 -->
		<c:forEach var="questionAttachDTO" items="${questionDTO.questionAttachList}" varStatus="status">
			<input type="hidden" class="input-questionAttach-list" name="questionAttachList[${status.index }].questionAttachPath" value="${questionAttachDTO.questionAttachPath }">
			<input type="hidden" class="input-questionAttach-list" name="questionAttachList[${status.index }].questionAttachRealName" value="${questionAttachDTO.questionAttachRealName }">
			<input type="hidden" class="input-questionAttach-list" name="questionAttachList[${status.index }].questionAttachChgName" value="${questionAttachDTO.questionAttachChgName }">
			<input type="hidden" class="input-questionAttach-list" name="questionAttachList[${status.index }].questionAttachExtension" value="${questionAttachDTO.questionAttachExtension }">
		</c:forEach>
	</form>
	
	<hr>
	
	<!-- 첨부 파일 영역 -->
	<div class="questionAttach-upload">
		<div class="questionAttach-view">
			<h2>첨부 파일 리스트</h2>
			<div class="questionAttach-list">
			<c:forEach var="questionAttachDTO" items="${questionDTO.questionAttachList}">
		 		<c:if test="${not (questionAttachDTO.questionAttachExtension eq 'jpg' or 
			    			  questionAttachDTO.questionAttachExtension eq 'jpeg' or 
			    			  questionAttachDTO.questionAttachExtension eq 'png' or 
			    			  questionAttachDTO.questionAttachExtension eq 'gif')}">
			    	<div class="questionAttach_item">
			    	<p><a href="../questionAttach/download?questionAttachId=${questionAttachDTO.questionAttachId }">
			    	${questionAttachDTO.questionAttachRealName }.${questionAttachDTO.questionAttachExtension }</a></p>
			    	</div>
			    </c:if>
			</c:forEach>		
			</div>
		</div>
		<div class="questionAttach-modify" style="display : none;">
			<h2>첨부 파일 업로드</h2>
			<p>* 첨부 파일은 최대 3개까지 가능합니다.</p>
			<p>* 최대 용량은 10MB 입니다.</p>
			<input type="file" id="questionAttachInput" name="files" multiple="multiple"><br>
			<h2>선택한 첨부 파일 정보 :</h2>
			<div class="questionAttach-list"></div>
		</div>
	</div>
	
	<div class="questionAtachDTOFile-list">
	</div>
	
	<script	src="${pageContext.request.contextPath }/resources/js/questionAttach.js"></script>
	
	<script type="text/javascript">
		<div>
			<input type="submit" value="등록">
		</div>
</body>
</html>