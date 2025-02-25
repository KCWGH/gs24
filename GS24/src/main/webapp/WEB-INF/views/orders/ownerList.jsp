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
    <title>주문 목록</title>
    <style>
        /* 전체 페이지 스타일 */
        body {
            margin: 0;
            padding: 15px;
            background-color: #f8f9fa;
            text-align: center;
        }

        /* 제목 스타일 */
        h2 {
            color: #333;
            margin-bottom: 5px;
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
            text-align: center;
        }

        th {
            background-color: #f1f1f1;
            color: #555;
        }

        /* 버튼 스타일 */
        button {
            background: #ddd;
            color: black;
            padding: 5px 10px;
            border-radius: 5px;
            text-decoration: none;
            margin: 5px;
            border: none;
            cursor: pointer;
        }

        button:hover {
            background: #bbb;
        }
    </style>
</head>
<body>
<%@ include file="../common/header.jsp" %>

    <h1>발주 목록</h1>

    <table>
        <thead>
            <tr>
            	<th>점주 ID</th>
                <th>주문 ID</th>
                <th>음식 ID</th>
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
<ul>
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
<%@ include file="../common/footer.jsp" %>
</body>
</html>
