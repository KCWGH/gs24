<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
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

    <h1>주문 목록</h1>

    <table>
        <thead>
            <tr>
                <th>주문 ID</th>
                <th>음식 ID</th>
                <th>주문 수량</th>
                <th>주문 날짜</th>
                <th>주문 상태</th>
                <th>승인/거절</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="order" items="${orderList}">
                <tr>
                    <td>${order.orderId}</td>
                    <td>${order.foodId}</td>
                    <td>${order.orderAmount}</td>
                    <td>${order.orderDateCreated}</td>
                    <td>
                        <c:choose>
                            <c:when test="${order.approvalStatus == 0}">대기</c:when>
                            <c:when test="${order.approvalStatus == 1}">승인</c:when>
                            <c:when test="${order.approvalStatus == 2}">거절</c:when>
                        </c:choose>
                    </td>
                    <td>
                        <c:if test="${order.approvalStatus == 0}">
                            <button onclick="approveOrder(${order.orderId})">승인</button>
                            <button onclick="rejectOrder(${order.orderId})">거절</button>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <script>
        $(document).ajaxSend(function(e, xhr, opt){
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            xhr.setRequestHeader(header, token);
        });

        function approveOrder(orderId) {
            $.ajax({
                url: '../orders/approve',
                type: 'POST',
                data: { "orderId": orderId }, 
                success: function(response) {
                    if (response === "success") {
                        alert('해당 발주가 승인되었습니다.');
                        location.reload();
                    } else {
                        alert('해당 발주의 승인 에러 발생: ' + response);
                    }
                },
                error: function(xhr) {
                    alert('Error approving order: ' + xhr.status + ' ' + xhr.statusText);
                }
            });
        }

        function rejectOrder(orderId) {
            $.ajax({
                url: '../orders/reject',
                type: 'POST',
                data: { "orderId": orderId }, 
                success: function(response) {
                    if (response === "success") {
                        alert('해당 발주가 거절되었습니다.');
                        location.reload();
                    } else {
                        alert('해당 발주의 거절 에러 발생: ' + response);
                    }
                },
                error: function(xhr) {
                    alert('Error rejecting order: ' + xhr.status + ' ' + xhr.statusText);
                }
            });
        }
    </script>
<%@ include file="../common/footer.jsp" %>
</body>
</html>
