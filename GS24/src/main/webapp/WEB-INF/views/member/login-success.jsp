<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 성공</title>
</head>
<script type="text/javascript">
	function onLoginSuccess() {
		if (window.opener) {
			window.opener.location.reload();
		}
		window.close();
	}

	let memberId = "${sessionScope.memberId}";

	if (memberId) {
		onLoginSuccess();
	}
</script>
</html>