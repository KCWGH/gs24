<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>쿠폰 상세 보기</title>
</head>
<body>

<h2>쿠폰 목록</h2>
    <div>
        <p>쿠폰명: ${couponVO.couponName}</p>
        <p>발급일: <fmt:formatDate value="${couponVO.couponGrantDate}" pattern="yyyy-MM-dd HH:mm" /></p>
        <p>만료일: <fmt:formatDate value="${couponVO.couponExpiredDate}" pattern="yyyy-MM-dd HH:mm" /></p>
        <p>
            <c:choose>
                <c:when test="${couponVO.isValid == 1}">
                    사용가능
                </c:when>
                <c:otherwise>
                    만료됨
                </c:otherwise>
            </c:choose>
        </p>
        <p>할인율: ${couponVO.discountRate}%</p>
        <p>사용 여부: 
            <c:choose>
                <c:when test="${couponVO.isUsed == 1}">
                    사용됨
                </c:when>
                <c:otherwise>
                    미사용
                </c:otherwise>
            </c:choose>
        </p>
    </div>
    <a href="list"><button>전체 쿠폰 리스트</button></a>

</body>
</html>
