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
<title>예약 식품 목록</title>
</head>
<body>
	<h1>에약된 식품 목록</h1>
	<div id="list"></div>
	<br>
	<button id="cancell">예약 취소</button>
	<button onclick="location.href='../food/list'">돌아가기</button>
	<button id="delete">예약 취소 목록 삭제</button>
	<script type="text/javascript">
	
	$(document).ready(function(){
	    
	    getAllPreorder();
	    checkToCheckBox();
	    onCancellButton();
	    onDeletePreorder();
	    
	    //회원이 예약한 식품들 불러오는 코드
	    function getAllPreorder(){
	        let url = "../preorder/all/"+ "${sessionScope.memberId}";
	        
	        $.getJSON(url, function(data){
	            console.log(data);
	            
	            let list = '';
	            
	            $(data).each(function(){
	                let pickUpDate = new Date(this.pickupDate);
	                
	                let StringDate = formatDate(pickUpDate);
					
	                let foodId = this.foodId;
	                
	                let isPickUp = '미수령';
	                if(this.isPickUp == 1){
	                    isPickUp = '수령 완';
	                }
	                
	                let isExpiredOrder = '예약 중';
	                if(this.isExpiredOrder == 1){
	                    isExpiredOrder = '예약 취소';
	                }
	                
	                list += '<div class="preorderList">'
	                        + '<input type="checkbox" class="check-box">'
	                        + '<div><img src="../ImgFood?foodId='+foodId+'"></div>'
	                        + '<div>수량 : '+this.preorderAmount+'</div>'
	                        + '<div>수령일 : '+ StringDate+'</div>'
	                        + '<div>'+isPickUp+'</div>'
	                        + '<div class="isExpriedOrder">'+isExpiredOrder+'</div>'
	                        + '</div>';
	            });
	            $('#list').html(list);
	        });
	    }// end getAllPreorder
	    
	    //각 예약 식품 항목 중 아무거나 누르면 체크박스에 활성화/비활성화 하는 코드
	    function checkToCheckBox(){
	        $('#list').on('click', '.preorderList', function(){
	            let isChecked = $(this).find("input[type='checkbox']").prop('checked');
	            $(this).find("input[type='checkbox']").prop('checked', !isChecked);
	        });
	    }// end checkToCheckBox
	    
	    function onCancellButton(){
	        $('#cancell').click(function(){
	            let selectedPreordersId = [];
	            
	            $(".preorderList").each(function(){
	                if ($(this).find("input[type='checkbox']").prop('checked')) {
	                    let preorderNO = $(this).find(".preorderNO").text();
	                    selectedPreordersId.push(preorderNO);
	                }
	            });
	           
	             $.ajax({
	                 url: "../preorder/cancell",
	                 method: 'POST',
	                 headers : {'Content-Type' : 'application/json' }, 
	                 data: JSON.stringify(selectedPreordersId),
	                 success: function(result) {
	                     if(result == 1){
	                    	 alert("삭제 성공!");
	                    	 getAllPreorder();
	                     }
	                 }
	             });
	        });
	    }// end onCancellButton
	    
	    function onDeletePreorder(){
	    	$('#delete').click(function(){
	    		let cancelledPreorderId = [];
	    		console.log(this);
	    		$(".preorderList").each(function(){
	    			if($(this).find(".isExpriedOrder").text() == "예약 취소"){
	    				let preorderNO = $(this).find(".preorderNO").text();
	    				cancelledPreorderId.push(preorderNO);
	    			}
	    		});
	    		
	    		$.ajax({
	                 url: "../preorder/delete",
	                 method: 'POST',
	                 headers : {'Content-Type' : 'application/json' }, 
	                 data: JSON.stringify(cancelledPreorderId),
	                 success: function(result) {
	                     if(result == 1){
	                    	 alert("삭제 성공!");
	                    	 getAllPreorder();
	                     }
	                 }
	             });
	    	});
	    }
	    
	    function formatDate(date){
	    	let yyyy = date.getFullYear();
			let mm = date.getMonth() + 1;
			let dd = date.getDate();
            
			if (mm < 10) mm = '0' + mm;
			if (dd < 10) dd = '0' + dd;
			
			let toStringDate = yyyy + "년" + mm + "월" + dd + "일";
			
			return toStringDate;
	    }
	    
	});
	
	</script>
</body>
</html>

