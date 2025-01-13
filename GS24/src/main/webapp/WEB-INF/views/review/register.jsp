<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.7.1.js">
</script>
<title>리뷰 작성</title>
<style>
	/* 사용자가 이미지를 드롭할 수 있는 영역에 대한 스타일 */
.image-drop {
    width: 200px;
    height: 200px;
    border: 2px dashed grey; /* 점선 테두리 */
    margin-bottom: 20px; /* 하단 여백 */
    text-align: center; /* 텍스트 가운데 정렬 */
    line-height: 200px; /* 높이와 동일한 라인 높이 */
    font-size: 16px; /* 폰트 크기 */
    color: grey; /* 텍스트 색상 */
}

/* 업로드된 이미지를 출력하는 영역에 대한 스타일 */
.image-list {
    margin-top: 20px; /* 상단 여백 */
    background-color: #f9f9f9; /* 배경색 */
    border: 1px solid #ddd; /* 실선 테두리 */
    padding: 5px; /* 안쪽 여백 */
    margin-bottom: 20px; /* 하단 여백 */
    height: 120px; /* 높이 */
    width: 350px; /* 너비 */
}

/* 업로드된 이미지에 대한 스타일 */
.image_item {
    margin-left: 10px; /* 왼쪽 여백 */
    position: relative; /* 상대적 위치 지정 */
    display: inline-block; /* 인라인 블록으로 표시 */
    margin: 4px; /* 여백 */
}
.image_item:hover {
	border-color: blue;
	border-style: solid;
}
</style>
</head>
<body>
	<input type="hidden" class="path">
	<p>리뷰 작성</p>
	
	<form action="../review/register" method="post" id="registForm">
		<input type="hidden" name="foodId" value="${foodId }">
		<input type="hidden" name="memberId" value="test">
		<p>제목 : <p>
		<input type="text" name="reviewTitle"><br>
		<p>내용 : <p>
		<textarea rows="10" cols="40" name="reviewContent"></textarea><br>
		<p>별점 : <p>
		<input type="number" name="reviewRating">
	</form>
	
	<p>사진등록<p>
	<div class="image-drop"></div>
	<div class="image-list">
	</div><button class="cancel">취소</button>
	<br><br><button class="submit">등록</button>
	
	<div class="ImgReviewVOList"></div>
	
	<script type="text/javascript">
	$(document).ready(function(){
		// 파일 객체를 배열로 전달받아 검증하는 함수
		function validateImages(files){
			var maxSize = 10 * 1024 * 1024; // 10 MB 
			var allowedExtensions = /(\.jpg|\.jpeg|\.png|\.gif)$/i; 
			// 허용된 확장자 정규식 (jpg, jpeg, png, gif)
			
			if(files.length > 3) { // 파일 개수 제한
				alert("파일은 최대 3개만 가능합니다.");
				return false;
			}
			
			for(var i = 0; i < files.length; i++) {
				console.log(files[i]);
				var fileName = files[i].name; // 파일 이름
				var fileSize = files[i].size; // 파일 크기
				
				// 파일 크기가 설정 크기보다 크면
				if (fileSize > maxSize) {
					alert("파일의 최대 크기는 10MB입니다.");
					return false;
				}
				
				// regExp.exec(string) : 지정된 문자열에서 정규식을 사용하여 일치 항목 확인
				// 지정된 문자열이 없는 경우 true를 리턴
		        if(!allowedExtensions.exec(fileName)) {
		            alert("이 파일은 업로드할 수 없습니다. jpg, jpeg, png, gif파일만 가능합니다."); // 알림 표시
		            return false;
		        }
			}

			return true; // 모든 조건을 만족하면 true 리턴
		} // end validateImage()

		
		// 파일을 끌어다 놓을 때(drag&drop)
		// 브라우저가 파일을 자동으로 열어주는 기능을 막음
		$('.image-drop').on('dragenter dragover', function(event){
			event.preventDefault();
			console.log('drag 테스트');
		}); 
		
		$('.image-drop').on('drop', function(event){
			event.preventDefault();
			console.log('drop 테스트');
			console.log($(".image_item").length);
			if($(".image_item").length > 2){
				$('.image-list').empty(); // 기존 이미지 dto 초기화
			}
							
			// 드래그한 파일 정보를 갖고 있는 객체
			var files = event.originalEvent.dataTransfer.files;
			console.log(files);
			
			if(!validateImages(files)) { 
				return;
			}
			
			// Ajax를 이용하여 서버로 파일을 업로드
			// multipart/form-data 타입으로 파일을 업로드하는 객체
			var formData = new FormData();

			for(var i = 0; i < files.length; i++) {
				formData.append("files", files[i]); 
			}
			
			$.ajax({
				type : 'post', 
				url : '../image/review', 
				data : formData,
				processData : false,
				contentType : false,
				success : function(data) {
					var list =""
					$(data).each(function(){
						console.log(this);
						var ImgReviewVO = this;
						var ImgReviewPath = encodeURIComponent(ImgReviewVO.imgReviewPath);
						console.log(ImgReviewPath);
						var input = $('<input>').attr('type', 'hidden').attr('name', 'ImgReviewVO').attr('data-chgName', ImgReviewVO.imgReviewChgName);
						
						input.val(JSON.stringify(ImgReviewVO));
						
						$('.ImgReviewVOList').append(input);
						
						list += '<div class="image_item" data-chgName="'+ this.imgReviewChgName +'"'
							 +	'<pre>'
							 +	'<input type="hidden" id="ImgReviewPath" value="'+ ImgReviewVO.imgReviewPath +'">'
							 +	'<input type="hidden" id="ImgReviewChgName" value="'+ ImgReviewVO.imgReviewChgName +'">'
							 +	'<input type="hidden" id="ImgReviewExtension" value="'+ ImgReviewVO.imgReviewExtension +'">'
							 +	'<img src="../image/display?path='+ ImgReviewPath + "&chgName=" + ImgReviewVO.imgReviewChgName +"&extension=" + ImgReviewVO.imgReviewExtension + '" target="_blank" width="100px" height="100px" />'
							 +	'</pre>'
							 +	'</div>';
					});// end each 
					
					var htmlList = $(".image-list").html();
					htmlList = htmlList + list;
					$(".image-list").html(htmlList);
				} // end success
			}); // end $.ajax()
			
		}); // end image-drop()
		
		$(".image-list").on('click','.image_item',function(){
			console.log(this);
			var clickedItem = this;
			var isDelete = confirm("삭제하시겠습니까?");
			if(isDelete){
				
				var path = $(this).find('#ImgReviewPath').val();
				var chgName = $(this).find('#ImgReviewChgName').val();
				var extension = $(this).find('#ImgReviewExtension').val();
				
				$.ajax({
					type : 'post',
					url : '../image/delete',
					data : {'path' : path, 'chgName' : chgName, 'extension' : extension},
					success : function(result){
						if(result == 1){
							console.log("file delete success");
							$(clickedItem).remove();
							var find = $(".ImgReviewVOList").find('input[data-chgName='+chgName+']');
							$(find).remove();
						} else {
							console.log("file delete fail");
						}
					}
				});
			}
		});
		
		$(".cancel").click(function(){
			var isCancel = confirm("리뷰 작성을 취소하시겠습니까?");
			
			if(isCancel){
				var path = $(".image_item").find("input[id=ImgReviewPath]").val();
				console.log(path);
				$.ajax({
					type : 'post',
					url : '../image/cancel',
					data : {'path': path},
					success: function(result){
						$(".image-list").empty();
						$(".ImgReviewVOList").remove();
						location.href="../food/list";
					}
				});//end ajax
			}
		});// end click
		
		$(".submit").click(function(){
			var registForm = $("#registForm");
			
			var i = 0;
			$(".ImgReviewVOList input").each(function(){
				var ImgReviewVO = JSON.parse($(this).val());
				console.log(ImgReviewVO);
				
				var reviewId =	$("<input>").attr('type','hidden').attr('name','imgReviewList['+i+'].reviewId').attr('value',ImgReviewVO.reviewId);
				var realName =	$("<input>").attr('type','hidden').attr('name','imgReviewList['+i+'].ImgReviewRealName').attr('value',ImgReviewVO.imgReviewRealName);
				var chgName =	$('<input>').attr('type','hidden').attr('name','imgReviewList['+i+'].ImgReviewChgName').attr('value',ImgReviewVO.imgReviewChgName);
				var extension =	$('<input>').attr('type','hidden').attr('name','imgReviewList['+i+'].ImgReviewExtension').attr('value',ImgReviewVO.imgReviewExtension);
				var path =		$('<input>').attr('type','hidden').attr('name','imgReviewList['+i+'].ImgReviewPath').attr('value',ImgReviewVO.imgReviewPath);
				
				registForm.append(reviewId);
				registForm.append(realName);
				registForm.append(chgName);
				registForm.append(extension);
				registForm.append(path);
				
				i++;
			}); //end each
			
			registForm.submit();
		});//end click
	}); // end document
	</script>
</body>
</html>