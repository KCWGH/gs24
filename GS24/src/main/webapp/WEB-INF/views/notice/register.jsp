<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="../resources/css/fonts.css">
    <title>글 작성 페이지</title>
    <%@ include file="../common/header.jsp" %>
    
    <style>
        /* 전체 페이지 스타일 */
        body {
        	font-family: 'Pretendard-Regular', sans-serif;
            margin: 0;
            background-color: #f8f9fa;
            font-family: 'Arial', sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            text-align: center;
        }

        h2 {
            color: #333;
            margin-bottom: 20px;
            font-size: 24px;
        }

        /* 글 작성 컨테이너 */
        .form-container {
        	font-family: 'Pretendard-Regular', sans-serif;
            width: 100%;
            max-width: 800px;
            margin: 20px;
            background: #fff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
            text-align: left;
            box-sizing: border-box;
        }

        /* 각 항목 스타일 */
        .form-container p {
            font-size: 16px;
            color: #444;
            padding: 8px 0;
            margin-bottom: 12px;
        }

        .form-container input[type="text"],
        .form-container textarea {
        	font-family: 'Pretendard-Regular', sans-serif;
            width: 100%;
            padding: 12px;
            border: 1px solid #ccc;
            border-radius: 5px;
            background: #f9f9f9;
            font-size: 16px;
            line-height: 1.5;
            resize: none;
            margin-bottom: 20px;
            box-sizing: border-box;
        }

        .form-container textarea {
            height: 200px;
        }

        .form-container input[type="submit"] {
        	font-family: 'Pretendard-Regular', sans-serif;
            background-color: #bbb;
            color: black;
            padding: 10px 18px;
            border-radius: 5px;
            border: none;
            cursor: pointer;
            font-size: 16px;
            transition: background 0.3s;
        }

        .form-container input[type="submit"]:hover {
            background-color: #ddd;
        }

        .form-container .checkbox-label {
            display: flex;
            align-items: center;
            margin-bottom: 20px;
        }

        .form-container .checkbox-label input {
            margin-right: 10px;
        }

    </style>
</head>
<body>

    <div class="form-container">
        <h2>공지사항 작성</h2>
        <form action="register" method="POST">
            <!-- 체크박스와 hidden 입력 필드 -->
            <div class="checkbox-label">
                <label for="noticeTypeCheckbox">
                    <input type="checkbox" id="noticeTypeCheckbox"> 점주 전용 공지사항
                </label>
                <input type="hidden" name="noticeType" id="noticeType" value="0">
            </div>

            <!-- 제목 입력 -->
            <p>제목</p>
            <input type="text" name="noticeTitle" placeholder="제목 입력" maxlength="300" required>

            <!-- 작성자 입력 -->
            <p>작성자</p>
            <input type="text" name="memberId" value="관리자" maxlength="10" readonly required>

            <!-- 내용 입력 -->
            <p>내용</p>
            <textarea name="noticeContent" placeholder="내용 입력" maxlength="300" required></textarea>

            <!-- 제출 버튼 -->
            <input type="submit" value="등록">

            <!-- CSRF 토큰 -->
            <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
        </form>
    </div>

    <script type="text/javascript">
        document.getElementById("noticeTypeCheckbox").addEventListener("change", function() {
            document.getElementById("noticeType").value = this.checked ? "1" : "0";
        });
    </script>

</body>
</html>
