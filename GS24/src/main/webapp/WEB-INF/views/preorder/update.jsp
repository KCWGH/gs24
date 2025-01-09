<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.7.1.js">
</script>
<title>Insert title here</title>
</head>
<body>
	<c:forEach var="preorderVO" items="${preorderList }">
		<p>${preorderVO.preorderId }</p>
		<img src="../Img/Food?foodId=${preorderVO.foodId }">
		<p>${preorderVO.memberId }</p>
		<p>${preorderVO.preorderAmount }</p>
		<p>${preorderVO.pickupDate }</p>
		<p>${preorderVO.isPickUp }</p>
		<p>${isExpiredOrder }</p>
	</c:forEach>
</body>
</html>