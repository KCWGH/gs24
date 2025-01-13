<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>쿠폰 발행</title>
    <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
    <script type="text/javascript">
        function setAmount(value) {
            $("input[name='couponAmount']").val(value);
        }

        function setDiscountValue(value) {
            $("input[name='discountValue']").val(value);
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
                const selectedChoice = $("input[name='discountType']:checked").val();
                if (selectedChoice == 'P') {
                    $('#amountLabel, #amountValue').hide();
                    $('#percentLabel, #percentValue').show();
                } else if (selectedChoice == 'A') {
                    $('#percentLabel, #percentValue').hide();
                    $('#amountLabel, #amountValue').show();
                }
            });
        });
    </script>
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
        <table>
            <tr>
                <th><label for="couponName">쿠폰 이름</label></th>
                <td><input type="text" id="couponName" name="couponName"></td>
            </tr>
            <tr>
                <td colspan="2"><span style="color:gray">쿠폰 이름을 적지 않으면 기본 쿠폰 이름으로 제공됩니다.<br>예) 음료 1000원 할인권</span></td>
            </tr>
            <tr>
                <th><label for="foodType">음식 종류</label></th>
                <td><select id="foodType" name="foodType" required>
                    <option value="" selected disabled>선택하세요</option>
                    <c:forEach var="food" items="${foodTypeList}">
                        <option value="${food}">${food}</option>
                    </c:forEach>
                </select></td>
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
                <td><input type="number" name="discountValue" required> %</td>
            </tr>
            <tr id="percentValue" hidden="hidden">
                <th></th>
                <td>
                    <button type="button" onclick="setDiscountValue(5)">5%</button>
                    <button type="button" onclick="setDiscountValue(10)">10%</button>
                    <button type="button" onclick="setDiscountValue(20)">20%</button>
                    <button type="button" onclick="setDiscountValue(30)">30%</button>
                    <button type="button" onclick="setDiscountValue(50)">50%</button>
                </td>
            </tr>
            <tr id="amountLabel" hidden="hidden">
                <th><label for="discountValue">할인액</label></th>
                <td><input type="number" name="discountValue" required> 원</td>
            </tr>
            <tr id="amountValue" hidden="hidden">
                <th></th>
                <td>
                    <button type="button" onclick="setDiscountValue(1000)">1000원</button>
                    <button type="button" onclick="setDiscountValue(3000)">3000원</button>
                    <button type="button" onclick="setDiscountValue(5000)">5000원</button>
                    <button type="button" onclick="setDiscountValue(10000)">10000원</button>
                </td>
            </tr>
            <tr>
                <th><label for="couponAmount">수량</label></th>
                <td><input type="number" name="couponAmount" required> 매</td>
            </tr>
            <tr>
                <th></th>
                <td>
                    <button type="button" onclick="setAmount(5)">5매</button>
                    <button type="button" onclick="setAmount(10)">10매</button>
                    <button type="button" onclick="setAmount(50)">50매</button>
                    <button type="button" onclick="setAmount(100)">100매</button>
                    <button type="button" onclick="setAmount(500)">500매</button>
                </td>
            </tr>
            <tr>
                <th><label for="couponExpiredDate">유효기간</label></th>
                <td><input type="datetime-local" id="couponExpiredDate" name="couponExpiredDate"></td>
            </tr>
            <tr>
                <th></th>
                <td>
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
                <td colspan="2"></td>
            </tr>
        </table>
        <div>
            <label for="isDuplicateAllowed">기프트카드와 중복 사용 가능 여부: </label>
            <label><input type="radio" name="isDuplicateAllowed" value="1" required> 예</label>
            <label><input type="radio" name="isDuplicateAllowed" value="0"> 아니오</label>
        </div>
        <br>
        <button type="submit" id="btnPublish">쿠폰 발행</button>
    </form>
</body>
</html>