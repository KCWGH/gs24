<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<style>
header {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    background-color: #f8f9fa;
    z-index: 1000;
    border-bottom: 1px solid #ccc;
    padding: 10px 0;
}

.header-container {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0 20px;
}

.header-section {
    display: flex;
    align-items: center;
    gap: 10px;
}

.header-right {
    display: flex;
    align-items: center;
    gap: 10px;
    margin-left: auto;
}

header a {
    color: #333;
    text-decoration: none;
    font-weight: bold;
}

header a:hover {
    color: #555;
}

header button,
header input[type="submit"] {
    background-color: #ddd;
    color: #333;
    border: none;
    padding: 8px 12px;
    border-radius: 4px;
    cursor: pointer;
    font-size: 14px;
    transition: background-color 0.3s ease;
}

header button:hover,
header input[type="submit"]:hover {
    background-color: #bbb;
}

header span {
    color: #666;
    font-size: 14px;
}

.logout-form {
    display: flex;
    align-items: center;
    gap: 10px;
    margin: 0;
}

.content {
    margin-top: 70px;
}
#logout {
	background-color: #ff6666;
	color: white;
}
#logout:hover {
	background: #ff4d4d;
}
</style>
<header>
    <div class="header-container">
        <div class="header-section">
            <sec:authorize access="isAnonymous() or hasRole('ROLE_MEMBER')">
                <a href="../convenience/list">GS24</a>
            </sec:authorize>
            <sec:authorize access="hasRole('ROLE_OWNER')">
                <a href="../convenienceFood/list?convenienceId=${convenienceId }">GS24</a>
            </sec:authorize>
            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <a href="../admin/console">GS24</a>
            </sec:authorize>
        </div>

        <div class="header-right">
            <!-- 로그인 버튼 -->
            <sec:authorize access="isAnonymous()">
                <button onclick='location.href="../auth/login"'>로그인</button>
                <button type="button" onclick='location.href="../notice/list"'>공지사항</button>
            </sec:authorize>

            <!-- 로그아웃 버튼 및 사용자 관련 정보 -->
            <sec:authorize access="isAuthenticated()">
                <form action="../auth/logout" method="post" class="logout-form">
                    <span>환영합니다, 
                        <sec:authorize access="hasRole('ROLE_OWNER')">점주 </sec:authorize>
                        <sec:authorize access="hasRole('ROLE_ADMIN')">관리자 </sec:authorize>
                        <sec:authorize access="hasRole('ROLE_OWNER') or hasRole('ROLE_MEMBER')">
                            <a href="javascript:void(0);" onclick="window.open('../user/mypage', '_blank', 'width=500,height=700,top=100,left=200');">
                                <sec:authentication property="principal.username" />
                            </a>님
                        </sec:authorize>
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                            <sec:authentication property="principal.username" />님
                        </sec:authorize>
                    </span>
                    <input id="logout" type="submit" value="로그아웃">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                </form>
            </sec:authorize>

            <sec:authorize access="hasRole('ROLE_MEMBER')">
                <button type="button" onclick='location.href="../preorder/list"'>예약 식품 목록</button>
                <button type="button" onclick="window.open('../giftcard/list', '_blank', 'width=500,height=700,top=100,left=200')">기프트카드</button>
                <button type="button" onclick='location.href="../notice/list"'>공지사항</button>
                <button type="button" onclick='location.href="../question/list"'>문의사항(QnA)</button>
            </sec:authorize>

            <sec:authorize access="hasRole('ROLE_OWNER')">
                <button type="button" onclick='location.href="../preorder/update?convenienceId=${FoodList[0].convenienceId}"'>예약 상품 수령 확인</button>
                <button type="button" onclick='location.href="../foodlist/list"'>발주하기</button>
                <button type="button" onclick='location.href="../notice/list"'>공지사항</button>
                <button type="button" onclick='location.href="../question/ownerList"'>매장 문의사항(QnA)</button>
            </sec:authorize>

            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <button type="button" onclick='location.href="../foodlist/list"'>음식 창고</button>
                <button type="button" onclick="window.open('../coupon/publish', '_blank', 'width=500,height=700,top=100,left=200')">쿠폰 발행</button>
                <button type="button" onclick='location.href="../notice/list"'>공지사항</button>
                <button type="button" onclick='location.href="../orders/list"'>발주내역</button>
                <button type="button" onclick='location.href="../admin/activate"'>비활성화 해제 승인</button>
            </sec:authorize>
        </div>
    </div>
</header>

<!-- 아래 콘텐츠가 헤더 아래에서 시작되도록 충분한 여백을 줍니다. -->
<div class="content">
    <!-- 실제 콘텐츠가 들어갑니다. -->
</div>
