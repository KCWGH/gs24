<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<head>
<style>
body {
	font-family: 'Pretendard-Regular', sans-serif;
	margin: 0;
	padding: 15px;
	background-color: #f8f9fa;
	text-align: center;
}

h1, h2 {
	color: #333;
}

table {
	max-width: 1200px;
	margin: 20px auto;
	width: 100%;
	border-collapse: collapse;
	text-align: center;
	background-color: white;
}

th, td {
	font-family: 'Pretendard-Regular', sans-serif;
	border: 1px solid #ccc;
	padding: 10px;
}

th {
	background-color: #f1f1f1;
	color: #555;
}

button, input[type="button"] {
	background: #ddd;
	color: black;
	padding: 5px 10px;
	border-radius: 5px;
	border: none;
	cursor: pointer;
}

button:hover, input[type="button"]:hover {
	background: #bbb;
}

#searchForm {
	margin-top: 10px;
}

.title {
	color: black;
}

.pagination_button {
	display: inline-block;
	margin: 5px;
}

.pagination_button a {
	text-decoration: none;
	padding: 5px 10px;
	border-radius: 5px;
	color: black;
}

.pagination_button a:hover {
	background: #bbb;
}

.button-container {
	text-align: right;
	margin-bottom: 10px;
}

.pagination_button.current a {
	background: #333;
	color: white;
}
ul {
    list-style: none;
    padding: 0;
    display: flex;
    justify-content: center;
}
</style>

<meta charset="UTF-8">
<title>매장 질문 리스트</title>
</head>
<body>
<%@ include file="../common/header.jsp" %>
<sec:authentication property="principal" var="user"/> 
<h1>매장 질문 목록</h1>

<table>
    <thead>
        <tr>
            <th style="width: 60px">번호</th>
            <th style="width: 80px">식품</th>
            <th style="width: 700px">제목</th>
            <th style="width: 120px">작성자</th>
            <th style="width: 100px">작성일</th>
            <th style="width: 100px">상태</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="QuestionVO" items="${questionList}">
            <tr>
                <td>${QuestionVO.questionId}</td>
                
                <td>${QuestionVO.foodType}</td>
                
                <td><a href="detail?questionId=${QuestionVO.questionId}">
                    ${QuestionVO.questionTitle}</a></td>
                    
                <td>${QuestionVO.memberId}</td>
                
                <td><fmt:formatDate value="${QuestionVO.questionDateCreated }" pattern="yyyy-MM-dd HH:mm"/></td>
                
                <td><c:if test="${QuestionVO.isAnswered == 0}">
                        답변대기
                    </c:if> 
                    <c:if test="${QuestionVO.isAnswered == 1}">
                        <span style="color:green;">답변완료</span>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

	<form id="listForm" action="ownerList" method="get">
        <input type="hidden" name="pageNum">
        <input type="hidden" name="pageSize">
    </form>

    <ul>
        <c:if test="${pageMaker.isPrev()}">
            <li class="pagination_button"><a href="${pageMaker.startNum - 1}">이전</a></li>
        </c:if>
        <c:forEach begin="${pageMaker.startNum}" end="${pageMaker.endNum}" var="num">
    		<li class="pagination_button <c:if test='${num == pageMaker.pagination.pageNum}'>current</c:if>">
        		<a href="${num}">${num}</a>
    		</li>
		</c:forEach>
        <c:if test="${pageMaker.isNext()}">
            <li class="pagination_button"><a href="${pageMaker.endNum + 1}">다음</a></li>
        </c:if>
    </ul>

</body>

<script type="text/javascript">
$(".pagination_button a").on("click", function(e) {
    var listForm = $("#listForm");
    e.preventDefault();

    var pageNum = $(this).attr("href");
    var pageSize = "<c:out value='${pageMaker.pagination.pageSize }' />";

    listForm.find("input[name='pageNum']").val(pageNum);
    listForm.find("input[name='pageSize']").val(pageSize);
    listForm.submit();
});
</script>
</html>
