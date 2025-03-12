<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/uploadImage.css">
<title>리뷰 작성</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f4f4f4;
        margin: 0;
        padding: 0;
    }
    .container {
        max-width: 800px;
        margin: 40px auto;
        padding: 20px;
        background-color: #fff;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        border-radius: 8px;
    }
    h1 {
        text-align: center;
        margin-bottom: 20px;
    }
    .form-group {
        margin-bottom: 20px;
    }
    .form-group label {
        font-size: 16px;
        margin-bottom: 8px;
        display: block;
    }
    .form-group input,
    .form-group textarea {
        width: 100%;
        padding: 10px;
        font-size: 16px;
        border: 1px solid #ddd;
        border-radius: 4px;
        box-sizing: border-box;
    }
    .form-group textarea {
        resize: vertical;
        min-height: 100px;
    }
    .form-group input[type="number"] {
        width: 60px;
    }
    .button-container {
    	display: flex;
        justify-content: space-between;
        align-items: center;
    }
    .button-group {
        display: flex;
        justify-content: space-between;
        align-items: center;
    }
    .button-group button {
        padding: 10px 20px;
        font-size: 16px;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        transition: background-color 0.3s;
    }
    .button-group .cancel {
        background-color: #f0f0f0;
    }
    .button-group .cancel:hover {
        background-color: #ddd;
    }
    .button-group .back {
        background-color: #f0f0f0;
    }
    .button-group .back:hover {
        background-color: #ddd;
    }
    .button-group .submit {
        background-color: #4CAF50;
        color: white;
    }
    .button-group .submit:hover {
        background-color: #ddd;
    }
    .image-list {
        display: flex;
        flex-wrap: wrap;
        gap: 10px;
        margin-top: 20px;
    }
    .image-item .delete {
        position: absolute;
        top: 5px;
        right: 5px;
        background-color: rgba(0, 0, 0, 0.6);
        color: white;
        padding: 5px;
        border-radius: 50%;
        cursor: pointer;
    }
</style>
</head>
<body>
	<input type="hidden" class="foreignId" value=0>
 	<input type="hidden" class="type" value="review">
    <div class="container">
        <h1>리뷰 작성</h1>
        <form action="../review/register" method="post" id="registForm">
            <input type="hidden" name="foodId" value="${foodId }">
            <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
            <sec:authentication property="principal" var="user"/>    
            <sec:authorize access="isAuthenticated()">
                <div class="form-group">
                    <label for="memberId">회원 아이디</label>
                    <input type="text" name="memberId" id="memberId" value="${user.username}" readonly="readonly">
                </div>
            </sec:authorize>
            
            <div class="form-group">
                <label for="reviewTitle">제목</label>
                <input type="text" name="reviewTitle" id="reviewTitle" maxlength="200">
            </div>
            
            <div class="form-group">
                <label for="reviewContent">내용</label>
                <textarea name="reviewContent" id="reviewContent" rows="10" maxlength="500"></textarea>
            </div>
            
            <div class="form-group">
                <label for="reviewRating">별점</label>
                <input type="number" name="reviewRating" id="reviewRating" min="1" max="5">
            </div>

            <div class="form-group">
                <p>사진 등록</p>
                <div class="image-drop">
                    <p>사진을 클릭&드래그로 등록</p>
                </div>
                <input type="file" id="image-click" style="display: none;" multiple="multiple"/>
                <div class="image-list"></div>
            </div>
			
			<div class="button-container">
	            <div class="button-group">
    	            <button type="button" class="cancel" value="reset">사진 초기화</button>
        	        <button type="button" class="back" onclick="history.back()">취소</button>
            	</div>

	            <div class="button-group">
    	            <button type="button" class="submit">등록</button>
        	    </div>
			</div>
        </form>
    </div>
	
	<div class="ImgVOList"></div>

    <script src="${pageContext.request.contextPath }/resources/js/uploadImage.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            $(document).ajaxSend(function(e, xhr, opt){
                var token = $("meta[name='_csrf']").attr("content");
                var header = $("meta[name='_csrf_header']").attr("content");
                
                xhr.setRequestHeader(header, token);
            });

            $(".submit").click(function(){
                var registForm = $("#registForm");
                var convenienceId = ${convenienceId };
                var inputConvenienceId = $('<input>').attr('type','hidden').attr('name','convenienceId').attr('value',convenienceId);
                var foodId = $('input[name=foodId]').val();
                
                var preorderId = $('<input>').attr('type','hidden').attr('name','preorderId').attr('value',${preorderId});
                registForm.append(preorderId);
                
                var status = $('<input>').attr('type','hidden').attr('name','status').attr('value',checkFoodId(foodId));
                registForm.append(status);
                
                var isInputEmpty = false;
                registForm.find('input').each(function(){
                    if($(this).val() == ''){
                        console.log("값이 빈 곳이 존재합니다.");
                        isInputEmpty = true;
                    }
                });
                if(isInputEmpty){
                    alert("값이 빈 곳이 존재합니다.");
                    return;
                }
                
                var i = 0;
                $(".ImgVOList input").each(function(){
                    var ImgVO = JSON.parse($(this).val());
                    
                    console.log(ImgVO);
                    
                    var foreignId = $("<input>").attr('type','hidden').attr('name','imgList['+i+'].foreignId').attr('value',ImgVO.foreignId);
                    var realName = $("<input>").attr('type','hidden').attr('name','imgList['+i+'].ImgRealName').attr('value',ImgVO.imgRealName);
                    var chgName = $('<input>').attr('type','hidden').attr('name','imgList['+i+'].ImgChgName').attr('value',ImgVO.imgChgName);
                    var extension = $('<input>').attr('type','hidden').attr('name','imgList['+i+'].ImgExtension').attr('value',ImgVO.imgExtension);
                    var path = $("<input>").attr('type','hidden').attr('name','imgList['+i+'].ImgPath').attr('value',ImgVO.imgPath);
                    
                    registForm.append(foreignId);
                    registForm.append(realName);
                    registForm.append(chgName);
                    registForm.append(extension);
                    registForm.append(path);
                    
                    i++;
                });

                registForm.append(inputConvenienceId);
                registForm.submit();
            });

            $(".image-list").on('click','.image-item',function(){
                $(this).remove();
                var isDelete = confirm("삭제하시겠습니까?");
                if(isDelete){
                    var path = $(this).find('#ImgPath').val();
                    var chgName = $(this).find('#ImgChgName').val();
                    var extension = $(this).find('#ImgExtension').val();
                    
                    $.ajax({
                        type: 'post',
                        url: '../image/delete',
                        data: {'path': path, 'chgName': chgName, 'extension': extension},
                        success: function(result){
                            var find = $(".ImgVOList").find('input[data-chgName='+chgName+']');
                            $(find).remove();
                        }
                    });
                }
            });

            function checkFoodId(foodId){
                var checkFoodId = ${foodId};
                if(checkFoodId != foodId){
                    alert("리뷰를 작성 할 수 없습니다.");
                    return 0;
                }
                return 1;
            }
        });
    </script>
</body>
</html>
