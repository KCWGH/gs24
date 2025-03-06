<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<!-- css 파일 불러오기 -->
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/questionAttach.css">
    <title>글 작성 페이지</title>
    <%@ include file="../common/header.jsp" %>
</head>
<style>
    /* 전체 페이지 스타일 */
    body {
        margin: 0;
        padding: 15px;
        background-color: #f8f9fa;
        text-align: center;
        font-family: 'Pretendard-Regular', sans-serif;
    }

    h2 {
        color: #333;
        margin-bottom: 20px;
    }

    /* 폼 컨테이너 */
    .form-container {
        width: 80%;
        max-width: 600px;
        margin: 0 auto;
        background: #fff;
        padding: 20px;
        border-radius: 10px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        text-align: left;
    }

    .form-container label {
        font-size: 16px;
        color: #444;
        margin-bottom: 5px;
        display: block;
        font-weight: bold;
    }

    .form-container input[type="text"],
    .form-container select,
    .form-container textarea {
        font-family: 'Pretendard-Regular', sans-serif;
        width: 100%;
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
        font-size: 16px;
        box-sizing: border-box;
        background: #f9f9f9;
        margin-bottom: 10px;
    }

    .form-container textarea {
        height: 150px;
        resize: vertical;
        overflow-y: auto;
        word-wrap: break-word;
    }

    /* 체크박스 스타일 */
    .form-check-input {
        margin-right: 5px;
    }

    /* 버튼 스타일 */
    .button-container {
        display: flex;
        justify-content: right;
        gap: 10px;
        margin-top: 20px;
    }

    .button-container button {
        background: #4CAF50;
        color: white;
        padding: 10px 20px;
        border-radius: 5px;
        border: none;
        cursor: pointer;
        font-size: 16px;
        font-weight: bold;
        transition: background 0.3s ease;
    }

    .button-container button:hover {
        background: #45a049;
    }

    /* 파일 업로드 영역 */
    .questionAttach-upload {
        margin-top: 20px;
        padding: 15px;
        background: #f9f9f9;
        border-radius: 10px;
        text-align: center;
    }

    .questionAttach-upload input[type="file"] {
        margin-top: 10px;
        display: block;
        width: 100%;
        padding: 8px;
        border: 1px solid #ccc;
        border-radius: 5px;
        font-size: 14px;
        background: #fff;
    }

</style>

<body>

    <h2>문의사항 작성</h2>

    <div class="form-container">
        <form id="registerForm" action="register" method="POST">
            <label>
                <input class="form-check-input" type="checkbox" name="questionSecret" id="secret">
                비밀글 설정
            </label><br>

            <label for="ownerId">매장 선택</label>
            <select id="ownerId" name="ownerId">
                <option value="" selected disabled>선택하세요</option>
                <c:forEach var="owner" items="${ownerVOList}">
                    <option value="${owner.ownerId}">${owner.address}</option>
                </c:forEach>
            </select>

            <label for="foodType">식품 종류</label>
            <select id="foodType" name="foodType" required>
                <option value="" selected disabled>선택하세요</option>
                <c:forEach var="food" items="${foodTypeList}">
                    <option value="${food}">${food}</option>
                </c:forEach>
            </select>

            <label for="questionTitle">제목</label>
            <input type="text" name="questionTitle" placeholder="제목 입력" maxlength="20">

            <label for="memberId">작성자</label>
            <input type="text" name="memberId" value="${memberId}" maxlength="10" readonly>

            <label for="questionContent">내용</label>
            <textarea name="questionContent" placeholder="내용 입력" maxlength="300"></textarea>

            <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
        </form>

        <div class="questionAttach-upload">
            <h2>첨부 파일 업로드</h2>
            <p>* 첨부 파일은 최대 3개까지 가능합니다.</p>
            <p>* 최대 용량은 10MB 입니다.</p>
            <input type="file" id="questionAttachInput" name="files" multiple="multiple">
        </div>

        <div class="button-container">
            <button id="registerQuestion">등록</button>
        </div>
    </div>

    <script src="${pageContext.request.contextPath }/resources/js/questionAttach.js"></script>

    <script>
        $(document).ajaxSend(function(e, xhr, opt) {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            xhr.setRequestHeader(header, token);
        });

        $(document).ready(function() {
            $('#registerQuestion').click(function(event) {
                event.preventDefault();

                var title = $('input[name="questionTitle"]').val().trim();
                var content = $('textarea[name="questionContent"]').val().trim();
                var foodType = $('select[name="foodType"]').val();
                var owner = $('select[name="ownerId"]').val();

                if (!title) {
                    alert("제목을 입력해주세요.");
                    return;
                }
                if (!content) {
                    alert("내용을 입력해주세요.");
                    return;
                }
                if (!foodType) {
                    alert("식품 종류를 선택해주세요.");
                    return;
                }
                if (!owner) {
                    alert("매장을 선택해주세요.");
                    return;
                }

                $('#registerForm').submit();
            });
        });
    </script>

</body>
</html>
