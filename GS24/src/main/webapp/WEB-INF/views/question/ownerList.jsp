<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
table, th, td {
    border-style: solid;
    border-width: 1px;
    text-align: center;
}

ul {
    list-style-type: none;
    text-align: center;
}

li {
    display: inline-block;
}
</style>

<meta charset="UTF-8">
<title>매장 질문 리스트</title>
</head>
<body>
<%@ include file="../common/header.jsp" %>
<sec:authentication property="principal" var="user"/> 
<h1>매장 질문 목록</h1>

<hr>
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
        <c:forEach var="QuestionVO" items="${myQuestionList}">
            <tr>
                <td>${QuestionVO.questionId}</td>
                <td>${QuestionVO.foodType}</td>

                
                <td><a href="detail?questionId=${QuestionVO.questionId}">
                    ${QuestionVO.questionTitle}</a></td>
                    
                <td>${QuestionVO.memberId}</td>
                
                <!-- questionDateCreated 데이터 포맷 변경 -->
                <fmt:formatDate value="${QuestionVO.questionDateCreated }"
                    pattern="yyyy-MM-dd HH:mm" var="questionDateCreated" />
                <td>${questionDateCreated }</td>
                
                <td><c:if test="${QuestionVO.isAnswered == 0}">
                        답변대기
                    </c:if> 
                    <c:if test="${QuestionVO.isAnswered == 1}">
                        답변완료
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
<hr>

</body>
</html>
