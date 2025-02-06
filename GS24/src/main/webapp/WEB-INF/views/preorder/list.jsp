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
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<script type="text/javascript">
	$(document).ajaxSend(function(e, xhr, opt){
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        
        xhr.setRequestHeader(header, token);
     });
	
	$(document).ready(function(){
	    
	    getAllPreorder();
	    checkToCheckBox();
	    onCancellButton();
	    onDeletePreorder();
	    
	    //회원이 예약한 식품들 불러오는 코드
	    function getAllPreorder(){
	        let url = "../preorder/all/"+ "${memberId}";
	        
	        $.getJSON(url, function(data){
	            console.log(data);
	            
	            let list = '';
	            
	            $(data).each(function(){
	                let pickUpDate = new Date(this.pickupDate);
	                
	                let StringDate = formatDate(pickUpDate);
					
	                let foodId = this.foodId;
	                
	                let preorderNO = this.preorderId;
	                
	                let isPickUp = '미수령';
	                
	                let isExpiredOrder = '예약 중';
	                
	                if(this.isPickUp == 1){
	                    isPickUp = '수령 완';
	                    isExpiredOrder = '예약 종료';
	                }
	                
	                if(this.isExpiredOrder == 1){
	                	isPickUp = '수령 취소';
	                    isExpiredOrder = '예약 취소';
	                }
	                
	                list += '<div class="preorderList">';
	                		if(isExpiredOrder == '예약 중'){
	                        list += '<input type="checkbox" class="check-box">'
	                		}
	                list += '<input type="hidden" class="preorderNO" value="'+preorderNO+'">'
	                        + '<div><img src="../image/foodThumnail?foodId='+foodId+'" style="width:150px; height=150px;"></div>'
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
	        $('#cancel').click(function(){
	            var isCancel = confirm("취소하시겠습니까?");
	            if(isCancel){
	            	let selectedPreordersId = [];
		            
		            $(".preorderList").each(function(){
		                if ($(this).find("input[type='checkbox']").prop('checked')) {
		                    let preorderNO = $(this).find(".preorderNO").val();
		                    selectedPreordersId.push(preorderNO);
		                }
		            });
		           
		             $.ajax({
		                 url: "../preorder/cancel",
		                 method: 'POST',
		                 headers : {'Content-Type' : 'application/json' }, 
		                 data: JSON.stringify(selectedPreordersId),
		                 success: function(result) {
		                     if(result == 1){
		                    	 alert("예약 취소 완료");
		                    	 getAllPreorder();
		                     }
		                 }
		             });
	            }
	        });
	    }// end onCancellButton
	    
	    function onDeletePreorder(){
	    	$('#delete').click(function(){
	    		let cancelledPreorderId = [];
	    		console.log(this);
	    		$(".preorderList").each(function(){
	    			if($(this).find(".isExpriedOrder").text() == "예약 취소"){
	    				let preorderNO = $(this).find(".preorderNO").val();
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
<title>예약 식품 목록</title>
</head>
<%@ include file="../common/header.jsp" %>
<body>
	<h1>예약된 식품 목록</h1>
	<div id="list"></div>
	<br>
	<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
	<button id="cancel">미수령 예약 취소</button>
	<button onclick="location.href='../food/list'">돌아가기</button>
	<button id="delete">예약 취소 목록 삭제</button>
	
</body>
</html>

