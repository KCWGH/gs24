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
<style>
.image-drop{
	display: none;
}
.image-list{
	display: none;
}
</style>
<title>새 식품 등록</title>
</head>
<body>
	<input type="hidden" class="foreignId" value=0>
	<input type="hidden" class="type" value="food">

	<h1>새 식품 등록</h1>
	<!-- 나중에 여기에 식품 이미지도 같이 DB에 저장해야 한다. -->
	<form action="register" method="post" id="registerForm">
		<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }"><br>
		<input type="text" name="foodType" placeholder="식품 유형 입력" required="required"><br>
		<input type="text" name="foodName" placeholder="식품명 입력" required="required"><br>
		<input type="number" name="foodStock" placeholder="재고량 입력"  required="required"><br>
		<input type="number" name="foodPrice" placeholder="식품 가격 입력" required="required"><br>
		<input type="number" name="foodFat" placeholder="지방 영양소 입력" required="required"><br>
		<input type="number" name="foodProtein" placeholder="단백질 영양소 입력" required="required"><br>
		<input type="number" name="foodCarb" placeholder="탄수화물 영양소 입력" required="required"><br>
		판매 진행
		<input type="radio" name="isSelling" value="1">
		판매 대기
		<input type="radio" name="isSelling" value="2">
	</form>
	
	<div class="thumnail-drop">
		대표 사진 드래그로 등록
	</div>
	
	<div class="thumnail-image"></div>
		
	<div class="image-drop">
		세부 사진 드래그로 등록
	</div>
	
	<div class="image-list"></div>
	
	<div class="ImgVOList"></div>
	
	<button class="cancel">사진 초기화</button>
	<button class="insertImage" disabled="disabled">세부 사진 추가</button>
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
			function checkThumnail(file){
				
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
			
			$(".thumnail-drop").on('dragenter dragover',function(event){				
				event.preventDefault();
				console.log("드래그 중");
			});
			
			$(".thumnail-drop").on('drop',function(event){
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
				
				if(checkThumnail(file)){
					$.ajax({
						type : 'post',
						url : '../image/thumnail',
						processData : false,
						contentType : false,
						data : formData,
						success : function(data){
							console.log(this);
							var list = "";
							var ImgPath = encodeURIComponent(data.imgPath);
							
							var input = $('<input>').attr('type','hidden').attr('name','ImgVO').attr('data-chgName',data.imgChgName);
							input.val(JSON.stringify(data));
							
							$(".ImgVOList").append(input);
							
							list += '<div class="thumnail-item">'
								 +	'<pre>'
								 +	'<input type="hidden" id="thumanilPath" value='+data.imgPath+'>'
								 +	'<input type="hidden" id="thumanilChgName value='+data.imgChgName+'>'
								 +	'<input type="hidden" id="thumanilExtension" value='+data.imgExtension+'>'
								 +	'<img src="../image/display?path='+ImgPath+'&chgName='+data.imgChgName+'&extension='+data.imgExtension+'"width="200px" height="200px" />'
								 +	'</pre>'
								 +	'</pre>'
								 + 	'</div>';
								 
								 $(".thumnail-image").html(list);
								 $(".insertImage").attr('disabled',false);
						}
					});
				}
			});
			
			$(".insertImage").click(function(){
				$(".image-drop").show();
				$(".image-list").show();
			});
			
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
				
				if($(".thumnail-item").length < 1){
					alert("대표 사진을 등록해야 합니다.");
					return;
				}

				var registerForm = $("#registerForm");
				
				var isInputEmpty = false;
				registerForm.find('input').each(function(){
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