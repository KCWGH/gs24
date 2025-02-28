<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="_csrf" content="${_csrf.token}" />
    <meta name="_csrf_header" content="${_csrf.headerName}" />
    <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
    
    <sec:authorize access="hasRole('ROLE_OWNER')"><title>발주 이력</title></sec:authorize>
    <sec:authorize access="hasRole('ROLE_ADMIN')"><title>발주 승인</title></sec:authorize>
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
    		max-width: 1000px; /* 테이블 최대 너비 설정 */
    		margin: 20px auto; /* 중앙 정렬 */
    		width: 100%;
    		border-collapse: collapse;
    		text-align: center;
    		background-color: white;
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
            border: none;
            cursor: pointer;
        }

        button:hover {
            background: #bbb;
        }
        ul {
    	display: flex;
    	justify-content: center; /* 가운데 정렬 */
    	padding: 0;
    	margin: 20px 0; /* 위아래 간격 */
    	list-style-type: none;
	}
	
    .pagination_button {
        display: inline-block;
        margin: 5px;
        text-align: center;
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
    .pagination_button.current a {
    background: #333;
    color: white;
	} 
    </style>
</head>
<body>
<%@ include file="../common/header.jsp" %>
	<sec:authorize access="hasRole('ROLE_OWNER')"><h1>발주 이력</h1></sec:authorize>
    <sec:authorize access="hasRole('ROLE_ADMIN')"><h1>발주 승인</h1></sec:authorize>

    <table>
        <thead>
            <tr>
                <th>주문 ID</th>
                <th>식품 ID</th>
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
                    <td><fmt:formatDate value="${order.orderDateCreated}" pattern="yyyy-MM-dd HH:mm"/></td>
                    <td>
                        <c:choose>
                            <c:when test="${order.approvalStatus == 0}"><span>대기</span></c:when>
                            <c:when test="${order.approvalStatus == 1}"><span style="color:green;">승인</span></c:when>
                            <c:when test="${order.approvalStatus == 2}"><span style="color:red;">거절</span></c:when>
                        </c:choose>
                    </td>
                    <td>
                        <c:if test="${order.approvalStatus == 0}">
                            <button style="background: #d4edda;" onclick="approveOrder(${order.orderId})">승인</button>
                            <button style="background: #f8d7da;" onclick="rejectOrder(${order.orderId})">거절</button>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    
    <form id="listForm" action="list" method="get">
        <input type="hidden" name="pageNum">
        <input type="hidden" name="pageSize">
    </form>

    <!-- 페이징 처리 -->
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

    <script>
        $(document).ajaxSend(function(e, xhr, opt){
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            xhr.setRequestHeader(header, token);
        });
        
        $(".pagination_button a").on("click", function(e) {
            var listForm = $("#listForm");
            e.preventDefault();

            var pageNum = $(this).attr("href");
            var pageSize = "<c:out value='${pageMaker.pagination.pageSize }' />";

            listForm.find("input[name='pageNum']").val(pageNum);
            listForm.find("input[name='pageSize']").val(pageSize);
            listForm.submit();
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
