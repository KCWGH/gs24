<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<link rel="stylesheet" href="../resources/css/fonts.css">
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

.header-right span {
	font-family: 'Pretendard-Regular', sans-serif;
	font-size: 16px !important;
}

.header-section a {
	font-size: 20px !important;
}

header a {
	font-family: 'Pretendard-Regular', sans-serif;
    color: #333 !important;
    text-decoration: none !important;
    font-size: 16px !important;
    font-weight: bold !important;
}

header a:hover {
    color: #555 !important;
}

header button,
header input[type="submit"] {
	font-family: 'Pretendard-Regular', sans-serif;
    background-color: #ddd !important;
    color: #333 !important;
    border: none !important;
    padding: 8px 12px !important;
    border-radius: 4px !important;
    cursor: pointer !important;
    font-size: 16px !important;
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
<script>
document.addEventListener("keydown", function(event) {
	if (document.activeElement.tagName === "INPUT" || document.activeElement.tagName === "TEXTAREA") {
        return;
    }
	
    if (event.key >= "1" && event.key <= "6" && !event.target.closest("#searchForm")) {
    	
        let key = event.key;
        
        let button = document.querySelector("[data-key='" + key + "']");
        
        if (button) {
            button.click();
        }
    }
});

 function requestNotificationPermission() {
     if (Notification.permission === 'granted') {
         return;
     } else if (Notification.permission !== 'denied') {
         Notification.requestPermission().then(function(permission) {
             if (permission === 'granted') {
                 console.log('Notification permission granted.');
             } else {
                 console.log('Notification permission denied.');
             }
         });
     }
 }

 function showNotification(message) {
	    if (Notification.permission === 'granted') {
	        new Notification('GS24', {
	            body: message
	        });
	    } else {
	        console.log('알림 권한이 없습니다.');
	    }
	}

 window.onload = function() {
	    var username = '${pageContext.request.userPrincipal != null ? pageContext.request.userPrincipal.name : ""}';
	    console.log(username);
	    requestNotificationPermission();

	    if (username) {
	        const eventSource = new EventSource('../sse/subscribe/' + username);

	        eventSource.onmessage = function(event) {
	            const message = event.data;
	            showNotification(message);
	        };

	        eventSource.onerror = function(error) {
	            console.error('SSE 연결에 오류가 발생했습니다:', error);
	            eventSource.close();
	        };

	        eventSource.onclose = function() {
	            console.log('SSE 연결이 종료되었습니다.');
	        };
	    }
	};



</script>
<header>
    <div class="header-container">
        <div class="header-section">
            <sec:authorize access="isAnonymous() or hasRole('ROLE_MEMBER')">
                <a href="../convenience/list" data-key="1">GS24</a>
            </sec:authorize>
            <sec:authorize access="hasRole('ROLE_OWNER')">
                <a href="../convenienceFood/list?convenienceId=${convenienceId }" data-key="1">GS24</a>
            </sec:authorize>
            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <a href="../admin/console">GS24</a>
            </sec:authorize>
        </div>

        <div class="header-right">
            <sec:authorize access="isAnonymous()">
                <button id="login" onclick='location.href="../auth/login"'>로그인 🔑</button>
                <button type="button" onclick='location.href="../notice/list"'>공지사항 📢</button>
            </sec:authorize>

            <sec:authorize access="isAuthenticated()">
                <form action="../auth/logout" method="post" class="logout-form">
                    <span>환영합니다, 
                        <sec:authorize access="hasRole('ROLE_OWNER')">점주 </sec:authorize>
                        <sec:authorize access="hasRole('ROLE_ADMIN')">관리자 </sec:authorize>
                        <sec:authorize access=" hasRole('ROLE_MEMBER')">
                            <a href="javascript:void(0);" onclick="window.open('../user/mypage', '_blank', 'width=500,height=710,top=100,left=200');">
                                <sec:authentication property="principal.nickname" />
                            </a>님
                        </sec:authorize>
                         <sec:authorize access="hasRole('ROLE_OWNER')">
                            <a href="javascript:void(0);" onclick="window.open('../user/mypage', '_blank', 'width=500,height=710,top=100,left=200');">
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
                <button type="button" onclick='location.href="../preorder/list"' data-key="2">예약 내역 📋</button>
                <button type="button" onclick="window.open('../member/myhistory', '_blank', 'width=500,height=710,top=100,left=200')" data-key="3">내 찜 목록 💕</button>
                <button type="button" onclick="window.open('../giftcard/list', '_blank', 'width=500,height=710,top=100,left=200')" data-key="4">기프트카드 🎁</button>
                <button type="button" onclick='location.href="../notice/list"' data-key="5">공지사항 📢</button>
                <button type="button" onclick='location.href="../question/list"' data-key="6">문의사항(Q&amp;A) 💬</button>
            </sec:authorize>

            <sec:authorize access="hasRole('ROLE_OWNER')">
                <button type="button" onclick='location.href="../preorder/update?convenienceId=${convenienceId }"' data-key="2">결제 및 지급 💳</button>
                <button type="button" onclick='location.href="../food/list"' data-key="3">발주하기 🚚</button>
                <button type="button" onclick='location.href="../orders/ownerList"' data-key="4">발주 이력 📋</button>
                <button type="button" onclick='location.href="../notice/list"' data-key="5">공지사항 📢</button>
                <button type="button" onclick='location.href="../question/ownerList"' data-key="6">매장 문의사항(Q&amp;A) 💬</button>
            </sec:authorize>

            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <button type="button" onclick='location.href="../food/list"' data-key="1">식품 창고 📦</button>
                <button type="button" onclick='location.href="../orders/list"' data-key="2">발주 승인 ✔️</button>
                <button type="button" onclick='location.href="../coupon/list"' data-key="3">쿠폰 🏷️</button>
                <button type="button" onclick='location.href="../admin/auth-option"' data-key="4">점주 승인 👥</button>
                <button type="button" onclick='location.href="../notice/list"' data-key="5">공지사항 📢</button>
            </sec:authorize>
        </div>
    </div>
</header>

<div class="content">
</div>
