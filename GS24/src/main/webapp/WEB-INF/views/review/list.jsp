<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.7.1.js">
</script>
<meta charset="UTF-8">
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<title>리뷰 작성</title>
<style>
	#img #foodImg{
		width: 450px;
		height: 300px;
	}
	#img #reviewImg, img{
		width: 250px;
		height: 150px;
	}
	#editForm{
		border-style: ridge;     
        box-sizing:content-box;
        
        background-color: silver;
		left: 300px;
		top: 200px;
		position: absolute;
	}
	#updateImg{
		width: 150px;
		height: 100px;
	}
	.reviewItems{
		position: relative;
	}
	#ratingStar {
		font-size: xx-large;
		font-weight: bold;
	}
	.spans{
	position: relative;
  	left: 50%;
 	top: 50%;
 	transform: translate(-50%, -50%);
	}
	.spans:hover {
	font-size: large;	
	color: blue;
	}
</style>
</head>
<body>
	<div id="img">
	<img src="/website/Img/Food?foodId=${foodId }" id="foodImg">
		<button onclick="location.href='../food/list'">돌아가기</button>
		<br>
		<div id="review"></div>
	</div>
	<form action="register" method="post" enctype="multipart/form-data">
		<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
		<input type="hidden" value="${foodId }" name="foodId">
		<sec:authentication property="principal" var="user"/>	
		<sec:authorize access="isAuthenticated()">
			<input type="text" readonly="readonly" value="${user.username }" name="memberId">
		</sec:authorize>
		<input type="file" required="required" name="file" id="registerFile"><br>
		<input type="text" required="required" name="reviewTitle"><br>
		<textarea rows="15" cols="80" name="reviewContent"></textarea><br>
		<input type="number" name="reviewRating" id="reviewRating">
		<div id="ratingStar"></div>
		<input type="submit" value="작성하기">
	</form>
	<div id="reviewList">
	<c:forEach var="reviewVO" items="${reviewList }">
		<div class="reviewItems">
		<hr>
		<input type="hidden" value="${reviewVO.reviewId }"/>
		<p>회원 아이디 : ${reviewVO.memberId }</p>
		<img src='../Img/Review?reviewId=${reviewVO.reviewId }'>
		<p>리뷰 제목 : ${reviewVO.reviewTitle }</p>
		<p>리뷰 내용 : ${reviewVO.reviewContent }</p>
		<p>리뷰 별점 : ${reviewVO.reviewRating }</p>
		<sec:authentication property="principal" var="user"/>	
		<sec:authorize access="isAuthenticated()">
		<c:if test="${ reviewVO.memberId eq user.username }">
			<button class="reviewUpdate">수정</button>
			<button onclick="location.href='delete?reviewId=${reviewVO.reviewId}&foodId=${reviewVO.foodId }'" id="reviewDelete">삭제</button>
		</c:if>
		</sec:authorize>
		</div>
	</c:forEach>
	<hr>
	</div>
	
	<c:if test="${pageMaker.isPrev() }">
		<a href="list?pageNum=${pageMaker.startPageNo - 1 }">이전</a>
	</c:if>

	<c:forEach begin="${pageMaker.startNum }" end="${pageMaker.endNum }" var="num">
		<span class="spans" onclick="location.href='list?foodId=${foodId }&pageNum=${num}'">${num }</span>
	</c:forEach>
	
	
	<c:if test="${pageMaker.isNext() }">
		<a href="list?pageNum=${pageMaker.endPageNo + 1 }">다음</a>
	</c:if>
	
	<div id="editForm" style="display: none;">
	
	<form action="update" method="post" enctype="multipart/form-data">
		<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
		<input type="hidden" id="reviewId" name="reviewId" />
		<sec:authentication property="principal" var="user"/>	
		<sec:authorize access="isAuthenticated()">
		<input type="hidden" name="memberId" value="${user.username }">
		</sec:authorize>
		<input type="hidden" value="${foodId }" name="foodId">
		<div id="update"></div>
		<input type="file" name="file" id="updateFile"/><br>
  		<input type="text" id="newTitle" placeholder="새로운 제목" name="reviewTitle"/><br>
  		<textarea id="newContent" rows="10" cols="40" placeholder="새로운 내용" name="reviewContent"></textarea><br>
  		<input type="number" id="newRating" placeholder="새로운 별점" name="reviewRating"/><br>
  		<input type="submit" value="저장" />
	</form>
  	<button id="cancelBtn" >취소</button>
	</div>

	<script type="text/javascript">
		$(document).ready(function(){
			
			updateReview();
			editForm();
			ratingStar($('reviewRating'));
			
			// 업로드를 허용할 확장자를 정의한 정규표현식
			var accessExtensions = /\.(png|jpg|jpeg)$/i; 
					
			// 파일 전송 form validation
			$("#registerFile").change(function(event) {
				var fileInput = $("#registerFile");
				var selector = "review";
				checkFile(fileInput,event,selector);
				changeImg(this,selector);
			});
			
			$("#updateFile").change(function(event){
				var fileInput = $("#updateFile");
				var selector = "update";
				checkFile(fileInput,event);
				changeImg(this,selector);
			});
			
			function checkFile(fileInput,event,selector){
				
				var file = fileInput.prop('files')[0]; // file 객체 참조
				var fileName = fileInput.val()
					
				if (!file) { // file이 없는 경우
					alert("파일을 선택하세요.");
					event.preventDefault();
					return;
				}
					
				if (!accessExtensions.test(fileName)) { // 허용된 확장자가 아닌 경우
					alert("이 확장자의 파일은 등록할 수 없습니다.");
					event.preventDefault();
					$(fileInput).val("");
					$("#" + selector).html("");
					return;
				}
					
				var maxSize = 1 * 1024 * 1024; // 1 MB 
				if (file.size > maxSize) {
					alert("파일 크기가 너무 큽니다. 최대 크기는 1MB입니다.");
					event.preventDefault();
					$(fileInput).val("");
					$("#" + selector).html("");
					return;
				}
			}
				
				function changeImg(input, selector){
					console.log("파일이 등록되었어요");
					let fileReader = new FileReader();
					let id = selector + "Img";
					fileReader.onload = function(event){
					$('#' + selector).html("<img src="+ event.target.result +" id="+id+">");
					};
					fileReader.readAsDataURL(input.files[0]);
			}
			
			function updateReview(){
				$("#reviewList").on('click', '.reviewItems .reviewUpdate' ,function(){
					var review = $(this).parent();
					var reviewX = review.offset().right;
					var reviewY = review.offset().top;
					
					var id = $(this).prevAll("input[type='hidden']").val();
					console.log(id);
					
					$("#editForm").css({
						'top' : reviewY,
						'left' : reviewX
					});
					
					$("#reviewDelete").prop('disabled', true);
					$("#editForm").show();
					$("#editForm").find("#reviewId").val(id);
				});
			}
			
			function editForm(){
				$("#cancelBtn").click(function(){
					$("#update").html("");
					$("#updateFile").val("");
					$("#newTitle").val("");
					$("#newContent").val("");
					$("#newRating").val("");
					
					$("#editForm").hide();
					
					$("#reviewDelete").prop('disabled',false);
				});
			}
			
			function ratingStar(inputNum){
				let num = inputNum.val();
				console.log(num);
				
				let star = $("#ratingStar").text();
				
				for(i = 0; i< num; i++){
					star += '★';
				}
				for(i = 0; i < 5-num; i++){
					star += '☆';
				}
				$("#ratingStar").text(star);
			}
		});
	</script>
</body>
</html>