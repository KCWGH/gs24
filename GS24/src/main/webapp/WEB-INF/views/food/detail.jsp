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
	<img src="../ImgFood?foodId=${FoodVO.foodId }">
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
	
	<c:if test="${not empty sessionScope.memberId}">
		<a href="../review/list?foodId=${FoodVO.foodId}"><button>리뷰 작성</button></a>
	</c:if>
	
	<script type="text/javascript">
	window.onload = function () {
	    pieChartDraw();
	}

	let pieChartData = {
	    labels: ["protein", "fat", "carbohydrate"],
	    datasets: [{
	        data: [${FoodVO.foodProtein},${FoodVO.foodFat},${FoodVO.foodCarb}],
	        backgroundColor: ['rgb(255,0,0)','rgb(0,255,0)','rgb(0,0,255)']
	    }] 
	};

	let pieChartDraw = function () {
	    let ctx = document.getElementById('pieChart').getContext('2d');
	    
	    window.pieChart = new Chart(ctx, {
	        type: 'pie',
	        data: pieChartData,
	        options: {
	            responsive: false
	        }
	    });
	};
	</script>
</body>
</html>