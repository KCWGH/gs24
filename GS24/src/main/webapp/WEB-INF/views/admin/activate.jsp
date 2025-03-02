<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="_csrf" content="${_csrf.token}" />
    <meta name="_csrf_header" content="${_csrf.headerName}" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>비활성화 해제 승인</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<style>
body {
	margin: 0;
	padding: 15px;
	text-align: center;
	background-color: #f8f9fa;
}

.container {
	margin-top: 50px;
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 30px;
    width: 100%;
    height: 100%;
}

.left-panel, .right-panel {
	position: relative;
	width: 300px;
	height: 400px;
	padding: 15px;
	border: 1px solid #ccc;
	border-radius: 5px;
	overflow-y: auto;
	background: white;
}

#update {
	position: absolute;
	bottom: 15px;
	left: 83%;
	transform: translateX(-50%);
	width: 80px;
	height: 40px;
}

.middle-panel {
	display: flex;
	flex-direction: column;
	align-items: center;
}

.approve-btn {
	display: flex;
	align-items: center;
	justify-content: center;
	background: #ddd;
	border: none;
	padding: 10px;
	cursor: pointer;
	font-size: 16px;
	border-radius: 5px;
	margin-top: 10px;
	width: 80px;
	height: 40px;
}

.approve-btn:hover {
	background: #bbb;
}

.item {
	display: flex;
	align-items: center;
	justify-content: space-between;
	gap: 10px;
	padding: 5px;
	border-bottom: 1px solid #ddd;
	cursor: pointer;
}

.remove-btn {
	cursor: pointer;
	color: red;
	border: none;
	background: none;
	font-weight: bold;
}

#reset-approved {
	position: absolute;
	bottom: 15px;
	left: 20%;
	transform: translateX(-50%);
	width: 100px;
	height: 40px;
	background: #f8d7da;
	color: #721c24;
}

#reset-approved:hover {
	background: #f5c6cb;
}

#select-all-btn {
	display: block;
	margin-bottom: 10px;
	width: 100px;
	height: 30px;
	background: #d4edda;
	color: #155724;
	border: none;
	border-radius: 5px;
	cursor: pointer;
	font-size: 14px;
	text-align: center;
	line-height: 30px;
	padding: 0;
}

#select-all-btn:hover {
	background: #c3e6cb;
}

.left-panel h2,
.right-panel h2 {
    text-align: center;
}
#noRequests {
	text-align: center;
}
</style>
</head>
<body>
<%@ include file="../common/header.jsp" %>
<h1>비활성화 해제 승인</h1>
<div class="container">
    <div class="left-panel">
    <h2>재활성화 요청 점주 목록</h2>
    <div id="request-list">
    <c:choose>
        <c:when test="${not empty ownerIds}">
    <button id="select-all-btn" class="approve-btn">전체 선택</button> <!-- 텍스트 아래로 이동 -->
            <c:forEach var="ownerId" items="${ownerIds}">
                <div class="item">
                    <input type="checkbox" class="item-checkbox">
                    <span>${ownerId}</span>
                </div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <p id="noRequests">재활성화 요청이 없습니다.</p>
        </c:otherwise>
    </c:choose>
	</div>
	</div>

    <div class="middle-panel">
        <button id="approve-selected" class="approve-btn">→</button>
    </div>

    <div class="right-panel">
    	<h2>승인된 점주</h2>
    	<div id="approved-list"></div>
    	<button id="reset-approved" class="approve-btn">전체 해제</button> <!-- 추가된 버튼 -->
    	<button type="submit" id="update" class="approve-btn">확인</button>
	</div>
</div>

<script>
$(document).ready(function() {
    $(document).ajaxSend(function(e, xhr, opt){
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        xhr.setRequestHeader(header, token);
    });
    
    let isAllSelected = false;

    $('#select-all-btn').click(function() {
        isAllSelected = !isAllSelected;
        $('.item-checkbox').prop('checked', isAllSelected);

        if (isAllSelected) {
            $(this).text('전체 해제');
        } else {
            $(this).text('전체 선택');
        }
    });
    
    $(document).on('click', '#reset-approved', function() {
        $('#approved-list .item').each(function() {
            var item = $(this);
            item.find('.remove-btn').remove();
            item.prepend('<input type="checkbox" class="item-checkbox">');
            $('#request-list').append(item);
        });
    });

    $('#approve-selected').click(function() {
        var selectedItems = $('.item-checkbox:checked').closest('.item');

        selectedItems.each(function() {
            var item = $(this).clone();
            item.addClass('approved-item');
            item.find('.item-checkbox').remove();
            
            var removeBtn = $('<button class="remove-btn">X</button>');
            removeBtn.click(function() {
                var item = $(this).closest('.item');
                item.find('.remove-btn').remove();
                item.prepend('<input type="checkbox" class="item-checkbox">');
                $('#request-list').append(item);
            });

            item.append(removeBtn);
            $('#approved-list').append(item);
        });

        $('.item-checkbox:checked').closest('.item').remove();

        isAllSelected = false;
        $('#select-all-btn').text('전체 선택');
        $('.item-checkbox').prop('checked', false);
    });

    $(document).on('click', '.item', function(e) {
        if ($(e.target).is('input[type="checkbox"], .remove-btn')) {
            e.stopPropagation();
            return;
        }
        
        var checkbox = $(this).find('.item-checkbox');
        checkbox.prop('checked', !checkbox.prop('checked')).trigger('change');
    });

    $(document).on('click', '.remove-btn', function() {
        var item = $(this).closest('.item');
        item.find('.remove-btn').remove();
        item.prepend('<input type="checkbox" class="item-checkbox">');
        $('#request-list').append(item);
    });

    $('#update').click(function() {
        var approvedIds = [];
        
        $('#approved-list .item').each(function() {
            var ownerId = $(this).find('span').text();
            approvedIds.push(ownerId);
        });
        
        if (approvedIds.length === 0) {
            alert('승인할 점주가 없습니다.');
            return;
        }

        $.ajax({
            url: '../admin/activate',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ ownerIds: approvedIds }),
            success: function(response) {
                alert('승인 완료!');
                $('#approved-list').empty();
                window.location.reload();
            },
            error: function(xhr, status, error) {
                alert('승인 실패: ' + error);
            }
        });
    });
});
</script>
<%@ include file="../common/footer.jsp" %>
</body>
</html>