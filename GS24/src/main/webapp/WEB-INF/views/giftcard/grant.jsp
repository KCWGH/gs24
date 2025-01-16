<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<title>기프트카드 제공</title>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<script type="text/javascript">
$(document).ajaxSend(function(e, xhr, opt){
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    
    xhr.setRequestHeader(header, token);
 });
let isIdChecked = false;

function checkId(event) {
  event.preventDefault();
  let memberId = $('#memberId').val();
  if (!memberId) {
    alert('회원 아이디를 입력해주세요.');
    return;
  }
  $.ajax({
    url: 'dup-check-id',
    type: 'POST',
    data: { memberId: memberId },
    success: function(response) {
      if (response == 1) {
        alert('존재하는 아이디입니다.');
        isIdChecked = true;
      } else {
        alert('존재하지 않는 아이디입니다.');
        isIdChecked = false;
      }
      updateSubmitButton();
    },
    error: function() {
      alert('중복 확인 중 오류가 발생했습니다.');
    }
  });
}

function updateSubmitButton() {
  if (isIdChecked) {
    $('#btnGrant').prop('disabled', false);
  } else {
    $('#btnGrant').prop('disabled', true);
  }
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
    $('#giftCardExpiredDate').val(formattedDate);
  } else {
    console.error('잘못된 날짜입니다.');
  }
}

function setBalance(value) {
  $("input[name='balance']").val(value);
}

$(document).ready(function() {
  let currentDate = new Date();
  let year = currentDate.getFullYear();
  let month = String(currentDate.getMonth() + 1).padStart(2, '0');
  let day = String(currentDate.getDate()).padStart(2, '0');
  let hours = String(currentDate.getHours()).padStart(2, '0');
  let minutes = String(currentDate.getMinutes()).padStart(2, '0');
  let minFormattedDate = year + '-' + month + '-' + day + 'T' + hours + ':' + minutes;
  $('#giftCardExpiredDate').attr('min', minFormattedDate);
});
</script>
</head>
<body>
<c:if test="${not empty message}">
        <script type="text/javascript">
            alert("${message}");
        </script>
    </c:if>
<h2>개별 기프트카드 제공</h2>
<form action="grant" method="POST">
<table>
	<tr>
		<th><label for="giftCardName">기프트카드 이름</label></th>
		<td><input type="text" id="giftCardName" name="giftCardName"></td>
	</tr>
	<tr>
		<td colspan="2">※ 기입하지 않으면 기본 기프트카드 이름으로 제공됩니다.<br>
    	<span style="color:gray">예) 음료 10000원 금액권</span></td>
	</tr>
	<tr>
		<th><label for="memberId">아이디</label></th>
		<td><input type="text" id="memberId" name="memberId" required><button type="button" onclick="checkId(event)">아이디 체크</button></td>
	</tr>
	<tr>
		<th><label for="foodType">음식 종류</label></th>
		<td><select id="foodType" name="foodType" required>
    <option value="" selected disabled >선택하세요</option>
      <c:forEach var="food" items="${foodTypeList}">
        <option value="${food}">${food}</option>
      </c:forEach>
    </select></td>
	</tr>
	<tr>
		<th><label for="discountValue">금액</label></th>
		<td><input type="number" name="balance" min="1000" step="1" required> 원</td>
	</tr>
	<tr>
		<th></th>
		<td><button type="button" onclick="setBalance(1000)">1000원</button>
		<button type="button" onclick="setBalance(3000)">3000원</button>
		<button type="button" onclick="setBalance(5000)">5000원</button>
		<button type="button" onclick="setBalance(10000)">10000원</button>
		<button type="button" onclick="setBalance(30000)">30000원</button></td>
	</tr>
	<tr>
		<th><label for="giftCardExpiredDate">유효기간</label></th>
		<td><input type="datetime-local" id="giftCardExpiredDate" name="giftCardExpiredDate" required></td>
	</tr>
	<tr>
		<th></th>
		<td><button type="button" onclick="setExpirationDate(1)">하루</button>
    <button type="button" onclick="setExpirationDate(3)">3일</button>
    <button type="button" onclick="setExpirationDate(7)">1주일</button>
    <button type="button" onclick="setExpirationDate(30)">1개월</button>
    <button type="button" onclick="setExpirationDate(365)">1년</button></td>
	</tr>
</table>
<br>
<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
    <button type="submit" id="btnGrant" disabled>기프트카드 제공</button>
    <a href="list"><button type="button">기프트카드 리스트로 돌아가기</button></a>
</form>

</body>
</html>
