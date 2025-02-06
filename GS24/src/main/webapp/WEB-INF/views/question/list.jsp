<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <!-- jQuery 라이브러리 로드 -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

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

        .answer_item {
            padding: 10px;
            margin: 10px 0;
        }

        .answer_item .answer-content {
            font-size: 1em;
            color: #333;
        }

        .answer_item .answer-meta {
            font-size: 0.9em;
            color: #777;
            margin-bottom: 5px;
        }
    </style>
</head>
<body>

<%@ include file="../common/header.jsp" %>
<sec:authentication property="principal" var="user"/> 

<h1>QnA 게시판</h1>
<h2>고객의 궁금증을 빠르게 해결해 드립니다.</h2>
<sec:authorize access="hasRole('ROLE_MEMBER')">
<a href="register"><input type="button" value="글 작성"></a>
</sec:authorize>


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
        <c:forEach var="QuestionVO" items="${questionList}">
            <tr>
                <td>${QuestionVO.questionId}</td>
                <td>${QuestionVO.foodType}</td>
                <td>
                    <sec:authorize access="hasRole('ROLE_MEMBER')"> <!-- 일반회원일 때 -->
                        <c:choose>
                            <c:when test="${QuestionVO.questionSecret == true}"> <!-- 비밀글일 때 --> 
                                <c:choose>
                                    <c:when test="${user.username == QuestionVO.memberId}">
                                        <a href="detail?questionId=${QuestionVO.questionId}">
                                        ${QuestionVO.questionTitle} 🔒
                                    	</a>
                                    </c:when>
                                    <c:otherwise>
                                        비밀글입니다.
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:when test="${QuestionVO.questionSecret == false}"> <!-- 비밀글이 아닐 때 -->
                                <a href="detail?questionId=${QuestionVO.questionId}">
                                ${QuestionVO.questionTitle}
                           		</a>
                            </c:when>
                        </c:choose>
                    </sec:authorize>

                    <sec:authorize access="hasRole('ROLE_OWNER')"> <!-- 점주일 때 -->
                        <a href="detail?questionId=${QuestionVO.questionId}">
                            ${QuestionVO.questionTitle} 
                        </a>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                   		<a href="detail?questionId=${QuestionVO.questionId}">
                            ${QuestionVO.questionTitle} 
                        </a>
                    </sec:authorize>
                    
                    <sec:authorize access="isAnonymous()">       
                    ${QuestionVO.questionTitle}  
            		</sec:authorize>
                </td>
                <td>${QuestionVO.memberId}</td>
                <fmt:formatDate value="${QuestionVO.questionDateCreated}" pattern="yyyy-MM-dd HH:mm" var="questionDateCreated" />
                <td>${questionDateCreated}</td>
                <td>
                    <c:if test="${QuestionVO.isAnswered == 0}">
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

<ul>
    <!-- 이전 버튼 생성을 위한 조건문 -->
    <c:if test="${pageMaker.isPrev() }">
        <li><a href="list?pageNum=${pageMaker.startNum - 1}">이전</a></li>
    </c:if>
    
    <!-- 반복문으로 시작 번호부터 끝 번호까지 생성 -->
    <c:forEach begin="${pageMaker.startNum }" end="${pageMaker.endNum }" var="num">
        <li><a href="list?pageNum=${num }">${num }</a></li>
    </c:forEach>
    
    <!-- 다음 버튼 생성을 위한 조건문 -->
    <c:if test="${pageMaker.isNext() }">
        <li><a href="list?pageNum=${pageMaker.endNum + 1}">다음</a></li>
    </c:if>
</ul>

</body>
</html>
