<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
        	var pageNum = "<c:out value='${pageMaker.pagination.pageNum }' />";
            var pageSize = "<c:out value='${pageMaker.pagination.pageSize }' />";
        	
            getAllPreorder(pageNum,pageSize);
            checkToCheckBox();
            onCancellButton();
            onDeletePreorder();

            //회원이 예약한 식품들 불러오는 코드
            function getAllPreorder(pageNum,pageSize){
                let URL = "../preorder/all/"+ "${memberId}";
                
                
                console.log(pageNum);
                
                $.ajax({
                	type : "get",
                	url : URL,
                	data : {"pageNum": pageNum, "pageSize": pageSize},
                	success : function(data){
                		 let list = '';                        
                         $(data).each(function(){
                             let pickUpDate = new Date(this.pickupDate);
                             
                             let StringDate = formatDate(pickUpDate);
         
                             let foodId = this.foodId;
                             
                             let preorderNO = this.preorderId;
                             
                             let convenienceId = this.convenienceId;
                             
                             let address = this.address;
                             
                             let isPickUp = ' style="color:gray;">미수령';
                             
                             let isExpiredOrder = ' style="color:gray;">예약 중';
                             
                             if(this.isPickUp == 1){
                                 isPickUp = ' style="color:green;">수령 완료';
                                 isExpiredOrder = ' style="color:blue;">예약 종료';
                             }
                             
                             if(this.isExpiredOrder == 1){
                                 isPickUp = ' style="color:red;">수령 취소';
                                 isExpiredOrder = ' style="color:red;">예약 취소';
                             }
                             
                             list += '<div class="preorderList">';
                                 if(isExpiredOrder == ' style="color:gray;">예약 중'){
                                     list += '<input type="checkbox" class="check-box">';
                                 }
                             list += '<div class="preorderNO"><strong style="font-size:25px;">'+preorderNO+'</strong></div>'
                                     + '<div><img src="../image/foodThumbnail?foodId='+foodId+'" style="width:150px; height=150px;"></div>'
                                     + '<div><strong style="font-size:18px;">'+ StringDate+'</strong><br>까지 수령</div>'
                                     + '<div><span style="color:gray;">'+ address +'</span></div>'
                                     + '<div>예약 수량 : '+this.preorderAmount+'개</div>'
                                     + '<span'+isPickUp+', </span>'
                                     + '<span class="isExpriedOrder"'+isExpiredOrder+'</span><br><br>'
                                     if (this.writeReview == 0 && this.isPickUp == 1) {
                                     	let onclick = '\"location.href=\'../review/register?foodId='+foodId+'&convenienceId='+convenienceId+'&preorderId='+preorderNO+'\'\"';
                                     	list += '<button onclick=' + onclick + '>리뷰 작성</button>';
                                     }
                             list += '</div>';
                         });
                         $('#list').html(list);	
                	}
                });
            }
            
            $(".pagination_button a").on("click", function (e) {
                e.preventDefault();

                var pageNum = $(this).attr("href").split("=")[1];
                var pageSize = "<c:out value='${pageMaker.pagination.pageSize }' />";
                
                getAllPreorder(pageNum,pageSize);
                
                $(".pagination_button").removeClass("current");
                $(".pagination_button a[href='?pageNum=" + pageNum + "']").parent().addClass("current");
            });

            
            //각 예약 식품 항목 중 아무거나 누르면 체크박스에 활성화/비활성화 하는 코드
            function checkToCheckBox(){
                $('#list').on('click', '.preorderList', function(){
                    let isChecked = $(this).find("input[type='checkbox']").prop('checked');
                    $(this).find("input[type='checkbox']").prop('checked', !isChecked);
                });
                
                $('#list').on('click', '.preorderList .check-box',function(){
                     let isChecked = $(this).prop('checked');
                     $(this).prop('checked', !isChecked);
                });
            }

            function onCancellButton(){
                $('#cancel').click(function(){
                	var pageNum = "<c:out value='${pageMaker.pagination.pageNum }' />";
                    var pageSize = "<c:out value='${pageMaker.pagination.pageSize }' />";
                    var isCancel = confirm("취소하시겠습니까?");
                    if(isCancel){
                        let selectedPreordersId = [];
                        
                        $(".preorderList").each(function(){
                            if ($(this).find("input[type='checkbox']").prop('checked')) {
                            	let preorderNO = $(this).find(".preorderNO").text();
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
                                     getAllPreorder(pageNum,pageSize);
                                 }
                             }
                         });
                    }
                });
            }

            function onDeletePreorder(){
            	var pageNum = "<c:out value='${pageMaker.pagination.pageNum }' />";
                var pageSize = "<c:out value='${pageMaker.pagination.pageSize }' />";
                $('#delete').click(function(){
                    let cancelledPreorderId = [];
                    $(".preorderList").each(function(){
                        if ($(this).find(".isExpriedOrder").text() == "예약 취소") {
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
                                 getAllPreorder(pageNum,pageSize);
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
                
                let toStringDate = yyyy + "년 " + mm + "월 " + dd + "일";
                
                return toStringDate;
            }

	        $("#checkAllBox").click(function(){
    	    	console.log("클릭");
        		$('.check-box').prop('checked', true);
       		});
        });
    </script>
    <title>예약 내역</title>

<style>
body {
	font-family: 'Pretendard-Regular', sans-serif;
	margin: 0;
	padding: 15px;
	background-color: #f8f9fa;
	text-align: center;
}

h1, h2 {
	color: #333;
}

#list {
	max-width: 1400px;
	display: flex;
	flex-wrap: wrap;
	justify-content: center;
	gap: 20px;
	margin: 0 auto;
}

.preorderList {
	width: 200px;
	height: auto;
	border: 1px solid #ccc;
	padding: 15px;
	background-color: #fff;
	text-align: center;
	border-radius: 10px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	transition: transform 0.3s, box-shadow 0.3s;
	position: relative;
}

.preorderList input[type="checkbox"] {
	position: absolute;
	top: 10px;
	left: 10px;
}

.preorderList img {
	width: 100%;
	height: auto;
	border-radius: 8px;
	margin-bottom: 10px;
}

.preorderList div {
	margin-bottom: 10px;
	position: relative;
}

.preorderList .pickup-info, .preorderList .quantity-info, .preorderList .status-info
	{
	position: relative;
	margin-bottom: 10px;
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

button:hover, input[type="button"]:hover {
	background: #bbb;
}

#checkAllBox {
	margin-bottom: 10px;
}

.button-container {
	text-align: center;
	margin-top: 15px;
}

/* 페이징 스타일 */
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
</style>
</head>
<%@ include file="../common/header.jsp" %>
<body>
    <h1>예약 내역</h1>
    <button id="checkAllBox">전체 선택</button>
    <div id="list"></div>
    <br>
    <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
    <div class="button-container">
        <button id="cancel">예약 취소</button>
        <button id="delete">예약 취소 목록 삭제</button>
        <button onclick="history.back()">돌아가기</button>
    </div>
    
    <ul id="paginationList">
        <c:if test="${pageMaker.isPrev()}">
            <li class="pagination_button"><a href="${pageMaker.startNum - 1}">이전</a></li>
        </c:if>
        <c:forEach begin="${pageMaker.startNum}" end="${pageMaker.endNum}" var="num">
<li class="pagination_button <c:if test='${num == pageMaker.pagination.pageNum}'>current</c:if>">
    <a href="?pageNum=${num}">${num}</a>
</li>

		</c:forEach>
        <c:if test="${pageMaker.isNext()}">
            <li class="pagination_button"><a href="${pageMaker.endNum + 1}">다음</a></li>
        </c:if>
    </ul>
    
<%@ include file="../common/footer.jsp"%>    
</body>
</html>