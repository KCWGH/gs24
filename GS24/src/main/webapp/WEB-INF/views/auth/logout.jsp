<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>로그아웃</title>
</head>
<script type="text/javascript">
	if (window.opener) {
		window.opener.location.reload();
		window.close();
	} else if (window.parent !== window) {
		window.opener.location.reload();
		window.close();
	} else {
		window.location.href = '../food/list';
	}
</script>
</html>