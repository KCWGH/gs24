<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/uploadImage.css">
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

        .image-list,.thumnail-image {
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
		
		.thumbnail-item img{
			width: 200px;
            height: 200px;
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
        .ImgVOList {
            display: none;
        }
        .radio-group {
    display: flex;
    gap: 20px;
    justify-content: center;
    margin: 20px 0;
}

/* 개별 라디오 버튼의 레이블 */
.radio-group label {
    display: inline-block;
    background-color: #f1f1f1;
    padding: 15px 30px;
    border-radius: 50px;
    cursor: pointer;
    text-align: center;
    width: 100px;
}

/* 라디오 버튼이 선택된 상태일 때 */
input[type="radio"]:checked + label {
    background-color: #007bff;
    color: white;
}

/* 라디오 버튼 비선택 상태 */
input[type="radio"]:not(:checked) + label {
    background-color: #f1f1f1;
    color: #333;
}

/* 라디오 버튼 숨기기 */
input[type="radio"] {
    display: none;
}
.thumnail-item{
	border-style: solid;
	border-color: black;
}
    </style>
<meta charset="UTF-8">
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<script src="https://code.jquery.com/jquery-3.7.1.js">
</script>
<title>식품 정보 수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/uploadImage.css">
</head>
<body>
${sessionScope.role}
	<input type="hidden" class="foreignId" value=${FoodVO.foodId }>
	<input type="hidden" class="type" value="food">

	<h1>식품 정보 수정</h1>
	<form action="update" method="post" id="updateForm">
	<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
		<h1>${FoodVO.foodName }</h1>
		<input type="hidden" name="foodId" value="${FoodVO.foodId }"><br>
		<label>재고량</label>
		<input type="number" name="foodStock" value="${FoodVO.foodStock }" required="required"><br>
		<label>가격</label>
		<input type="number" name="foodPrice" value="${FoodVO.foodPrice }" required="required"><br>	
		<!-- 판매 상태 직접 찝어서 설정해야한다 -->
		<!-- 0 : 판매 중지 | 1 : 판매 진행 | 2 : 판매 대기 -->
		<div class="radio-group">
    		<div>
        		<input type="radio" name="isSelling" id="val0" value="0">
        		<label for="val0">판매 중지</label>
   			</div>
    		<div>
       			<input type="radio" name="isSelling" id="val1" value="1">
        		<label for="val1">판매 진행</label>
    		</div>
    		<div>
        		<input type="radio" name="isSelling" id="val2" value="2">
        		<label for="val2">판매 대기</label>
    		</div>
		</div>
		
		<input type="hidden" class="input-thumbnail-image" name="imgThumbnail.ImgRealName" value="${FoodVO.imgThumbnail.imgRealName }">
		<input type="hidden" class="input-thumbnail-image" name="imgThumbnail.ImgChgName" value="${FoodVO.imgThumbnail.imgChgName }">
		<input type="hidden" class="input-thumbnail-image" name="imgThumbnail.ImgExtension" value="${FoodVO.imgThumbnail.imgExtension }">
		<input type="hidden" class="input-thumbnail-image" name="imgThumbnail.ImgPath" value="${FoodVO.imgThumbnail.imgPath }">
		
		<c:forEach var="ImgVO" items="${FoodVO.imgList }" varStatus="status">	
			<c:if test="${not fn:startsWith(ImgVO.imgChgName,'t_') }">
			<div class="input-image-items">
	            <input type="hidden" class="input-image-list" name="imgList[${status.index}].ImgRealName" value="${ImgVO.imgRealName}">
    	        <input type="hidden" class="input-image-list" name="imgList[${status.index}].ImgChgName" value="${ImgVO.imgChgName}">
        	    <input type="hidden" class="input-image-list" name="imgList[${status.index}].ImgExtension" value="${ImgVO.imgExtension}">
            	<input type="hidden" class="input-image-list" name="imgList[${status.index}].ImgPath" value="${ImgVO.imgPath}">
        	</div>
			</c:if>
		</c:forEach>
		<div class="thumbnail-drop" style="display: none;">
			<p>대표 사진 클릭&amp;드래그</p>
		</div>
	
		<div class="thumbnail-image">
			<div class="thumbnail-item">
				<img src="../image/foodThumbnail?foodId=${FoodVO.foodId }" width="200px" height="200px">
			</div>
		</div>
		<button type="button" class="update-thumbnail">대표 사진 수정</button>
		
		<div class="image-drop" style="display: none">
			<p>세부 사진 클릭&amp;드래그</p>
		</div>
		<div class="image-list">
			<c:forEach var="ImgVO" items="${FoodVO.imgList }">
				<div class="image-item">
					<input type="hidden" class="imgChgName" value="${ImgVO.imgChgName }">
					<img src="../image/foodImage?imgFoodId=${ImgVO.imgId }" width="100px" height="100px">
				</div>
			</c:forEach>
		</div>
		
		<div class="button-container">
			<button type="button" class="update-image">세부 사진 수정</button>
			<button type="button" class="insert-image">세부 사진 추가</button>
			<button type="button" class="submit">수정</button>
			<button type="button" class="cancel" value="back">돌아가기</button>
	</div>
		
	</form>
	
	<div class="ThumbnailVO"></div>
	<div class="ImgVOList"></div>
	<input type="file" id="thumbnail-click" style="display: none;">
	<input type="file" id="image-click" style="display: none;" multiple="multiple">
	
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
			
			function saveThumnail(file){
				
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
								 +	'<img src="../image/display?path='+ImgPath+'&chgName='+data.imgChgName+'&extension='+data.imgExtension+'"width="200px" height="200px" />'
								 + 	'</div>';
								 
								 $(".thumbnail-image").html(list);
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
				console.log("클릭함");
				$("#thumbnail-click").click();
			});
			
			$("#thumbnail-click").change(function(){
				var file = this.files;
				
				saveThumnail(file);
			})
			
			$(".thumbnail-drop").on('drop',function(event){
				event.preventDefault();
				console.log("사진 떨어뜨림");
				
				var file = event.originalEvent.dataTransfer.files;
				console.log(file);
				
				saveThumnail(file);
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
			
			$(".image-list").on('click','.image-item',function(){
                var isDelete = confirm("삭제하시겠습니까?");
                if(isDelete){
	                $(this).remove();
                    var chgName = $(this).find('.imgChgName').val();
                    
                    $(".input-image-list").each(function(){
                    	if($(this).val() == chgName){
                    		$(this).parent().remove();
                    	}
                    });
                    
                    var i = 0;
                    $(".input-image-items").each(function(){
                    	var realName	= $(this).children().eq(0).val();
                    	var chgName		= $(this).children().eq(1).val();
                    	var extension	= $(this).children().eq(2).val();
                    	var path		= $(this).children().eq(3).val();
                    	console.log(realName + " " + chgName + " " + extension + " " + path);
                    	$(this).children().eq(0).attr("name","imgList["+i+"].ImgRealName");
                    	$(this).children().eq(1).attr("name","imgList["+i+"].ImgChgName");
                    	$(this).children().eq(2).attr("name","imgList["+i+"].ImgExtension");
                    	$(this).children().eq(3).attr("name","imgList["+i+"].ImgPath");
                    	
                    	i++;
                    });
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
					
					var div		 =	$('<div>').attr('class','input-image-items');
					var realName 	= $('<input>').attr('type','hidden').attr('class','input-image-list').attr('name','imgList['+i+'].ImgRealName').attr('value',ImgVO.imgRealName);
					var chgName		= $('<input>').attr('type','hidden').attr('class','input-image-list').attr('name','imgList['+i+'].ImgChgName').attr('value',ImgVO.imgChgName);
					var extension	= $('<input>').attr('type','hidden').attr('class','input-image-list').attr('name','imgList['+i+'].ImgExtension').attr('value',ImgVO.imgExtension);
					var path		= $('<input>').attr('type','hidden').attr('class','input-image-list').attr('name','imgList['+i+'].ImgPath').attr('value',ImgVO.imgPath);
					
					div.append(realName);
					div.append(chgName);
					div.append(extension);
					div.append(path);
					
					updateForm.append(div);

					i++;
				});
						
				updateForm.submit();
			});
		});
	</script>
</body>
</html>