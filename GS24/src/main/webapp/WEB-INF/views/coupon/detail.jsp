<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>쿠폰 상세 보기</title>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		console.log(couponVO.discountType);
	});
</script>
<style>
    .bold {
        font-weight: bold;
        text-align: center;
    }
    table {
        width: 100%;
    }
    td {
        padding: 8px;
    }
</style>
</head>
<body>

    <h2>쿠폰 상세 정보</h2>
    <div>
        <table>
            <tr>
                <td colspan="2" style="text-align: center;">
                    <c:choose>
                        <c:when test="${couponVO.isUsed == 1}">
                            <img
                                src="${pageContext.request.contextPath}/resources/images/coupon/usedCoupon.png"
                                alt="Used Coupon" width="200" height="200" />
                        </c:when>
                        <c:when test="${sysDate < couponVO.couponExpiredDate and couponVO.isUsed == 0}">
                            <img
                                src="${pageContext.request.contextPath}/resources/images/coupon/coupon.png"
                                alt="Coupon" width="200" height="200" />
                        </c:when>
                        <c:when test="${sysDate >= couponVO.couponExpiredDate}">
                            <img
                                src="${pageContext.request.contextPath}/resources/images/coupon/expiredCoupon.png"
                                alt="Expired Coupon" width="200" height="200" />
                        </c:when>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <td class="bold">쿠폰 이름</td>
                <td>${couponVO.couponName}</td>
            </tr>
            <tr>
                <td class="bold">발급 날짜</td>
                <td>
                    <fmt:formatDate value="${couponVO.couponGrantDate}" pattern="yyyy-MM-dd HH:mm" />
                </td>
            </tr>
            <tr>
                <td class="bold">만료 날짜</td>
                <td>
                    <fmt:formatDate value="${couponVO.couponExpiredDate}" pattern="yyyy-MM-dd HH:mm" />
                </td>
            </tr>
            <tr>
            
            </tr>
            <tr>
                <td class="bold">유효 여부</td>
                <td>
                    <c:choose>
                        <c:when test="${sysDate >= couponVO.couponExpiredDate}">
                            만료됨
                        </c:when>
                        <c:otherwise>
                            기간 내의 쿠폰
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <td class="bold">사용 여부</td>
                <td>
                    <c:choose>
                        <c:when test="${couponVO.isUsed == 1}">
                            사용됨
                        </c:when>
                        <c:otherwise>
                            미사용
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </table>
    </div>
    <a href="list"><button>전체 쿠폰 리스트</button></a>

</body>
</html>
