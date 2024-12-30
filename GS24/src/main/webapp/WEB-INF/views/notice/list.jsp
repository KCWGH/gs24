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

.prgs {
	display: flex;
	align-items: center;
	justify-content: flex-start;
	gap: 10px;
	margin-top: 20px;
}

.search-container {
	display: flex;
	gap: 10px;
	align-items: center;
}

.search-container select, .search-container input[type="text"],
	.search-container input[type="submit"] {
	padding: 6px 12px;
	font-size: 14px;
}

.search-container select {
	width: auto;
}

.search-container input[type="text"] {
	width: 200px;
}
</style>
<meta charset="UTF-8">
<title>공지사항</title>
</head>
<body>

	<a href="../food/list"><button>메인페이지</button></a>
	<a href="../question/list"><button>QnA게시판</button></a>
	<h1>공지사항</h1>
	<h2>GS24의 새로운 소식을 전해 드립니다.</h2>
	<hr>
	<!-- 글 작성 페이지 이동 버튼, 관리자만 보이도록 -->
	<c:if test="${ memberVO.memberRole == 2}">
		<a href="register"><input type="button" value="글 작성"></a>
	</c:if>

	<!-- 공지사항 목록 테이블 -->
	<table>
		<thead>
			<tr>
				<th style="width: 60px">번호</th>
				<th style="width: 700px">제목</th>
				<th style="width: 100px">작성일</th>
				<th style="width: 100px">조회수</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="NoticeVO" items="${noticeList}">
				<tr>
					<td>${NoticeVO.noticeId}</td>
					<td><a href="detail?noticeId=${NoticeVO.noticeId}">${NoticeVO.noticeTitle}</a></td>
					<!-- 작성일 포맷 변경 -->
					<fmt:formatDate value="${NoticeVO.noticeDateCreated}"
						pattern="yyyy-MM-dd" var="noticeDateCreated" />
					<td>${noticeDateCreated}</td>
					<td>${NoticeVO.noticeViews}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<!-- 검색 기능 -->
<div class="prgs">
    <form action="list" method="get">
        <div class="search-container">
            <!-- searchType을 GET 파라미터로 전달 -->
            <select name="searchType" id="searchType" title="검색 유형">
                <option value="title" ${param.searchType == 'title' ? 'selected' : ''}>제목</option>
                <option value="content" ${param.searchType == 'content' ? 'selected' : ''}>내용</option>
            </select>

            <!-- 제목 또는 내용에 맞는 input 필드 표시 -->
            <c:choose>
                <c:when test="${param.searchType == 'title'}">
                    <input type="text" name="noticeTitle" value="${param.noticeTitle}">
                </c:when>
                <c:when test="${param.searchType == 'content'}">
                    <input type="text" name="noticeContent" value="${param.noticeContent}">
                </c:when>
                <c:otherwise>
                    <!-- 기본적으로 제목 검색을 사용 -->
                    <input type="text" name="noticeTitle" value="${param.noticeTitle}">
                </c:otherwise>
            </c:choose>

            <input type="submit" value="검색">
        </div>
    </form>
</div>
    <!-- 결과 없음 처리 -->
    <c:if test="${empty noticeList}">
        <p>검색 결과가 없습니다.</p>
    </c:if>

	<!-- 페이징 처리 -->
	<ul>
		<c:if test="${pageMaker.isPrev()}">
			<li><a href="list?pageNum=${pageMaker.startNum - 1}&noticeTitle=${param.noticeTitle}&noticeContent=${param.noticeContent}&searchType=${param.searchType}">이전</a></li>
		</c:if>

		<c:forEach begin="${pageMaker.startNum}" end="${pageMaker.endNum}" var="num">
			<li><a href="list?pageNum=${num}&noticeTitle=${param.noticeTitle}&noticeContent=${param.noticeContent}&searchType=${param.searchType}" style="${param.pageNum == num ? 'font-weight: bold;' : ''}">${num}</a></li>
		</c:forEach>

		<c:if test="${pageMaker.isNext()}">
			<li><a href="list?pageNum=${pageMaker.endNum + 1}&noticeTitle=${param.noticeTitle}&noticeContent=${param.noticeContent}&searchType=${param.searchType}">다음</a></li>
		</c:if>
	</ul>
</body>
</html>
