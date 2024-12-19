<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>회원 등록 실패</title>
</head>
<script type="text/javascript">
        alert('회원등록이 실패했습니다.\n\n유효하지 않은 회원정보(중복된 아이디, 패스워드, 전화번호)입니다.');
        window.location.href = 'login';
    </script>
<body>
</body>
</html>
