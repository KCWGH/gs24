<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <style type="text/css">
        table, th, td {
            border-style: solid;
            border-width: 1px;
            text-align: center;
        }

        ul {
            list-style-type: none;
            text-align: center;
        }

        li {
            display: inline-block;
        }
    </style>

    <meta charset="UTF-8">
    <title>나의 질문 리스트</title>
</head>
<body>
    <a href="../food/list"><button>메인페이지</button></a>
    <a href="../notice/list"><button>공지사항</button></a>
    <h1>나의 질문 목록</h1>

    <hr>
    <table>
        <thead>
            <tr>
                <th style="width: 60px">번호</th>
                <th style="width: 80px">식품</th>
                <th style="width: 700px">제목</th>
                <th style="width: 120px">작성자</th>
                <th style="width: 100px">작성일</th>
                <th style="width: 100px">상태</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="QuestionVO" items="${myQuestionList}">
                <tr>
                    <td>${QuestionVO.questionId}</td>
                    <td>${QuestionVO.foodName}</td>

                    <!-- 게시판 제목 클릭 시 checkAuthorAndRedirect 함수 호출 -->
                    <td><a href="javascript:void(0);"
                        onclick="checkAuthorAndRedirect(${QuestionVO.questionId}, '${QuestionVO.memberId}', '${sessionScope.memberRole}')">
                        ${QuestionVO.questionTitle}</a></td>

                    <td>${QuestionVO.memberId}</td>
                    <!-- questionDateCreated 데이터 포멧 변경 -->
                    <fmt:formatDate value="${QuestionVO.questionDateCreated }"
                        pattern="yyyy-MM-dd HH:mm" var="questionDateCreated" />
                    <td>${questionDateCreated }</td>
                    <td><c:if test="${QuestionVO.isAnswered == 0}">
                            답변대기
                        </c:if> <c:if test="${QuestionVO.isAnswered == 1}">
                            답변완료
                        </c:if></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    
</body>

<script type="text/javascript">
    // 작성자 확인 후 이동하는 함수
    function checkAuthorAndRedirect(questionId, authorId, memberRole) {
        var currentUser = "${sessionScope.memberId}"; // 현재 로그인된 사용자 ID
        var currentUserRole = "${sessionScope.memberVO.memberRole}"; // 현재 로그인된 사용자 권한

        if (currentUser === authorId || currentUserRole == 2) {
            // 작성자이거나 관리자일 경우 게시판 상세로 이동
            window.location.href = "detail?questionId=" + questionId;
        } else {
            // 작성자가 아니고 관리자가 아닐 경우 경고
            alert("게시판 작성자 또는 관리자만 해당 게시판에 접근할 수 있습니다.");
        }
    }
</script>

</html>
