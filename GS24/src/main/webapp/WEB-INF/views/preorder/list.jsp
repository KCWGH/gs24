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
	
	<script type="text/javascript">
	
		$(document).ready(function(){
			
			getAllPreorder();
			
			function getAllPreorder(){
				let url = "../preorder/all/"+ "${sessionScope.memberId}";
				
				$.getJSON(
					url,
					function(data){
						
						console.log(data);
						
						let list = '';
						
						$(data).each(function(){
						
						let pickUpDate = new Date(this.pickupDate);
						
						let isPickUp = '미수령';
						if(this.isPickUp == 1){
							isPickUp = '수령 완';
						}
						
						let isExpiredOrder = '예약 중';
						if(this.isExpiredOrder == 1){
							isExpiredOrder = '예약 취소';
						}
						
						list += '<input type="checkbox" class="check-box">'
								+ '<div class="preorderNO">'+this.preorderId+'</div>'
								+ '<div>FoodName : '+this.foodId+'</div>'
								+ '<div>Amount :'+this.preorderAmount+'</div>'
								+ '<div>Pickup :'+ pickUpDate+'</div>'
								+ '<div>'+isPickUp+'</div>'
								+ '<div>'+isExpiredOrder+'</div>';
						});
						$('#list').html(list);
					}
				);
			}// end getAllPreorder
			
		});
	
	</script>
</body>
</html>

