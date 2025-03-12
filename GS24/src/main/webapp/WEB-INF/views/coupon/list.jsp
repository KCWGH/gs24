<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="../resources/css/fonts.css">
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<title>쿠폰 리스트</title>
<style type="text/css">
body {
    font-family: 'Pretendard-Regular', sans-serif;
    margin: 0;
    padding: 15px;
    text-align: center;
    background-color: #f8f9fa;
}

h2 {
    color: #333;
    margin-bottom: 5px;
}

table {
    max-width: 1200px;
    margin: 20px auto;
    width: 100%;
    border-collapse: collapse;
    text-align: center;
    background-color: white;
}

th, td {
    border: 1px solid #ccc;
    padding: 10px;
    text-align: center;
}

th {
    background-color: #f1f1f1;
    color: #555;
}

.couponRow td a, .couponRow td .publish {
    font-family: 'Pretendard-Regular', sans-serif;
    background: #ddd;
    color: black;
    padding: 5px 10px;
    border-radius: 5px;
    text-decoration: none;
    border: none;
    margin: 5px;
}

.couponRow td a:hover, .couponRow td .publish:hover {
    background: #bbb;
}

#publish, .action-buttons button {
    font-family: 'Pretendard-Regular', sans-serif;
    background: #ddd;
    color: black;
    padding: 5px 10px;
    border-radius: 5px;
    border: none;
    cursor: pointer;
    white-space: nowrap;
}

#publish:hover, .action-buttons button:hover {
    background: #bbb;
}

ul {
    display: flex;
    justify-content: center;
    padding: 0;
    margin: 20px 0;
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

.couponRow {
    position: relative;
}

.action-buttons {
	visibility: hidden;
    gap: 5px;
    position: absolute;
    top: 50%;
    border: none;
    transform: translateY(-50%);
}

.couponRow:hover .action-buttons {
    display: flex;
    flex-direction: row;
    visibility: visible;
}
</style>
</head>
<body>
<%@ include file="../common/header.jsp" %>
<h1>쿠폰 리스트</h1>

<table id="couponTable">
<sec:authorize access="hasRole('ROLE_ADMIN')">
    	<button id="publish" onclick="window.open('../coupon/publish', '_blank', 'width=500,height=710,top=100,left=200')">쿠폰 추가</button>
</sec:authorize>
    <thead>
        <tr>
            <th>쿠폰번호</th>
            <th>식품 유형</th>
            <th>쿠폰 이름</th>
            <th>쿠폰 수량</th>
            <th>할인 유형</th>
            <th>할인값</th>
            <th>만료일자</th>
            <th>기프트카드<br>사용 허용여부</th>
        </tr>
    </thead>

		<tbody class="couponBody">
			<c:forEach var="couponVO" items="${couponList}">
				<tr class="couponRow">
					<td class="couponId">${couponVO.couponId}</td>
					<td>${couponVO.foodType}</td>
					<td class="couponName">${couponVO.couponName}</td>
					<td>${couponVO.couponAmount}</td>
					<td><c:choose>
							<c:when test="${couponVO.amount == 0}">
								<span style="color: red;">퍼센트 할인</span>
							</c:when>
							<c:when test="${couponVO.percentage == 0}">
								<span style="color: blue;">금액 할인</span>
							</c:when>
						</c:choose></td>
					<td><c:choose>
							<c:when test="${couponVO.percentage == 0}">${couponVO.amount }원</c:when>
							<c:when test="${couponVO.amount == 0}">${couponVO.percentage }%</c:when>
						</c:choose></td>
					<fmt:formatDate value="${couponVO.couponExpiredDate}" pattern="yyyy-MM-dd HH:mm" var="couponExpiredDate" />
					<td>${couponExpiredDate}</td>
					<td><c:choose>
							<c:when test="${couponVO.isDuplicateAllowed == 1 }">
								<span style="color: green;">사용 허용</span>
							</c:when>
							<c:when test="${couponVO.isDuplicateAllowed == 0 }">
								<span style="color: red;">사용 불가</span>
							</c:when>
						</c:choose></td>
					<td class="action-buttons">
    					<button class="edit-btn">수정</button>
    					<button class="delete-btn">삭제</button>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
<form id="listForm" action="list" method="get">
        <input type="hidden" name="pageNum">
        <input type="hidden" name="pageSize">
    </form>

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

<script type="text/javascript">
$(document).ready(function () {
    $(document).ajaxSend(function (e, xhr, opt) {
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        xhr.setRequestHeader(header, token);
    });
    
    $(".edit-btn").on("click", function(e) {
    	let couponId = $(this).parents().prevAll('.couponId').text();
    	window.open('../coupon/modify?couponId=' + couponId, '_blank', 'width=500,height=710,top=100,left=200');
    });
    
    $(".delete-btn").on("click", function(e) {
        let couponId = $(this).closest("tr").find(".couponId").text();
        let couponName = $(this).closest("tr").find(".couponName").text();
        let isConfirmed = confirm("<쿠폰 ID> " + couponId + "\n<쿠폰 이름> " + couponName + "\n해당 쿠폰 대기열도 모두 삭제됩니다." + "\n정말 삭제할까요?");

        if (isConfirmed) {
            $.ajax({
                type: "POST",
                url: "../coupon/delete",
                data: { couponId: couponId },
                success: function(response) {
                    alert("삭제 완료!");
                    location.reload();
                },
                error: function() {
                    alert("삭제 실패");
                }
            });
        }
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
});
</script>
<%@ include file="../common/footer.jsp"%>
</body>
</html>
