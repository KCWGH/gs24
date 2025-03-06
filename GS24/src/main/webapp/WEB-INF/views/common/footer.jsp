<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div style="margin-top: 70px;">
    <!-- 실제 콘텐츠가 들어갑니다. -->
</div>
<footer>
    <div class="footer-container">
        <div class="footer-section">
            <a href="../notice/list">공지사항</a>
            <a href="../user/terms">이용약관</a>
            <a href="../user/privacy-policy">개인정보처리방침</a>
            <a href="../user/customer-service">고객센터</a>
        </div>
        <div class="footer-section">
            <span>© 2025 GS24. All Rights Reserved.</span>
        </div>
    </div>
</footer>
<style>
footer {
    position: fixed;
    bottom: 0;
    left: 0;
    width: 100%;
    border-top: 1px solid #ccc;
    padding: 10px 0;
    text-align: center;
    background-color: white;
    z-index: 1000;
}

.footer-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 8px;
}

.footer-section {
    display: flex;
    gap: 15px;
}

footer a {
    color: #333; /* 어두운 회색 */
    text-decoration: none;
    font-size: 14px;
}

footer a:hover {
    color: #555; /* 조금 더 밝은 회색 */
    text-decoration: underline;
}

footer span {
    color: #666; /* 중간 회색 */
    font-size: 13px;
}
</style>
