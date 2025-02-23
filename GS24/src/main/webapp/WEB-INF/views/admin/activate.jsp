<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>권한 요청 승인</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        /* 기본 스타일 */
        .container {
            display: flex;
            justify-content: space-between;
        }

        .left-panel, .right-panel {
            width: 45%;
            padding: 20px;
            border: 1px solid #ccc;
            height: 300px;
            overflow-y: auto;
        }

        .item {
            margin: 10px 0;
        }

        .approved-item {
            background-color: #d3f9d8; /* 승인된 항목 배경 */
        }

        .select-all {
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
<%@ include file="../common/header.jsp" %>
    <div class="container">
        <!-- 왼쪽: 요청 목록 -->
        <div class="left-panel">
            <h3>비활성화 해제 요청 점주 목록</h3>
            <div class="select-all">
                <input type="checkbox" id="select-all"> <label for="select-all">전체 선택</label>
            </div>
            <div id="request-list">
                <!-- 여기에 권한 요청 목록이 동적으로 들어옵니다. -->
                <div class="item">
                    <input type="checkbox" class="item-checkbox">
                    <span>사용자1</span>
                </div>
                <div class="item">
                    <input type="checkbox" class="item-checkbox">
                    <span>사용자2</span>
                </div>
                <!-- 추가적인 요청 목록이 동적으로 들어올 수 있습니다. -->
            </div>
            <button id="approve-selected" style="margin-top: 10px;">선택된 요청 승인</button>
        </div>

        <!-- 오른쪽: 승인된 항목 -->
        <div class="right-panel">
            <h3>승인된 점주</h3>
            <div id="approved-list">
                <!-- 승인된 항목들이 이곳에 추가됩니다. -->
            </div>
        </div>
    </div>

    <script>
        $(document).ready(function() {
            // 전체 선택 체크박스 처리
            $('#select-all').change(function() {
                var isChecked = $(this).prop('checked');
                $('.item-checkbox').prop('checked', isChecked); // 전체 선택 / 해제
            });

            // 선택된 항목을 오른쪽으로 옮기고 승인 버튼 클릭 처리
            $('#approve-selected').click(function() {
                // 체크된 항목들
                var selectedItems = $('.item-checkbox:checked').closest('.item');

                // 선택된 항목들을 오른쪽 목록으로 옮기기
                selectedItems.each(function() {
                    var item = $(this).clone();
                    item.addClass('approved-item'); // 승인된 항목 스타일 추가
                    item.find('.item-checkbox').remove(); // 체크박스 제거
                    $('#approved-list').append(item); // 오른쪽 목록에 추가
                });

                // 왼쪽 목록에서 선택된 항목 삭제
                $('.item-checkbox:checked').closest('.item').remove();
            });
        });
    </script>
<%@ include file="../common/footer.jsp" %>
</body>
</html>
