<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<header style="position: fixed; top: 0; left: 0; width: 100%; background-color: #fff; z-index: 1000; border-bottom: 1px solid #ddd; padding: 10px 0;">
    <div class="header-container" style="display: flex; justify-content: space-between; align-items: center; padding: 0 20px;">
        <div class="header-section" style="display: flex; align-items: center; gap: 10px;">
        <a href="../food/list" style="display: inline-block;">GS24</a>
            <sec:authorize access="isAnonymous()">
                <button onclick='location.href="../auth/login"'>로그인</button>
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">
                <form action="../auth/logout" method="post" style="display: flex; align-items: center; gap: 10px; margin: 0;">
                    <span>환영합니다, <sec:authorize access="hasRole('ROLE_OWNER')">점주 </sec:authorize>
                        <a href="javascript:void(0);" 
                           onclick="window.open('../user/mypage', '_blank', 'width=500,height=700');">
                           <sec:authentication property="principal.username" /></a>님
                    </span>
                    <input type="submit" value="로그아웃">
                    <sec:authorize access="hasRole('ROLE_MEMBER')">
                    <button type="button" onclick='location.href="../preorder/list"'>예약 식품 목록</button>
                    <button type="button" onclick="window.open('../giftcard/list', '_blank', 'width=500,height=700')">기프트카드</button>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_OWNER')">
                    <button type="button" onclick="window.open('../giftcard/grant', '_blank', 'width=500,height=700')">기프트카드 제공</button>
                    </sec:authorize>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                </form>
            </sec:authorize>
            <a href="../notice/list" style="display: inline-block;"><button>공지사항</button></a>
            <a href="../question/list" style="display: inline-block;"><button>문의사항(QnA)</button></a>
        </div>
    </div>
</header>

<!-- 아래 콘텐츠가 헤더 아래에서 시작되도록 충분한 여백을 줍니다. -->
<div style="margin-top: 50px;">
    <!-- 실제 콘텐츠가 들어갑니다. -->
</div>
