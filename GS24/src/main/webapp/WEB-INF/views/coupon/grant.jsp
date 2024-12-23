<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>쿠폰 제공</title>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>

<script type="text/javascript">
	isCouponNameChecked = false;
	isIdChecked = false;
	function checkCouponName() {
    	let couponName = $('#couponName').val();
    	if (!couponName) {
    		alert("쿠폰 이름을 입력해주세요.");
    		return;
    	}

    	$.ajax({
        	url: 'dup-check-couponName',
        	type: 'POST',
        	data: { couponName: couponName },
        	success: function(response) {
            	if (response == 1) {
                	alert("존재하는 쿠폰 이름입니다.");
                	isCouponNameChecked = false;
                } else {
                	alert("사용 가능한 쿠폰 이름입니다.");
                	isCouponNameChecked = true;
            	}
            	updateSubmitButton();
        	},
        	error: function() {
            	alert("중복 확인 중 오류가 발생했습니다.");
        	}
    	});
	}
	function checkId() {
    	let memberId = $('#memberId').val();
    	if (!memberId) {
    		alert("아이디를 입력해주세요.");
    		return;
    	}

    	$.ajax({
        	url: '../member/dup-check-id',
        	type: 'POST',
        	data: { memberId: memberId },
        	success: function(response) {
            	if (response == 1) {
                	alert("존재하는 아이디입니다.");
                	isIdChecked = true;
                } else {
                	alert("존재하지 않는 아이디입니다.");
                	isIdChecked = false;
            	}
            	updateSubmitButton();
        	},
        	error: function() {
            	alert("중복 확인 중 오류가 발생했습니다.");
        	}
    	});
	}
</script>

</head>
<body>
	<h2>쿠폰 제공</h2>

	<form action="grant" method="POST">
		<div>
			<label for="couponName">쿠폰 이름: </label> <input type="text" id="couponName" name="couponName" required>
			<button type="button" onclick="checkCouponName()">쿠폰 이름 중복
				확인</button>
		</div>
		<div>
			<label for="memberId">아이디: </label> <input type="text" id="memberId" name="memberId" required>
			<button type="button" onclick="checkId()">아이디 확인</button>
		</div>

		<div>
			<label for="discountRate">할인율(%): </label> <input type="number" id="discountRate" name="discountRate" required>
		</div>

		<div>
			<label for="couponExpiredDate">유효기간: </label> <input type="datetime-local" id="couponExpiredDate" name="couponExpiredDate" required>
		</div>
		<div>
			<button type="submit" id="btnGrant">쿠폰 제공</button>
			<a href="list"><button type="button">쿠폰 리스트로 돌아가기</button></a>
		</div>
	</form>
</body>
</html>
