<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<title>GS24 Q&A 게시판</title>
<script type="text/javascript">
    // 작성자 확인 후 이동하는 함수
    function checkAuthorAndRedirect(questionId, authorId, memberRole) {
        var currentUser = "${sessionScope.memberId}"; // 현재 로그인된 사용자 ID
        var currentUserRole = "${sessionScope.memberVO.memberRole}"; // 현재 로그인된 사용자 권한

        if (currentUser === authorId || currentUserRole == 2) {
            // 작성자이거나 관리자일 경우 게시판 상세로 이동
            window.location.href = "detail?questionId=" + questionId;
        } else {
            // 작성자가 아니고 관리자가 아닐 경우 경고
            alert("게시판 작성자 또는 관리자만 해당 게시판에 접근할 수 있습니다.");
        }
    }
</script>

</head>
<body>
	<h1>Q&A 게시판</h1>
	<h2>고객의 궁금증을 빠르게 해결해 드립니다.</h2>

	<!-- 글 작성 페이지 이동 버튼 -->
	<c:if test="${empty sessionScope.memberId}">
        * 글작성은 로그인이 필요한 서비스입니다.
        <a href="../member/login">로그인하기</a>
	</c:if>

	<c:if test="${not empty sessionScope.memberId}">
		<a href="register"><input type="button" value="글 작성"></a>
		<a href="myList"><input type="button" value="내가 작성한 글"></a>
	</c:if>

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

					<!-- 게시판 제목 클릭 시 checkAuthorAndRedirect 함수 호출 -->
					<td><a href="javascript:void(0);"
						onclick="checkAuthorAndRedirect(${QuestionVO.questionId}, '${QuestionVO.memberId}', '${sessionScope.memberRole}')">
							${QuestionVO.questionTitle}</a></td>


					<td>${QuestionVO.memberId}</td>
					<!-- questionDateCreated 데이터 포멧 변경 -->
					<fmt:formatDate value="${QuestionVO.questionDateCreated }"
						pattern="yyyy-MM-dd HH:mm" var="questionDateCreated" />
					<td>${questionDateCreated }</td>
					<td><c:if test="${QuestionVO.isAnswered == 0}">
                            답변대기
                        </c:if> <c:if test="${QuestionVO.isAnswered != 0}">
                            답변완료
                        </c:if></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<ul>
		<!-- 이전 버튼 생성을 위한 조건문 -->
		<c:if test="${pageMaker.isPrev() }">
			<li><a href="list?pageNum=${pageMaker.startNum - 1}">이전</a></li>
		</c:if>

		<!-- 반복문으로 시작 번호부터 끝 번호까지 생성 -->
		<c:forEach begin="${pageMaker.startNum }" end="${pageMaker.endNum }"
			var="num">
			<li><a href="list?pageNum=${num }">${num }</a></li>
		</c:forEach>

		<!-- 다음 버튼 생성을 위한 조건문 -->
		<c:if test="${pageMaker.isNext() }">
			<li><a href="list?pageNum=${pageMaker.endNum + 1}">다음</a></li>
		</c:if>
	</ul>
	
	
</body>
</html>
