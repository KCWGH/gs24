<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<title>${convenienceId}호점</title>
<style>
body {
	font-family: 'Pretendard-Regular', sans-serif;
    margin: 0;
    padding: 0;
    background-color: #f4f4f4;
    font-size: 18px;
    text-align: center;
}

h1 {
    margin-top: 100px;
}

.food_box {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    align-items: flex-start;
    gap: 20px;
    padding: 20px;
    list-style-type: none;
    margin: 0;
}

.food_box .List {
    width: 250px;
    text-align: center;
    border: 1px solid #ddd;
    padding: 10px;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    background-color: #fff;
    transition: box-shadow 0.3s ease-in-out;
}

.image-item img {
    width: 100%;
    height: auto;
    border-radius: 4px;
    cursor: pointer;
}

.button-container {
    display: flex;
    justify-content: center;
    gap: 10px;
    margin-top: 10px;
}

.button-container button {
	font-family: 'Pretendard-Regular', sans-serif;
    margin-top: 10px;
    padding: 10px 20px;
    border-radius: 4px;
    border: none;
    background-color: #ddd;
    color: black;
    cursor: pointer;
    font-size: 16px;
    text-align: center;
}

.button-container button:hover {
    background-color: #bbb;
}

.foodAvgRating {
    color: gold;
    font-size: 18px;
}

.foodAvgRating::before {
    content: "★★★★★";
    letter-spacing: 3px;
}

.foodAvgRating[data-rating="5"]::before {
    content: "★★★★★";
}

.foodAvgRating[data-rating="4"]::before {
    content: "★★★★☆";
}

.foodAvgRating[data-rating="3"]::before {
    content: "★★★☆☆";
}

.foodAvgRating[data-rating="2"]::before {
    content: "★★☆☆☆";
}

.foodAvgRating[data-rating="1"]::before {
    content: "★☆☆☆☆";
}

.foodAvgRating[data-rating="0"]::before {
    content: "☆☆☆☆☆";
}

.food-info p {
    line-height: 1.0;
}

.food-info span {
    line-height: 0.6;
}

.searchList li {
	width: 100px;
	cursor: pointer;
	display: inline-block;
}

.searchList li:hover {
	background-color: silver;
	border-radius: 5px;
}

ul {
    display: flex;
    justify-content: center;
    padding: 0;
    margin: 20px 0;
    list-style-type: none;
}

.pagination_button {
    display: inline-block;
    margin: 5px;
    text-align: center;
}

.pagination_button a {
    text-decoration: none;
    border-radius: 5px;
    color: black;
}

.button-container {
    text-align: right;
    margin-bottom: 10px;
}

.pagination_button.current a {
    background: #333;
    color: white;
}

.pagination_button span {
    color: #444;
    font-size: 20px;
}

@media screen and (max-width: 768px) {
    .pagination_button span {
        font-size: 15px;
    }
}

#search {
    padding: 10px;
    background-color: #fff;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    border-radius: 8px;
    display: inline-block;
    border: 1px solid #ddd;
    margin-top: 10px;
}

.search-box {
    display: flex;
    justify-content: flex-start;
    gap: 20px;
    width: auto;
}

.search-name, .price-range {
    flex-grow: 1;
}

.search-name {
	margin-top: 5px;
}

.searchFoodName {
	font-family: 'Pretendard-Regular', sans-serif;
    width: 71%;
    padding: 5px;
    border: 1px solid #ddd;
    border-radius: 5px;
    text-align: center;
    font-size: 18px;
}

.price-input {
	font-family: 'Pretendard-Regular', sans-serif;
    width: 41%;
    border: 1px solid #ddd;
    border-radius: 5px;
    text-align: center;
    font-size: 18px;
}

.searchFoodName:focus {
    border-color: #888;
}

.searchButton {
    padding: 7px 10px;
    font-family: 'Pretendard-Regular', sans-serif;
    font-size: 16px;
    background-color: #ddd;
    color: black;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s;
}

.initialize {
    padding: 7px 10px;
    font-family: 'Pretendard-Regular', sans-serif;
    font-size: 16px;
    background-color: #ff6666;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s;
}

.searchButton:hover {
    background-color: #bbb;
}

.initialize:hover {
    background: #ff4d4d;
}

.sort-options {
    margin-top: 20px;
}

.sort-options ul {
    display: flex;
    justify-content: center;
    gap: 20px;
    list-style-type: none;
    padding: 0;
    margin: 0;
}

.sort-options li {
	width: 120px;
	cursor: pointer;
	display: inline-block;
	font-size: 20px;
	border-radius: 5px;
}

.sort-options li:hover {
    background-color: silver;
}
</style>
</head>
<body>
<c:if test="${not empty message}">
    <script type="text/javascript">
        alert("${message}");

        var closeWindow = "${closeWindow}";
        if (closeWindow === 'true') {
            window.close();
        }
        
        window.opener.location.reload();
    </script>
</c:if>

    <%@ include file="../common/header.jsp" %>
    
   <h1>${convenienceId}호점 식품 리스트</h1>
   
	<!-- 정렬 기준 선택 -->
    <div class="sort-options">
        <ul>
            <li>최근등록순✨</li>
            <li>낮은가격순🔻</li>
            <li>높은가격순🔺</li>
            <li class="detailed-search-link">상세검색🔎</li>
        </ul>
    </div>
    
    
    
    <div id="search" style="display: none;">
    <div class="search-box">
        <!-- 가격 범위 입력 -->
        <div class="price-range">
            <input id="bottomPrice" type="text" class="price-input" value="${pageMaker.pagination.bottomPrice }"> 원 ~
            <input id="topPrice" type="text" class="price-input" value="${pageMaker.pagination.topPrice }"> 원
        </div>
    </div>

    <!-- 식품 이름 검색 -->
    <div class="search-name">
        <input class="searchFoodName" type="text" placeholder="식품 이름 검색" value="${pageMaker.pagination.keyword }"> <button class="initialize">초기화</button> <button class="searchButton">검색</button>
    </div>
	</div>

   <ul class="food_box">
    <c:forEach var="FoodVO" items="${FoodList}">
    <sec:authorize access="hasRole('ROLE_MEMBER') or !isAuthenticated()">
    <c:if test="${FoodVO.showStatus == 1 }">
        <li class="List">
            <input type="hidden" class="foodId" value="${FoodVO.foodId }">
            <div class="image-item">
                <input type="hidden" class="path" value="${FoodVO.imgPath }">
                <a onclick="location.href='detail?foodId=${FoodVO.foodId}&convenienceId=${FoodVO.convenienceId }'">
                    <img src="../image/foodThumbnail?foodId=${FoodVO.foodId }">
                </a>
            </div>
            <div class="food-info">
            <p>${FoodVO.foodName}</p>
            <p><strong style="font-size:1.5em;">${FoodVO.foodPrice}</strong><strong>원</strong></p>
            <span class="foodAvgRating" data-rating="${FoodVO.foodAvgRating}"></span><span style="color:brown; font-size:0.9em;"><strong>(${FoodVO.foodReviewCnt })</strong></span>
            </div>
                <div class="button-container">
                    <sec:authorize access="isAnonymous()">
                		<button id="needLogin">예약하기</button>
                	</sec:authorize>
                	<sec:authorize access="hasRole('ROLE_MEMBER')">
                    <button onclick="window.open('../preorder/create?foodId=${FoodVO.foodId }&convenienceId=${FoodVO.convenienceId }', '_blank', 'width=500,height=700,top=100,left=200')">예약하기</button>
                	</sec:authorize>
                    <c:choose>
                        <c:when test="${isAddedMap[FoodVO.foodId] == 1}">
                            <button class="deleteFavorites" data-foodId="${FoodVO.foodId}"
                                data-convenienceId="${FoodVO.convenienceId }">찜 해제하기</button>
                        </c:when>
                        <c:otherwise>
                            <button class="addFavorites" data-foodId="${FoodVO.foodId}"
                                data-convenienceId="${FoodVO.convenienceId }">찜하기</button>
                        </c:otherwise>
                    </c:choose>
                </div>
        </li>
    </c:if>
    </sec:authorize>
   	<sec:authorize access="hasRole('ROLE_OWNER')">
   		<li class="List">
            <input type="hidden" class="foodId" value="${FoodVO.foodId }">
            <div class="image-item">
                <input type="hidden" class="path" value="${FoodVO.imgPath }">
                <a onclick="location.href='detail?foodId=${FoodVO.foodId}&convenienceId=${FoodVO.convenienceId }'">
                    <img src="../image/foodThumbnail?foodId=${FoodVO.foodId }">
                </a>
            </div>
            <div class="food-info">
            <p>${FoodVO.foodName}</p>
            <p><strong style="font-size:1.5em;">${FoodVO.foodPrice}</strong><strong>원</strong></p>
            <span class="foodAvgRating" data-rating="${FoodVO.foodAvgRating}"></span><span style="color:brown; font-size:0.9em;"><strong>(${FoodVO.foodReviewCnt })</strong></span>
            </div>
            <div class="button-container">
            	<c:if test="${FoodVO.showStatus == 1}">
	   				<button class="HideFood">식품 숨기기</button>
            	</c:if>
            	<c:if test="${FoodVO.showStatus == 0 }">
            		<button class="HideFood">식품 보이기</button>
            	</c:if>
            </div>
        </li>
    </sec:authorize>
    </c:forEach>
</ul>
   <!-- 실제 controller로 데이터를 전송해주는 form -->
   <form id="searchForm" action="list" method="GET">
   	  <input type="hidden" name="convenienceId">
      <input type="hidden" name="pageNum">
      <input type="hidden" name="pageSize">
      <input type="hidden" name="type">
      <input type="hidden" name="keyword">
      <input type="hidden" name="sortType">
      <input type="hidden" name="bottomPrice">
      <input type="hidden" name="topPrice">
   </form>
   
   <form id="listForm" action="list" method="GET">
      <input type="hidden" name="convenienceId">
      <input type="hidden" name="pageNum">
      <input type="hidden" name="pageSize">
      <input type="hidden" name="type">
      <input type="hidden" name="keyword">
      <input type="hidden" name="sortType">
      <input type="hidden" name="bottomPrice">
      <input type="hidden" name="topPrice">
   </form>
	<c:if test="${not empty address}">
    	<span style="color: gray; font-size: 15px">${address}</span>
    </c:if>   
	<ul>
    	<c:if test="${pageMaker.isPrev()}">
        	<li class="pagination_button">
            	<a href="${pageMaker.startNum - 1}">이전</a>
        	</li>
    	</c:if>

    	<c:forEach begin="${pageMaker.startNum}" end="${pageMaker.endNum}" var="num">
        	<li class="pagination_button">
            	<c:choose>
                	<c:when test="${num == pageMaker.pagination.pageNum}">
                    	<span>●</span>
                	</c:when>
                	<c:otherwise>
                    	<a href="${num}"><span>○</span></a>
                	</c:otherwise>
            	</c:choose>
        	</li>
    	</c:forEach>

    	<c:if test="${pageMaker.isNext()}">
        	<li class="pagination_button">
            	<a href="${pageMaker.endNum + 1}">다음</a>
        	</li>
    	</c:if>
	</ul>

</body>

<script type="text/javascript">
   $(document).ready(function(){
	   $(document).ajaxSend(function(e, xhr, opt){
	        var token = $("meta[name='_csrf']").attr("content");
	        var header = $("meta[name='_csrf_header']").attr("content");
	        
	        xhr.setRequestHeader(header, token);
	     });
	   
	   $(document).on('click', '#needLogin', function(event) {
		   alert('예약하시려면 로그인해주세요');
	   });
	   
	   $(document).on('click', '.addFavorites', function(event) {
		    let memberId = '${memberId}';
		    let foodId = $(this).data('foodid');
		    let convenienceId = $(this).data('convenienceid');

		    if (memberId === '') {
		        alert('찜하시려면 로그인해주세요');
		        return;
		    }

		    $.ajax({
		        url: '../favorites/add',
		        type: 'POST',
		        data: {
		            memberId: memberId,
		            foodId: foodId,
		            convenienceId: convenienceId
		        },
		        success: function(response) {
		            alert("찜 목록에 추가되었습니다");

		            let button = $("button[data-foodid='" + foodId + "']");
		            
		            button.removeClass('addFavorites')
		                  .addClass('deleteFavorites')
		                  .text('찜 해제하기')
		        },
		        error: function(xhr, status, error) {
		            let responseText = xhr.responseText;
		            if (responseText == 0) {
		                alert("찜 목록 추가에 실패했습니다");
		            }
		        }
		    });
		});

		
	   $(document).on('click', '.deleteFavorites', function(event) {
		    let memberId = '${memberId}';
		    let foodId = $(this).data('foodid');
		    let convenienceId = $(this).data('convenienceid');

		    $.ajax({
		        url: '../favorites/delete',
		        type: 'POST',
		        data: {
		            memberId: memberId,
		            foodId: foodId,
		            convenienceId: convenienceId
		        },
		        success: function(response) {
		            alert("찜 목록에서 삭제되었습니다");

		            let button = $("button[data-foodid='" + foodId + "']");

		            button.removeClass('deleteFavorites')
		                  .addClass('addFavorites')
		                  .text('찜하기')
		        },
		        error: function(xhr, status, error) {
		            let responseText = xhr.responseText;
		            if (responseText == 0) {
		                alert("찜 목록 삭제에 실패했습니다");
		            }
		        }
		    });
		});
      
      $(".pagination_button a").on("click", function(e){
         var listForm = $("#listForm"); // form 객체 참조
         e.preventDefault(); // a 태그 이벤트 방지
      
         var convenienceId = '${convenienceId}';
         var pageNum = $(this).attr("href"); // a태그의 href 값 저장
         // 현재 페이지 사이즈값 저장
         var pageSize = "<c:out value='${pageMaker.pagination.pageSize }' />";
         var type = "<c:out value='${pageMaker.pagination.type }' />";
         var keyword = "<c:out value='${pageMaker.pagination.keyword }' />";
         var sortType = "<c:out value='${pageMaker.pagination.sortType}' />";
         var bottomPrice = "<c:out value='${pageMaker.pagination.bottomPrice}' />";
         var topPrice = "<c:out value='${pageMaker.pagination.topPrice}' />";
          
         // 페이지 번호를 input name='pageNum' 값으로 적용
         listForm.find("input[name='convenienceId']").val(convenienceId);
         listForm.find("input[name='pageNum']").val(pageNum);
         // 선택된 옵션 값을 input name='pageSize' 값으로 적용
         listForm.find("input[name='pageSize']").val(pageSize);
         // type 값을 적용
         listForm.find("input[name='type']").val(type);
         // keyword 값을 적용
         listForm.find("input[name='keyword']").val(keyword);
         listForm.find("input[name='sortType']").val(sortType);
         listForm.find("input[name='bottomPrice']").val(bottomPrice);
         listForm.find("input[name='topPrice']").val(topPrice);
         listForm.submit(); // form 전송
      }); // end on()
      
      $(".sort-options").on("click", "li", function(e){
         let optionType = $(this).text();
         console.log("optionType : " + optionType);
         let searchForm = $("#searchForm");
         var convenienceId = '${convenienceId}';
         searchForm.find("input[name='convenienceId']").val(convenienceId);
         if(optionType=="전체"){
            searchForm.find("input[name='sortType']").val("All");
            var pageNum = 1; // 검색 후 1페이지로 고정
            // 현재 페이지 사이즈값 저장
            var pageSize = "<c:out value='${pageMaker.pagination.pageSize }' />";
            // 페이지 번호를 input name='pageNum' 값으로 적용
            searchForm.find("input[name='pageNum']").val(pageNum);
            // 선택된 옵션 값을 input name='pageSize' 값으로 적용
            searchForm.find("input[name='pageSize']").val(pageSize);
            searchForm.find("input[name='type']").val("");
            searchForm.find("input[name='keyword']").val("");
            searchForm.submit(); // form 전송
            return;
         } else if(optionType == "최근등록순✨"){
            searchForm.find("input[name='sortType']").val("recentRegist");
         } else if(optionType == "낮은가격순🔻"){
            searchForm.find("input[name='sortType']").val("rowPrice");
         } else if(optionType == "높은가격순🔺"){
            searchForm.find("input[name='sortType']").val("topPrice");
         }
         
         console.log(searchForm.find("input[name='sortType']").val());
         
         var pageNum = 1; // 검색 후 1페이지로 고정
         // 현재 페이지 사이즈값 저장
         var pageSize = "<c:out value='${pageMaker.pagination.pageSize }' />";
         var type = "<c:out value='${pageMaker.pagination.type }' />";
         var keyword = "<c:out value='${pageMaker.pagination.keyword }' />";
         var bottomPrice = "<c:out value='${pageMaker.pagination.bottomPrice }' />";
         var topPrice = "<c:out value='${pageMaker.pagination.topPrice }' />";
          
         // 페이지 번호를 input name='pageNum' 값으로 적용
         searchForm.find("input[name='pageNum']").val(pageNum);
         // 선택된 옵션 값을 input name='pageSize' 값으로 적용
         searchForm.find("input[name='pageSize']").val(pageSize);
         searchForm.find("input[name='type']").val(type);
         searchForm.find("input[name='keyword']").val(keyword);
         searchForm.find("input[name='bottomPrice']").val(bottomPrice);
         searchForm.find("input[name='topPrice']").val(topPrice);
         searchForm.submit(); // form 전송
      });
      
      $(".searchButton").click(function(e){
         e.preventDefault(); // a 태그 이벤트 방지
         
         let searchForm = $("#searchForm");

         let searchFoodName = $(".searchFoodName").val();
         
         var bottomPrice = $("#bottomPrice").val();
         var topPrice = $("#topPrice").val();
         
         var convenienceId = '${convenienceId}';
         searchForm.find("input[name='convenienceId']").val(convenienceId);
         
         searchForm.find("input[name='keyword']").val(searchFoodName);
         searchForm.find("input[name='topPrice']").val(topPrice);
         searchForm.find("input[name='bottomPrice']").val(bottomPrice);
               
         var pageNum = 1; // 검색 후 1페이지로 고정
         // 현재 페이지 사이즈값 저장
         var pageSize = "<c:out value='${pageMaker.pagination.pageSize }' />";
         var sortType = "<c:out value='${pageMaker.pagination.sortType }' />";
          
         // 페이지 번호를 input name='pageNum' 값으로 적용
         searchForm.find("input[name='pageNum']").val(pageNum);
         // 선택된 옵션 값을 input name='pageSize' 값으로 적용
         searchForm.find("input[name='pageSize']").val(pageSize);
         searchForm.find("input[name='sortType']").val(sortType);

         searchForm.submit(); // form 전송
      });
      
      $(".food_box").on('click','.List .HideFood',function(){
    	  let foodId = $(this).parent().siblings().eq(0).val();
    	  console.log(foodId);
    	  let convenienceId = ${param.convenienceId};
    	  console.log(convenienceId);
    	  
    	  location.href="updateShowStatus?foodId=" + foodId + "&convenienceId=" + convenienceId;
      });
      
      $(".detailed-search-link").click(function(event) {
    	    event.stopPropagation();

    	    $("#search").toggle();

    	    var currentText = $(this).text();
    	    if (currentText === '상세검색🔎') {
    	        $(this).text('상세검색📖');
    	    } else {
    	        $(this).text('상세검색🔎');
    	    }
    	});
      
      $(".searchFoodName").keypress(function(e) {
          if (e.which == 13) {
              e.preventDefault();
              $(".searchButton").click();
          }
      });
      
      $(document).on('click', '.initialize', function(event) {
          $("#bottomPrice").val('');
          $("#topPrice").val('');
          $(".searchFoodName").val('');
      });
   });
</script>
<%@ include file="../common/footer.jsp"%>
</html>
