<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>기프트카드 제공</title>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<script type="text/javascript">
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

function setDiscountValue(value) {
  $("input[name='discountValue']").val(value);
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

    let discountFormHTML = '';
      discountFormHTML += '<div><label for="discountValue">금액: </label>';
      discountFormHTML += '<input type="number" name="discountValue" required> 원<br>';
      discountFormHTML += '<button type="button" onclick="setDiscountValue(1000)">1000원</button>';
      discountFormHTML += '<button type="button" onclick="setDiscountValue(3000)">3000원</button>';
      discountFormHTML += '<button type="button" onclick="setDiscountValue(5000)">5000원</button>';
      discountFormHTML += '<button type="button" onclick="setDiscountValue(10000)">10000원</button>';
      discountFormHTML += '<button type="button" onclick="setDiscountValue(30000)">30000원</button></div>';
    $('#discountForm').html(discountFormHTML);
 
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
  <div>
    <label for="giftCardName">기프트카드 이름: </label>
    <input type="text" id="giftCardName" name="giftCardName"><br>
    기프트카드 이름을 적지 않으면 기본 기프트카드 이름으로 제공됩니다.<br>
    <span style="color:gray">예) 음료 1000원 할인권</span>
  </div><br>
  <div>
    <label for="memberId">아이디: </label>
    <input type="text" id="memberId" name="memberId" required>
    <button type="button" onclick="checkId(event)">아이디 체크</button>
  </div><br>
  <div>
    <label for="foodType">음식 종류: </label>
    <select id="foodType" name="foodType">
    <option value="" selected disabled>선택하세요</option>
      <c:forEach var="food" items="${foodTypeList}">
        <option value="${food}">${food}</option>
      </c:forEach>
    </select>
  </div><br>
  <div id="discountForm"></div><br>
  <div>
    <label for="giftCardExpiredDate">유효기간: </label>
    <input type="datetime-local" id="giftCardExpiredDate" name="giftCardExpiredDate" required><br>
  </div>
  <div>
    <button type="button" onclick="setExpirationDate(1)">하루</button>
    <button type="button" onclick="setExpirationDate(3)">3일</button>
    <button type="button" onclick="setExpirationDate(7)">1주일</button>
    <button type="button" onclick="setExpirationDate(30)">1개월</button>
    <button type="button" onclick="setExpirationDate(365)">1년</button>
  </div><br>
  <div>
    <button type="submit" id="btnGrant" disabled>기프트카드 제공</button>
    <a href="list"><button type="button">기프트카드 리스트로 돌아가기</button></a>
  </div>
</form>

</body>
</html>
