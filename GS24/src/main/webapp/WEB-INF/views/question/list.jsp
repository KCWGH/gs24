<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">

<<<<<<< HEAD
    <!-- jQuery 라이브러리 로드 -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <style type="text/css">
        table, th, td {
            border-style: solid;
            border-width: 1px;
            text-align: center;
        }
=======
table, th, td {
	border-style: solid;
	border-width: 1px;
	text-align: center;
}
>>>>>>> c10c874fbe2a4197563ffb7d351cdee7e02242a6

ul {
	list-style-type: none;
	text-align: center;
}

li {
	display: inline-block;
}

</style>

<<<<<<< HEAD
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
    <!-- 페이지 내용 -->
    <a href="../food/list"><button>메인페이지</button></a>
    <a href="../notice/list"><button>공지사항</button></a>

    <h1>QnA 게시판</h1>
    <h2>고객의 궁금증을 빠르게 해결해 드립니다.</h2>

    <!-- 글 작성 페이지 이동 버튼 -->
    <c:if test="${empty sessionScope.memberId}">
=======
<meta charset="UTF-8">

<title>GS24 Q&A 게시판</title>
<body>
<a href="../food/list"><button>메인페이지</button></a>
<a href="../notice/list"><button>공지사항</button></a>

	<a href="register"><input type="button" value="글 작성"></a>
	
	
	 <div class="prgs">
      <label for="prgs" class="invisible">구분</label>
      <div class="sel2_rt">
      <select name="prgs" id="prgs" title="제목+작성일" class="select1">
      	<option value="all">식품+제목+작성일</option>
      	<option value="foodName">식품</option>
      	<option value="title">제목</option>
      	<option value="dateCreated">작성일</option>
      </select>
      </div>	
      <input type="text" id="us_id" name="us_id" title="검색어 입력" class="ml7 fl" style="width:200px;">
      <div class="sel2_lt">
      <input type="button" value="검색" id="searchBtn">
      </div>	
      </div> 
	<c:if test="${empty sessionScope.memberId}">
>>>>>>> c10c874fbe2a4197563ffb7d351cdee7e02242a6
        * 글작성은 로그인이 필요한 서비스입니다.
        <a href="../member/login">로그인하기</a>
	</c:if>

	<c:if test="${not empty sessionScope.memberId}">
		<a href="register"><input type="button" value="글 작성"></a>
		<a href="myList"><input type="button" value="내가 작성한 글"></a>
	</c:if>

<<<<<<< HEAD
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
                        <c:choose>
    <c:when test="${QuestionVO.questionSecret == true}"> <!-- Boolean true로 비교 -->
        <c:if test="${sessionScope.memberId == QuestionVO.memberId || sessionScope.memberVO.memberRole == 2}">
            <!-- 관리자 또는 작성자일 경우 비밀글 표시 -->
            <a href="javascript:void(0);" onclick="handleClick(${QuestionVO.questionId}, '${QuestionVO.memberId}')">
                ${QuestionVO.questionTitle}
            </a>
        </c:if>
        <c:if test="${sessionScope.memberId != QuestionVO.memberId && sessionScope.memberVO.memberRole != 2}">
            <!-- 일반 사용자(관리자, 작성자 외)는 비밀글 제목만 보임 -->
            ${QuestionVO.questionTitle} 🔒
        </c:if>
    </c:when>
    <c:otherwise>
        <!-- 비밀글이 아닌 경우 제목을 클릭 가능 -->
        <a href="javascript:void(0);" onclick="handleClick(${QuestionVO.questionId}, '${QuestionVO.memberId}')">
            ${QuestionVO.questionTitle}
        </a>
    </c:otherwise>
</c:choose>
        </td>
            <td>${QuestionVO.memberId}</td>
               <fmt:formatDate value="${QuestionVO.questionDateCreated}" pattern="yyyy-MM-dd HH:mm" var="questionDateCreated" />
                  <td>${questionDateCreated}</td>
                    <td>
                        <c:if test="${QuestionVO.isAnswered == 0}">
=======
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
			<c:forEach var="QuestionVO" items="${questionList }">
				<tr>
					<td>${QuestionVO.questionId }</td>
					<td>${QuestionVO.foodName }</td>
					<td><a href="detail?questionId=${QuestionVO.questionId }">
							${QuestionVO.questionTitle }</a></td>
					<td>${QuestionVO.memberId }</td>
					<!-- boardDateCreated 데이터 포멧 변경 -->
					<fmt:formatDate value="${QuestionVO.questionDateCreated }"
						pattern="yyyy-MM-dd HH:mm" var="questionDateCreated" />
					<!-- 시간 표시 방법을 원하는 디자인으로 변경 가능  -->
					<td>${questionDateCreated }</td>
					<td><c:if test="${QuestionVO.isAnswered == 0}">
>>>>>>> c10c874fbe2a4197563ffb7d351cdee7e02242a6
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
</html>