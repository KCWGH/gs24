<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<style>
.header-wrapper {
    position: relative;
    width: 100%;
}

header {
    position: fixed !important;
    top: 0 !important;
    left: 0 !important;
    width: 100% !important;
    background-color: white;
    z-index: 1000 !important;
    border-bottom: 1px solid #ccc !important;
    padding: 10px 0 !important;
}

.header-container {
    display: flex !important;
    justify-content: space-between !important;
    align-items: center !important;
    padding: 0 20px !important;
}

.header-section {
    display: flex !important;
    align-items: center !important;
    gap: 10px !important;
}

.header-right {
    display: flex !important;
    align-items: center !important;
    gap: 10px !important;
    margin-left: auto !important;
}

header a {
    color: #333 !important;
    text-decoration: none !important;
    font-weight: bold !important;
}

header a:hover {
    color: #555 !important;
}

header button,
header input[type="submit"] {
    background-color: #ddd !important;
    color: #333 !important;
    border: none !important;
    padding: 8px 12px !important;
    border-radius: 4px !important;
    cursor: pointer !important;
    font-size: 14px !important;
    transition: background-color 0.3s ease !important;
}

header button:hover,
header input[type="submit"]:hover {
    background-color: #bbb !important;
}

#logout {
    background-color: #ff6666 !important;
    color: white !important;
}

#logout:hover {
    background: #ff4d4d !important;
}

header span {
    color: #666 !important;
    font-size: 14px !important;
}

#login {
	background-color: #4CAF50 !important;
	color: white !important;
}

#login:hover {
	background: #388E3C !important;
}

.logout-form {
    display: flex !important;
    align-items: center !important;
    gap: 10px !important;
    margin: 0 !important;
}

.content {
    margin-top: 70px !important;
}

.console-page header {
    all: unset !important;
    display: block !important;
    position: fixed !important;
    top: 0 !important;
    left: 0 !important;
    width: 100% !important;
    background-color: #f8f9fa !important;
    z-index: 1000 !important;
    border-bottom: 1px solid #ccc !important;
    padding: 10px 0 !important;
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
            <sec:authorize access="isAnonymous()">
                <button id="login" onclick='location.href="../auth/login"'>로그인 🔑</button>
                <button type="button" onclick='location.href="../notice/list"'>공지사항 📌</button>
            </sec:authorize>

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
                    <input id="logout" type="submit" value="로그아웃 🔑">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                </form>
            </sec:authorize>

            <sec:authorize access="hasRole('ROLE_MEMBER')">
                <button type="button" onclick='location.href="../preorder/list"'>예약 내역 📋</button>
                <button type="button" onclick="window.open('../giftcard/list', '_blank', 'width=500,height=700,top=100,left=200')">기프트카드 🎁</button>
                <button type="button" onclick='location.href="../notice/list"'>공지사항 📌</button>
                <button type="button" onclick='location.href="../question/list"'>문의사항(Q&amp;A) 🙋‍♂️</button>
            </sec:authorize>

            <sec:authorize access="hasRole('ROLE_OWNER')">
                <button type="button" onclick='location.href="../preorder/update?convenienceId=${convenienceId }"'>결제 및 지급 💳</button>
                <button type="button" onclick='location.href="../foodlist/list"'>발주하기 🚚</button>
                <button type="button" onclick='location.href="../orders/ownerList"'>발주 이력 📋</button>
                <button type="button" onclick='location.href="../notice/list"'>공지사항 📌</button>
                <button type="button" onclick='location.href="../question/ownerList"'>매장 문의사항(Q&amp;A) 🙋‍♂️</button>
            </sec:authorize>

            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <button type="button" onclick='location.href="../foodlist/list"'>식품 창고 🏢</button>
                <button type="button" onclick='location.href="../orders/list"'>발주 승인 ✔️</button>
                <button type="button" onclick="window.open('../coupon/publish', '_blank', 'width=500,height=700,top=100,left=200')">쿠폰 발행 😄</button>
                <button type="button" onclick='location.href="../admin/activate"'>비활성화 해제 승인 ✔️</button>
                <button type="button" onclick='location.href="../notice/list"'>공지사항 📌</button>
            </sec:authorize>
        </div>
    </div>
</header>

<div class="content">
</div>
