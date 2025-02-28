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
        /* 전체 페이지 스타일 */
        body {
            margin: 0;
            padding: 15px;
            background-color: #f8f9fa;
            text-align: center;
        }

        /* 제목 스타일 */
        h1, h2 {
            color: #333;
        }

        /* 테이블 스타일 */
	table {
    	max-width: 1200px;
    	margin: 20px auto;
    	width: 100%;
    	border-collapse: collapse;
    	text-align: center;
    	background-color: white;
	}

        th, td {
            border: 1px solid #ccc;
            padding: 10px;
            font-size: 14px;
        }

        th {
            background-color: #f1f1f1;
            color: #555;
        }

        /* 버튼 스타일 */
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

        /* 검색 폼 스타일 */
        #searchForm {
            margin-top: 10px;
        }

        /* 페이징 스타일 */
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

    /* 글 작성 버튼 컨테이너 */
    .button-container {
        text-align: right;
        margin-bottom: 10px;
    }
    .pagination_button.current a {
    background: #333;
    color: white;
	} 
    </style>
    <title>Q&amp;A 게시판</title>
</head>
<body>

<%@ include file="../common/header.jsp" %>
<sec:authentication property="principal" var="user"/>

<h1>Q&amp;A 게시판</h1>
<h2>고객의 궁금증을 빠르게 해결해 드립니다.</h2>

<!-- QnA 목록 -->
<table>
    <thead>
    <sec:authorize access="hasRole('ROLE_MEMBER')">
        <a href="register"><input type="button" value="글 작성"></a>
	</sec:authorize>
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

<!-- 페이징 처리 -->
<ul>
    <c:if test="${pageMaker.isPrev()}">
        <li class="pagination_button"><a href="list?pageNum=${pageMaker.startNum - 1}">이전</a></li>
    </c:if>

    <c:forEach begin="${pageMaker.startNum}" end="${pageMaker.endNum}" var="num">
        <li class="pagination_button 
            <c:if test='${num == pageMaker.pagination.pageNum}'>current</c:if>">
            <a href="list?pageNum=${num}">${num}</a>
        </li>
    </c:forEach>

    <c:if test="${pageMaker.isNext()}">
        <li class="pagination_button"><a href="list?pageNum=${pageMaker.endNum + 1}">다음</a></li>
    </c:if>
</ul>

<%@ include file="../common/footer.jsp"%>
</body>
</html>