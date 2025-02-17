<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<meta charset="UTF-8">
<title>글 작성 페이지</title>
<%@ include file="../common/header.jsp" %>
</head>
<body>
   <h2>글 작성 페이지</h2>
   <form action="register" method="POST">
   <!-- input 태그의 name은 vo의 멤버 변수 이름과 동일하게 작성 -->
   	  <div>
   	  	<label>
   	  	  <input type="checkbox" id="noticeTypeCheckbox">점주 전용 공지사항
   	  	</label>
   	  	<input type="hidden" name="noticeType" id="noticeType" value="0">
   	  </div>
      <div>
         <p>제목 : </p><input type="text" name="noticeTitle" placeholder="제목 입력" maxlength="300" required>
      </div>
      <div>
         <p>작성자 : </p>
         <input type="text" name="memberId" value="관리자" maxlength="10" readonly required>
      </div>
      <div>
         <p>내용 : </p>
         <textarea rows="20" cols="120" name="noticeContent" 
         placeholder="내용 입력" maxlength="300" required></textarea>
      </div>
      <div>
         <input type="submit" value="등록">
      </div>
      <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }"><br>
   </form>
   <script type="text/javascript">
   document.getElementById("noticeTypeCheckbox").addEventListener("change", function() {
       document.getElementById("noticeType").value = this.checked ? "1" : "0";
    });
   </script>
   
</body>
</html>