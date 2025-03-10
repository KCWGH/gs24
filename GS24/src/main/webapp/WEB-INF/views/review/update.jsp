<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="_csrf" content="${_csrf.token}" />
    <meta name="_csrf_header" content="${_csrf.headerName}" />
    <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/uploadImage.css">
    <title>리뷰 수정</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f4f7f6;
        }

        h1 {
            font-size: 24px;
            text-align: center;
            margin-bottom: 20px;
        }

        form {
            max-width: 600px;
            margin: 0 auto;
            background: #ffffff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
        }

        label {
            font-size: 16px;
            color: #333;
            margin-top: 10px;
            display: block;
        }

        input[type="text"], input[type="number"], textarea {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
        }

        input[type="text"]:focus, input[type="number"]:focus, textarea:focus {
            border-color: #007bff;
            outline: none;
        }

        .image-list {
            display: flex;
            gap: 10px;
            flex-wrap: wrap;
            margin-top: 20px;
        }

        .image-item img {
            width: 100px;
            height: 100px;
            object-fit: cover;
            border-radius: 5px;
            box-shadow: 0 0 5px rgba(0, 0, 0, 0.2);
        }

        .button-container {
            text-align: center;
            margin-top: 20px;
        }

        button {
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            background-color: #ddd;
            color: black;
            font-size: 16px;
            cursor: pointer;
            margin: 5px;
        }

        button:hover {
            background-color: #ccc;
        }

        .cancel-button {
            background-color: #f44336;
        }

        .cancel-button:hover {
            background-color: #d32f2f;
        }

        .image-drop, .update-image-drop {
            display: none;
            text-align: center;
            padding: 15px;
            margin-top: 20px;
            border: 2px dashed #007bff;
            border-radius: 4px;
            background-color: #f1f9ff;
            color: #007bff;
        }

        .image-drop p, .update-image-drop p {
            font-size: 18px;
            font-weight: bold;
        }

        .ImgVOList {
            display: none;
        }
    </style>
</head>
<body>

    <h1>리뷰 수정</h1>

    <form action="../review/update" id="updateForm" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <input type="hidden" name="reviewId" class="reviewId" value="${reviewVO.reviewId}" />

        <label for="foodId">식품 아이디</label>
        <input type="number" name="foodId" value="${reviewVO.foodId}" />

        <sec:authentication property="principal" var="user" />
        <sec:authorize access="isAuthenticated()">
            <label for="memberId">회원 아이디</label>
            <input type="text" name="memberId" value="${user.username}" readonly="readonly" />
        </sec:authorize>

        <label for="reviewTitle">리뷰 제목</label>
        <input type="text" name="reviewTitle" value="${reviewVO.reviewTitle}" />

        <label for="reviewContent">리뷰 내용</label>
        <textarea rows="5" cols="30" name="reviewContent">${reviewVO.reviewContent}</textarea>

        <label for="reviewRating">리뷰 별점</label>
        <input type="number" name="reviewRating" value="${reviewVO.reviewRating}" min="1" max="5" />

        <c:forEach var="ImgVO" items="${reviewVO.imgList}" varStatus="status">
            <input type="hidden" class="input-image-list" name="imgList[${status.index}].ImgRealName" value="${ImgVO.imgRealName}">
            <input type="hidden" class="input-image-list" name="imgList[${status.index}].ImgChgName" value="${ImgVO.imgChgName}">
            <input type="hidden" class="input-image-list" name="imgList[${status.index}].ImgExtension" value="${ImgVO.imgExtension}">
            <input type="hidden" class="input-image-list" name="imgList[${status.index}].ImgPath" value="${ImgVO.imgPath}">
        </c:forEach>

        <div class="image-drop">
            <p>드래그 해서 사진을 추가</p>
        </div>

        <div class="update-image-drop">
            <p>드래그 해서 사진 수정</p>
        </div>

        <div class="image-list">
            <c:forEach var="ImgVO" items="${reviewVO.imgList}">
                <div class="image-item">
                    <img src="../image/reviewImage?imgId=${ImgVO.imgId}" alt="Review Image" />
                </div>
            </c:forEach>
        </div>

        <div class="button-container">
            <button type="button" class="insert-image">사진 추가</button>
            <button type="button" class="update-image">사진 수정</button>
            <button type="submit" class="update">리뷰 수정</button>
            <button type="button" class="cancel-button" onclick="history.back()">취소</button>
        </div>
    </form>

    <div class="ImgUpdateList"></div>
    <div class="ImgVOList"></div>

    <script src="${pageContext.request.contextPath }/resources/js/uploadImage.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            $(document).ajaxSend(function(e, xhr, opt){
                var token = $("meta[name='_csrf']").attr("content");
                var header = $("meta[name='_csrf_header']").attr("content");
                xhr.setRequestHeader(header, token);
            });

            $(".insert-image").click(function(){
                $(".image-drop").show();
            });

            $(".update-image").click(function(){
                var isUpdate = confirm("사진들이 삭제됩니다. 하시겠습니까?");
                if (isUpdate) {
                    $(".input-image-list").remove();
                    $('.image-list').empty();
                    $(".image-drop").show();
                }
            });

            $(".update").click(function(){
                var updateForm = $("#updateForm");

                var isInputEmpty = false;
                updateForm.find('input, textarea').each(function(){
                    if ($(this).val() === '') {
                        isInputEmpty = true;
                    }
                });

                if (isInputEmpty) {
                    alert("값이 빈 곳이 존재합니다.");
                    return;
                }

                updateForm.submit();
            });
        });
    </script>
</body>
</html>
