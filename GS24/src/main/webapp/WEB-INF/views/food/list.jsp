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
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<title>식품 리스트</title>
<style>
#paginationList{
   list-style-type: none;
   text-align: center;
}
.food_box {
   list-style-type: none;
}

.food_box .List {
   display: inline-block;
}
img {
   width: 200px;
   height: 150px;
}
.pagination_button {   
   display: inline-block;
}
.searchList{
   list-style-type: none;
   text-align: center;
   border-top: 1px solid;
   border-bottom: 1px solid;
}
.searchList li{
   display: inline-block;
   width: 150px;
   margin-left: 20px;
   border-left: 1px solid;
   border-right:1px solid;
}
.searchList li:hover {
   background-color: silver;
}

</style>
<%
    String message = (String) session.getAttribute("message");
    if (message != null) {
        out.println("<script>alert('생일 축하 기프트카드 10000원권이 발급되었습니다.');</script>");
        session.removeAttribute("message");  // 메시지를 표시한 후 세션에서 제거
    }
%>

</head>
<body>
<c:if test="${not empty message}">
        <script type="text/javascript">
            alert("${message}");
        </script>
    </c:if>
    <%@ include file="../common/header.jsp" %>
 
   <h1>식품 리스트</h1>
   <sec:authorize access="hasRole('ROLE_OWNER')">
      <button onclick='location.href="../preorder/update"'>예약 상품 수령 확인</button>
      <button onclick='location.href="register"'>식품등록</button>
      <button onclick="window.open('../coupon/publish', '_blank', 'width=500,height=700')">쿠폰 발행</button><br>
   </sec:authorize>
      <input id="bottomPrice" type="text" value="${pageMaker.pagination.bottomPrice }">원 ~<input id="topPrice" type="text" value="${pageMaker.pagination.topPrice }">원 <button id="priceSearch">검색</button><br>
   <input class="searchFoodName" type="text" placeholder="식품 이름 검색" value="${pageMaker.pagination.keyword }">
   <button class="search">검색</button>
   <ul class="searchList">
      <li>전체</li>
      <li>최신등록순</li>
      <li>낮은가격순</li>
      <li>높은가격순</li>
   </ul>

   <ul class="food_box">
      <c:forEach var="FoodVO" items="${FoodList}">
         <li class="List">
            <input type="hidden" class="foodId" value="${FoodVO.foodId }">
            <div class="image-item">
            	<input type="hidden" class="path" value="${FoodVO.imgList[0].imgPath }">
	            <img src="../image/foodImage?imgFoodId=${FoodVO.imgList[0].imgId }">
            </div>
            <p>${FoodVO.foodType}</p>
            <p>${FoodVO.foodName}</p>
            <p>${FoodVO.foodStock}개</p>
            <p>${FoodVO.foodPrice}원</p>
            <p>${FoodVO.foodAvgRating }점</p>
            <p>리뷰 ${FoodVO.foodReviewCnt }개</P>
            <button onclick="location.href='detail?foodId=${FoodVO.foodId}'">상세 보기</button><br>
            <button onclick='location.href="../preorder/create?foodId=${FoodVO.foodId }"'>예약하기</button><br>
            <c:choose>
                <c:when test="${isAddedMap[FoodVO.foodId] == 1}">
                    <button class="deleteFavorites" data-foodId="${FoodVO.foodId}">찜 해제하기</button><br>
                </c:when>
                <c:otherwise>
                    <button class="addFavorites" data-foodId="${FoodVO.foodId}" data-foodType="${FoodVO.foodType }" data-foodName="${FoodVO.foodName }">찜하기</button><br>
                </c:otherwise>
               </c:choose>
            	<sec:authorize access="hasRole('ROLE_OWNER')">
					<button onclick="location.href='update?foodId=${FoodVO.foodId}'">식품 수정</button><br>
               		<button class="deleteFood">식품 삭제</button>
            </sec:authorize>
         </li>
      </c:forEach>
   </ul>
   
   <!-- 실제 controller로 데이터를 전송해주는 form -->
   <form id="searchForm" action="list" method="GET">
      <input type="hidden" name="pageNum">
      <input type="hidden" name="pageSize">
      <input type="hidden" name="type">
      <input type="hidden" name="keyword">
      <input type="hidden" name="sortType">
      <input type="hidden" name="bottomPrice">
      <input type="hidden" name="topPrice">
   </form>
   
   <form id="listForm" action="list" method="GET">
      <input type="hidden" name="pageNum">
      <input type="hidden" name="pageSize">
      <input type="hidden" name="type">
      <input type="hidden" name="keyword">
      <input type="hidden" name="sortType">
      <input type="hidden" name="bottomPrice">
      <input type="hidden" name="topPrice">
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
</body>

<script type="text/javascript">
   $(document).ready(function(){
	   $(document).ajaxSend(function(e, xhr, opt){
	        var token = $("meta[name='_csrf']").attr("content");
	        var header = $("meta[name='_csrf_header']").attr("content");
	        
	        xhr.setRequestHeader(header, token);
	     });
	   $(document).on('click', '.addFavorites', function(event) {
			let memberId = '${memberId}';
		    let foodId = $(this).data('foodid');
		    let foodType = $(this).data('foodtype');
		    let foodName = $(this).data('foodname');
		    
		    if (memberId === ''){
		    	alert('찜하시려면 로그인해주세요');
		    	return;
		    }
		    
		    $.ajax({
               url: '../favorites/add',
               type: 'POST',
               data: { memberId: memberId,
               		foodId: foodId,
               		foodType: foodType,
               		foodName: foodName
               },
               success: function(response) {
                   alert("찜 목록에 추가되었습니다");
                   let button = $("button[data-foodid='" + foodId + "']");
                   button.removeClass('addFavorites').addClass('deleteFavorites');
                   button.text('찜 해제하기');
               },
               error: function(xhr, status, error) {
                   let responseText = xhr.responseText;
                   if (responseText == 0) {
                      alert("찜 목록 추가에 실패했습니다");
                   } else {
                   }
               }
           });
		});
		
		$(document).on('click', '.deleteFavorites', function(event) {
			let memberId = '${memberId}';
		    let foodId = $(this).data('foodid');
		    
		    $.ajax({
               url: '../favorites/delete',
               type: 'POST',
               data: { memberId: memberId,
               		foodId: foodId
               },
               success: function(response) {
                   alert("찜 목록에서 삭제되었습니다");
                   let button = $("button[data-foodid='" + foodId + "']");
                   button.removeClass('deleteFavorites').addClass('addFavorites');
                   button.text('찜하기');
               },
               error: function(xhr, status, error) {
                   let responseText = xhr.responseText;
                   if (responseText == 0) {
                      alert("찜 목록 추가에 실패했습니다");
                   } else {
                   }
               }
           });
		});

      
      $("#searchForm button").on("click", function(e){
         var searchForm = $("#searchForm");
         e.preventDefault(); // a 태그 이벤트 방지
         
         var keywordVal = searchForm.find("input[name='keyword']").val();
         console.log(keywordVal);
         if(keywordVal == '') {
            alert('검색 내용을 입력하세요.');
            return;
         }
         
         var pageNum = 1; // 검색 후 1페이지로 고정
         // 현재 페이지 사이즈값 저장
         var pageSize = "<c:out value='${pageMaker.pagination.pageSize }' />";
          
         // 페이지 번호를 input name='pageNum' 값으로 적용
         searchForm.find("input[name='pageNum']").val(pageNum);
         // 선택된 옵션 값을 input name='pageSize' 값으로 적용
         searchForm.find("input[name='pageSize']").val(pageSize);
         searchForm.submit(); // form 전송
      }); // end on()
      
      $(".pagination_button a").on("click", function(e){
         var listForm = $("#listForm"); // form 객체 참조
         e.preventDefault(); // a 태그 이벤트 방지
      
         var pageNum = $(this).attr("href"); // a태그의 href 값 저장
         // 현재 페이지 사이즈값 저장
         var pageSize = "<c:out value='${pageMaker.pagination.pageSize }' />";
         var type = "<c:out value='${pageMaker.pagination.type }' />";
         var keyword = "<c:out value='${pageMaker.pagination.keyword }' />";
         var sortType = "<c:out value='${pageMaker.pagination.sortType}' />";
         var bottomPrice = "<c:out value='${pageMaker.pagination.bottomPrice}' />";
         var topPrice = "<c:out value='${pageMaker.pagination.topPrice}' />";
          
         // 페이지 번호를 input name='pageNum' 값으로 적용
         listForm.find("input[name='pageNum']").val(pageNum);
         // 선택된 옵션 값을 input name='pageSize' 값으로 적용
         listForm.find("input[name='pageSize']").val(pageSize);
         // type 값을 적용
         listForm.find("input[name='type']").val(type);
         // keyword 값을 적용
         listForm.find("input[name='keyword']").val(keyword);
         listForm.find("input[name='bottomPrice']").val(bottomPrice);
         listForm.find("input[name='topPrice']").val(topPrice);
         listForm.submit(); // form 전송
      }); // end on()
      
      $(".searchList").on("click", "li", function(e){
         let optionType = $(this).text();
         console.log("optionType : " + optionType);
         let searchForm = $("#searchForm");
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
         } else if(optionType == "최신등록순"){
            searchForm.find("input[name='sortType']").val("recentRegist");
         } else if(optionType == "낮은가격순"){
            searchForm.find("input[name='sortType']").val("rowPrice");
         } else if(optionType == "높은가격순"){
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
      
      $(".search").click(function(e){
         let searchFoodName = $(".searchFoodName").val();
         let searchForm = $("#searchForm");
         console.log("검색어 : " + searchFoodName);
         
         if(searchFoodName == ""){
            alert("검색할 단어를 입력해 주십시오.");
            return;
         }
         
         searchForm.find("input[name='type']").val("name");
         searchForm.find("input[name='keyword']").val(searchFoodName);
         
         e.preventDefault(); // a 태그 이벤트 방지
         
         var keywordVal = searchForm.find("input[name='keyword']").val();
         console.log(keywordVal);
         if(keywordVal == '') {
            alert('검색 내용을 입력하세요.');
            return;
         }
         
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
      
      $("#priceSearch").click(function(e){
         let searchForm = $("#searchForm");
         
         searchForm.find("input[name='type']").val("price");
         
         e.preventDefault(); // a 태그 이벤트 방지
         
         var bottomPrice = $("#bottomPrice").val();
         var topPrice = $("#topPrice").val();
         var type = "<c:out value='${pageMaker.pagination.type }' />";
         var keyword = "<c:out value='${pageMaker.pagination.keyword }' />";
         searchForm.find("input[name='bottomPrice']").val(bottomPrice);
         searchForm.find("input[name='topPrice']").val(topPrice);
         searchForm.find("input[name='type']").val(type);
         searchForm.find("input[name='keyword']").val(keyword);
         
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
      
      $(".food_box").on('click','.List .deleteFood',function(){
    	  console.log(this);
    	  	var path = $(this).prevAll('.image-item').find('.path').val();
  			var foodId = $(this).prevAll('.foodId').val();
  			console.log("path : " + path);
  			console.log("foodId : " + foodId);
  			
  			$.ajax({
  				type : 'post',
  				url : '../image/remove',
  				data : {"path" : path},
  				success : function(result){
  					location.href='../food/delete?foodId=' + foodId;
  				}
  			});
      });
   });
</script>

</html>
