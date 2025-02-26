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
<script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<title>예약 식품 확인</title>
</head>
<style>
	ul {
		list-style-type: none;	
	}
	li {
		margin-bottom: 40px;
		width: 100px;
		display: inline-block;
	}
	li div:hover {
		background-color: silver;
	}
	.preorder-item:hover{
		border-style: dotted;
		border-color: aqua;
		margin-bottom: 20px;
	}
}
</style>
<body>
<%@ include file="../common/header.jsp"%>
	<input type="text" class="searchMemberId" placeholder="회원 아이디 입력">
	<ul class="preorder">
		<li>
			<div class="Order">
				예약 순
			</div>
		</li>
		<li>
			<div class="Food">
				식품 순
			</div>
		</li>
		<li>
			<div class="Date">
				날짜 순
			</div>
		</li>
	</ul>
	
	<div class="preorder-list">
		<c:forEach var="preorderVO" items="${preorderList}">
			<div class="preorder-item">
				<p class="preorderId">${preorderVO.preorderId }</p>
				<img src="../image/foodThumnail?foodId=${preorderVO.foodId }" width="100px" height="100px">
				<p class="pickupDate">${preorderVO.pickupDate }</p>
				<p class="memberId">${preorderVO.memberId }</p>
				<p class="totalPrice">${preorderVO.totalPrice }</p>
			</div>
		</c:forEach>
	</div>
	
	<button type="button" onclick='location.href="../convenienceFood/list?convenienceId=${convenienceId }"'>돌아가기</button>
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
	
	<form action="update" method="get" id="updateForm">
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
				
				var pageNum = "<c:out value='${pageMaker.pagination.pageNum}' />";
				var pageSize = "<c:out value='${pageMaker.pagination.pageSize}' />";
				var sortType = $(this).attr('class');
				var keyword = "<c:out value='${pageMaker.pagination.keyword}' />";
				
				updateForm.find("input[name=pageNum]").val(pageNum);
				updateForm.find("input[name=pageSize]").val(pageSize);
				updateForm.find("input[name=sortType]").val(sortType);
				updateForm.find("input[name=keyword]").val(keyword);
				
				updateForm.submit();
			});
			
			 $(".pagination_button a").on("click", function(e){
		         var updateForm = $("#updateForm"); // form 객체 참조
		         e.preventDefault(); // a 태그 이벤트 방지
		      
		         var pageNum = $(this).attr("href"); // a태그의 href 값 저장
		         // 현재 페이지 사이즈값 저장
		         var pageSize = "<c:out value='${pageMaker.pagination.pageSize }' />";
		         var type = "<c:out value='${pageMaker.pagination.type }' />";
		         var keyword = "<c:out value='${pageMaker.pagination.keyword }' />";
		         var sortType = "<c:out value='${pageMaker.pagination.sortType}' />";
		          
		         updateForm.find("input[name='pageNum']").val(pageNum);
		         updateForm.find("input[name='pageSize']").val(pageSize);
		         updateForm.find("input[name='keyword']").val(keyword);
		         updateForm.find("input[name='sortType']").val(sortType);
		         
		         updateForm.submit(); // form 전송
		      }); // end on()
			
			$(".searchMemberId").change(function(){
				var updateForm = $("#updateForm");
				
				var pageNum = "<c:out value='${pageMaker.pagination.pageNum}' />";
				var pageSize = "<c:out value='${pageMaker.pagination.pageSize}' />";
				var sortType = "<c:out value='${pageMaker.pagination.sortType}' />";
				var keyword = $(".searchMemberId").val();
				
				updateForm.find('input[name=pageNum]').val(pageNum);
				updateForm.find('input[name=pageSize]').val(pageSize);
				updateForm.find('input[name=sortType]').val(sortType);
				updateForm.find('input[name=keyword]').val(keyword);
				
				updateForm.submit();
			});
			
			$(".preorder-list").on("click","div",function(){
				console.log(this);
				var isCheck = confirm("수락하시겠습니까?");
				if(isCheck){
					var preorderId = $(this).find(".preorderId").text();
					console.log(preorderId);
					var totalPrice = $(this).find(".totalPrice").text();
					if (totalPrice == 0){
						$("#updateForm").submit();
						location.reload();
						$.ajax({
    						type : "post",
    						url : "../preorder/check",
    						data : {"preorderId" : preorderId},
    						success : function(result){
    							console.log(result);
    							if(result == 1){
    								
    							} else {
    								alert("예약이 취소되어 결제가 취소되었습니다.");
    								location.reload();
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
        								alert("예약이 취소 되어 결제가 취소되었습니다.");
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