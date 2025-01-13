<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>기프트카드 상세 보기</title>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>

<style>
    .bold {
        font-weight: bold;
        text-align: center;
    }
    table {
        width: 100%;
    }
    td {
        padding: 8px;
    }

    img {
        max-width: 100%;
        max-height: 300px;
        width: auto;
        height: auto;
    }
</style>
</head>
<body>

    <h2>기프트카드 상세 정보</h2>
    <div>
        <table>
            <tr>
                <td colspan="2" style="text-align: center;">
                    <c:choose>
                        <c:when test="${giftCardVO.isUsed == 1 && giftCardVO.balance != 0 && sysDate < giftCardVO.giftCardExpiredDate}">
                            <img src="${pageContext.request.contextPath}/resources/images/giftCard/usedGiftCard.png"
                                alt="Used GiftCard" />
                        </c:when>
                        <c:when test="${sysDate < giftCardVO.giftCardExpiredDate && giftCardVO.isUsed == 0}">
                            <img src="${pageContext.request.contextPath}/resources/images/giftCard/giftCard.png"
                                alt="GiftCard" />
                        </c:when>
                        <c:when test="${sysDate >= giftCardVO.giftCardExpiredDate || giftCardVO.balance == 0}">
                            <img src="${pageContext.request.contextPath}/resources/images/giftCard/expiredGiftCard.png"
                                alt="Expired GiftCard" />
                        </c:when>
                    </c:choose>
                </td>
            </tr>
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
                            사용됨
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
