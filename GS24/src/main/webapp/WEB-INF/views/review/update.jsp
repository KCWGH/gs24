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
<title>리뷰 수정</title>
</head>
<body>
	<input type="hidden" class="foreignId" value=${reviewVO.reviewId }>
	<input type="hidden" class="type" value="review">
	<form action="../review/update" id="updateForm" method="post">
	<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
		<p>리뷰 아이디</p><input type="number" name="reviewId" class="reviewId" value="${reviewVO.reviewId }">
		<p>식품 아이디</p><input type="number" name="foodId" value="${reviewVO.foodId }">
		<sec:authentication property="principal" var="user"/>	
		<sec:authorize access="isAuthenticated()">
		<p>회원 아이디</p><input type="text" name="memberId" value="${user.username}" readonly="readonly"><br>
		</sec:authorize>
		<p>리뷰 제목 : </p><input type="text" name="reviewTitle" value="${reviewVO.reviewTitle }">
		<p>리뷰 내용 : </p><textarea rows="5" cols="30" name="reviewContent">${reviewVO.reviewContent }</textarea>
		<p>리뷰 별점 : </p><input type="number" name="reviewRating" value="${reviewVO.reviewRating }" min="1" max="5">
		
		
		<c:forEach var="ImgVO" items="${reviewVO.imgList }" varStatus="status">
				<input type="hidden" class="input-image-list" name="imgList[${status.index }].ImgRealName" value="${ImgVO.imgRealName }">
				<input type="hidden" class="input-image-list" name="imgList[${status.index }].ImgChgName" value="${ImgVO.imgChgName }">
				<input type="hidden" class="input-image-list" name="imgList[${status.index }].ImgExtension" value="${ImgVO.imgExtension }">
				<input type="hidden" class="input-image-list" name="imgList[${status.index }].ImgPath" value=${ImgVO.imgPath }>
		</c:forEach>
	</form>
	
	<div class="image-drop" style="display: none;">
		드래그 해서 사진을 추가
	</div>
	
	<div class="update-image-drop" style="display: none;">
		드래그 해서 사진 수정 
	</div>
	
	<div class='image-list'>
		<c:forEach var="ImgVO" items="${reviewVO.imgList }">
			<div class="image-item">
				<img src="../image/reviewImage?imgId=${ImgVO.imgId }" width="100" height="100">
			</div>
		</c:forEach>
	</div>
	
	<button class="update-image">사진 수정</button>
	<button class="insert-image">사진 추가</button>
	<button class="update">리뷰 수정</button>
	<button onclick="location.href='../food/list'">돌아가기</button>
	
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
		});
		
		$(".insert-image").click(function(){
			$(".image-drop").show();
		});
		$(".update-image").click(function(){
			var isUpdate = confirm("사진들이 삭제됩니다. 하시겠습니까?")
			if(isUpdate){
				$(".input-image-list").remove();
				$('.image-list').empty();
				$(".image-drop").show();
			}
		});
		
		$(".update").click(function(){
			var updateForm = $("#updateForm");
			
			var isInputEmpty = false;
			updateForm.find('input').each(function(){
				if($(this).val() == ''){
					isInputEmpty = true;
				}
			});
			if(isInputEmpty){
				alert("값이 빈 곳이 존재합니다.");
				return;
			}
			
			var i = $("#updateForm").children(".input-image-list").length / 4;
			console.log(i);
			
			$(".ImgVOList input").each(function(){
				var ImgVO = JSON.parse($(this).val());
				
				var realName =	$('<input>').attr('type','hidden').attr('class','input-image-list').attr('name','imgList['+i+'].ImgRealName').attr('value',ImgVO.imgRealName);
				var chgName =	$('<input>').attr('type','hidden').attr('class','input-image-list').attr('name','imgList['+i+'].ImgChgName').attr('value',ImgVO.imgChgName);
				var extension =	$('<input>').attr('type','hidden').attr('class','input-image-list').attr('name','imgList['+i+'].ImgExtension').attr('value',ImgVO.imgExtension);
				var path =		$('<input>').attr('type','hidden').attr('class','input-image-list').attr('name','imgList['+i+'].ImgPath').attr('value',ImgVO.imgPath);
				
				updateForm.append(realName);
				updateForm.append(chgName);
				updateForm.append(extension);
				updateForm.append(path);
				
				i++;
			});
			
			updateForm.submit();
		});
	}); // end document
	</script>
</body>
</html>