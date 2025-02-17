<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

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
    <script src="https://code.jquery.com/jquery-3.7.1.js">
</script>
    <meta charset="UTF-8">
    <title>공지사항</title>
     <%@ include file="../common/header.jsp" %>
</head>
<body>

    <h1>공지사항</h1>
    <h2>GS24의 새로운 소식을 전해 드립니다.</h2>
    
    <!-- 글 작성 페이지 이동 버튼, 점주만 보이도록 -->
	<sec:authorize access="hasRole('ROLE_ADMIN')">
    <a href="register"><input type="button" value="글 작성"></a>
	</sec:authorize>
	 
	    
	 <hr>
	 
    <!-- 공지사항 목록 테이블 -->
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
        <c:choose>
            <c:when test="${NoticeVO.noticeType == 0}">
                <!-- 일반 공지사항 (누구나 볼 수 있음) -->
                <tr>
                    <td>${NoticeVO.noticeId}</td>
                    <td><a href="detail?noticeId=${NoticeVO.noticeId}">${NoticeVO.noticeTitle}</a></td>
                    <fmt:formatDate value="${NoticeVO.noticeDateCreated}" pattern="yyyy-MM-dd HH:mm" var="noticeDateCreated" />
                    <td>${noticeDateCreated}</td>
                </tr>
            </c:when>
            <c:when test="${NoticeVO.noticeType == 1}">
                <!-- 점주 전용 공지사항 (ROLE_OWNER 또는 ROLE_ADMIN만 볼 수 있음) -->
                <sec:authorize access="hasRole('ROLE_OWNER') or hasRole('ROLE_ADMIN')">
                    <tr>
                        <td>${NoticeVO.noticeId}</td>
                        <td><a href="detail?noticeId=${NoticeVO.noticeId}">${NoticeVO.noticeTitle}</a></td>
                        <fmt:formatDate value="${NoticeVO.noticeDateCreated}" pattern="yyyy-MM-dd HH:mm" var="noticeDateCreated" />
                        <td>${noticeDateCreated}</td>
                    </tr>
                </sec:authorize>
            </c:when>
        </c:choose>
    </c:forEach>
        </tbody>
    </table>
    
    <hr>
    
    <form id="searchForm" action="list" method="get">
			<input type="hidden" name="pageNum">
	    	<input type="hidden" name="pageSize">
			<select name="type">
				<option value="title">제목</option>
				<option value="content">내용</option>
			</select>
			<input type="text" name="keyword">
	
	    	<button>검색</button>
		</form>
		
    <!-- 게시글 번호, 페이지 번호, 페이지 사이즈를 전송하는 form  -->
		<form id="detailForm" action="detail" method="get">
			<input type="hidden" name="noticeId" >
			<input type="hidden" name="pageNum" >
	    	<input type="hidden" name="pageSize" >
	    	<input type="hidden" name="type" >
			<input type="hidden" name="keyword" >
		</form>
    
    <!-- 페이징 처리 -->
    <ul>
			<!-- 이전 버튼 생성을 위한 조건문 -->
			<c:if test="${pageMaker.isPrev() }">
				<li class="pagination_button"><a href="${pageMaker.startNum - 1}">이전</a></li>
			</c:if>
			<!-- 반복문으로 시작 번호부터 끝 번호까지 생성 -->
			<c:forEach begin="${pageMaker.startNum }"
				end="${pageMaker.endNum }" var="num">
				<li class="pagination_button"><a href="${num }">${num }</a></li>
			</c:forEach>
			<!-- 다음 버튼 생성을 위한 조건문 -->
			<c:if test="${pageMaker.isNext() }">
				<li class="pagination_button"><a href="${pageMaker.endNum + 1}">다음</a></li>
			</c:if>
		</ul>
		<!-- 페이지 번호와 페이지 사이즈를 전송하는 form -->
		<form id="listForm" action="list" method="get">
	    	<input type="hidden" name="pageNum" >
	    	<input type="hidden" name="pageSize" >
	    	<input type="hidden" name="type">
			<input type="hidden" name="keyword">
	    </form>
	    
	    <script type="text/javascript">
		$(document).ready(function(){
			
			
									
			// pagination_button을 클릭하면 페이지 이동
			$(".pagination_button a").on("click", function(e){
				var listForm = $("#listForm"); // form 객체 참조
				e.preventDefault(); // a 태그 이벤트 방지
			
				var pageNum = $(this).attr("href"); // a태그의 href 값 저장
				// 현재 페이지 사이즈값 저장
				var pageSize = "<c:out value='${pageMaker.pagination.pageSize }' />";
				var type = "<c:out value='${pageMaker.pagination.type }' />";
				var keyword = "<c:out value='${pageMaker.pagination.keyword }' />";
				 
				// 페이지 번호를 input name='pageNum' 값으로 적용
				listForm.find("input[name='pageNum']").val(pageNum);
				// 선택된 옵션 값을 input name='pageSize' 값으로 적용
				listForm.find("input[name='pageSize']").val(pageSize);
				// type 값을 적용
				listForm.find("input[name='type']").val(type);
				// keyword 값을 적용
				listForm.find("input[name='keyword']").val(keyword);
				listForm.submit(); // form 전송
			}); // end on()
			
			// detail_button을 클릭하면 페이지 이동
			$(".detail_button a").on("click", function(e){
				var detailForm = $("#detailForm");
				e.preventDefault(); // a 태그 이벤트 방지
			
				var noticed = $(this).attr("href"); // a태그의 href 값 저장

				var type = "<c:out value='${pageMaker.pagination.type }' />";
				var keyword = "<c:out value='${pageMaker.pagination.keyword }' />";
				var pageNum = "<c:out value='${pageMaker.pagination.pageNum }' />";
				// 현재 페이지 사이즈값 저장
				var pageSize = "<c:out value='${pageMaker.pagination.pageSize }' />";
				 
				// 클릭된 게시글 번호를 input name='noticeId' 값으로 적용
				detailForm.find("input[name='noticeId']").val(noticeId);
				// 페이지 번호를 input name='pageNum' 값으로 적용
				detailForm.find("input[name='pageNum']").val(pageNum);
				// 선택된 옵션 값을 input name='pageSize' 값으로 적용
				detailForm.find("input[name='pageSize']").val(pageSize);
				// type 값을 적용
				detailForm.find("input[name='type']").val(type);
				// keyword 값을 적용
				detailForm.find("input[name='keyword']").val(keyword);
				detailForm.submit(); // form 전송
			}); // end on()
			
			
			$("#searchForm button").on("click", function(e){
				var searchForm = $("#searchForm");
				e.preventDefault(); // a 태그 이벤트 방지
				
				var keywordVal = searchForm.find("input[name='keyword']").val();
				console.log(keywordVal);
				if(keywordVal == '') {
					alert('검색 내용을 입력하세요.');
					return;
				}
				
				var pageNum = 1; // 검색 후 1페이지로 고정
				// 현재 페이지 사이즈값 저장
				var pageSize = "<c:out value='${pageMaker.pagination.pageSize }' />";
				 
				// 페이지 번호를 input name='pageNum' 값으로 적용
				searchForm.find("input[name='pageNum']").val(pageNum);
				// 선택된 옵션 값을 input name='pageSize' 값으로 적용
				searchForm.find("input[name='pageSize']").val(pageSize);
				searchForm.submit(); // form 전송
			}); // end on()
			
		}); // end document()
	</script>
<%@ include file="../common/footer.jsp"%>
</body>
