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
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.3/Chart.bundle.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.3/Chart.min.css">
    <title>식품 상세 정보</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .content-container {
            display: flex;
            justify-content: space-between;
            padding: 20px;
            flex-wrap: wrap;
        }
        .image-list {
            flex: 1;
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        .image-scroll {
            margin-top: 10px;
            text-align: center;
            display: flex; /* 가로로 정렬 */
            justify-content: center; /* 중앙 정렬 */
            overflow-x: auto; /* 가로 스크롤 가능 */
        }
        .image-scroll ol {
            display: flex;
            padding: 0;
            margin: 0;
            list-style-type: none;
        }
        .image-scroll li {
            margin-right: 10px; /* 이미지들 간의 간격 */
        }
        .food-details {
            flex: 1;
            margin-left: 20px;
            display: flex;
            flex-direction: column;
            align-items: flex-start;
        }
        .food-details table {
            width: 100%;
            margin-bottom: 20px;
        }
        .food-details th, .food-details td {
            padding: 10px;
            text-align: left;
        }
        .food-details th {
            width: 150px;
            text-align: center;
        }
        .food-details canvas {
            margin-top: 10px;
        }
        .subImage {
            width: 100px;
            height: 100px;
            margin: 5px;
            cursor: pointer;
            object-fit: cover;
        }
        .mainImage {
            width: 500px;
            height: 500px;
            object-fit: cover;
            margin-bottom: 10px;
        }
        #paginationList {
            display: flex;
            justify-content: center;
            list-style-type: none;
            padding: 0;
        }
        .pagination_button a {
            padding: 5px 10px;
            margin: 0 5px;
            text-decoration: none;
            background-color: #f0f0f0;
            border-radius: 5px;
            color: #333;
        }
        .pagination_button a:hover {
            background-color: #d0d0d0;
        }
        .pagination_button .active {
            background-color: #c0c0c0;
        }
        .reviewRegister {
            display: none;
        }
        @media (max-width: 768px) {
            .content-container {
                flex-direction: column;
                align-items: center;
            }
            .food-details {
                margin-left: 0;
                margin-top: 20px;
            }
            .image-list, .food-details {
                width: 100%;
                align-items: center;
            }
            .mainImage {
                width: 80%;
                height: auto;
            }
            .subImage {
                width: 50px;
                height: 50px;
            }
            .food-details table th, .food-details table td {
                font-size: 14px;
                padding: 8px;
            }
        }
    </style>
</head>
<body>
    <%@ include file="../common/header.jsp"%>
    <div class="content-container">
        <div class="image-list">
            <img id="mainImage" class="mainImage" src="../image/foodThumnail?foodId=${FoodVO.foodId}">
            <div class="image-scroll">
                <ol>
                    <li>
                        <img class="subImage" src="../image/foodThumnail?foodId=${FoodVO.foodId}">
                    </li>
                    <c:forEach var="ImgVO" items="${FoodVO.imgList}">
                        <li>
                            <img class="subImage" src="../image/foodImage?imgFoodId=${ImgVO.imgId}">
                        </li>
                    </c:forEach>
                </ol>
            </div>
        </div>
        <div class="food-details">
        <table>
        	<tr>
        		<th>식품 유형</th>
        		<td>${FoodVO.foodType }</td>
        	</tr>
        	<tr>
        		<th>식품 이름</th>
        		<td>${FoodVO.foodName }</td>
        	</tr>
        	<tr>
        		<th>재고량</th>
        		<td>${FoodVO.foodAmount }개</td>
        	</tr>
        	<tr>
        		<th>가격</th>
        		<td>${FoodVO.foodPrice }원</td>
        	</tr>
        	<tr>
        		<th>영양정보</th>
        		<td><canvas id="diagram" width="300px" height="300px"></canvas></td>
        	</tr>
        </table>
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
                <p>리뷰 제목 : ${reviewVO.reviewTitle }</p>
                <div class='imageList'>
                    <c:forEach var="ImgVO" items="${reviewVO.imgList }">
                        <input type="hidden" class="image_path" value="${ImgVO.imgPath }">
                        <img src="../image/reviewImage?imgId=${ImgVO.imgId }">
                    </c:forEach>
                </div>
                <p>회원 아이디 : ${reviewVO.memberId } 별점 : ${reviewVO.reviewRating }점</p>
                <p>리뷰 내용 : ${reviewVO.reviewContent }</p>
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
        <c:forEach begin="${pageMaker.startNum }" end="${pageMaker.endNum }" var="num">
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
            $(".subImage").on("click", function () {
                let newSrc = $(this).attr("src");
                $("#mainImage").attr("src", newSrc);
            });
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
