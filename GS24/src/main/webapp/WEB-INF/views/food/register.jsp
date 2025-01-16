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
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/uploadImage.css">
<title>편의점 식품 등록</title>
</head>
<body>
	<input type="hidden" class="foreignId" value=0>
	<input type="hidden" class="type" value="food">

	<h1>편의점 식품 등록</h1>
	<!-- 나중에 여기에 식품 이미지도 같이 DB에 저장해야 한다. -->
	<form action="register" method="post" id="registerForm">
		<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
		<div><input type="text" name="foodType" placeholder="식품 유형 입력" required="required"></div>
		<div><input type="text" name="foodName" placeholder="식품명 입력" required="required"></div>
		<div><input type="number" name="foodStock" placeholder="재고량 입력" required="required"></div>
		<div><input type="number" name="foodPrice" placeholder="식품 가격 입력" required="required"></div>
		<div><input type="number" name="foodFat" placeholder="지방 영양소 입력" required="required"></div>
		<div><input type="number" name="foodProtein" placeholder="단백질 영양소 입력" required="required"></div>
		<div><input type="number" name="foodCarb" placeholder="탄수화물 영양소 입력" required="required"></div>
	</form>
	
	<div class="image-drop">
	사진을 드래그해서 등록
	</div>
	
	<div class="image-list"></div>
	
	<div class="ImgVOList"></div>
	
	<button class="cancel">사진 초기화</button>
	<button class="submit">등록</button>
	<button class="cancel" value="cancel">취소</button>
	
	<script src="${pageContext.request.contextPath }/resources/js/uploadImage.js"></script>
	<script>
			$(document).ajaxSend(function(e, xhr, opt){
		        var token = $("meta[name='_csrf']").attr("content");
		        var header = $("meta[name='_csrf_header']").attr("content");
		        
		        xhr.setRequestHeader(header, token);
		     });
		$(document).ready(function() {
			$(".image-list").on('click','.image-item',function(){
				console.log(this);
				var isDelete = confirm("삭제하시겠습니까?");
				if(isDelete){					
					$(this).remove();
					
					var path = $(this).find("#ImgPath").val();
					var chgName = $(this).find("#ImgChgName").val();
					var extension = $(this).find("#ImgExtension").val();
					
					$.ajax({
						type : 'post',
						url : '../image/delete',
						data : {'path' : path, 'chgName' : chgName, 'extension' : extension},
						success : function(result){
							var find = $(".ImgVOList").find("input[data-chgName = "+ chgName +"]");
							find.remove();
						}//end success
					});//end ajax
				}
			});//end on
			
			$(".submit").click(function(){
				var registerForm = $("#registerForm");
				
				var i = 0;
				$(".ImgVOList input").each(function(){
					var ImgVO = JSON.parse($(this).val());
					
					console.log(ImgVO);
					
					var foodId		= $('<input>').attr('type','hidden').attr('name','imgList['+i+'].foreignId').attr('value',ImgVO.foreignId);
					var realName	= $('<input>').attr('type','hidden').attr('name','imgList['+i+'].ImgRealName').attr('value',ImgVO.imgRealName);
					var chgName		= $('<input>').attr('type','hidden').attr('name','imgList['+i+'].ImgChgName').attr('value',ImgVO.imgChgName);
					var extension	= $('<input>').attr('type','hidden').attr('name','imgList['+i+'].ImgExtension').attr('value',ImgVO.imgExtension);
					var path		= $('<input>').attr('type','hidden').attr('name','imgList['+i+'].ImgPath').attr('value',ImgVO.imgPath);
					
					registerForm.append(foodId);
					registerForm.append(realName);
					registerForm.append(chgName);
					registerForm.append(extension);
					registerForm.append(path);
					
					i++;
				});
				
				registerForm.submit();
			});
		});
	</script>
</body>
</html>