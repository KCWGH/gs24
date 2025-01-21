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
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/uploadImage.css">
<title>리뷰 작성</title>
</head>
<body>
	<input type="hidden" class="path">
	<input type="hidden" class="foreignId" value=0>
	<input type="hidden" class="type" value="review">
	<p>리뷰 작성</p>
	
	<form action="../review/register" method="post" id="registForm">
		<input type="hidden" name="foodId" value="${foodId }">
		<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
		<sec:authentication property="principal" var="user"/>	
		<sec:authorize access="isAuthenticated()">
		<p>회원 아이디</p><input type="text" name="memberId" value="${user.username}" readonly="readonly"><br>
		</sec:authorize>
		<p>제목 : <p>
		<input type="text" name="reviewTitle"><br>
		<p>내용 : <p>
		<textarea rows="10" cols="40" name="reviewContent"></textarea><br>
		<p>별점 : <p>
		<input type="number" name="reviewRating">
	</form>
	
	<p>사진등록<p>
	<div class="image-drop">
		<p>사진을 드래그 해서 등록</p>
	</div>
	
	<div class="image-list"></div>
	<button class="cancel" value="reset">사진 초기화</button>
	<button class="cancel" value="cancel">취소</button>
	<br><br><button class="submit">등록</button>
	
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
			
			var i = 0;
			$(".ImgVOList input").each(function(){
				var ImgVO = JSON.parse($(this).val());
				console.log(ImgVO);
				
				var foreignId =	$("<input>").attr('type','hidden').attr('name','imgList['+i+'].foreignId').attr('value',ImgVO.foreignId);
				var realName =	$("<input>").attr('type','hidden').attr('name','imgList['+i+'].ImgRealName').attr('value',ImgVO.imgRealName);
				var chgName =	$('<input>').attr('type','hidden').attr('name','imgList['+i+'].ImgChgName').attr('value',ImgVO.imgChgName);
				var extension =	$('<input>').attr('type','hidden').attr('name','imgList['+i+'].ImgExtension').attr('value',ImgVO.imgExtension);
				var path =		$('<input>').attr('type','hidden').attr('name','imgList['+i+'].ImgPath').attr('value',ImgVO.imgPath);
				
				registForm.append(foreignId);
				registForm.append(realName);
				registForm.append(chgName);
				registForm.append(extension);
				registForm.append(path);
				
				i++;
			}); //end each  
			
			registForm.submit();
		});//end click
		
		
		$(".image-list").on('click','.image-item',function(){
			console.log(this);
			$(this).remove();
			var isDelete = confirm("삭제하시겠습니까?");
			if(isDelete){
				
				var path = $(this).find('#ImgPath').val();
				var chgName = $(this).find('#ImgChgName').val();
				var extension = $(this).find('#ImgExtension').val();
				
				$.ajax({
					type : 'post',
					url : '../image/delete',
					data : {'path' : path, 'chgName' : chgName, 'extension' : extension},
					success : function(result){
						var find = $(".ImgVOList").find('input[data-chgName='+chgName+']');
						$(find).remove();
					}
				});
			}
		});
		
	}); // end document
	</script>
</body>
</html>