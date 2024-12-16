<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>음식 리스트</title>
</head>
<body>
	<span>환영합니다, ${memberId }님</span>
	<c:if test="${not empty memberVO}">
	<a href="../member/mypage"><button>마이페이지</button></a>
	<a href="../member/logout"><button>로그아웃</button></a>
	<a href="../notice/list"><button>공지사항</button></a>
	<a href="../question/list"><button>문의사항(QnA)</button></a>
	</c:if>
	<c:if test="${empty memberVO}">
	<a href="../member/login"><button>로그인</button></a>
	<a href="../notice/list"><button>공지사항</button></a>
	<a href="../question/list"><button>문의사항(QnA)</button></a>
	</c:if>
	<hr>
</body>
</html>