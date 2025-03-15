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
<link rel="stylesheet" href="../resources/css/fonts.css">
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/uploadImage.css">
<style>
.image-drop, .image-list {
	display: none;
}

body {
    font-family: 'Pretendard-Regular', sans-serif;
    margin: 0;
    padding: 20px;
    background-color: #f4f7f6;
    text-align: center;
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

input[type="text"], 
input[type="number"], 
textarea {
    font-family: 'Pretendard-Regular', sans-serif;
    width: 100%;
    padding: 10px;
    margin-top: 5px;
    border: 1px solid #ddd;
    border-radius: 4px;
    box-sizing: border-box;
}

input[type="text"]:focus, 
input[type="number"]:focus, 
textarea:focus {
    border-color: #007bff;
    outline: none;
}

.image-item img {
    width: 100px;
    height: 100px;
    object-fit: cover;
    border-radius: 5px;
    margin-right: 10px;
    box-shadow: 0 0 5px rgba(0, 0, 0, 0.2);
}

.thumbnail-item {
	width: 100%;
    height: 100%;
    object-fit: cover;
    border-radius: 5px;
    box-shadow: 0 0 5px rgba(0, 0, 0, 0.2);
}

.thumbnail-item img {
    width: auto;
    height: auto;
    object-fit: contain;
    border-radius: 5px;
    box-shadow: 0 0 5px rgba(0, 0, 0, 0.2);
}

.button-container {
    text-align: center;
    margin-top: 20px;
}

.button-container button {
    color: white;
    padding: 10px 33px;
    font-size: 20px;
}

button {
    font-family: 'Pretendard-Regular', sans-serif;
    background: #ddd;
    color: black;
    padding: 5px 10px;
    border-radius: 5px;
    border: none;
    cursor: pointer;
}

button:hover {
    background-color: #ccc;
}

.submit {
    background-color: #4CAF50;
}

.submit:hover {
    background-color: #388E3C;
}

.cancel {
    background-color: #f44336;
}

.cancel:hover {
    background-color: #d32f2f;
}

.radio-group {
    display: flex;
    gap: 20px;
    justify-content: center;
    margin: 20px 0;
}

.radio-group label {
    display: inline-block;
    background-color: #f1f1f1;
    padding: 15px 30px;
    border-radius: 50px;
    cursor: pointer;
    text-align: center;
    width: 100px;
}

input[type="radio"]#val2:checked + label {
    background-color: #007bff;
    color: white;
}

input[type="radio"]#val0:checked + label {
    background-color: #f44336;
    color: white;
}

input[type="radio"]#val1:checked + label {
    background-color: #4CAF50;
    color: white;
}

input[type="radio"] {
    display: none;
}

.form-container {
    display: flex;
    justify-content: space-between;
    align-items: center;
    max-width: 600px;
    margin: 0 auto;
    background: #ffffff;
    border-radius: 8px;
}

.thumbnail-container {
    height: 200px; /* 높이를 고정 */
    display: flex;
    align-items: center;
    justify-content: center;
    overflow: hidden;
}

.thumbnail-image {
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 200px;
    background: #f9f9f9;
    margin-bottom: 0px;
    border: 1px solid #ddd;
    overflow: hidden;
}

.inputs {
    width: 50%;
    padding-left: 20px;
}

.inputs input {
    width: 100%;
    padding: 10px;
    margin-top: 5px;
    border: 1px solid #ddd;
    border-radius: 4px;
    box-sizing: border-box;
}

.thumbnail-item img,
.image-item img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    box-shadow: 0 0 5px rgba(0, 0, 0, 0.2);
}

.thumbnail-drop {
    width: auto;
    margin-top: 20px;
    background-color: #f9f9f9;
    border: 1px solid #ddd;
    height: auto;
    text-align: center;
    padding: 10px;
    cursor: pointer;
}
</style>
<title>새 식품 등록</title>
</head>
<body>
	<input type="hidden" class="foreignId" value=0>
	<input type="hidden" class="type" value="food">

	<h1>새 식품 등록</h1>
	<form action="register" method="post" id="registerForm">
	<button type="button" onclick="openSearchPage()">식품 검색</button>
<div class="form-container">
    <div class="thumbnail-container">
        <div class="thumbnail-image"></div>
    </div>

    <div class="inputs">
        <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }"><br>
        <input type="text" id="foodType" name="foodType" placeholder="식품 유형 입력" required="required"><br>
        <input type="text" id="foodName" name="foodName" placeholder="식품명 입력" required="required"><br>
        <input type="number" id="foodCarb" name="foodCarb" placeholder="탄수화물 영양소 입력" required="required"><br>
        <input type="number" id="foodProtein" name="foodProtein" placeholder="단백질 영양소 입력" required="required"><br>
        <input type="number" id="foodFat" name="foodFat" placeholder="지방 영양소 입력" required="required">
        <input type="number" name="foodStock" placeholder="재고량 입력"  required="required"><br>
        <input type="number" name="foodPrice" placeholder="식품 가격 입력" required="required"><br>
    </div>
</div>

		<div class="radio-group">
    		<div>
        		<input type="radio" name="isSelling" id="val2" value="2">
        		<label for="val2">판매 대기</label>
    		</div>
    		<div>
        		<input type="radio" name="isSelling" id="val0" value="0">
        		<label for="val0">판매 중지</label>
   			</div>
    		<div>
       			<input type="radio" name="isSelling" id="val1" value="1">
        		<label for="val1">판매 진행</label>
    		</div>
		</div>
	<div class="thumbnail-drop">
		대표사진 - 클릭하여 파일을 직접 선택하거나 Drag&amp;Drop하세요
	</div>
	
		
	<div class="image-drop">
		세부사진 - 클릭하여 파일을 직접 선택하거나 Drag&amp;Drop하세요
	</div>
	
	<div class="image-list"></div>
	
		<button type="button" class="insertImage" disabled="disabled">세부 사진 추가</button>
		<button type="button" class="cancel" style="background-color: #ddd;">세부사진 초기화</button>
	<div class="button-container">
		<button type="button" class="submit">등록</button>
		<button type="button" class="cancel" value="cancel">취소</button>
	</div>
	</form>
	
	
	<div class="ThumbnailVO"></div>
	<div class="ImgVOList"></div>
	<input type="file" id="thumbnail-click" style="display: none;">
	<input type="file" id="image-click" style="display: none;" multiple="multiple">
	
	<script src="${pageContext.request.contextPath }/resources/js/uploadImage.js"></script>
	<script>
			$(document).ajaxSend(function(e, xhr, opt){
		        var token = $("meta[name='_csrf']").attr("content");
		        var header = $("meta[name='_csrf_header']").attr("content");
		        
		        xhr.setRequestHeader(header, token);
		     });
			
			function fillForm(foodName, foodType, carbs, protein, fat) {
			    $("#foodName").val(foodName);
			    $("#foodType").val(foodType);
			    $("#foodCarb").val(carbs);
			    $("#foodProtein").val(protein);
			    $("#foodFat").val(fat);
			}
			
			function openSearchPage() {
				window.open('../food/auto-nutrition-input', '_blank', 'width=500,height=710,top=100,left=200');
			}
		$(document).ready(function() {
			
			
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
			
			function saveThumbnail(file){
				var foodId = $(".foreignId").val();
				
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
								 +	'<input type="hidden" id="thumanilPath" value='+data.imgPath+'>'
								 +	'<input type="hidden" id="thumanilChgName value='+data.imgChgName+'>'
								 +	'<input type="hidden" id="thumanilExtension" value='+data.imgExtension+'>'
								 +	'<img src="../image/display?path='+ImgPath+'&chgName='+data.imgChgName+'&extension='+data.imgExtension+'"width="200px" height="200px" />'
								 + 	'</div>';
								 $(".thumbnail-image").html(list);
								 $(".thumbnail-image").show();
								 $(".insertImage").attr('disabled',false);
							}
						});
					}
				}
			
			$(".thumbnail-drop").on('dragenter dragover',function(event){				
				event.preventDefault();
				console.log("드래그 중");
			});
			
			$(".thumbnail-drop").click(function(){
				console.log("클릭");
				$("#thumbnail-click").click();
			});
			
			$(".thumbnail-drop").on('drop',function(event){
				event.preventDefault();
				console.log("사진 떨어뜨림");
				
				var file = event.originalEvent.dataTransfer.files;
				console.log(file);
				
				saveThumbnail(file);
			});
			
			$("#thumbnail-click").change(function(){
				var file = this.files;
				
				saveThumbnail(file);
			});
			
			$(".insertImage").click(function(){
				$(".image-drop").show();
			});
			$(".image-drop").on('drop',function(event){
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
				
				if($(".thumbnail-item").length < 1){
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
				
				$(".ThumbnailVO input").each(function(){
					var ImgVO = JSON.parse($(this).val());
					
					console.log(ImgVO);
					
					var foodId		= $('<input>').attr('type','hidden').attr('name','imgThumbnail.foreignId').attr('value',ImgVO.foreignId);
					var realName	= $('<input>').attr('type','hidden').attr('name','imgThumbnail.ImgRealName').attr('value',ImgVO.imgRealName);
					var chgName		= $('<input>').attr('type','hidden').attr('name','imgThumbnail.ImgChgName').attr('value',ImgVO.imgChgName);
					var extension	= $('<input>').attr('type','hidden').attr('name','imgThumbnail.ImgExtension').attr('value',ImgVO.imgExtension);
					var path		= $('<input>').attr('type','hidden').attr('name','imgThumbnail.ImgPath').attr('value',ImgVO.imgPath);
					
					registerForm.append(foodId);
					registerForm.append(realName);
					registerForm.append(chgName);
					registerForm.append(extension);
					registerForm.append(path);
				});
				
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