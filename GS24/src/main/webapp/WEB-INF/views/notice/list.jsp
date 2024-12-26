<<<<<<< Updated upstream
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
	<title>공지사항</title>
	</head>
	<body>
		<h1>공지사항</h1>
		<h2>새로운 소식</h2>
		<!-- 글 작성 페이지 이동 버튼 -->

		<c:if test="${ memberVO.memberRole == 2}">
		<a href="register"><input type="button" value="글 작성"></a>
		</c:if>
		<c:if test="${ memberVO.memberRole != 2}">

		</c:if>


		<hr>
		<table>
			<thead>
				<tr>
					<th style="width: 60px">번호</th>
					<th style="width: 700px">제목</th>
					<th style="width: 120px">작성자</th>
					<th style="width: 100px">작성일</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="NoticeVO" items="${noticeList }">
					<tr>
						<td>${NoticeVO.noticeId }</td>
						<td><a href="detail?noticeId=${NoticeVO.noticeId }">
						${NoticeVO.noticeTitle }</a></td>
						<td>${NoticeVO.memberId }</td>
						<!-- boardDateCreated 데이터 포멧 변경 -->
						<fmt:formatDate value="${NoticeVO.noticeDateCreated }"
							pattern="yyyy-MM-dd" var="noticeDateCreated" /> <!-- 시간 표시 방법을 원하는 디자인으로 변경 가능  -->
						<td>${noticeDateCreated }</td>
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
			<c:forEach begin="${pageMaker.startNum }"
				end="${pageMaker.endNum }" var="num">
				<li><a href="list?pageNum=${num }">${num }</a></li>
			</c:forEach>
			<!-- 다음 버튼 생성을 위한 조건문 -->
			<c:if test="${pageMaker.isNext() }">
				<li><a href="list?pageNum=${pageMaker.endNum + 1}">다음</a></li>
			</c:if>
		</ul>
	</body>
=======
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

        .search-container select,
        .search-container input[type="text"],
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

    <!-- 글 작성 페이지 이동 버튼, 관리자만 보이도록 -->
    <c:if test="${ memberVO.memberRole == 2}">
        <a href="register"><input type="button" value="글 작성"></a>
    </c:if>

    <hr>

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
                    <fmt:formatDate value="${NoticeVO.noticeDateCreated}" pattern="yyyy-MM-dd" var="noticeDateCreated" />
                    <td>${noticeDateCreated}</td>
                    <td>${NoticeVO.noticeViews}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <!-- 페이징 처리 -->
    <ul>
        <!-- 이전 버튼 -->
        <c:if test="${pageMaker.isPrev()}">
            <li><a href="list?pageNum=${pageMaker.startNum - 1}&noticeTitle=${param.noticeTitle}&noticeContent=${param.noticeContent}&searchType=${param.searchType}">이전</a></li>
        </c:if>

        <!-- 페이지 번호 -->
        <c:forEach begin="${pageMaker.startNum}" end="${pageMaker.endNum}" var="num">
            <li><a href="list?pageNum=${num}&noticeTitle=${param.noticeTitle}&noticeContent=${param.noticeContent}&searchType=${param.searchType}">${num}</a></li>
        </c:forEach>

        <!-- 다음 버튼 -->
        <c:if test="${pageMaker.isNext()}">
            <li><a href="list?pageNum=${pageMaker.endNum + 1}&noticeTitle=${param.noticeTitle}&noticeContent=${param.noticeContent}&searchType=${param.searchType}">다음</a></li>
        </c:if>
    </ul>

    <!-- 제목 또는 내용으로 검색 -->
    <div class="prgs">
        <form action="list" method="get">
            <div class="search-container">
                <!-- 검색 유형 선택 -->
                <select name="searchType" id="searchType" title="검색 유형" class="select1" onchange="toggleSearchPlaceholder()">
                    <option value="title" ${param.searchType == 'title' ? 'selected' : ''}>제목</option>
                    <option value="content" ${param.searchType == 'content' ? 'selected' : ''}>내용</option>
                </select>

                <input type="text" id="searchInput" name="${param.searchType == 'title' ? 'noticeTitle' : 'noticeContent'}"
                       value="${param.searchType == 'title' ? param.noticeTitle : param.noticeContent}"
                       title="${param.searchType == 'title' ? '검색' : '검색'}"
                       placeholder="${param.searchType == 'title' ? '검색' : '검색'}">

                <input type="submit" value="검색">
            </div>
        </form>
    </div>
>>>>>>> Stashed changes

</body>
</html>
