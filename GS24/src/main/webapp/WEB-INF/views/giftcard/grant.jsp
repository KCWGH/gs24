<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<style>
/* 전체 페이지 스타일 */
body {
	font-family: 'Pretendard-Regular', sans-serif;
    margin: 0;
    padding: 15px;
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
    text-align: center;
}

/* 입력 필드 스타일 */
input[type="text"], input[type="email"], input[type="number"] {
	font-family: 'Pretendard-Regular', sans-serif;
    width: 60%;
    padding: 8px;
    border: 1px solid #ddd;
    border-radius: 5px;
    text-align: center;
}

/* 버튼 스타일 */
button {
	font-family: 'Pretendard-Regular', sans-serif;
    padding: 7px 10px;
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
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <link rel="stylesheet" href="../resources/css/fonts.css">
    <title>기프트카드 선물하기</title>
    <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
    <script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>
    <script type="text/javascript">
        $(document).ajaxSend(function(e, xhr, opt) {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            xhr.setRequestHeader(header, token);
        });

        function setBalance(value) {
            $("input[name='balance']").val(value);
        }
        
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

        $(document).ready(function() {
            let currentDate = new Date();
            currentDate.setFullYear(currentDate.getFullYear() + 1);
            let year = currentDate.getFullYear();
            let month = String(currentDate.getMonth() + 1).padStart(2, '0');
            let day = String(currentDate.getDate()).padStart(2, '0');
            let hours = String(currentDate.getHours()).padStart(2, '0');
            let minutes = String(currentDate.getMinutes()).padStart(2, '0');
            let formattedDate = year + '-' + month + '-' + day + 'T' + hours + ':' + minutes;
            
            $("#giftCardExpiredDate").val(formattedDate);
            $('#foodType').val("전체");

            $('#btnGrant').on('click', function(event) {
                event.preventDefault();
                let memberId = $('#memberId').val();
                let loginedId = '${memberId}';
                let giftCardName = $('#giftCardName').val();
                let balance = $('#balance').val();

                if (giftCardName === '생일 축하 기프트카드') {
                    alert('사용할 수 없는 이름입니다. 다른 이름을 입력해주세요.');
                    return;
                }
                
                if (!memberId) {
                    alert('선물할 아이디를 입력해주세요.');
                    return;
                }

                if (memberId === loginedId) {
                    alert('본인의 아이디입니다. 다른 아이디를 입력해주세요.');
                    return;
                }

                if (balance == null || balance < 1000 || balance > 30000) {
                	alert('구매할 금액을 1000원 이상, 30000원 이하로 입력해주세요.');
                	return;
                }

                let buyPrice = $('#balance').val().replace(/[^\d]/g, '');
                console.log(buyPrice);

                $.ajax({
                    url: 'dup-check-id',
                    type: 'POST',
                    data: { memberId: memberId },
                    success: function(response) {
                        if (response == 1) {
                            alert(memberId + '님께 기프트카드를 선물합니다.');
                            var IMP = window.IMP;
                            IMP.init('imp84362136');
                            IMP.request_pay({
                                pg: 'kakaopay',
                                pay_method: 'card',
                                merchant_uid: 'order_' + new Date().getTime(),
                                name: 'GS24 기프트카드',
                                amount: buyPrice
                            }, function(rsp) {
                                if (rsp.success) {
                                    alert('결제가 완료되었습니다.');
                                    $('form').submit();
                                } else {
                                    alert('결제에 실패하였습니다. 에러 메시지: ' + rsp.error_msg);
                                }
                            });
                        } else if (response == 2) {
                            alert('점주 아이디입니다. 기프트카드를 제공할 수 없습니다.');
                        } else {
                            alert('존재하지 않는 아이디입니다.');
                        }
                    },
                    error: function() {
                        alert('중복 확인 중 오류가 발생했습니다.');
                    }
                });
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

    <h2>기프트카드 선물하기</h2>
    <p>선물한 기프트카드는 1년간 사용 가능합니다.</p>

    <form action="grant" method="POST">
        <table>
            <tr>
                <th><label for="giftCardName">카드<br>이름</label></th>
                <td><input type="text" id="giftCardName" name="giftCardName" oninput="checkByteLimit(this, 100)"></td>
            </tr>
            <tr>
                <td colspan="2">
                    ※ 기입하지 않으면 기본 기프트카드 이름으로 제공됩니다.<br>
                    <span style="color: gray">예) 10000원 금액권</span>
                </td>
            </tr>
            <tr>
                <th><label for="memberId">선물할<br>아이디</label></th>
                <td><input type="text" id="memberId" name="memberId" required></td>
            </tr>
            <tr hidden="hidden">
                <th><label for="foodType">식품 종류</label></th>
                <td><input id="foodType" name="foodType" required></td>
            </tr>
            <tr>
                <th><label for="discountValue">선물할<br>금액권</label></th>
                <td>
                    <input type="number" id="balance" name="balance" required min="1000" max="30000"> 원<br>
                    <button type="button" onclick="setBalance(1000)">1000원</button>
                    <button type="button" onclick="setBalance(3000)">3000원</button>
                    <button type="button" onclick="setBalance(5000)">5000원</button>
                    <button type="button" onclick="setBalance(10000)">10000원</button>
                    <button type="button" onclick="setBalance(30000)">30000원</button>
                </td>
            </tr>
            <tr>
                <td colspan="2">※ 1000원 이상, 30000원 이하로 입력해 주세요.</td>
            </tr>
            <tr hidden="hidden">
                <th><label for="giftCardExpiredDate">유효기간</label></th>
                <td>
                    <input type="datetime-local" id="giftCardExpiredDate" name="giftCardExpiredDate" required>
                </td>
            </tr>
        </table>
        <br>
        <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
        <button type="submit" id="btnGrant">선물하기</button>
        <a href="list"><button type="button">기프트카드 리스트로 돌아가기</button></a>
    </form>
</body>
</html>
