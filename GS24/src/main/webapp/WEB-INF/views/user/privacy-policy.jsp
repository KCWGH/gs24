<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>개인정보 처리방침</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            padding: 20px;
        }
        h2 {
            text-align: center;
            color: #2c3e50;
        }
        .privacy-container {
            width: 80%;
            margin: 0 auto;
            border: 1px solid #ddd;
            padding: 20px;
            height: 300px;
            overflow-y: scroll;
            background: #f9f9f9;
        }
        .button-container {
            margin-top: 20px;
            text-align: center;
        }
    </style>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<%@ include file="../common/header.jsp" %>
    <h2>개인정보 처리방침</h2>
    
    <div class="privacy-container">
        <p><strong>제 1 조 (목적)</strong></p>
        <p>본 개인정보 처리방침은 이용자의 개인정보를 어떻게 처리하고 보호하는지에 대한 내용을 안내합니다.</p>

        <p><strong>제 2 조 (수집하는 개인정보의 항목)</strong></p>
        <p>회사는 회원가입, 서비스 제공 등을 위해 다음과 같은 개인정보를 수집합니다:</p>
        <ul>
            <li>이름, 이메일, 전화번호</li>
            <li>서비스 이용 기록, 접속 로그, 쿠키 등</li>
        </ul>

        <p><strong>제 3 조 (개인정보의 이용 목적)</strong></p>
        <p>회사는 수집한 개인정보를 다음의 목적을 위해 사용합니다:</p>
        <ul>
            <li>서비스 제공 및 고객 지원</li>
            <li>이벤트, 프로모션 정보 전달</li>
            <li>서비스 개선을 위한 분석</li>
        </ul>

        <p><strong>제 4 조 (개인정보의 보유 및 이용 기간)</strong></p>
        <p>회사는 법령에 의해 요구되는 기간 동안만 개인정보를 보유하며, 보유 기간이 끝난 후에는 즉시 파기합니다.</p>

        <p><strong>제 5 조 (개인정보의 제3자 제공)</strong></p>
        <p>회사는 이용자의 개인정보를 제3자에게 제공하지 않습니다. 단, 법령에 의해 요구될 경우 예외가 있을 수 있습니다.</p>

        <p><strong>제 6 조 (개인정보의 보호 조치)</strong></p>
        <p>회사는 이용자의 개인정보를 보호하기 위해 다음과 같은 조치를 취하고 있습니다:</p>
        <ul>
            <li>개인정보 암호화</li>
            <li>접근 제한 및 보안 시스템</li>
        </ul>

        <p>...</p> <!-- 실제 개인정보 처리방침 내용 추가 -->
    </div>
<%@ include file="../common/footer.jsp" %>
</body>
</html>