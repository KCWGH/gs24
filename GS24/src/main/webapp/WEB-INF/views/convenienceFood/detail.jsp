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
    <title>${FoodVO.foodName }</title>
<style>
body {
	font-family: 'Pretendard-Regular', sans-serif;
	padding: 15px 300px;
	font-size: 18px;
}

h1 {
	color: #333;
	text-align: center;
}

h3 {
	color: #333;
}

a {
    text-decoration: none;
    font-family: 'Pretendard-Regular', sans-serif;
    font-size: 18px;
    color: inherit;
}

.content-container {
	display: flex;
	flex-wrap: wrap;
	justify-content: center;
	align-items: center;
	text-align: center;
	width: 100%;
	max-width: 1200px;
	margin: 0 auto;
	margin-bottom: 30px;
}

.image-list {
	flex: 6;
	display: flex;
	flex-direction: column;
	align-items: center;
}

.image-scroll {
	margin-top: 10px;
	text-align: center;
	display: flex;
	justify-content: center;
	overflow-x: hidden;
}

.image-scroll ol {
	display: flex;
	padding: 0;
	margin: 0;
	list-style-type: none;
}

.image-scroll li {
	margin-right: 10px;
}

.food-details {
	flex: 4;
	vertical-align: middle;
	margin-top: 50px;
}

.food-details table {
	width: 70%;
}

.food-details th, .food-details td {
	padding: 10px;
	text-align: center;
}

.food-details th {
	width: 40%;
}

.food-details td {
	width: 40%;
	vertical-align: middle;
}

.food-details td canvas {
	display: block;
	margin: 0 auto;
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

.pagination_button {
	display: inline-block;
	margin: 5px;
}

.pagination_button a {
	text-decoration: none;
	padding: 5px 10px;
	border-radius: 5px;
	color: black;
}

.pagination_button a:hover {
	background: #bbb;
}

.button-container {
	text-align: right;
	margin-bottom: 10px;
}

.pagination_button.current a {
	background: #333;
	color: white;
}

.reviewRegister {
	display: none;
}

.imageList img {
	width: 150px;
	height: 150px;
	margin: 5px;
	cursor: pointer;
	object-fit: cover;
}

@media ( max-width : 992px) {
	.content-container {
		flex-direction: column;
		width: 100%;
		max-width: 100%;
	}
	.food-details {
		width: 80%;
	}
	.mainImage {
		width: 100%;
		max-width: 100%;
		height: auto;
	}
	.image-scroll {
		width: 100%;
		overflow-x: hidden;
	}
	.subImage {
		width: 80px;
		height: 80px;
	}
	#diagram {
		width: 100%;
		max-width: 300px;
		height: auto;
	}
	.food-details table {
		width: 100%;
	}
	body {
		padding: 15px;
	}
}

.reviewItems .reviewRating {
	color: gold;
	font-size: 18px;
}

.reviewRating::before {
	content: "★★★★★";
	letter-spacing: 3px;
}

.reviewRating[data-rating="5"]::before {
	content: "★★★★★";
}

.reviewRating[data-rating="4"]::before {
	content: "★★★★☆";
}

.reviewRating[data-rating="3"]::before {
	content: "★★★☆☆";
}

.reviewRating[data-rating="2"]::before {
	content: "★★☆☆☆";
}

.reviewRating[data-rating="1"]::before {
	content: "★☆☆☆☆";
}

.reviewRating[data-rating="0"]::before {
	content: "☆☆☆☆☆";
}

.function {
	text-align: center;
}

.function button {
	font-family: 'Pretendard-Regular', sans-serif;
	display: inline-block;
	margin: 10px;
	padding: 10px 20px;
	border-radius: 4px;
	border: none;
	background-color: #ddd;
	color: black;
	cursor: pointer;
	font-size: 18px;
}

.function button:hover {
	background-color: #bbb;
}

.review-buttons button {
	font-family: 'Pretendard-Regular', sans-serif;
	padding: 5px 10px;
	border-radius: 4px;
	border: none;
	background-color: #ddd;
	color: black;
	cursor: pointer;
	font-size: 12px;
}

.review-buttons button:hover {
	background-color: #bbb;
}

#preorder, #needLogin {
	font-family: 'Pretendard-Regular', sans-serif;
	padding: 10px 30px;
	background-color: #4CAF50 !important;
	color: white !important;
}

#preorder:hover, #needLogin:hover {
	background: #388E3C !important;
}

hr {
	border: none;
	height: 1px;
	background-color: #ccc;
}

.comments {
    border: none;
    height: 1px;
    background-color: transparent;
    border-top: 1px dashed #aaa;
}

.recommendedFoodsContainer {
	display: flex;
	flex-wrap: wrap;
	gap: 20px;
}

.recommendedFoodsContainer {
	display: flex;
	justify-content: space-between;
	flex-wrap: wrap;
	gap: 20px;
	padding: 0px 50px;
}

.recommendedFoods {
	flex: 0 1 200px;
	text-align: center;
	margin: 10px;
}

.recommendedFoodImage {
	width: 200px;
	height: 200px;
	object-fit: cover;
}

.recommendedFoodName {
	font-size: 14px;
}
</style>

<script type="text/javascript">
    $(document).ajaxSend(function (e, xhr, opt) {
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        xhr.setRequestHeader(header, token);
    });
    
    function formatDate(timestamp) {
        var date = new Date(timestamp);
        var year = date.getFullYear();
        var month = ('0' + (date.getMonth() + 1)).slice(-2);
        var day = ('0' + date.getDate()).slice(-2);
        var hours = ('0' + date.getHours()).slice(-2);
        var minutes = ('0' + date.getMinutes()).slice(-2);
        return year + "-" + month + "-" + day + " " + hours + ":" + minutes;
    }

    $(document).ready(function () {
 	   $(document).on('click', '#needLogin', function(event) {
		    var isConfirmed = confirm("예약하시려면 로그인이 필요합니다.\n로그인 페이지로 이동할까요?");
		    if (isConfirmed) {
		        window.location.href = '../auth/login';
		    }
		});
    	
        $(".subImage").on("click", function () {
            let newSrc = $(this).attr("src");
            $("#mainImage").attr("src", newSrc);
        });

        pieChartDraw();

        $("#reviewList").on("click", ".reviewItems #reviewDelete", function () {
            var path = $(".imageList").find(".image_path").val();
            var reviewId = $(this).closest('.reviewItems').find('.reviewId').val();
            var foodId = ${FoodVO.foodId};
            var convenienceId = ${FoodVO.convenienceId};

            $.ajax({
                type: "post",
                url: "../image/remove",
                data: { "path": path },
                success: function (result) {
                    location.href = "../review/delete?reviewId=" + reviewId + "&foodId=" + foodId + "&convenienceId=" + convenienceId;
                }
            });
        });

        $(".pagination_button a").on("click", function (e) {
            var detailForm = $("#detailForm");
            e.preventDefault();

            var pageNum = $(this).attr("href");
            var pageSize = "<c:out value='${pageMaker.pagination.pageSize }' />";
            var type = "<c:out value='${pageMaker.pagination.type }' />";

            detailForm.find("input[name='pageNum']").val(pageNum);
            detailForm.find("input[name='pageSize']").val(pageSize);
            
            var loginUser = "";
            var convenienceId = ${FoodVO.convenienceId};
            
            $.ajax({
            	type : "POST",
            	url : "../review/user",
            	success : function(result){
            		if(result != null)
            			loginUser = result;
            	}
            });

            $.ajax({
                type: "POST",
                url: "../review/list",
                data: { "foodId": ${FoodVO.foodId}, "pageNum": pageNum, "pageSize": pageSize },
                success: function (result) {
                    let list = '';
                    $(result).each(function () {
                    	let formattedDate = formatDate(this.reviewDateCreated);
                        list += "<div class='reviewItems'>"
                            + "<input type='hidden' class='reviewId' value='" + this.reviewId + "'/>"
                            + "<p><strong>" + this.memberId + "</strong></p>"
                            + "<p class='reviewRating' data-rating='" + this.reviewRating + "'> " + "<span style='color:gray; font-size:14px;'>" + formattedDate + "</span>" + "</p>"
                            + "<div class='imageList'>";

                        $(this.imgList).each(function () {
                            list+= "<input type='hidden' class='image_path' value='" + this.imgPath + "'>"
                                + "<img src='../image/reviewImage?imgId=" + this.imgId + "'>";
                        });

                        list+= "</div>"
                            + "<p><strong>" + this.reviewTitle + "</strong></p>"
                            + "<p>" + this.reviewContent + "</p>";
                        
                            	list += "<sec:authorize access='isAuthenticated()'>"
                            		 +  "<c:if test='${ reviewVO.memberId eq user.nickname}'>"
                            		 + "<div class=review-buttons>" 
                            		 + "<button onclick=\"location.href=\'../review/update?reviewId="+this.reviewId+"&convenienceId="+convenienceId+"\"\'>수정</button> "
                            		 + "<button id='reviewDelete'>삭제</button>"
                            		 + "</div>"
                            		 + "</c:if>"
                                  	 + "</sec:authorize>";
                            
                        list+= "</div>"
                            + '<hr class="comments">';
                    });

                    $("#reviewList").html(list);
                }
            });
        });
    });

    let protein = ${FoodVO.foodProtein};
    let fat = ${FoodVO.foodFat};
    let carb = ${FoodVO.foodCarb};

    function pieChartDraw() {
        let ctx = document.getElementById("diagram").getContext("2d");

        let chart = new Chart(ctx, {
            type: "bar",
            data: {
                labels: ["단백질(g)", "지방(g)", "탄수화물(g)"],
                datasets: [{
                    data: [protein, fat, carb],
                    backgroundColor: ["rgba(54, 162, 235, 0.6)", "rgba(255, 99, 132, 0.6)", "rgba(255, 206, 86, 0.6)"],
                    borderColor: ["rgba(54, 162, 235, 1)", "rgba(255, 99, 132, 1)", "rgba(255, 206, 86, 1)"],
                    borderWidth: 1
                }]
            },
            options: {
                responsive: false,
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero: true
                        }
                    }]
                },
                legend: {
                    display: false
                }
            }
        });
    }
</script>

</head>
<body>
    <%@ include file="../common/header.jsp"%>
    <h1>${FoodVO.foodName }</h1>
    <div class="content-container">
        <div class="image-list">
            <img id="mainImage" class="mainImage" src="../image/foodThumbnail?foodId=${FoodVO.foodId}">
            <div class="image-scroll">
                <ol>
                    <li>
                        <img class="subImage" src="../image/foodThumbnail?foodId=${FoodVO.foodId}">
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
        		<th>재고량</th>
        		<td>${FoodVO.foodAmount }개</td>
        	</tr>
        	<tr>
        		<th>가격</th>
        		<td>${FoodVO.foodPrice }원</td>
        	</tr>
        	<tr>
        		<td colspan="2">
        		</td>
        	</tr>
				<tr>
					<td colspan="2" class="function" style="text-align: center;">
					<strong>영양정보</strong><br><br><canvas id="diagram" width="300px" height="300px"></canvas><br>
						<sec:authorize access="isAnonymous()">
                		<button id="needLogin">예약하기</button>
                	</sec:authorize>
                	<sec:authorize access="hasRole('ROLE_MEMBER')">
						<button id="preorder" onclick="window.open('../preorder/create?foodId=${FoodVO.foodId }&convenienceId=${FoodVO.convenienceId }', '_blank', 'width=500,height=710,top=100,left=200')">예약하기</button>
					</sec:authorize>
						<button onclick="location.href='../convenienceFood/list?convenienceId=${FoodVO.convenienceId}'">돌아가기</button>
					</td>
				</tr>
			</table>
    	</div>
    </div>  
    <hr>
		<h3>이런 상품은 어떠세요?</h3>
	<div class="recommendedFoodsContainer">
	    <c:choose>
	        <c:when test="${empty recommendation}">
	            <p style="text-align: center; width: 100%;">추천 상품이 없습니다.</p>
	        </c:when>
	        <c:otherwise>
	            <c:forEach var="recommendedFoodVO" items="${recommendation }">
	                <div class="recommendedFoods">
	                	<a href="../convenienceFood/detail?foodId=${recommendedFoodVO.foodId }&convenienceId=${convenienceId }">
	                    <img class="recommendedFoodImage" src="../image/foodThumbnail?foodId=${recommendedFoodVO.foodId }">
	                    <span class="recommendedFoodName">${recommendedFoodVO.foodName }</span></a>
	                </div>
	            </c:forEach>
	        </c:otherwise>
	    </c:choose>
	</div>

    <hr>
    	<h3>${FoodVO.foodName } 상품평</h3>
    <div id="reviewList">
        <c:forEach var="reviewVO" items="${reviewList }">
            <div class="reviewItems">
                <input type="hidden" class="reviewId" value="${reviewVO.reviewId }"/>
                	<fmt:formatDate value="${reviewVO.reviewDateCreated}" pattern="yyyy-MM-dd HH:mm" var="reviewDateCreated" />
                <p><strong>${reviewVO.memberId }</strong></p>
                <p class="reviewRating" data-rating="${reviewVO.reviewRating}"> <span style="color:gray; font-size:14px;">${reviewDateCreated }</span></p>
                <div class='imageList'>
                    <c:forEach var="ImgVO" items="${reviewVO.imgList }">
                        <input type="hidden" class="image_path" value="${ImgVO.imgPath }">
                        <img src="../image/reviewImage?imgId=${ImgVO.imgId }">
                    </c:forEach>
                </div>
                <p><strong>${reviewVO.reviewTitle }</strong></p>
                <p>${reviewVO.reviewContent }</p>
                <sec:authentication property="principal" var="user"/>
                <sec:authorize access="isAuthenticated()">
                    <c:if test="${ reviewVO.memberId eq user.nickname}">
                    <div class=review-buttons>
                        <button onclick="location.href='../review/update?reviewId=${reviewVO.reviewId}&convenienceId=${FoodVO.convenienceId }'">수정</button>
                        <button id="reviewDelete">삭제</button>
                    </div>
                    </c:if>
                </sec:authorize>
            </div>
        <hr class="comments">
        </c:forEach>
    </div>
    
    <form id="detailForm" action="detail" method="GET">
    	<input type="hidden" name="convenienceId" value="${convenienceId }">
        <input type="hidden" name="foodId" value="${FoodVO.foodId }">
        <input type="hidden" name="pageNum">
        <input type="hidden" name="pageSize">
    </form>
    
    <ul id="paginationList">
        <c:if test="${pageMaker.isPrev()}">
            <li class="pagination_button"><a href="${pageMaker.startNum - 1}">이전</a></li>
        </c:if>
        <c:forEach begin="${pageMaker.startNum}" end="${pageMaker.endNum}" var="num">
    		<li class="pagination_button <c:if test='${num == pageMaker.pagination.pageNum}'>current</c:if>">
        		<a href="${num}">${num}</a>
    		</li>
		</c:forEach>
        <c:if test="${pageMaker.isNext()}">
            <li class="pagination_button"><a href="${pageMaker.endNum + 1}">다음</a></li>
        </c:if>
    </ul>
    <%@ include file="../common/footer.jsp"%>
</body>
</html>
