<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
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
		<!-- 판매 상태 직접 찝어서 설정해야한다 -->
		<!-- 0 : 판매 중지 | 1 : 판매 진행 | 2 : 판매 대기 -->
		판매 중지
		<input type="radio" name="isSelling" id="val0" value="0">
		판매 진행
		<input type="radio" name="isSelling" id="val1" value="1">
		판매 대기
		<input type="radio" name="isSelling" id="val2" value="2">
		
		<input type="hidden" class="input-thumbnail-image" name="imgThumbnail.ImgRealName" value="${FoodVO.imgThumbnail.imgRealName }">
		<input type="hidden" class="input-thumbnail-image" name="imgThumbnail.ImgChgName" value="${FoodVO.imgThumbnail.imgChgName }">
		<input type="hidden" class="input-thumbnail-image" name="imgThumbnail.ImgExtension" value="${FoodVO.imgThumbnail.imgExtension }">
		<input type="hidden" class="input-thumbnail-image" name="imgThumbnail.ImgPath" value="${FoodVO.imgThumbnail.imgPath }">
		
		<c:forEach var="ImgVO" items="${FoodVO.imgList }" varStatus="status">	
			<c:if test="${not fn:startsWith(ImgVO.imgChgName,'t_') }">
				<input type="hidden" class="input-image-list" name="imgList[${status.index }].ImgRealName" value="${ImgVO.imgRealName }">
				<input type="hidden" class="input-image-list" name="imgList[${status.index }].ImgChgName" value="${ImgVO.imgChgName }">
				<input type="hidden" class="input-image-list" name="imgList[${status.index }].ImgExtension" value="${ImgVO.imgExtension }">
				<input type="hidden" class="input-image-list" name="imgList[${status.index }].ImgPath" value="${ImgVO.imgPath }">
			</c:if>
		</c:forEach>
	</form>
	
	
	<div class="thumbnail-drop" style="display: none;">
		대표 사진 드래그로 등록
	</div>
	
	<div class="thumbnail-image">
		<div class="thumbnail-item">
			<img src="../image/foodThumbnail?foodId=${FoodVO.foodId }" width="200px" height="200px">
		</div>
	</div>
	<button class="update-thumbnail">대표 사진 수정</button>
	
		
	<div class="image-drop" style="display: none">
		사진을 드래그 하기
	</div>
	<div class="image-list">
		<c:forEach var="ImgVO" items="${FoodVO.imgList }">
			<div class="image-item">
				<input type="hidden" class="imgChgName" value="${ImgVO.imgChgName }">
				<img src="../image/foodImage?imgFoodId=${ImgVO.imgId }" width="100px" height="100px">
			</div>
		</c:forEach>
	</div>
	
	<div class="ThumbnailVO"></div>
	<div class="ImgVOList"></div>
	
	<button class="update-image">세부 사진 수정</button>
	<button class="insert-image">세부 사진 추가</button>
	<button class="submit">수정</button>
	<button onclick="location.href='../foodlist/list'">돌아가기</button>
	
	<script src="${pageContext.request.contextPath }/resources/js/uploadImage.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$(document).ajaxSend(function(e, xhr, opt){
		        var token = $("meta[name='_csrf']").attr("content");
		        var header = $("meta[name='_csrf_header']").attr("content");
		        
		        xhr.setRequestHeader(header, token);
		     });
			
			var isSelling = ${FoodVO.isSelling};
			
			$("#val"+isSelling).prop("checked",true);
			
			function checkThumbnail(file){
				
				var checkExtension = /(\.jpg|\.jpeg|\.png|\.gif)$/i;
				var checkFileSize = 1 * 1024 * 1024;
				
				if(file.length > 1){
					console.log("썸네일 파일이 너무 많습니다.");
					return false;
				}
				var fileName = file[0].name;
				var fileSize = file[0].size;
				console.log(fileName);
				
				if(!checkExtension.exec(fileName)){
					console.log("사진 파일이 아닙니다.");
					return false;
				}
				
				if(fileSize > checkFileSize){
					console.log("파일 크기가 너무 큽니다.");
				}
				
				return true;
			}
			
			$(".thumbnail-drop").on('dragenter dragover',function(event){				
				event.preventDefault();
				console.log("드래그 중");
			});
			
			$(".thumbnail-drop").on('drop',function(event){
				event.preventDefault();
				console.log("사진 떨어뜨림");
				
				var file = event.originalEvent.dataTransfer.files;
				console.log(file);
				
				var foodId = $(".foreignId").val();
				console.log(foodId);
				
				var formData = new FormData();
				for(var i = 0; i < file.length; i++) {
					formData.append("file", file[i]); 
				}
				formData.append('foreignId',foodId);
				
				if(checkThumbnail(file)){
					$.ajax({
						type : 'post',
						url : '../image/thumbnail',
						processData : false,
						contentType : false,
						data : formData,
						success : function(data){
							console.log(this);
							var list = "";
							var ImgPath = encodeURIComponent(data.imgPath);
							
							var input = $('<input>').attr('type','hidden').attr('name','ImgVO').attr('data-chgName',data.imgChgName);
							input.val(JSON.stringify(data));
							
							$(".ThumbnailVO").append(input);
							
							list += '<div class="thumbnail-item">'
								 +	'<pre>'
								 +	'<input type="hidden" id="thumanilPath" value='+data.imgPath+'>'
								 +	'<input type="hidden" id="thumanilChgName value='+data.imgChgName+'>'
								 +	'<input type="hidden" id="thumanilExtension" value='+data.imgExtension+'>'
								 +	'<img src="../image/display?path='+ImgPath+'&chgName='+data.imgChgName+'&extension='+data.imgExtension+'"width="200px" height="200px" />'
								 +	'</pre>'
								 +	'</pre>'
								 + 	'</div>';
								 
								 $(".thumbnail-image").html(list);
								 $(".insertImage").attr('disabled',false);
						}
					});
				}
			});
			
			console.log($(".image-list .image-item").length);
			$(".image-list .image-item").each(function(){
				var chgName = $(this).find(".imgChgName").val();
				console.log(chgName);
				if(chgName.startsWith('t_')){
					$(this).remove();
				}
			});
			
			$(".insert-image").click(function(){
				$(".image-drop").show();
			});
			$(".update-image").click(function(){
				var isUpdate = confirm("기존 사진들은 없어지고 다시 등록해야 합니다.");
				if(isUpdate){
					$(".image-list").empty();
					$(".input-image-list").remove();
					$(".image-drop").show();
				}
			});
			
			$(".update-thumbnail").click(function(){
				$(".thumbnail-drop").show();
				$(".thumbnail-image").empty();
				
				$("#updateForm .input-thumbnail-image").each(function(){
					console.log(this);
					$(this).remove();
				});
			});
			
			$(".submit").click(function(){
				
				if($(".thumbnail-item").length < 1){
					alert("대표 사진을 등록해야 합니다.");
					return;
				}
				
				var updateForm = $('#updateForm');
				
				var isInputEmpty = false;
				updateForm.find('input').each(function(){
					if($(this).val() == ''){
						console.log("값이 빈 곳이 존재합니다.");
						isInputEmpty = true;
					}
				});
				
				if(isInputEmpty){
					alert("값이 빈 곳이 존재합니다.");
					return;
				}
				
				$(".ThumbnailVO input").each(function(){
					var ImgVO = JSON.parse($(this).val());
						
					var realName 	= $('<input>').attr('type','hidden').attr('class','input-thumbnail-image').attr('name','imgThumbnail.ImgRealName').attr('value',ImgVO.imgRealName);
					var chgName		= $('<input>').attr('type','hidden').attr('class','input-thumbnail-image').attr('name','imgThumbnail.ImgChgName').attr('value',ImgVO.imgChgName);
					var extension	= $('<input>').attr('type','hidden').attr('class','input-thumbnail-image').attr('name','imgThumbnail.ImgExtension').attr('value',ImgVO.imgExtension);
					var path		= $('<input>').attr('type','hidden').attr('class','input-thumbnail-image').attr('name','imgThumbnail.ImgPath').attr('value',ImgVO.imgPath);
					
					updateForm.append(realName);
					updateForm.append(chgName);
					updateForm.append(extension);
					updateForm.append(path);
				});
				
				var i = 0; 
				i = $(".input-image-list").length / 4;
				
				if(i == null){}
				
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