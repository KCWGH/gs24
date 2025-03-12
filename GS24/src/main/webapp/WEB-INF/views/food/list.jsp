<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="../resources/css/fonts.css">
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<title>식품 창고</title>
<style type="text/css">
body {
	font-family: 'Pretendard-Regular', sans-serif;
	margin: 0;
	padding: 15px;
	text-align: center;
	background-color: #f8f9fa;
}

h2 {
	color: #333;
	margin-bottom: 5px;
}

table {
	max-width: 1200px;
	margin: 20px auto;
	width: 100%;
	border-collapse: collapse;
	text-align: center;
	background-color: white;
}

th, td {
	border: 1px solid #ccc;
	padding: 10px;
	text-align: center;
}

th {
	background-color: #f1f1f1;
	color: #555;
}

td a {
	color: #007bff;
	text-decoration: none;
}

td a:hover {
	text-decoration: underline;
}

.status-text {
	font-weight: bold;
	display: inline-block;
	padding: 3px 8px;
	border-radius: 5px;
}

.foodRow td a, .foodRow td .insert {
	font-family: 'Pretendard-Regular', sans-serif;
	background: #ddd;
	color: black;
	padding: 5px 10px;
	border-radius: 5px;
	text-decoration: none;
	border: none;
	margin: 5px;
}

.foodRow td a:hover, .foodRow td .insert:hover {
	background: #bbb;
}

#insert {
	font-family: 'Pretendard-Regular', sans-serif;
	background: #ddd;
	color: black;
	padding: 5px 10px;
	border-radius: 5px;
	border: none;
	cursor: pointer;
}

#insert:hover {
	background: #bbb;
}

ul {
	display: flex;
	justify-content: center;
	padding: 0;
	margin: 20px 0;
	list-style-type: none;
}

.pagination_button {
	display: inline-block;
	margin: 5px;
	text-align: center;
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

.pagination_button.current a {
	background: #333;
	color: white;
}

input[type="number"] {
    font-family: 'Pretendard-Regular', sans-serif;
    width: 50%;
    padding: 5px;
    border: 1px solid #ddd;
    border-radius: 5px;
    text-align: center;
}
</style>
</head>
<body>
<%@ include file="../common/header.jsp" %>
<h1>식품 리스트</h1>


<table id="foodTable">
<sec:authorize access="hasRole('ROLE_ADMIN')">
    	<button id="insert" onclick='location.href="register"'>식품 추가</button>
</sec:authorize>
    <thead>
        <tr>
            <th>식품번호</th>
            <th>식품 유형</th>
            <th>식품 이름</th>
            <th>식품 가격</th>
            <th>단백질 양</th>
            <th>지방 양</th>
            <th>탄수화물 양</th>
            <th>재고량</th>
            <th>상태</th>
            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <th colspan="3">작업</th>
            </sec:authorize>
            <sec:authorize access="hasRole('ROLE_OWNER')">
                <th colspan="1">작업</th>
                <th>발주</th>
            </sec:authorize>
        </tr>
    </thead>
    
    <tbody class="foodBody">
        <c:forEach var="foodVO" items="${food}">
        <tr class="foodRow">
            <td class="foodId">${foodVO.foodId}</td>
            <td>${foodVO.foodType}</td>
            <td>${foodVO.foodName}</td>
            <td>${foodVO.foodPrice}</td>
            <td>${foodVO.foodProtein}</td>
            <td>${foodVO.foodFat}</td>
            <td>${foodVO.foodCarb}</td>
            <td class="foodStock">${foodVO.foodStock}</td>
            <c:choose>
                <c:when test="${foodVO.isSelling == 0}"> <td class="isSelling" style="color:red;">발주 중지</td> </c:when>
                <c:when test="${foodVO.isSelling == 1}"> <td class="isSelling" style="color:green;">발주 가능</td> </c:when>
                <c:when test="${foodVO.isSelling == 2}"> <td class="isSelling" style="color:blue;">발주 준비</td> </c:when>
            </c:choose>
            <td><a href="../image/foodThumbnail?foodId=${foodVO.foodId}" target="_blank">대표 사진 보기</a></td>
            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <td><a href="update?foodId=${foodVO.foodId}">수정</a></td>
                <td class="delete"><a href="delete?foodId=${foodVO.foodId}">삭제</a></td>
            </sec:authorize>
            <sec:authorize access="hasRole('ROLE_OWNER')">
                <td><input class="foodAmount" type="number" min="1" max="${foodVO.foodStock}"><button class="insert">발주</button></td>
            </sec:authorize>
        </tr>
        </c:forEach>
    </tbody>
</table>
<form id="listForm" action="list" method="get">
        <input type="hidden" name="pageNum">
        <input type="hidden" name="pageSize">
    </form>

    <ul>
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

<script type="text/javascript">
$(document).ready(function () {
    $(document).ajaxSend(function (e, xhr, opt) {
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        xhr.setRequestHeader(header, token);
    });

    $(".foodRow").on('click', 'td .insert', function () {
        var foodAmount = $(this).prev().val();
        var foodId = $(this).parents().prevAll('.foodId').text();
        var foodStock = $(this).parents().prevAll('.foodStock').text();
        var isSelling = $(this).parents().prevAll('.isSelling').text();

        if (isSelling != '발주 가능') {
            alert("발주 가능한 상품이 아닙니다.");
            return;
        }

        if (foodAmount.trim() === "" || isNaN(foodAmount) || foodAmount <= 0) {
            alert("발주 수량은 0보다 큰 숫자를 입력해주세요.");
            return;
        }

        if (Number(foodAmount) > Number(foodStock)) {
            alert("재고량보다 발주량이 많습니다.");
            return;
        }

        $.ajax({
            type: 'GET',
            url: "../convenienceFood/register",
            data: { foodId: foodId, foodAmount: foodAmount },
            success: function (result) {
                if (result == 1) {
                    alert("발주 처리에 성공했습니다.");
                } else {
                    alert("발주 처리에 실패했습니다.");
                }
                location.reload();
            },
            error: function () {
                alert("발주 처리에 실패했습니다.");
            }
        });
    });

    $(".foodRow").on('click','.delete',function(e){
        e.preventDefault();
        var foodId = $(this).prevAll().eq(10).html();
        var isSelling = $(this).prevAll('.isSelling').text();

        if(isSelling != '발주 중지'){
            alert("발주 중지 중인 상품이 아닙니다.");
            return;
        }

        $.ajax({
            type : 'post',
            contentType: "application/json",
            url : 'checkdelete',
            data : foodId,
            success : function(result){
                if(result == 'success'){
                    $.ajax({
                        type : 'post',
                        url : '../image/remove2',
                        data : {"foreignId" : foodId},
                        success : function(result){
                            location.href='../food/delete?foodId=' + foodId;
                        },
                        error: function(){
                            alert("이미지 삭제에 실패했습니다.");
                        }
                    });
                }
            },
            error: function(){
                alert("삭제 확인에 실패했습니다.");
            }
        });
    });
    
    $(".pagination_button a").on("click", function(e) {
        var listForm = $("#listForm");
        e.preventDefault();

        var pageNum = $(this).attr("href");
        var pageSize = "<c:out value='${pageMaker.pagination.pageSize }' />";

        listForm.find("input[name='pageNum']").val(pageNum);
        listForm.find("input[name='pageSize']").val(pageSize);
        listForm.submit();
    });
});
</script>
<%@ include file="../common/footer.jsp"%>
</body>
</html>
