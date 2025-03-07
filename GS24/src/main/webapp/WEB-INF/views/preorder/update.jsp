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
<script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<link rel="stylesheet" href="../resources/css/fonts.css">
<title>예약 식품 확인</title>
</head>
<style>
body {
	font-family: 'Pretendard-Regular', sans-serif;
	margin: 0;
	padding: 15px;
	background-color: #f8f9fa;
	text-align: center;
}

.preorder-list {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-evenly;
    gap: 15px;
}


.preorder-item {
    display: flex;
    align-items: center;
    justify-content: center;
    border: 2px solid #ddd;
    margin-bottom: 20px;
    max-width: 500px;
	margin: 20px auto;
	width: 100%;
	border-collapse: collapse;
	text-align: center;
	background-color: white;
	border-radius: 30px;
	box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.preorder-item:hover {
    border: 2px solid #4CAF50;
	cursor: pointer;
}

.preorder-image {
    margin-right: 20px;
}

li {
	width: 100px;
	cursor: pointer;
	display: inline-block;
}

li div:hover {
	background-color: silver;
	border-radius: 5px;
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

ul {
	list-style: none;
	padding: 0;
	display: flex;
	justify-content: center;
}

button, input[type="button"] {
	font-family: 'Pretendard-Regular', sans-serif;
	background: #ddd;
	color: black;
	padding: 5px 10px;
	border-radius: 5px;
	border: none;
	cursor: pointer;
}

button:hover {
	background: #bbb;
}

input[type="text"] {
	font-family: 'Pretendard-Regular', sans-serif;
    width: 15%;
    padding: 5px;
    border: 1px solid #ddd;
    border-radius: 5px;
    text-align: center;
    font-size: 18px;
}

.preorderId {
	font-size: 40px;
	padding: 20px;
}

.Order span{
	font-size: 20px;
}

.Food span{
	font-size: 20px;
}

.Date span{
	font-size: 20px;
}
</style>
<body>
<%@ include file="../common/header.jsp"%>

<h1>결제 및 지급</h1>
	<ul class="preorder">
		<li>
			<div class="Order">
				<span>최근예약순</span>
			</div>
		</li>
		<li>
			<div class="Food">
				<span>식품종류순</span>
			</div>
		</li>
		<li>
			<div class="Date">
				<span>수령기한순</span>
			</div>
		</li>
	</ul>
	<input type="text" class="searchMemberId" placeholder="회원 아이디 입력">
<div class="container">
<div class="preorder-list">
	<c:forEach var="preorderVO" items="${preorderList}">
		<div class="preorder-item">
			<strong class="preorderId">${preorderVO.preorderId }</strong>
			<img class="preorder-image" src="../image/foodThumbnail?foodId=${preorderVO.foodId }" width="80px" height="80px">
			<table>
				<tr>
					<th>회원명</th>
					<td><span class="memberId">${preorderVO.memberId }</span></td>
				</tr>
				<tr>
					<th>결제액</th>
					<td><span class="totalPrice">${preorderVO.totalPrice }</span>원</td>
				</tr>
				<tr>
					<th>수령기한</th>
					<fmt:formatDate value="${preorderVO.pickupDate }" pattern="yyyy년 MM월 dd일" var="pickupDate" />
					<td><span class="pickupDate">${pickupDate }</span></td>
				</tr>
			</table>
		</div>
	</c:forEach>
</div>
</div>
	
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
	<button type="button" onclick='location.href="../convenienceFood/list?convenienceId=${convenienceId }"'>돌아가기</button>
	
	<form action="update" method="get" id="updateForm">
		<input type="hidden" name="convenienceId">
		<input type="hidden" name="pageSize">
		<input type="hidden" name="pageNum">
		<input type="hidden" name="sortType">
		<input type="hidden" name="keyword">
	</form>
	
	<script type="text/javascript">
		$(document).ajaxSend(function(e, xhr, opt){
  	    	var token = $("meta[name='_csrf']").attr("content");
  	    	var header = $("meta[name='_csrf_header']").attr("content");
   	     
  	    	xhr.setRequestHeader(header, token);
    	 });
	
		$(document).ready(function(){
			$(".preorder").on('click','li div',function(){
				var updateForm = $("#updateForm");
				var convenienceId = '${convenienceId}';
				var pageNum = "<c:out value='${pageMaker.pagination.pageNum}' />";
				var pageSize = "<c:out value='${pageMaker.pagination.pageSize}' />";
				var sortType = $(this).attr('class');
				var keyword = "<c:out value='${pageMaker.pagination.keyword}' />";
				
				updateForm.find("input[name=convenienceId]").val(convenienceId);
				updateForm.find("input[name=pageNum]").val(pageNum);
				updateForm.find("input[name=pageSize]").val(pageSize);
				updateForm.find("input[name=sortType]").val(sortType);
				updateForm.find("input[name=keyword]").val(keyword);
				
				updateForm.submit();
			});
			
			 $(".pagination_button a").on("click", function(e){
		         var updateForm = $("#updateForm"); // form 객체 참조
		         e.preventDefault(); // a 태그 이벤트 방지
		         var convenienceId = '${convenienceId}';
		         var pageNum = $(this).attr("href"); // a태그의 href 값 저장
		         // 현재 페이지 사이즈값 저장
		         var pageSize = "<c:out value='${pageMaker.pagination.pageSize }' />";
		         var type = "<c:out value='${pageMaker.pagination.type }' />";
		         var keyword = "<c:out value='${pageMaker.pagination.keyword }' />";
		         var sortType = "<c:out value='${pageMaker.pagination.sortType}' />";
		         
		         updateForm.find("input[name=convenienceId]").val(convenienceId);
		         updateForm.find("input[name='pageNum']").val(pageNum);
		         updateForm.find("input[name='pageSize']").val(pageSize);
		         updateForm.find("input[name='keyword']").val(keyword);
		         updateForm.find("input[name='sortType']").val(sortType);
		         
		         updateForm.submit(); // form 전송
		      }); // end on()
			
			$(".searchMemberId").change(function(){
				var updateForm = $("#updateForm");
				var convenienceId = '${convenienceId}';
				var pageNum = "<c:out value='${pageMaker.pagination.pageNum}' />";
				var pageSize = "<c:out value='${pageMaker.pagination.pageSize}' />";
				var sortType = "<c:out value='${pageMaker.pagination.sortType}' />";
				var keyword = $(".searchMemberId").val();
				
				updateForm.find("input[name=convenienceId]").val(convenienceId);
				updateForm.find('input[name=pageNum]').val(pageNum);
				updateForm.find('input[name=pageSize]').val(pageSize);
				updateForm.find('input[name=sortType]').val(sortType);
				updateForm.find('input[name=keyword]').val(keyword);
				
				updateForm.submit();
			});
			
			$(".preorder-list").on("click","div",function(){
				console.log(this);
				var isCheck = confirm("수락하시겠습니까?");
				if (isCheck) {
					var preorderId = $(this).find(".preorderId").text();
					console.log(preorderId);
					var totalPrice = $(this).find(".totalPrice").text();
					if (totalPrice == 0){
						$.ajax({
    						type : "post",
    						url : "../preorder/check",
    						data : {"preorderId" : preorderId},
    						success : function(result){
    							console.log(result);
    							if(result == 1){
    								$("#updateForm").submit();
    							} else {
    								alert("예약이 취소되어 결제가 취소되었습니다.");
    							}
    						}
    					});
					}
                            $.ajax({
        						type : "post",
        						url : "../preorder/check",
        						data : {"preorderId" : preorderId},
        						success : function(result){
        							console.log(result);
        							if(result == 1){
        								var IMP = window.IMP;
        								IMP.init('imp84362136');
        								IMP.request_pay({
        									pg: 'kakaopay',
        									pay_method: 'card',
        			 						merchant_uid: 'order_' + new Date().getTime(),
        									name: '편의점 식품 PICKUP',
        			                        amount: totalPrice
        			                    }, function(rsp) {
        			                        if (rsp.success) {
        			                           alert('결제가 완료되었습니다.');
        			                           var updateForm = $("#updateForm");
               								
       											var pageNum = "<c:out value='${pageMaker.pagination.pageNum}' />";
       											var pageSize = "<c:out value='${pageMaker.pagination.pageSize}' />";
       											var sortType = "<c:out value='${pageMaker.pagination.sortType}' />";
       											var keyword = "<c:out value='${pageMaker.pagination.keyword}' />";
       								
       											updateForm.find('input[name=pageNum]').val(pageNum);
       											updateForm.find('input[name=pageSize]').val(pageSize);
       											updateForm.find('input[name=sortType]').val(sortType);
       											updateForm.find('input[name=keyword]').val(keyword);
       									
       											updateForm.submit();
       											location.reload();
        			                        } else {
        			                            alert('결제에 실패하였습니다. 에러 메시지: ' + rsp.error_msg);
        			                        }
        			                    });
        								
        							} else {
        								alert("예약이 취소되어 결제가 취소되었습니다.");
        								location.reload();
        							}
        						}
        					});
				}
			});
		});
	</script>
	<%@ include file="../common/footer.jsp"%>
</body>
</html>