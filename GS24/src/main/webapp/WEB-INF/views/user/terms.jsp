<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>이용약관</title>
    <style>
        body {
            font-family: 'Pretendard-Regular', sans-serif;
            line-height: 1.6;
            padding: 20px;
            background: #f9f9f9;
        }
        h2 {
            text-align: center;
            color: #2c3e50;
        }
        .terms-container {
            width: 80%;
            margin: 0 auto;
            border: 1px solid #ddd;
            padding: 20px;
            height: 300px;
            overflow-y: scroll;
            background: white;
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
    <h2>이용약관</h2>
    
    <div class="terms-container">
        <p><strong>제 1 조 (목적)</strong></p>
        <p>이 약관은 회사(이하 '회사')가 제공하는 서비스의 이용 조건 및 절차, 회사와 이용자의 권리, 의무 및 책임 사항을 규정함을 목적으로 합니다.</p>

        <p><strong>제 2 조 (이용자의 의무)</strong></p>
        <p>이용자는 서비스 이용 시 다음 행위를 하지 않아야 합니다.</p>
        <ul>
            <li>타인의 정보를 도용하는 행위</li>
            <li>회사의 운영을 방해하는 행위</li>
            <li>기타 법령에 위반되는 행위</li>
        </ul>

        <p><strong>제 3 조 (서비스 제공 및 변경)</strong></p>
        <p>회사는 이용자에게 안정적인 서비스를 제공하기 위해 노력하며, 필요한 경우 서비스 내용을 변경할 수 있습니다.</p>

        <p>...</p> <!-- 실제 이용약관 내용 추가 -->
    </div>
<%@ include file="../common/footer.jsp" %>
</body>
</html>