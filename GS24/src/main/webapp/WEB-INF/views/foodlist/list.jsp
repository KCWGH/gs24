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
<title>식품 창고</title>
<style type="text/css">
        table, th, td {
            border-style: solid;
            border-width: 1px;
            text-align: center;
        }
</style>
</head>
<body>
<%@ include file="../common/header.jsp" %>
	<sec:authorize access="hasRole('ROLE_ADMIN')">
	<button onclick='location.href="register"'>추가</button>
	</sec:authorize>
	<table id="foodTable">
		<thead>
			<tr>
				<th>음식ID</th>
				<th>음식 유형</th>
				<th>음식 이름</th>
				<th>음식 가격</th>
				<th>단백질 양</th>
				<th>지방 양</th>
				<th>탄수화물 양</th>
				<th>재고량</th>
				<th>상태</th>
				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<th colspan="3">기타</th>
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_OWNER')">
					<th colspan="1">기타</th>
					<th>발주</th>
				</sec:authorize>
			</tr>
		</thead>
		
		<tbody class="foodBody">
		<c:forEach var="foodListVO" items="${foodList }">
		<tr class="foodRow">
			<td class="foodId" >${foodListVO.foodId }</td>
			<td>${foodListVO.foodType }</td>
			<td>${foodListVO.foodName }</td>
			<td>${foodListVO.foodPrice }</td>
			<td>${foodListVO.foodProtein }</td>
			<td>${foodListVO.foodFat }</td>
			<td>${foodListVO.foodCarb }</td>
			<td class="foodStock">${foodListVO.foodStock }</td>
			<c:choose>
				<c:when test="${foodListVO.isSelling == 0 }">
					<td class="isSelling">발주 중지</td>
				</c:when>
				<c:when test="${foodListVO.isSelling == 1 }">
					<td class="isSelling">발주 진행</td>
				</c:when>
				<c:when test="${foodListVO.isSelling == 2 }">
					<td class="isSelling">발주 준비</td>
				</c:when>
			</c:choose>
			<td><a href="../image/foodThumnail?foodId=${foodListVO.foodId }" target="_blank">썸내일 보기</a></td>
			<sec:authorize access="hasRole('ROLE_ADMIN')">
				<td><a href="update?foodId=${foodListVO.foodId }">수정</a></td>
				<td class="delete"><a href="delete?foodId=${foodListVO.foodId }">삭제</a></td>
			</sec:authorize>
			<sec:authorize access="hasRole('ROLE_OWNER')">
			 	<td><input class="foodAmount" type="number"><button class="insert">발주</button></td>
   			</sec:authorize>
		</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<script type="text/javascript">
		$(document).ready(function(e){
			$(document).ajaxSend(function(e, xhr, opt){
			    var token = $("meta[name='_csrf']").attr("content");
			    var header = $("meta[name='_csrf_header']").attr("content");
			     
			    xhr.setRequestHeader(header, token);
			});
			
			$(".foodRow").on('click','.delete',function(e){
				e.preventDefault();
				var foodId = $(this).prevAll().eq(10).html();
			
				var isSelling = $(this).prevAll('.isSelling').text();
				
				var checkDelete;
				
				if(isSelling != '발주 중지'){
					alert("발주 중지 중인 상품이 아닙니다.");
					return;
				}
				
				//삭제해도 되는지 확인하기 위한 json 데이터 전송
				$.ajax({
					type : 'post',
					contentType: "application/json",
					url : 'checkdelete',
					data : foodId,
					success : function(result){
						console.log(result);
						checkDelete = result;
					}
				});
				
				if(checkDelete == 'success'){
					console.log("삭제할 거에요");
					$.ajax({
		  				type : 'post',
		  				url : '../image/remove2',
		  				data : {"foreignId" : foodId},
		  				success : function(result){
		  					location.href='../foodlist/delete?foodId=' + foodId;
		  				}
		  			});
				}
				
			});
			$(".foodRow").on('click', 'td .insert', function() {
			    var foodAmount = $(this).prev().val();
			    var foodId = $(this).parents().prevAll('.foodId').text();
			    var foodStock = $(this).parents().prevAll('.foodStock').text();
			    var isSelling = $(this).parents().prevAll('.isSelling').text();

			    if (isSelling != '발주 진행') {
			        alert("발주 진행 중인 상품이 아닙니다.");
			        return;
			    }

			    if (foodStock - foodAmount >= 0) {
			        $.ajax({
			            type: 'GET',
			            url: "../convenienceFood/register",  // 발주 처리
			            data: { foodId: foodId, foodAmount: foodAmount },
			            success: function(response) {
			                // 주문 내역을 비동기적으로 가져오기
			                $.ajax({
			                    type: 'GET',
			                    url: "../convenienceFood/getOrderHistory",  // 주문 내역 가져오기
			                    success: function(orderHistoryList) {
			                        var tableBody = $("#orderHistoryTable tbody");
			                        tableBody.empty();

			                        $.each(orderHistoryList, function(index, orderHistory) {
			                            var newRow = "<tr>";
			                            newRow += "<td>" + orderHistory.foodId + "</td>";
			                            newRow += "<td>" + orderHistory.orderAmount + "</td>";
			                            newRow += "<td>" + orderHistory.ownerId + "</td>";
			                            newRow += "<td>" + orderHistory.orderDateCreated + "</td>";
			                            newRow += "</tr>";

			                            tableBody.append(newRow);
			                        });

			                        // 여기서 페이지 리다이렉트를 제거합니다.
			                    },
			                    error: function(xhr, status, error) {
			                        alert("주문 내역을 불러오는 데 실패했습니다.");
			                    }
			                });
			            },
			            error: function(xhr, status, error) {
			                alert("발주 처리에 실패했습니다.");
			            }
			        });
			    } else {
			        alert("재고량보다 발주량이 많습니다.");
			    }
			});

		});
	</script>
	<table id="orderHistoryTable">
    <thead>
        <tr>
            <th>음식ID</th>
            <th>주문 수량</th>
            <th>주문자 ID</th>
            <th>주문 일시</th>
        </tr>
    </thead>
    <tbody>
        <!-- 비동기적으로 주문 내역이 여기에 추가됩니다. -->
    </tbody>
</table>
</body>
</html>