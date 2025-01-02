<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.7.1.js">
</script>
<meta charset="UTF-8">
<title>리뷰 작성</title>
<style>
	#img #foodImg{
		width: 450px;
		height: 300px;
	}
	#img #reviewImg{
		width: 250px;
		height: 150px;
	}
</style>
</head>
<body>
	<div id="img">
		<img src="/website/ImgFood?foodId=${foodId }" id="foodImg">
		<button onclick="location.href='../food/list'">돌아가기</button>
		<br>
		<div id="review"></div>
	</div>
	
	<div id="reviewList">
	<form action="register" method="post" enctype="multipart/form-data">
		<input type="hidden" value="${foodId }" name="foodId">
		<input type="text" readonly="readonly" value="${sessionScope.memberId }" name="memberId">
		<input type="file" required="required" name="file"><br>
		<input type="text" required="required" name="reviewTitle"><br>
		<textarea rows="15" cols="80" name="reviewContent"></textarea><br>
		<input type="number" name="reviewRating">
		<input type="submit" value="작성하기">
	</form>
	</div>
	
	<c:forEach var="reviewVO" items="${reviewList }">
		<hr>
		<p>회원 아이디 : ${reviewVO.memberId }</p>
		<p>리뷰 제목 : ${reviewVO.reviewTitle }</p>
		<p>리뷰 내용 : ${reviewVO.reviewContent }</p>
		<c:if test="${ sessionScope.memberId eq reviewVO.memberId }">
			<button id="reviewUpdate">수정</button>
			<button onclick="location.href='delete?reviewId=${reviewVO.reviewId}&foodId=${reviewVO.foodId }'" id="reviewDelete">삭제</button>
		</c:if>
	</c:forEach>
	<hr>
	
	<script type="text/javascript">
		$(document).ready(function(){
			
			// 업로드를 허용할 확장자를 정의한 정규표현식
			var accessExtensions = /\.(png|jpg|jpeg)$/i; 
					
			// 파일 전송 form validation
			$("input[type=file]").change(function(event) {
				var fileInput = $("input[name='file']"); // File input 요소 참조
				var file = fileInput.prop('files')[0]; // file 객체 참조
				var fileName = fileInput.val();	
					
				if (!file) { // file이 없는 경우
					alert("파일을 선택하세요.");
					event.preventDefault();
					return;
				}
					
				if (!accessExtensions.test(fileName)) { // 허용된 확장자가 아닌 경우
					alert("이 확장자의 파일은 등록할 수 없습니다.");
					event.preventDefault();
					$(this).val("");
					$("#review").html("");
					return;
				}
					
				var maxSize = 1 * 1024 * 1024; // 1 MB 
				if (file.size > maxSize) {
					alert("파일 크기가 너무 큽니다. 최대 크기는 1MB입니다.");
					event.preventDefault();
					$(this).val("");
					$("#review").html("");
					return;
				}
				
				changeImg(this);
			});
			
			
			function changeImg(input){
					console.log("파일이 등록되었어요");
					let fileReader = new FileReader();
					fileReader.onload = function(event){
						$('#review').html("<img src="+ event.target.result +" id='reviewImg'>");
					};
					fileReader.readAsDataURL(input.files[0]);
			}
		});
	</script>
</body>
</html>