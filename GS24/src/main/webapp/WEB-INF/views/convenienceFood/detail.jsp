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
		width : 200px;
		height: 200px;
	}
	.thumnail{
		width : 500px;
		height: 500px;
	}
	ul {
		list-style-type: none;
		display: inline-block;
	}
	li {
		display: inline-block;
	}
	.content-container {
        display: flex; /* 가로 정렬 */
        align-items: flex-start; /* 상단 정렬 */
    }

    .image-list {
        width: 40%; /* 이미지 영역 */
        padding-right: 20px; /* 이미지와 정보 사이 간격 */
    }

    .food-details {
        width: 60%; /* 상세 정보 영역 */
    }
</style>
</head>
<body>
<%@ include file="../common/header.jsp"%>
	<div class="content-container">
    <!-- 왼쪽: 이미지 리스트 -->
    <div class="image-list">
        <ul>
            <li>
                <img class="thumnail" src="../image/foodThumnail?foodId=${FoodVO.foodId }">
            </li>
            <c:forEach var="ImgVO" items="${FoodVO.imgList }">
            <li>
                <div class="image-item">
                    <input type="hidden" class="imgChgName" value="${ImgVO.imgChgName }">
                    <img src="../image/foodImage?imgFoodId=${ImgVO.imgId }">
                </div>
            </li>
            </c:forEach>
        </ul>
    </div>

    <!-- 오른쪽: 식품 상세 정보 -->
    <div class="food-details">
        <p>식품 유형 : ${FoodVO.foodType }</p>
        <p>식품 이름 : ${FoodVO.foodName }</p>
        <p>재고량 : ${FoodVO.foodAmount }개</p>
        <p>가격 : ${FoodVO.foodPrice }원</p>

        <div id="chart">
        <p>영양 정보</p>
            <canvas id="diagram" width="300px" height="300px"></canvas>
        </div>
	</div>
    </div>
    <sec:authorize access="isAnonymous() or hasRole('ROLE_MEMBER')">
        <button onclick='location.href="../preorder/create?foodId=${FoodVO.foodId }&convenienceId=${FoodVO.convenienceId }"'>예약하기</button>
    </sec:authorize>
        <button onclick="location.href='../convenienceFood/list?convenienceId=${FoodVO.convenienceId}'">돌아가기</button>
        <button class="reviewRegister" onclick="location.href='../review/register?foodId=${FoodVO.foodId}&convenienceId=${FoodVO.convenienceId}'" style="display: none;">리뷰 작성</button>
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
				<button onclick="location.href='../review/update?reviewId=${reviewVO.reviewId}&convenienceId=${FoodVO.convenienceId }'">수정</button>
				<button id="reviewDelete">삭제</button>
			</c:if>
		</sec:authorize>
		</div>
	</c:forEach>
	<hr>
	</div>
	
	<form id="detailForm" action="detail" method="GET">
	  <input type="hidden" name="foodId" value="${FoodVO.foodId }">
      <input type="hidden" name="pageNum">
      <input type="hidden" name="pageSize">
      <input type="hidden" name="type">
      <input type="hidden" name="keyword">
   </form>
	
	 <ul id="paginationList">
         <!-- 이전 버튼 생성을 위한 조건문 -->
         <c:if test="${pageMaker.isPrev() }">
            <li class="pagination_button"><a href="${pageMaker.startNum - 1}">이전</a></li>
         </c:if>
         <!-- 반복문으로 시작 번호부터 끝 번호까지 생성 -->
         <c:forEach begin="${pageMaker.startNum }"
            end="${pageMaker.endNum }" var="num">
            <li class="pagination_button"><a href="${num }">●</a></li>
         </c:forEach>
         <!-- 다음 버튼 생성을 위한 조건문 -->
         <c:if test="${pageMaker.isNext() }">
            <li class="pagination_button"><a href="${pageMaker.endNum + 1}">다음</a></li>
         </c:if>
   </ul>
	
	
	<script type="text/javascript">
	$(document).ajaxSend(function(e, xhr, opt){
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	        
	xhr.setRequestHeader(header, token);
	});
	
	$(document).ready(function () {
	    pieChartDraw();
		
		$("#reviewList").on('click','.reviewItems #reviewDelete', function(){
			var path = $(".imageList").find('.image_path').val();
			var reviewId = $(this).prevAll(".reviewId").val();
			var foodId = ${FoodVO.foodId };
			var convenienceId = ${FoodVO.convenienceId}
			console.log("path : " + path);
			console.log("reviewId : " + reviewId);
			console.log("foodId : " + foodId);
			
			$.ajax({
				type : 'post',
				url : '../image/remove',
				data : {"path" : path},
				success : function(result){
					location.href='../review/delete?reviewId='+reviewId+'&foodId=' + foodId + '&convenienceId=' + convenienceId;
				}
			});
		});
		
		 $(".pagination_button a").on("click", function(e){
	         var detailForm = $("#detailForm"); // form 객체 참조
	         e.preventDefault(); // a 태그 이벤트 방지
	      
	         var pageNum = $(this).attr("href"); // a태그의 href 값 저장
	         // 현재 페이지 사이즈값 저장
	         var pageSize = "<c:out value='${pageMaker.pagination.pageSize }' />";
	         var type = "<c:out value='${pageMaker.pagination.type }' />";
	          
	         // 페이지 번호를 input name='pageNum' 값으로 적용
	         detailForm.find("input[name='pageNum']").val(pageNum);
	         // 선택된 옵션 값을 input name='pageSize' 값으로 적용
	         detailForm.find("input[name='pageSize']").val(pageSize);
	         
	         detailForm.submit();
	         
	      }); // end on()
		 if("${memberId}" != ""){    
			 $.ajax({
		    	 type : 'post',
		    	 url : '../preorder/pickedup',
		    	 data : {"foodId" : ${FoodVO.foodId}},
		    	 success : function(result){
		    		 console.log(result);
		    		if(result){
		    			$(".reviewRegister").show();
		    		}
		    	 }
		      });
		  }
	});
	
	let protein = ${FoodVO.foodProtein};
	let fat = ${FoodVO.foodFat};
	let carb = ${FoodVO.foodCarb};
	function pieChartDraw() {
	    let ctx = document.getElementById('diagram').getContext('2d');

	    let chart = new Chart(ctx, {
	        type: 'bar', // 세로 막대 그래프
	        data: {
	            labels: ['단백질(g)', '지방(g)', '탄수화물(g)'], // 항목 이름
	            datasets: [{
	                data: [protein, fat, carb], // 값
	                backgroundColor: ['rgba(54, 162, 235, 0.6)', 'rgba(255, 99, 132, 0.6)', 'rgba(255, 206, 86, 0.6)'],
	                borderColor: ['rgba(54, 162, 235, 1)', 'rgba(255, 99, 132, 1)', 'rgba(255, 206, 86, 1)'],
	                borderWidth: 1
	            }]
	        },
	        options: {
	            responsive: false,
	            scales: {
	                yAxes: [{
	                    ticks: {
	                        beginAtZero: true // 0부터 시작
	                    }
	                }]
	            },
	            legend: {
	                display: false // 범례 제거
	            }
	        }
	    });
	}

	</script>
	<%@ include file="../common/footer.jsp"%>
</body>
</html>