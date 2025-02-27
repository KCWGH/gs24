<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${noticeVO.noticeTitle }</title>
    <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
    <%@ include file="../common/header.jsp" %>
    
    <style>
        /* 전체 페이지 스타일 */
        body {
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

        /* 공지사항 컨테이너 */
        .notice-container {
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

        /* 공지사항 글 정보 스타일 */
        .notice-container p {
            font-size: 16px;
            color: #444;
            padding: 8px 0;
            border-bottom: 1px solid #ddd;
            margin-bottom: 12px;
        }

        .notice-container p strong {
            color: #333;
        }

        .notice-content {
            width: 100%;
            height: 300px;
            padding: 12px;
            border: 1px solid #ccc;
            border-radius: 5px;
            background: #f9f9f9;
            font-size: 16px;
            line-height: 1.5;
            resize: none;
            box-sizing: border-box; /* 부모 요소 너비를 넘지 않도록 설정 */
            overflow-y: auto; /* 스크롤 가능하게 */
            word-wrap: break-word; /* 긴 단어가 잘리지 않도록 */
            margin-top: 20px;
            margin-bottom: 20px;
        }

        /* 버튼 컨테이너 */
        .button-container {
            display: flex;
            justify-content: flex-end;
            gap: 12px;
        }

        /* 공통 버튼 스타일 */
        button {
            background: #ddd;
            color: black;
            padding: 10px 18px;
            border-radius: 5px;
            border: none;
            cursor: pointer;
            font-size: 14px;
            transition: background 0.3s;
        }

        button:hover {
            background: #bbb;
        }

        /* '글 수정' 및 '글 목록' 버튼 스타일 */
        .button-container button:not(#deleteNotice) {
            background-color: #ddd;
        }

        .button-container button:not(#deleteNotice):hover {
            background-color: #bbb;
        }

        /* '글 삭제' 버튼 스타일 */
        .button-container button#deleteNotice {
            background-color: #dc3545;
            color: white;
        }

        .button-container button#deleteNotice:hover {
            background-color: #c82333;
        }
    </style>
</head>
<body>

    <div class="notice-container">
        <h2>공지사항 상세보기</h2>
        
        <p hidden="hidden"><strong>글 번호 :</strong> ${noticeVO.noticeId }</p>
        <p><strong>제목 :</strong> ${noticeVO.noticeTitle }</p>
        <p><strong>작성자 :</strong> ${noticeVO.memberId }</p>

        <!-- 작성일 포맷 변경 -->
        <fmt:formatDate value="${noticeVO.noticeDateCreated }" pattern="yyyy-MM-dd HH:mm" var="noticeDateCreated" />
        <p><strong>작성일 :</strong> ${noticeDateCreated }</p>

        <textarea class="notice-content" readonly>${noticeVO.noticeContent }</textarea>

        <div class="button-container">
            <button onclick="location.href='list'">글 목록</button>

            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <button onclick="location.href='modify?noticeId=${noticeVO.noticeId}'">글 수정</button>
                <button id="deleteNotice">글 삭제</button>
            </sec:authorize>
        </div>
    </div>

    <form id="deleteForm" action="delete" method="POST">
        <input type="hidden" name="noticeId" value="${noticeVO.noticeId }">
        <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
    </form>

    <script type="text/javascript">
        $(document).ready(function() {
            $('#deleteNotice').click(function() {
                if (confirm('삭제하시겠습니까?')) {
                    $('#deleteForm').submit();
                }
            });
        });
    </script>

    <%@ include file="../common/footer.jsp" %>

</body>
</html>
