<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<title>편의점 목록</title>

<!-- 카드 스타일링을 위한 CSS -->
<style>
    /* 기존 스타일 유지 */
    body {
        font-family: Arial, sans-serif;
        margin: 0;
        padding: 0;
        background-color: #f4f4f4;
    }

    h1 {
        text-align: center;
        margin-top: 20px;
    }

    #conveniList {
        display: flex;
        flex-wrap: wrap;
        justify-content: space-around;
        margin: 20px;
    }

    .conveni {
        background-color: white;
        border: 1px solid #ddd;
        border-radius: 8px;
        width: 250px;
        margin: 10px;
        padding: 20px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        cursor: pointer;
        transition: box-shadow 0.3s;
    }

    .conveni:hover {
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }

    .conveni p {
        margin: 10px 0;
        font-size: 14px;
        color: #555;
    }

    .conveni p:first-child {
        font-weight: bold;
    }

    .conveni .address {
        color: #777;
        font-size: 12px;
    }

    /* 사진 스타일 추가 */
    .conveni img {
        width: 100%;
        height: auto;
        border-radius: 8px; /* 카드와 동일한 곡률 적용 */
        object-fit: cover; /* 이미지 비율을 유지하면서 전체 공간을 채움 */
    }

    #convenienceId {
        display: flex;
        justify-content: center;
        align-items: center;
        width: 100%;
    }
</style>

</head>
<body>
	<c:if test="${not empty message}">
		<script type="text/javascript">
			alert("${message}");
		</script>
	</c:if>
	<%@ include file="../common/header.jsp"%>
	<h1>지점 리스트</h1>
	<div id="conveniList">
		<c:forEach var="conveniVO" items="${conveniList}">
			<div class="conveni">
				<sec:authorize access="hasRole('ROLE_MEMBER') or isAnonymous()">
					<c:if test="${conveniVO.isEnabled == 1}">
						<p hidden="hidden">${conveniVO.convenienceId }</p>
						<p id="convenienceId">${conveniVO.convenienceId }호점</p>
						<img src="${pageContext.request.contextPath}/resources/images/convenienceStore/convenienceStore.jpg" alt="ConvenienceStore" />
						<p class="address">${conveniVO.address }</p>
						<p hidden="hidden">${conveniVO.isEnabled }</p>
					</c:if>
				</sec:authorize>
			</div>
		</c:forEach>
	</div>
	<sec:authorize access="hasRole('ROLE_OWNER')">
		<meta http-equiv="refresh" content="0;URL='http://localhost:8080/website/convenienceFood/list?convenienceId=${checkConvenienceId }'">
	</sec:authorize>
	<script type="text/javascript">
		$(document).ready(function(){
			$("#conveniList").on('click','.conveni',function(){
				console.log("클릭");
				console.log(this);
				var conveniId = $(this).children().first().text();
				location.href = '../convenienceFood/list?convenienceId='+conveniId;
			});
		});
	</script>
	<%@ include file="../common/footer.jsp"%>
</body>
</html>