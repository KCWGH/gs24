<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>쿠폰 리스트</title>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<script type="text/javascript">

	$(document).ready(function() {
		$('#btnAddCoupon').click(function() {
			window.location.href = 'grant';
		});
	});
</script>
</head>
<body>
	<c:if test="${memberVO.memberRole == 2 }">
	<button id=btnAddCoupon>쿠폰 제공</button>
	<button id=invalidateCoupon>쿠폰 비활성화</button>
	</c:if>
	<ul class="coupon_box">
		<c:forEach var="CouponVO" items="${couponList}">
			<li class="List">
				<%
				//<p>${ImgList.get(FoodList.indexOf(CouponVO)).getImgFoodPath()}</p>
				%>
				<p>${CouponVO.couponName}</p>
				<p>${CouponVO.couponGrantDate}</p>
				<p>${CouponVO.couponExpiredDate}</p>
				<p>${CouponVO.discountRate}</p>
				<p>${CouponVO.isValid}</p>
				<p>${CouponVO.isValid}</p> <c:if test="${memberVO.memberRole == 2 }">
				</c:if>
			</li>
		</c:forEach>
	</ul>

</body>
</html>