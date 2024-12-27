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

    /* Flexbox를 사용하여 a태그와 prgs를 같은 가로줄에 배치 */
    .header {
        display: flex; /* 플렉스 레이아웃 사용 */
        justify-content: space-between; /* 좌우 정렬 */
        align-items: center; /* 수직 중앙 정렬 */
    }

    .prgs {
        display: flex; /* prgs 내부 요소들을 가로로 나열 */
        justify-content: flex-end; /* prgs 내부 요소들을 오른쪽 정렬 */
        align-items: center; /* 수직 중앙 정렬 */
        margin-right: 20px; /* 오른쪽 여백 추가 */
    }

    /* prgs 내부 요소들 사이 여백 설정 */
    .prgs > * {
        margin-left: 10px; /* 각 요소 사이에 여백 추가 */
    }
</style>
	
<meta charset="UTF-8">
<title>GS24 Q&A 게시판</title>
</head>
<body>
	<h1>Q&A 게시판</h1>
	<h2>고객의 궁금증을 빠르게 해결해 드립니다.</h2>
	<!-- 글 작성 페이지 이동 버튼 -->
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