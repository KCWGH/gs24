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
</head>
<body>
<%@ include file="../common/header.jsp" %>
    <h2>주문 목록</h2>

    <table border="1">
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
                        <!-- 승인 버튼 (대기 상태일 때만 표시) -->
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
                    console.log(xhr.responseText);
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
                    console.log(xhr.responseText);
                }
            });
        }

    </script>
</body>
</html>
