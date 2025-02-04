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
</head>
<body>
	<div id="conveniList">
	<c:forEach var="conveniVO" items="${conveniList }">
	<div class="conveni">
		<c:if test="${conveniVO.isEnabled == 1 }">
			<p>${conveniVO.convenienceId }</p>
			<p>${conveniVO.ownerId }</p>
			<p>${conveniVO.address }</p>
			<p>${conveniVO.isEnabled }</p>
		</c:if>
	</div>
	</c:forEach>	
	</div>
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
</body>
</html>