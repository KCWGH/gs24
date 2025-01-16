<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<script src="https://code.jquery.com/jquery-3.7.1.js">
</script>
<title>식품 정보 수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/uploadImage.css">
</head>
<body>
	<input type="hidden" class="foreignId" value=${FoodVO.foodId }>
	<input type="hidden" class="type" value="food">

	<h1>식품 정보 수정</h1>
	<h2>${FoodVO.foodName }</h2>
	<form action="update" method="post" id="updateForm">
	<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
		<input type="hidden" name="foodId" value="${FoodVO.foodId }"><br>
		<p>재고량</p>
		<input type="number" name="foodStock" value="${FoodVO.foodStock }" required="required"><br>
		<p>가격</p>
		<input type="number" name="foodPrice" value="${FoodVO.foodPrice }" required="required"><br>	
		<p>단백질 함유량</p>
		<input type="number" name="foodProtein" value="${FoodVO.foodProtein }" required="required"><br>
		<p>지방 함유량</p>
		<input type="number" name="foodFat" value="${FoodVO.foodFat }" required="required"><br>
		<p>탄수화물 함유량</p>
		<input type="number" name="foodCarb" value= "${FoodVO.foodCarb }" required="required"><br>
		
		<c:forEach var="ImgVO" items="${FoodVO.imgList }" varStatus="status">
			<input type="hidden" class="input-image-list" name="imgList[${status.index }].ImgRealName" value="${ImgVO.imgRealName }">
			<input type="hidden" class="input-image-list" name="imgList[${status.index }].ImgChgName" value="${ImgVO.imgChgName }">
			<input type="hidden" class="input-image-list" name="imgList[${status.index }].ImgExtension" value="${ImgVO.imgExtension }">
			<input type="hidden" class="input-image-list" name="imgList[${status.index }].ImgPath" value="${ImgVO.imgPath }">
		</c:forEach>
	</form>
	
	<div class="image-drop" style="display: none">
		사진을 드래그 하기
	</div>
	<div class="image-list">
		<c:forEach var="ImgVO" items="${FoodVO.imgList }">
			<div class="image-item">
				<img src="../image/foodImage?imgFoodId=${ImgVO.imgId }" width="100px" height="100px">
			</div>
		</c:forEach>
	</div>
	
	<div class="ImgVOList"></div>
	
	<button class="update-image">사진 수정</button>
	<button class="insert-image">사진 추가</button>
	<button class="submit">수정</button>
	
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
				var isUpdate = confirm("기존 사진들은 삭제 됩니다. 삭제하시겠습니까?");
				if(isUpdate){
					$(".image-list").empty();
					$(".input-image-list").remove();
					$(".image-drop").show();
				}
			});
			$(".submit").click(function(){
				
				var updateForm = $('#updateForm');
				var i = $(".input-image-list").length / 4;
				console.log(i);
				
				$(".ImgVOList input").each(function(){
					
					var ImgVO = JSON.parse($(this).val());
					
					
					var realName 	= $('<input>').attr('type','hidden').attr('class','input-image-list').attr('name','imgList['+i+'].ImgRealName').attr('value',ImgVO.imgRealName);
					var chgName		= $('<input>').attr('type','hidden').attr('class','input-image-list').attr('name','imgList['+i+'].ImgChgName').attr('value',ImgVO.imgChgName);
					var extension	= $('<input>').attr('type','hidden').attr('class','input-image-list').attr('name','imgList['+i+'].ImgExtension').attr('value',ImgVO.imgExtension);
					var path		= $('<input>').attr('type','hidden').attr('class','input-image-list').attr('name','imgList['+i+'].ImgPath').attr('value',ImgVO.imgPath);
					
					updateForm.append(realName);
					updateForm.append(chgName);
					updateForm.append(extension);
					updateForm.append(path);
					
					i++;
					
				});
				updateForm.submit();
			});
		});
	</script>
</body>
</html>