<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>적용 가능한 선착순 쿠폰 리스트</title>
</head>
<body>
<h2>사용가능한 선착순 쿠폰 리스트</h2>
	<table> 
        <tbody>
            <c:forEach var="earlyBirdCouponVO" items="${couponList}">
                <tr>
                    <td hidden="hidden">${earlyBirdCouponVO.earlyBirdCouponId}</td>
                    <td>${earlyBirdCouponVO.earlyBirdCouponName}<br>
                    	${earlyBirdCouponVO.earlyBirdCouponAmount}개 남음<br>
                    	${earlyBirdCouponVO.earlyBirdCouponExpiredDate}까지<br>
                    	<c:choose>
                    		<c:when test="${earlyBirdCouponVO.isDuplicateAllowed == 1}">
                    			중복 사용 가능
                    		</c:when>
                    		<c:when test="${earlyBirdCouponVO.isDuplicateAllowed == 0}">
                    			중복 사용 불가
                    		</c:when>
                    	</c:choose>
                    	
                    </td>
                    <td><a href="applyEarlyBirdCoupon">받기</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>