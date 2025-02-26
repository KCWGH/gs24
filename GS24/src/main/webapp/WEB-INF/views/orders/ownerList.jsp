<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="_csrf" content="${_csrf.token}" />
    <meta name="_csrf_header" content="${_csrf.headerName}" />
    <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<<<<<<< Updated upstream
    <title>발주 이력</title>
=======
    <title>발주 목록</title>
>>>>>>> Stashed changes
    <style>
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
        width: 100%;
        margin-top: 20px;
        border-collapse: collapse;
        text-align: center;
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

<<<<<<< Updated upstream
        button:hover {
            background: #bbb;
        }
            /* 페이징 스타일 */
=======
    button:hover, input[type="button"]:hover {
        background: #bbb;
    }

    /* 검색 폼 스타일 */
    #searchForm {
        margin-top: 10px;
    }
    
    .title {
    	color: black;
    }

    /* 페이징 스타일 */
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
	}
=======
	} 
>>>>>>> Stashed changes
    </style>
</head>
<body>
<%@ include file="../common/header.jsp" %>

    <h1>발주 이력</h1>

    <table>
        <thead>
            <tr>
            	<th>점주 ID</th>
                <th>발주 ID</th>
                <th>식품 ID</th>
                <th>발주 수량</th>
                <th>발주 날짜</th>
                <th>발주 상태</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="order" items="${ordersByOwner}">
                <tr>
                	<td>${order.ownerId}</td>
                    <td>${order.orderId}</td>
                    <td>${order.foodId}</td>
                    <td>${order.orderAmount}</td>
                    <td>
                        <fmt:formatDate value="${order.orderDateCreated}" pattern="yyyy-MM-dd HH:mm" />
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${order.approvalStatus == 0}">대기</c:when>
                            <c:when test="${order.approvalStatus == 1}">승인</c:when>
                            <c:when test="${order.approvalStatus == 2}">거절</c:when>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <form id="listForm" action="ownerList" method="get">
        <input type="hidden" name="pageNum">
        <input type="hidden" name="pageSize">
<<<<<<< Updated upstream
        <input type="hidden" name="type" value="${param.type}">
        <input type="hidden" name="keyword" value="${param.keyword}">
    </form>
<ul>
=======
    </form>
<!-- 페이징 처리 -->
    <ul>
>>>>>>> Stashed changes
        <c:if test="${pageMaker.isPrev()}">
            <li class="pagination_button"><a href="${pageMaker.startNum - 1}">이전</a></li>
        </c:if>
        <c:forEach begin="${pageMaker.startNum}" end="${pageMaker.endNum}" var="num">
    		<li class="pagination_button <c:if test='${num == pageMaker.pagination.pageNum}'>current</c:if>">
        		<a href="${num}">${num}</a>
    		</li>
		</c:forEach>
        <c:if test="${pageMaker.isNext()}">
            <li class="pagination_button"><a href="${pageMaker.endNum + 1}">다음</a></li>
        </c:if>
    </ul>
<<<<<<< Updated upstream
<script type="text/javascript">
$(document).ready(function() {
    $(".pagination_button a").on("click", function(e) {
        var listForm = $("#listForm");
        e.preventDefault();

        var pageNum = $(this).attr("href");
        var pageSize = "<c:out value='${pageMaker.pagination.pageSize }' />";
        var type = "<c:out value='${pageMaker.pagination.type }' />";
        var keyword = "<c:out value='${pageMaker.pagination.keyword }' />";

        listForm.find("input[name='pageNum']").val(pageNum);
        listForm.find("input[name='pageSize']").val(pageSize);
        listForm.submit();
    });
=======
    
    <script type="text/javascript">
$(".pagination_button a").on("click", function(e) {
    var listForm = $("#listForm");
    e.preventDefault();

    var pageNum = $(this).attr("href");
    var pageSize = "<c:out value='${pageMaker.pagination.pageSize }' />";

    listForm.find("input[name='pageNum']").val(pageNum);
    listForm.find("input[name='pageSize']").val(pageSize);
    listForm.submit();
>>>>>>> Stashed changes
});
</script>
<%@ include file="../common/footer.jsp" %>
</body>
</html>
