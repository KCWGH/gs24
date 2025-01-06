<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
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
#customSelect{
	width: 100px;
}
#customSelect #selectType{
	border: 2px solid;
	display: flex;
	justify-content: space-between;
}
#selectOption{
	border: 2px solid;
	background-color: silver;
}
#customSelect ul{
	display: initial;
	padding-left: 0px;
}
#customSelect li{
	display: inline-block;
	width: 90px;
	padding: 8px 5px;
}
#customSelect li:hover{
	background-color: gray;
}
#optionName{
	position:absolute;
	width:200px;
	height:100px;
	background-color: silver;
}
#optionPrice{
	position:absolute;
	width:200px;
	height:100px;
	background-color: silver;
}
#optionRating{
	position:absolute;
	width:200px;
	height:100px;
	background-color: silver;
}
#optionReviewCnt{
	position:absolute;
	width:200px;
	height:100px;
	background-color: silver;
}
</style>

</head>
<body>
	<c:if test="${not empty memberVO}">
		<span>환영합니다, ${memberId}님</span>
		<button onclick="window.open('../member/mypage', '_blank', 'width=500,height=700')">마이페이지</button>
		<a href="../member/logout"><button>로그아웃</button></a>
		<button onclick='location.href="../preorder/list"'>예약 식품 목록</button>
		<button onclick="window.open('../coupon/list', '_blank', 'width=500,height=700')">쿠폰 목록</button>
	</c:if>

	<c:if test="${empty memberVO}">
		<button onclick="window.open('../member/login', '_blank', 'width=550,height=400')">로그인</button>
	</c:if>
		<a href="../notice/list"><button>공지사항</button></a>
		<a href="../question/list"><button>문의사항(QnA)</button></a>
	<hr>

   <h1>식품 리스트</h1>
   <c:if test="${memberVO.memberRole == 2 }">
      <button onclick='location.href="register"'>식품등록</button>
   </c:if>

	<ul class="food_box">
		<c:forEach var="FoodVO" items="${FoodList}">
			<li class="List">
				<img src="../Img/Food?foodId=${FoodVO.foodId }">
				<p>${FoodVO.foodType}</p>
				<p>${FoodVO.foodName}</p>
				<p>${FoodVO.foodStock}개</p>
				<p>${FoodVO.foodPrice}원</p>
				<p>${FoodVO.foodAvgRating }점</p>
				<p>리뷰 ${FoodVO.foodReviewCnt }개</P>
				<button onclick="location.href='detail?foodId=${FoodVO.foodId}'">상세 보기</button><br>
				<button onclick='location.href="../preorder/register?foodId=${FoodVO.foodId }"'>예약하기</button><br>
				<c:if test="${memberVO.memberRole == 2 }">
					<button onclick="location.href='update?foodId=${FoodVO.foodId}'">식품 수정</button><br>
					<button onclick="location.href='delete?foodId=${FoodVO.foodId}'">식품 삭제</button>
				</c:if>
			</li>
		</c:forEach>
	</ul>
	
	<!-- 실제 controller로 데이터를 전송해주는 form -->
	<form id="searchForm" action="list" method="GET">
		<input type="hidden" name="pageNum">
		<input type="hidden" name="pageSize">
		<input type="hidden" name="type">
		<input type="hidden" name="keyword">
		<button>검색</button>
	</form>
	
	<!-- 검색 조건 유형들 -->
	<div id="customSelect">
	<div id="selectType">All</div>
	<div id="selectOption" style="display: none;">
		<ul>
			<li>All</li>
			<li value="type">type</li>
			<li value="name">name</li>
			<li value="price">price</li>
			<li value="rating">rating</li>
			<li value="review_cnt">reviewCnt</li>
		</ul>
	</div>
	</div>
	
	<!-- 각 유형들 클릭 했을 때 검색 keyword를 저장하는 div들 -->
	<div id="optionName" style="display: none">
	<p>검색할 이름을 작성하세요</p>
	<input type="text" name="searchName">
	</div>
	
	<div id="optionPrice" style="display: none">
	<p>검색할 가격을 작성하세요</p>
	<input type="number" name="searchPrice" min="0">
	</div>
	
	<div id="optionRating" style="display: none">
	<p>검색할 평균 별점을 작성하세요</p>
	<input type="text" name="searchRating">
	</div>
	
	<div id="optionReviewCnt" style="display: none">
	<p>검색할 리뷰 개수을 작성하세요</p>
	<input type="text" name="searchRating">
	</div>
	
	<ul id="paginationList">
			<!-- 이전 버튼 생성을 위한 조건문 -->
			<c:if test="${pageMaker.isPrev() }">
				<li class="pagination_button"><a href="${pageMaker.startNum - 1}">이전</a></li>
			</c:if>
			<!-- 반복문으로 시작 번호부터 끝 번호까지 생성 -->
			<c:forEach begin="${pageMaker.startNum }"
				end="${pageMaker.endNum }" var="num">
				<li class="pagination_button"><a href="${num }">${num }</a></li>
			</c:forEach>
			<!-- 다음 버튼 생성을 위한 조건문 -->
			<c:if test="${pageMaker.isNext() }">
				<li class="pagination_button"><a href="${pageMaker.endNum + 1}">다음</a></li>
			</c:if>
	</ul>
</body>

<script type="text/javascript">
	$(document).ready(function(){
		
		select();
		
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
			 
			// 페이지 번호를 input name='pageNum' 값으로 적용
			listForm.find("input[name='pageNum']").val(pageNum);
			// 선택된 옵션 값을 input name='pageSize' 값으로 적용
			listForm.find("input[name='pageSize']").val(pageSize);
			// type 값을 적용
			listForm.find("input[name='type']").val(type);
			// keyword 값을 적용
			listForm.find("input[name='keyword']").val(keyword);
			listForm.submit(); // form 전송
		}); // end on()
		
		function select(){
			$("#selectType").click(function(){
				if($("#selectOption").css('display') == "none")
					$("#selectOption").show();
				else
					$("#selectOption").hide();
			});
			
			// select내 option들 클릭했을 때
			$("#selectOption li").click(function(){
				var option = $(this);
				var optionVal = $(this).val();
				var optionText = $(this).text();
				var optionX = option.offset().left;
				var optionY = option.offset().top;
				//console.log("optionVal : " + optionVal + " optionText : " + optionText);
				//console.log("optionX : " + optionX + " optionY : " + optionY);
				if(optionText == "name"){
					$("#selectType").text(optionText);
					$("#optionName").show();
					$("#optionName").css({'top': optionY, 'left': 110});
					$("input[name='type']").val(optionText);
					console.log("type : " + $("input[name='type']").val());
				}
				else if(optionText == "price"){
					$("#selectType").text(optionText);
					$("#optionPrice").show();
					$("#optionPrice").css({'top': optionY, 'left': 110});
					$("input[name='type']").val(optionText);
					console.log("type : " + $("input[name='type']").val());
				}
				else if(optionText == "rating"){
					$("#selectType").text(optionText);
					$("#optionRating").show();
					$("#optionRating").css({'top': optionY, 'left': 110});
					$("input[name='type']").val(optionText);
					console.log("type : " + $("input[name='type']").val());
				}
				else if(optionText == "reviewCnt"){
					$("#selectType").text(optionText);
					$("#optionRating").show();
					$("#optionRating").css({'top': optionY, 'left': 110});
					$("input[name='type']").val(optionText);
					console.log("type : " + $("input[name='type']").val());
				}
				else{
					$("input[name='type']").val("All");
					$("input[name='keyword']").val("All");
					console.log("keyword : " + $("input[name='keyword']").val());
					console.log("type : " + $("input[name='type']").val() + " keyword" + $("input[name='keyword']").val());
				}
			});
			// select내 option들 위에 마우스 올렸을 때
			// 이거만 수정하면 완성함
			$("#selectOption li").mouseover(function(){
				if($(this).text()!="name"){
					$("#optionForm").hide();
					$("input[name='searchName']").val("");
				}	
			});
			
			// select 내 option 검색 조건 입력 했을 때
			$("input[name='searchName']").change(function(){
				$("input[name='keyword']").val($(this).val());
				console.log("keyword : " + $("input[name='keyword']").val());
			});
			$("input[name='searchPrice']").change(function(){
				$("input[name='keyword']").val($(this).val());
				console.log("keyword : " + $("input[name='keyword']").val());
			});
			$("input[name='searchRating']").change(function(){
				$("input[name='keyword']").val($(this).val());
				console.log("keyword : " + $("input[name='keyword']").val());
			});
			$("input[name='searchReviewCnt']").change(function(){
				$("input[name='keyword']").val($(this).val());
				console.log("keyword : " + $("input[name='keyword']").val());
			});
		}
	});
</script>

</html>
