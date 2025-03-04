<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>고객센터</title>
    <style>
        body {
            font-family: 'Pretendard-Regular', sans-serif;
            line-height: 1.6;
            padding: 20px;
            background: #f9f9f9;
        }
        .service-container {
            width: 80%;
            margin: 0 auto;
            padding: 20px;
            border: 1px solid #ddd;
            background-color: white;
            border-radius: 8px;
        }
        h2 {
            text-align: center;
            color: #2c3e50;
        }
        .contact-info {
            font-size: 1.2em;
        }
        .contact-info p {
            margin: 10px 0;
        }
        .contact-info span {
            font-weight: bold;
        }
    </style>
</head>
<body>
<%@ include file="../common/header.jsp" %>
    <h2>고객센터</h2>

    <div class="service-container">
        <div class="contact-info">
            <p><span>전화번호:</span> 02-1234-5678</p>
            <p><span>이메일:</span> support@company.com</p>
            <p><span>운영시간:</span> 월 - 금, 09:00 - 18:00 (주말 및 공휴일 휴무)</p>
        </div>
    </div>
<%@ include file="../common/footer.jsp" %>
</body>
</html>