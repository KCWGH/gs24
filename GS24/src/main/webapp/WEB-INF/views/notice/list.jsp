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

</html>
