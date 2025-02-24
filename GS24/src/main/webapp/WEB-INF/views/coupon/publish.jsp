<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <title>쿠폰 발행</title>
    <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
    <script type="text/javascript">
    	function checkByteLimit(input, maxBytes) {
        	let str = input.value;
        	let totalBytes = 0;
        	let truncatedStr = "";

        	for (let i = 0; i < str.length; i++) {
            	let char = str.charAt(i);
            	let charCode = char.charCodeAt(0);
            	let charBytes = (charCode > 127) ? 3 : 1;

            	if (totalBytes + charBytes > maxBytes) {
                	alert("입력 가능한 최대 글자 수를 초과했습니다! (한글 약 33자, 영문 100자)");
                	input.value = truncatedStr;
                	return;
            	}

            	totalBytes += charBytes;
            	truncatedStr += char;
        	}

        	input.value = str;
    	}
    
        function setCouponAmount(value) {
            $("input[name='couponAmount']").val(value);
        }

        function setPercentage(value) {
            $("input[name='percentage']").val(value);
        }

        function setAmount(value) {
            $("input[name='amount']").val(value);
        }

        function setExpirationDate(days) {
            let currentDate = new Date();
            currentDate.setDate(currentDate.getDate() + days);
            let year = currentDate.getFullYear();
            let month = String(currentDate.getMonth() + 1).padStart(2, '0');
            let day = String(currentDate.getDate()).padStart(2, '0');
            let hours = String(currentDate.getHours()).padStart(2, '0');
            let minutes = String(currentDate.getMinutes()).padStart(2, '0');
            let formattedDate = year + '-' + month + '-' + day + 'T' + hours + ':' + minutes;
            if (!isNaN(currentDate.getTime())) {
                $('#couponExpiredDate').val(formattedDate);
            } else {
                console.error('잘못된 날짜입니다.');
            }
        }

        $(document).ready(function() {
            let currentDate = new Date();
            let year = currentDate.getFullYear();
            let month = String(currentDate.getMonth() + 1).padStart(2, '0');
            let day = String(currentDate.getDate()).padStart(2, '0');
            let hours = String(currentDate.getHours()).padStart(2, '0');
            let minutes = String(currentDate.getMinutes()).padStart(2, '0');
            let minFormattedDate = year + '-' + month + '-' + day + 'T' + hours + ':' + minutes;
            $('#couponExpiredDate').attr('min', minFormattedDate);

            $("input[name='discountType']").change(function() {
                let selectedChoice = $("input[name='discountType']:checked").val();
                if (selectedChoice == 'P') {
                    $('#amountLabel').prop('disabled', true).hide();
                    $('#percentLabel').prop('disabled', false).show();
                    $('#inputPercent').prop('disabled', false);
                    $('#inputAmount').prop('disabled', true);
                } else if (selectedChoice == 'A') {
                    $('#percentLabel').prop('disabled', true).hide();
                    $('#amountLabel').prop('disabled', false).show();
                    $('#inputAmount').prop('disabled', false);
                    $('#inputPercent').prop('disabled', true);
                }
            });
        });
    </script>
    <style>
        /* 전체 페이지 스타일 */
        body {
            margin: 0;
            background-color: #f8f9fa;
            text-align: center;
        }

        /* 컨테이너 스타일 */
        .container {
            max-width: 100%;
            padding: 10px;
            background: white;
            border-radius: 10px;
            box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1);
            text-align: center;
        }

        /* 제목 스타일 */
        h2 {
            color: #333;
            margin-bottom: 10px;
        }

        /* 테이블 스타일 */
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }

        th, td {
            padding: 8px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #f1f1f1;
            color: #333;
            font-size: 14px;
            text-align: center;
        }

        td {
            font-size: 14px;
        }

        /* 입력 필드 스타일 */
        input[type="text"], input[type="email"], input[type="number"], input[type="datetime-local"] {
            width: 60%;
            padding: 5px;
            border: 1px solid #ddd;
            border-radius: 5px;
            text-align: center;
            font-size: 13px;
        }

        select {
            width: 60%;
            padding: 5px;
            border: 1px solid #ddd;
            border-radius: 5px;
            text-align: center;
            font-size: 13px;
        }

        /* 버튼 스타일 */
        button {
            padding: 7px 10px;
            font-size: 13px;
            border: none;
            background: #ddd;
            border-radius: 5px;
            cursor: pointer;
            margin: 5px;
        }

        button:hover {
            background: #bbb;
        }

        /* 버튼 크기 통일 */
        .btn-container button {
            width: 120px; /* 모든 버튼의 너비를 동일하게 */
            padding: 10px; /* 패딩을 조정하여 크기 균일화 */
            font-size: 14px; /* 글자 크기 통일 */
            text-align: center;
        }
    </style>
</head>
<body>
    <c:if test="${not empty message}">
        <script type="text/javascript">
            alert("${message}");
        </script>
    </c:if>
    
    <h2>쿠폰 발행</h2>
    <p>유효기간 이후 30일이 경과한 쿠폰은 자동 삭제됩니다.</p>

    <form action="publish" method="POST">
        <div class="container">
            <table>
                <tr>
                    <th><label for="couponName">쿠폰 이름</label></th>
                    <td><input type="text" id="couponName" name="couponName" oninput="checkByteLimit(this, 100)"></td>
                </tr>
                <tr>
                    <td colspan="2"><span style="color:gray">쿠폰 이름을 적지 않으면 기본 쿠폰 이름으로 제공됩니다.<br>예) 음료 1000원 할인권</span></td>
                </tr>
                <tr>
                    <th><label for="foodType">음식 종류</label></th>
                    <td>
                        <select id="foodType" name="foodType" required>
                            <option value="" selected disabled>선택하세요</option>
                            <c:forEach var="food" items="${foodTypeList}">
                                <option value="${food}">${food}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td colspan="2"><span style="color:gray">현재 존재하는 음식 종류만 표시됩니다.</span></td>
                </tr>
                <tr>
                    <th><label for="discountType">쿠폰 종류</label></th>
                    <td>
                        <label><input type="radio" name="discountType" value="P" required> 비율권</label>
                        <label><input type="radio" name="discountType" value="A"> 정액권</label>
                    </td>
                </tr>
                <tr id="percentLabel" hidden="hidden">
                    <th><label for="discountValue">할인율</label></th>
                    <td><input type="number" id="inputPercent" name="percentage" min=5 max=50 required> %<br>
                    <button type="button" onclick="setPercentage(5)">5%</button>
                        <button type="button" onclick="setPercentage(10)">10%</button>
                        <button type="button" onclick="setPercentage(20)">20%</button>
                        <button type="button" onclick="setPercentage(30)">30%</button>
                        <button type="button" onclick="setPercentage(50)">50%</button><br>
                        <span style="color:gray">5% ~ 50% 사이의 숫자를 입력하세요.</span></td>
                </tr>
                <tr id="amountLabel" hidden="hidden">
                    <th><label for="discountValue">할인액</label></th>
                    <td><input type="number" id="inputAmount" name="amount" disabled min=1000 max=10000 required> 원<br>
                    <button type="button" onclick="setAmount(1000)">1000원</button>
                        <button type="button" onclick="setAmount(3000)">3000원</button>
                        <button type="button" onclick="setAmount(5000)">5000원</button>
                        <button type="button" onclick="setAmount(10000)">10000원</button><br>
                        <span style="color:gray">1000원 ~ 10000원 사이의 숫자를 입력하세요.</span></td>
                </tr>
                <tr>
                    <th><label for="couponAmount">수량</label></th>
                    <td><input type="number" name="couponAmount" required min=5 max=500> 매<br>
                    	<button type="button" onclick="setCouponAmount(5)">5매</button>
                        <button type="button" onclick="setCouponAmount(10)">10매</button>
                        <button type="button" onclick="setCouponAmount(50)">50매</button>
                        <button type="button" onclick="setCouponAmount(100)">100매</button>
                        <button type="button" onclick="setCouponAmount(500)">500매</button><br>
                        <span style="color:gray">5매 ~ 500매 사이의 숫자를 입력하세요.</span>
                    </td>
                </tr>
                <tr>
                    <th><label for="couponExpiredDate">유효기간</label></th>
                    <td><input type="datetime-local" id="couponExpiredDate" name="couponExpiredDate"><br>
                    	<button type="button" onclick="setExpirationDate(1)">하루</button>
                        <button type="button" onclick="setExpirationDate(3)">3일</button>
                        <button type="button" onclick="setExpirationDate(7)">1주일</button>
                        <button type="button" onclick="setExpirationDate(30)">1개월</button>
                        <button type="button" onclick="setExpirationDate(365)">1년</button>
                    </td>
                </tr>
                <tr>
                    <td colspan="2"><span style="color:gray">유효기간을 지정하지 않으면 모두 소진될 때까지 제공됩니다.</span></td>
                </tr>
                <tr>
                	<th><label for="isDuplicateAllowed">기프트카드<br>중복 사용<br>가능 여부</label></th>
                	<td><label><input type="radio" name="isDuplicateAllowed" value="1" required> 예</label>
                <label><input type="radio" name="isDuplicateAllowed" value="0"> 아니오</label></td>
                </tr>      
            </table>
            <button type="submit" id="btnPublish">쿠폰 발행</button>
        </div>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    </form>
</body>
</html>
