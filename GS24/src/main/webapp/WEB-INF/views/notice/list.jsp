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

        .sel2_rt select, #us_id {
            padding: 5px;
            font-size: 14px;
        }

        .sel2_lt input {
            padding: 6px 12px;
            font-size: 14px;
        }

    </style>
    <meta charset="UTF-8">
    <title>공지사항</title>
</head>
<body>
    <a href="../food/list"><button>메인페이지</button></a>
    <a href="../question/list"><button>Q&A게시판</button></a>
    <h1>공지사항</h1>
    <h2>GS24의 새로운 소식을 전해 드립니다.</h2>
    
    <!-- 글 작성 페이지 이동 버튼, 관리자만 보이도록 -->
    <c:if test="${ memberVO.memberRole == 2}">
        <a href="register"><input type="button" value="글 작성"></a>
    </c:if>

    <hr>
    <table>
        <thead>
            <tr>
                <th style="width: 60px">번호</th>
                <th style="width: 700px">제목</th>
                <th style="width: 100px">작성일</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="NoticeVO" items="${noticeList}">
                <tr>
                    <td>${NoticeVO.noticeId }</td>
                    <td><a href="detail?noticeId=${NoticeVO.noticeId }">${NoticeVO.noticeTitle }</a></td>
                    <!-- boardDateCreated 데이터 포멧 변경 -->
                    <fmt:formatDate value="${NoticeVO.noticeDateCreated }" pattern="yyyy-MM-dd" var="noticeDateCreated" />
                    <td>${noticeDateCreated }</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <!-- 페이징 처리 -->
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

    <!-- 제목 검색 -->
    <div class="prgs">
        <label for="prgs" class="invisible"></label>
        <div class="sel2_rt">
            <select name="prgs" id="prgs" title="제목" class="select1">
                <option value="title">제목</option>
            </select>
        </div>
        
        <!-- 검색 버튼 클릭 시 제목으로 검색 -->
        <form action="list" method="get">
            <input type="text" id="searchTitle" name="noticeTitle" value="${param.noticeTitle}" title="제목으로 검색" style="width: 200px;" placeholder="제목 검색">
            <input type="submit" value="검색">
        </form>
    </div>

</body>
</html>
