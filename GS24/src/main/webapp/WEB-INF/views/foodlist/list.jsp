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
	<button onclick='location.href="register"'>추가</button>
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
				<th colspan="3">기타</th>
				<sec:authorize access="hasRole('ROLE_OWNER')">
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
					<td>판매 중지</td>
				</c:when>
				<c:when test="${foodListVO.isSelling == 1 }">
					<td>판매 진행</td>
				</c:when>
				<c:when test="${foodListVO.isSelling == 2 }">
					<td>판매 대기</td>
				</c:when>
			</c:choose>
			<td><a href="../image/foodThumnail?foodId=${foodListVO.foodId }" target="_blank">썸내일 보기</a></td>
			<td><a href="update?foodId=${foodListVO.foodId }">수정</a></td>
			<td class="delete"><a href="delete?foodId=${foodListVO.foodId }">삭제</a></td>
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
				console.log(foodId);
				
				$.ajax({
	  				type : 'post',
	  				url : '../image/remove2',
	  				data : {"foreignId" : foodId},
	  				success : function(result){
	  					location.href='../foodlist/delete?foodId=' + foodId;
	  				}
	  			});
				
			});
			$(".foodRow").on('click','td .insert', function(){
				console.log(this);
				var foodAmount = $(this).prev().val();
				var foodId = $(this).parents().prevAll('.foodId').text();
				var foodStock = $(this).parents().prevAll('.foodStock').text();
				console.log(foodId);
				console.log(foodAmount);
				console.log(foodStock);
				console.log(foodStock - foodAmount);
				
				if(foodStock - foodAmount >= 0){
					location.href="../convenienceFood/register?foodId=" + foodId + "&foodAmount=" + foodAmount;
				}
				
				alert("재고량보다 발주량이 많습니다.");
			});
			
		});
	</script>
</body>
</html>