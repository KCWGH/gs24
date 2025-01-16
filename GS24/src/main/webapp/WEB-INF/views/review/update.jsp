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
	<form action="../review/update" id="updateForm" method="post">
		<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
		<p>리뷰 아이디</p><input type="number" name="reviewId" class="reviewId" value="${reviewVO.reviewId }">
		<p>식품 아이디</p><input type="number" name="foodId" value="${reviewVO.foodId }">
		<sec:authentication property="principal" var="user"/>	
		<sec:authorize access="isAuthenticated()">
		<p>회원 아이디</p><input type="text" name="memberId" value="${user.username }" readonly="readonly"><br>
		</sec:authorize>
		<p>리뷰 제목 : </p><input type="text" name="reviewTitle" value="${reviewVO.reviewTitle }">
		<p>리뷰 내용 : </p><textarea rows="5" cols="30" name="reviewContent">${reviewVO.reviewContent }</textarea>
		<p>리뷰 별점 : </p><input type="number" name="reviewRating" value="${reviewVO.reviewRating }">
		
		
		<c:forEach var="ImgReviewVO" items="${reviewVO.imgReviewList }" varStatus="status">
				<input type="hidden" class="input-image-list" name="imgReviewList[${status.index }].ImgReviewRealName" value="${ImgReviewVO.imgReviewRealName }">
				<input type="hidden" class="input-image-list" name="imgReviewList[${status.index }].ImgReviewChgName" value="${ImgReviewVO.imgReviewChgName }">
				<input type="hidden" class="input-image-list" name="imgReviewList[${status.index }].ImgReviewExtension" value="${ImgReviewVO.imgReviewExtension }">
				<input type="hidden" class="input-image-list" name="imgReviewList[${status.index }].ImgReviewPath" value=${ImgReviewVO.imgReviewPath }>
		</c:forEach>
	</form>
	
	<div class="image-drop" style="display: none;">
		드래그 해서 사진을 추가
	</div>
	
	<div class="update-image-drop" style="display: none;">
		드래그 해서 사진 수정 
	</div>
	
	<div class='image-list'>
		<c:forEach var="ImgReviewVO" items="${reviewVO.imgReviewList }">
			<div class="image-item">
				<img src="../image/reviewImage?imgReviewId=${ImgReviewVO.imgReviewId }" width="100" height="100">
			</div>
		</c:forEach>
	</div>
	
	<button class="update-image">사진 수정</button>
	<button class="insert-image">사진 추가</button>
	<button class="update">리뷰 수정</button>
	<button class="back">돌아가기</button>
	
	<div class="ImgUpdateList"></div>
	
	<div class="ImgReviewVOList"></div>
	
	<script type="text/javascript">
	 $(document).ajaxSend(function(e, xhr, opt){
         var token = $("meta[name='_csrf']").attr("content");
         var header = $("meta[name='_csrf_header']").attr("content");
         
         xhr.setRequestHeader(header, token);
      });
	
	$(document).ready(function(){
		// 파일 객체를 배열로 전달받아 검증하는 함수
		function validateImages(files){
			var maxSize = 10 * 1024 * 1024; // 10 MB 
			var allowedExtensions = /(\.jpg|\.jpeg|\.png|\.gif)$/i; 
			// 허용된 확장자 정규식 (jpg, jpeg, png, gif)
			
			console.log("files length : " +files.length);
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
			console.log($(".image-item").length);
							
			// 드래그한 파일 정보를 갖고 있는 객체
			var files = event.originalEvent.dataTransfer.files;
			console.log(files);
			
			if(!validateImages(files)) { 
				return;
			}
			
			if($(".image-item").length > 3){
				$('.image-list').empty(); // 기존 이미지 dto 초기화
			}
			
			if((files.length + $(".image-item").length) > 3){
				alert("출력할 수 있는 이미지는 최대 3개 입니다.");
				return;
			}
			
			// Ajax를 이용하여 서버로 파일을 업로드
			// multipart/form-data 타입으로 파일을 업로드하는 객체
			var formData = new FormData();
			for(var i = 0; i < files.length; i++) {
				formData.append("files", files[i]); 
			}
			formData.append("reviewId",$(".reviewId").val());
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
						
						list += '<div class="image-item" data-chgName="'+ this.imgReviewChgName +'"'
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
		
		$(".image-list").on('click','.image-item',function(){
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
			var path = $(".image-item").find("input[id=ImgReviewPath]").val();
			console.log(path);
			var buttonValue = $(this).val();
			console.log(buttonValue);
			$.ajax({
				type : 'post',
				url : '../image/remove',
				data : {'path': path},
				success: function(result){
					$(".image-list").empty();
					$(".ImgReviewVOList").remove();
				}
			});//end ajax
			
			if(buttonValue == 'cancel'){
				location.href="../food/list";
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
			
			var i = $("#updateForm").children(".input-image-list").length / 4;
			console.log(i);
			
			$(".ImgReviewVOList input").each(function(){
				var ImgReviewVO = JSON.parse($(this).val());
				
				var realName =	$('<input>').attr('type','hidden').attr('class','input-image-list').attr('name','imgReviewList['+i+'].ImgReviewRealName').attr('value',ImgReviewVO.imgReviewRealName);
				var chgName =	$('<input>').attr('type','hidden').attr('class','input-image-list').attr('name','imgReviewList['+i+'].ImgReviewChgName').attr('value',ImgReviewVO.imgReviewChgName);
				var extension =	$('<input>').attr('type','hidden').attr('class','input-image-list').attr('name','imgReviewList['+i+'].ImgReviewExtension').attr('value',ImgReviewVO.imgReviewExtension);
				var path =		$('<input>').attr('type','hidden').attr('class','input-image-list').attr('name','imgReviewList['+i+'].ImgReviewPath').attr('value',ImgReviewVO.imgReviewPath);
				
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