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
    <title>재활성화 승인</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        .container {
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 30px;
            margin-top: 250px;
        }
        .left-panel, .right-panel {
        	position: relative;
            width: 300px;
            height: 400px; /* 높이 고정 */
            padding: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
            background: #f9f9f9;
            overflow-y: auto; /* 스크롤 추가 */
        }
        #update {
    		position: absolute;
    		bottom: 15px;
    		left: 80%;
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
            font-size: 18px;
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
    </style>
</head>
<body>
<%@ include file="../common/header.jsp" %>
<div class="container">
    <div class="left-panel">
        <h3>비활성화 해제 요청 점주 목록</h3>
        <div class="select-all">
            <input type="checkbox" id="select-all"> <label for="select-all">전체 선택</label>
        </div>
        <div id="request-list">
            <c:forEach var="ownerId" items="${ownerIds}">
                <div class="item">
                    <input type="checkbox" class="item-checkbox">
                    <span>${ownerId}</span>
                </div>
            </c:forEach>
        </div>
    </div>

    <div class="middle-panel">
        <button id="approve-selected" class="approve-btn">→</button>
    </div>

    <div class="right-panel">
        <h3>승인된 점주</h3>
        <div id="approved-list"></div>
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

    $('#select-all').change(function() {
        var isChecked = $(this).prop('checked');
        $('.item-checkbox').prop('checked', isChecked);
    });

    $('#approve-selected').click(function() {
        var selectedItems = $('.item-checkbox:checked').closest('.item');

        selectedItems.each(function() {
            var item = $(this).clone();
            item.addClass('approved-item');
            item.find('.item-checkbox').remove();
            
            var removeBtn = $('<button class="remove-btn">X</button>');
            removeBtn.click(function() {
                $('#request-list').append(item);
                item.find('.remove-btn').remove();
                item.prepend('<input type="checkbox" class="item-checkbox">');
                $(this).remove();
            });

            item.append(removeBtn);
            $('#approved-list').append(item);
        });

        $('.item-checkbox:checked').closest('.item').remove();
    });

    $('.item').click(function(e) {
        if ($(e.target).is('input[type="checkbox"], .remove-btn')) {
            e.stopPropagation();
            return;
        }
        
        var checkbox = $(this).find('.item-checkbox');
        checkbox.prop('checked', !checkbox.prop('checked')).trigger('change');
    });

    $('#update').click(function() {
        var approvedIds = [];
        
        $('#approved-list .item').each(function() {
            var ownerId = $(this).find('span').text();
            approvedIds.push(ownerId);
        });

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