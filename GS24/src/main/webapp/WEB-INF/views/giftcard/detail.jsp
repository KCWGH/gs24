<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="../resources/css/fonts.css">
<title>기프트카드 상세 보기</title>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>

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
img {
	width: 100%;
    height: auto;
    border-radius: 5px;
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
    text-align: center;
    background-color: white;
}

th, td {
    padding: 8px;
    text-align: left;
    border-bottom: 1px solid #ddd;
}

th {
    background-color: #f1f1f1;
    color: #333;
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
    text-align: right;
}
</style>
</head>
<body>

    <h2>기프트카드 상세 정보</h2>
    <div>
		<c:choose>
			<c:when
				test="${giftCardVO.isUsed == 1 && giftCardVO.balance != 0 && sysDate < giftCardVO.giftCardExpiredDate}">
				<img src="${pageContext.request.contextPath}/resources/images/giftCard/usedGiftCard.png" alt="Used GiftCard" />
			</c:when>
			<c:when
				test="${sysDate < giftCardVO.giftCardExpiredDate && giftCardVO.isUsed == 0}">
				<img src="${pageContext.request.contextPath}/resources/images/giftCard/giftCard.png" alt="GiftCard" />
			</c:when>
			<c:when
				test="${sysDate >= giftCardVO.giftCardExpiredDate || giftCardVO.balance == 0}">
				<img src="${pageContext.request.contextPath}/resources/images/giftCard/expiredGiftCard.png" alt="Expired GiftCard" />
			</c:when>
		</c:choose>
		<table>
            <tr>
                <td class="bold">기프트카드 이름</td>
                <td>${giftCardVO.giftCardName}</td>
            </tr>
            <tr>
                <td class="bold">기프트카드 잔액</td>
                <td>${giftCardVO.balance}원</td>
            </tr>
            <tr>
                <td class="bold">발급 날짜</td>
                <td>
                    <fmt:formatDate value="${giftCardVO.giftCardGrantDate}" pattern="yyyy-MM-dd HH:mm" />
                </td>
            </tr>
            <tr>
                <td class="bold">만료 날짜</td>
                <td>
                    <fmt:formatDate value="${giftCardVO.giftCardExpiredDate}" pattern="yyyy-MM-dd HH:mm" />
                </td>
            </tr>
            <tr>
                <td class="bold">유효 여부</td>
                <td>
                    <c:choose>
                        <c:when test="${sysDate >= giftCardVO.giftCardExpiredDate}">
                            만료됨
                        </c:when>
                        <c:otherwise>
                            사용기간 내의 기프트카드
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <td class="bold">사용 여부</td>
                <td>
                    <c:choose>
                        <c:when test="${giftCardVO.isUsed == 1}">
                            사용중
                        </c:when>
                        <c:otherwise>
                            미사용
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </table>
    </div>
    <a href="list"><button>전체 기프트카드 리스트</button></a>

</body>
</html>
