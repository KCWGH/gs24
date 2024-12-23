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
<title>식품 상세 정보</title>
</head>
<body>
	<!-- 상품 이미지도 같이 넣어줘야 한다. -->
	<p>식품 사진 : ${FoodVO.imgFoodVO.imgFoodPath }</p>
	<p>식품 유형 : ${FoodVO.foodType }</p>
	<p>식품 이름 : ${FoodVO.foodName }</p>
	<p>재고량 : ${FoodVO.foodStock }개</p>
	<p>가격 : ${FoodVO.foodPrice }원</p>
	<p>단백질 함유량 : ${FoodVO.foodProtein }</p>
	<p>지방 함유량 : ${FoodVO.foodFat }</p>
	<p>탄수호물 함유량 : ${FoodVO.foodCarb }</p>
	<p>입고 날짜 : ${FoodVO.registeredDate }</p>
	
	<button onclick="location.href='list'">돌아가기</button>
	<button onclick="locatioin.href='../preorder/register'">예약하기</button>
	<hr>
	<div style="text-align: center;">
		<input type="text" id="memberId" readonly="readonly" value="${memberId }" >
		<input type="text" id="reviewTitle">
		<input type="text" id="reviewContent">
		<input type="text" id="reviewRating">
		<button id="btnAdd">작성</button>
	</div>
	<hr>
	<div style="text-align: center">
		<div id='review'></div>	
	</div>
	
	<script type="text/javascript">
	$(document).ready(function(){
		getAllReply();	
		
		$('#btnAdd').click(function(){
			let memberId = $('#memberId').val();
			let reviewTitle = $('#reviewTitle').val();
			let reviewContent = $('#reviewContent').val();
			let reviewRating = $('#reviewRating').val();
			let foodId = ${FoodVO.foodId};
			
			let obj = {
					'memberId' : memberId,
					'reviewTitle' : reviewTitle,
					'reviewContent' : reviewContent,
					'reviewRating' : reviewRating,
					'foodId' : foodId
			}
			console.log(obj);
			
			// $.ajax로 송수신
			$.ajax({
				type : 'POST', // 메서드 타입
				url : '../review', // url
				headers : { // 헤더 정보
					'Content-Type' : 'application/json'
				}, 
				data : JSON.stringify(obj),
				success : function(result) {
					console.log(result);
					if(result == 1) {
						alert('댓글 입력 성공');
						getAllReply();		
					}
				}
			});
		}); // end btnAdd.click()
		
		function getAllReply() {
			var foodId = ${FoodVO.foodId};
			
			var url = '../review/all/' + foodId;
			$.getJSON(
				url, 		
				function(data) {
					console.log(data);
					
					var list = '';
					
					$(data).each(function(){
						console.log(this);
					  
						var reviewDateCreated = new Date(this.reviewDateCreated);
						
						let memberId = "${memberId}";
						let disabled ='';
						if(memberId != this.memberId){
							disabled = 'disabled';
						}
						
						list += '<div class="reply_item">'
							+ '<pre>'
							+ '<input type="hidden" id="reviewId" value="'+ this.reviewId +'">'
							+ this.memberId
							+ '&nbsp;&nbsp;'
							+ '<input type="text" id="reviewTitle"'+disabled+' value="'+ this.reviewTitle+'">'
							+ '&nbsp;&nbsp;'
							+ '<input type="text" id="reviewContent"'+disabled+' value="'+ this.reviewContent +'">'
							+ '&nbsp;&nbsp;'
							+ '<input type="text" id="reviewRating" '+disabled+' value="'+ this.reviewRating +'">'
							+ reviewDateCreated
							+ '&nbsp;&nbsp;'
							+ '<button class="btn_update" '+disabled+'>수정</button>'
							+ '<button class="btn_delete" '+disabled+'>삭제</button>'
							+ '</pre>'
							+ '</div>';
					}); // end each()
						
					$('#review').html(list);
				} // end function()
			); // end getJSON()
		} // end getAllReply()
		
		
		$('#review').on('click', '.reply_item .btn_update', function(){
			console.log(this);
			
			let reviewId = $(this).prevAll('#reviewId').val();
			let reviewTitle = $(this).prevAll('#reviewTitle').val();
			let reviewContent = $(this).prevAll('#reviewContent').val();
			let foodId = ${FoodVO.foodId};
			let reviewRating = $(this).prevAll('#reviewRating').val();
			
			let obj = {'reviewId' : reviewId, 'reviewTitle' : reviewTitle, 'reviewContent' : reviewContent,'foodId' : foodId,'reviewRating' : reviewRating};
			
			$.ajax({
				type : 'PUT', 
				url : '../review/' + reviewId,
				headers : {
					'Content-Type' : 'application/json'
				},
				data : JSON.stringify(obj), 
				success : function(result) {
					console.log(result);
					if(result == 1) {
						alert('댓글 수정 성공!');
						getAllReply();
					}
				}
			});
			
		}); // end replies.on()
		
		// 삭제 버튼을 클릭하면 선택된 댓글 삭제
		$('#review').on('click', '.reply_item .btn_delete', function(){
			console.log(this);
			let reviewId = $(this).prevAll('#reviewId').val();
			let foodId = ${FoodVO.foodId};
			
			// ajax 요청
			$.ajax({
				type : 'DELETE', 
				url : '../review/' + reviewId + '/' + foodId,
				headers : {
					'Content-Type' : 'application/json'
				},
				success : function(result) {
					console.log(result);
					if(result == 1) {
						alert('댓글 삭제 성공!');
						getAllReply();
					}
				}
			});
		}); // end replies.on()		

	}); // end document()
	</script>
</body>
</html>