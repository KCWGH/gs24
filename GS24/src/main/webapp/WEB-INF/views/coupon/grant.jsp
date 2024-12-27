<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>쿠폰 제공</title>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>

<script type="text/javascript">
    let isCouponNameChecked = false;
    let isIdChecked = false;

    function checkCouponName() {
        let couponName = $('#couponName').val();
        if (!couponName) {
            alert('쿠폰 이름을 입력해주세요.');
            return;
        }

        $.ajax({
            url: 'dup-check-couponName',
            type: 'POST',
            data: { couponName: couponName },
            success: function(response) {
                if (response == 1) {
                    alert('존재하는 쿠폰 이름입니다.');
                    isCouponNameChecked = false;
                } else {
                    alert('사용 가능한 쿠폰 이름입니다.');
                    isCouponNameChecked = true;
                }
                updateSubmitButton();
            },
            error: function() {
                alert('중복 확인 중 오류가 발생했습니다.');
            }
        });
    }

    function checkId() {
        let memberId = $('#memberId').val();
        if (!memberId) {
            alert('아이디를 입력해주세요.');
            return;
        }

        $.ajax({
            url: '../member/dup-check-id',
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
        if (isCouponNameChecked && isIdChecked) {
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
            $('#couponExpiredDate').val(formattedDate);
        } else {
            console.error('잘못된 날짜입니다.');
        }
    }
    
    function setDiscountRate(rate) {
        $('#discountRate').val(rate);
    }

    $(document).ready(function() {
        $('#couponName').on('input', function() {
            isCouponNameChecked = false;
            updateSubmitButton();
        });

        $('#memberId').on('input', function() {
            isIdChecked = false;
            updateSubmitButton();
        });
    });
</script>

</head>
<body>
    <h2>쿠폰 제공</h2>

    <form action="grant" method="POST">
        <div>
            <label for="couponName">쿠폰 이름: </label>
            <input type="text" id="couponName" name="couponName" required>
            <button type="button" onclick="checkCouponName()">쿠폰 이름 중복 확인</button>
        </div>
        <div>
            <label for="memberId">아이디: </label>
            <input type="text" id="memberId" name="memberId" required>
            <button type="button" onclick="checkId()">아이디 확인</button>
        </div>

        <div>
            <label for="discountRate">할인율(%): </label>
            <input type="number" id="discountRate" name="discountRate" required>
        </div>
        
        <div>
            <button type="button" onclick="setDiscountRate(3)">3%</button>
            <button type="button" onclick="setDiscountRate(5)">5%</button>
            <button type="button" onclick="setDiscountRate(10)">10%</button>
            <button type="button" onclick="setDiscountRate(25)">25%</button>
            <button type="button" onclick="setDiscountRate(50)">50%</button>
        </div>

        <div>
            <label for="couponExpiredDate">유효기간: </label>
            <input type="datetime-local" id="couponExpiredDate" name="couponExpiredDate" required>
        </div>

        <div>
            <button type="button" onclick="setExpirationDate(1)">하루</button>
            <button type="button" onclick="setExpirationDate(3)">3일</button>
            <button type="button" onclick="setExpirationDate(7)">1주일</button>
            <button type="button" onclick="setExpirationDate(30)">1개월</button>
            <button type="button" onclick="setExpirationDate(365)">1년</button>
        </div>

        <div>
            <button type="submit" id="btnGrant" disabled>쿠폰 제공</button>
            <a href="list"><button type="button">쿠폰 리스트로 돌아가기</button></a>
        </div>
    </form>
</body>
</html>