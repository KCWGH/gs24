<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${noticeVO.noticeTitle }</title>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    
    <%@ include file="../common/header.jsp" %>
    
    <style>
        /* 전체 페이지 스타일 */
        body {
            margin: 0;
            padding: 15px;
            background-color: #f8f9fa;
            text-align: center;
        }

        h2 {
            color: #333;
            margin-bottom: 20px;
        }

        /* 수정 폼 스타일 */
        .form-container {
        	font-family: 'Pretendard-Regular', sans-serif;
            width: 80%;
            max-width: 600px;
            margin: 0 auto;
            background: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            text-align: left;
        }

        .form-container p {
            font-size: 16px;
            color: #444;
            margin: 10px 0 5px;
        }

        .form-container input[type="text"],
        .form-container textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 16px;
            box-sizing: border-box; /* 부모 요소 넘지 않도록 */
        }

        .form-container textarea {
            height: 200px;
            resize: none;
            background: #f9f9f9;
            overflow-y: auto;
            word-wrap: break-word;
        }

        /* 버튼 스타일 */
        .button-container {
            display: flex;
            justify-content: right;
            gap: 10px;
            margin-top: 20px;
        }

        .button-container button {
            background: #ddd;
            color: black;
            padding: 8px 15px;
            border-radius: 5px;
            border: none;
            cursor: pointer;
            font-size: 14px;
        }

        .button-container button:hover {
            background: #bbb;
        }
    </style>
</head>
<body>

    <h2>공지사항 수정</h2>

    <div class="form-container">
        <form action="modify" method="POST">
            <p hidden="hidden"><strong>글 번호 :</strong> ${noticeVO.noticeId }</p>
            
            <p><strong>제목</strong></p>
            <input type="text" name="noticeTitle" placeholder="제목 입력" maxlength="20" value="${noticeVO.noticeTitle }" required>

            <p><strong>작성자</strong></p>
            ${noticeVO.memberId}

            <p><strong>내용</strong></p>
            <textarea name="noticeContent" placeholder="내용 입력" maxlength="300" required>${noticeVO.noticeContent }</textarea>

            <div class="button-container">
                <button type="submit">수정 완료</button>
                <button type="button" onclick="location.href='list'">취소</button>
            </div>

            <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
        </form>
    </div>

    <%@ include file="../common/footer.jsp" %>

</body>
</html>