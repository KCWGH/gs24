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
<title>리뷰 수정</title>
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

/* 업로드된 이미지와 연결된 삭제 버튼에 대한 스타일 */
.image_delete {
    position: absolute; /* 절대적 위치 지정 */
    right: 5px; /* 오른쪽으로부터의 오프셋(offset) */
}
</style>
</head>
<body>
	<p>회원 아이디<p>
	<input type="text" value="${reviewVO.memberId }" readonly="readonly"><br>
	<div class="img">
		<img src='../Img/Review?reviewId=${reviewVO.reviewId }'>
	</div>
	<p>리뷰 제목 : ${reviewVO.reviewTitle }</p>
	<p>리뷰 내용 : ${reviewVO.reviewContent }</p>
	<p>리뷰 별점 : ${reviewVO.reviewRating }</p>
	<div class="image-drop"></div>
	
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
			
			$('.img').empty(); // 기존 이미지 dto 초기화
			var reviewId = ${reviewVO.reviewId};
							
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
				url : '../Image', 
				data : formData,
				processData : false,
				contentType : false,
				success : function(data) {
					console.log(data);
				} // end success
			}); // end $.ajax()
			
		}); // end image-drop()
		
	}); // end document
	</script>
</body>
</html>