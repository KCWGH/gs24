<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>쿠폰 리스트</title>
    <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $('#btnGrantCoupon').click(function() {
                window.location.href = 'grant';
            });
        });
    </script>
    <style>
        /* 그리드 레이아웃으로 쿠폰 이미지를 표시 */
        .coupon-grid {
            display: grid;
            grid-template-columns: repeat(2, 1fr); /* 2열 */
            grid-gap: 10px; /* 그리드 아이템 간의 간격 */
            margin-top: 20px;
        }

        .coupon-grid a {
            display: block;
            text-align: center;
        }

        .coupon-grid img {
            width: 100px;
            height: 100px;
            transition: filter 0.3s ease; /* 필터 효과 전환 */
        }

        /* 사용된 쿠폰을 흑백으로 표시 */
        .is-used img {
            filter: grayscale(100%); /* 흑백 처리 */
        }
    </style>
</head>
<body>
	<h2>쿠폰함</h2>
    <c:if test="${memberVO.memberRole == 2}">
        <button id="btnGrantCoupon">쿠폰 제공</button>
        <button id="invalidateCoupon">쿠폰 비활성화</button>
    </c:if>

    <!-- 쿠폰 이미지를 그리드로 표시 -->
    <div class="coupon-grid">
        <c:forEach var="CouponVO" items="${couponList}">
            <div class="${CouponVO.isUsed == 1 ? 'is-used' : ''}">
                <a href="detail?couponId=${CouponVO.couponId}">
                    <img src="${pageContext.request.contextPath}/resources/images/coupon/coupon.png" alt="Coupon Image" />
                </a>
            </div>
        </c:forEach>
    </div>

    <!-- 페이지 네비게이션 -->
    <ul>
        <c:if test="${pageMaker.isPrev() }">
            <li><a href="list?pageNum=${pageMaker.startNum - 1}">이전</a></li>
        </c:if>
        <c:forEach begin="${pageMaker.startNum }" end="${pageMaker.endNum }" var="num">
            <li><a href="list?pageNum=${num }">${num }</a></li>
        </c:forEach>
        <c:if test="${pageMaker.isNext() }">
            <li><a href="list?pageNum=${pageMaker.endNum + 1}">다음</a></li>
        </c:if>
    </ul>
</body>
</html>
