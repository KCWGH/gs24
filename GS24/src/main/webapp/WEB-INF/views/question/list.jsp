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
 <sec:authentication property="principal" var="user" />

    <h1>QnA 게시판</h1>
    <h2>고객의 궁금증을 빠르게 해결해 드립니다.</h2>
	
	<sec:authorize access="hasRole('ROLE_MEMBER')">
		<a href="register"><input type="button" value="글 작성"></a>
		<a href="myList"><input type="button" value="내가 작성한 글"></a>
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
					<td>${QuestionVO.foodName}</td>
					<td>
						<sec:authorize access="hasRole('ROLE_MEMBER')"> <!-- 일반회원이고 -->
							<c:choose>
								<c:when test="${QuestionVO.questionSecret == true }"> <!-- 비밀글일 때 -->
									<c:choose>
                						<c:when test="${user.username == QuestionVO.memberId}">
                    						<p>작성자 본인의 비밀글입니다.</p>
                						</c:when>
                						<c:otherwise>
                    						<p>비밀글입니다. 접근 권한이 없습니다.</p>
                						</c:otherwise>
            						</c:choose>
								</c:when>
								<c:when test="${QuestionVO.questionSecret == false }"> <!-- 비밀글이 아닐 때 -->
									<p>공개된 글입니다.</p>
								</c:when>
							</c:choose>
						</sec:authorize>
						<sec:authorize access="hasRole('ROLE_OWNER')"> <!-- 점주일 때 -->
							
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
                <!-- 질문 내용 (초기에는 숨겨짐) -->
                <tr class="question-content" id="content-${QuestionVO.questionId}" style="display: none;">
                    <td colspan="6" style="text-align: left; padding: 10px;">
                        <strong>내용:</strong>
                        <p>${QuestionVO.questionContent}</p>
                    </td>
                </tr>
                
                <!-- 댓글 영역 -->
                <tr id="answers-${QuestionVO.questionId}" style="display: none;">
                    <td colspan="6" style="padding: 10px;">
                        <div class="answers-section" id="replies-${QuestionVO.questionId}"></div>
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
	
	<!-- jQuery 스크립트 -->
	<script type="text/javascript">
        // 현재 로그인한 사용자 정보
        var currentUser = "${user.username}";
        var currentUserRole = "${user.authorities}"; // 권한: ROLE_OWNER 등

        // 게시글 제목 클릭 시 처리하는 함수
        function handleClick(questionId, authorId) {
            // 작성자이거나 관리자일 경우 상세 페이지로 이동
            if (currentUser === authorId || currentUserRole.indexOf('ROLE_OWNER') != -1) {
                window.location.href = "detail?questionId=" + questionId;
            } else {
                // 권한이 없으면 질문 내용을 토글로 펼침
                toggleQuestionContent(questionId);
                loadComments(questionId); // 댓글 로딩
            }
        }

        // 질문 내용 토글 함수
        function toggleQuestionContent(questionId) {
            var contentElement = document.getElementById("content-" + questionId);
            var answersElement = document.getElementById("answers-" + questionId);
            
            // 질문 내용 및 댓글을 펼치거나 숨기기
            if (contentElement.style.display === "none" || contentElement.style.display === "") {
                contentElement.style.display = "table-row";  // 내용 보이게
                answersElement.style.display = "table-row";  // 댓글 영역 보이게
            } else {
                contentElement.style.display = "none";  // 내용 숨기기
                answersElement.style.display = "none";  // 댓글 영역 숨기기
            }
        }

        // 댓글을 불러오는 함수
        function loadComments(questionId) {
            var repliesElement = document.getElementById("replies-" + questionId);

            // 이미 댓글이 로딩되었으면 다시 요청하지 않음
            if (repliesElement.innerHTML !== "") {
                return;
            }

            // 댓글을 서버에서 불러오기 위한 Ajax 요청
            $.getJSON('../answer/all/' + questionId, function(data) {
                var list = '';
                $(data).each(function() {
                    var answerDateCreated = new Date(this.answerDateCreated);
                    list += '<div class="answer_item">'
                        + '<div class="answer-content">' + this.answerContent + '</div>'
                        + '<div class="answer-meta">' + this.memberId + ' | ' + answerDateCreated.toLocaleString() + '</div>'
                        + '</div>';
                });
                repliesElement.innerHTML = list;

                // 댓글 영역을 보이게 처리
                repliesElement.style.display = 'block';
            });
        }
    </script>
</body>
</html>
