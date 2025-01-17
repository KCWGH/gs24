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
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.3/Chart.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.3/Chart.min.css">
<title>식품 상세 정보</title>
<style>
	img{
		width : 300px;
		height: 200px;
	}
</style>
</head>
<body>
	<!-- 상품 이미지도 같이 넣어줘야 한다. -->
	<img src="../image/foodThumnail?foodId=${FoodVO.foodId }">
	<div class="image-list">
		<c:forEach var="ImgVO" items="${FoodVO.imgList }">
			<div class="image-item">
				<input type="hidden" class="imgChgName" value="${ImgVO.imgChgName }">
				<img src="../image/foodImage?imgFoodId=${ImgVO.imgId }">
			</div>
		</c:forEach>
	</div>
	<p>식품 유형 : ${FoodVO.foodType }</p>
	<p>식품 이름 : ${FoodVO.foodName }</p>
	<p>재고량 : ${FoodVO.foodStock }개</p>
	<p>가격 : ${FoodVO.foodPrice }원</p>
	<p>입고 날짜 : ${FoodVO.registeredDate }</p><br>
	<p>영양 정보</p>
	<div id="chart">
		<canvas id="pieChart" width="300px" height="300px"></canvas>
	</div>

	<button onclick="location.href='../food/list'">돌아가기</button>

	<a href="../review/register?foodId=${FoodVO.foodId}"><button>리뷰 작성</button></a>
	
	<div id="reviewList">
	<c:forEach var="reviewVO" items="${reviewList }">
		<div class="reviewItems">
		<hr>
		<input type="hidden" class="reviewId" value="${reviewVO.reviewId }"/>
		<p>회원 아이디 : ${reviewVO.memberId }</p>
		<div class='imageList'>
			<c:forEach var="ImgVO" items="${reviewVO.imgList }">
				<input type="hidden" class="image_path" value="${ImgVO.imgPath }">
				<img src="../image/reviewImage?imgId=${ImgVO.imgId }">
			</c:forEach>
		</div>
		<p>리뷰 제목 : ${reviewVO.reviewTitle }</p>
		<p>리뷰 내용 : ${reviewVO.reviewContent }</p>
		<p>리뷰 별점 : ${reviewVO.reviewRating }</p>
		<sec:authentication property="principal" var="user"/>
		<sec:authorize access="isAuthenticated()">
			<c:if test="${ reviewVO.memberId eq user.username}">
				<button onclick="location.href='../review/update?reviewId=${reviewVO.reviewId}'">수정</button>
				<button id="reviewDelete">삭제</button>
			</c:if>
		</sec:authorize>
		</div>
	</c:forEach>
	<hr>
	</div>
	
	<script type="text/javascript">
	$(document).ready(function () {
	    pieChartDraw();
	    
	    console.log($(".image-list .image-item").length);
		$(".image-list .image-item").each(function(){
			var chgName = $(this).find(".imgChgName").val();
			console.log(chgName);
			if(chgName.startsWith("t_")){
				$(this).remove();
			}
		});
	    
	});
	
	let protein = ${FoodVO.foodProtein};
	let fat = ${FoodVO.foodFat};
	let carb = ${FoodVO.foodCarb};
	let pieChartData = {
	    labels: ["protein", "fat", "carbohydrate"],
	    datasets: [{
	        data: [protein, fat, carb],
	        backgroundColor: ['rgb(255,0,0)', 'rgb(0,255,0)', 'rgb(0,0,255)']
	    }]
	};

	let pieChartDraw = function () {
	    let ctx = $('#pieChart')[0].getContext('2d');  // jQuery를 사용하여 요소를 찾고, getContext 호출

	    window.pieChart = new Chart(ctx, {
	        type: 'pie',
	        data: pieChartData,
	        options: {
	            responsive: false
	        }
	    });
	};

	
	$("#reviewList").on('click','.reviewItems #reviewDelete', function(){
		var path = $(".imageList").find('.image_path').val();
		var reviewId = $(this).prevAll(".reviewId").val();
		var foodId = ${FoodVO.foodId };
		console.log("path : " + path);
		console.log("reviewId : " + reviewId);
		console.log("foodId : " + foodId);
		
		$.ajax({
			type : 'post',
			url : '../image/remove',
			data : {"path" : path},
			success : function(result){
				location.href='../review/delete?reviewId='+reviewId+'&foodId=' + foodId;
			}
		});
	}); 
	</script>
</body>
</html>