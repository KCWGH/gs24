<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<script src="https://t1.kakaocdn.net/kakao_js_sdk/2.7.4/kakao.min.js" integrity="sha384-DKYJZ8NLiK8MN4/C5P2dtSmLQ4KwPaoqAfyA/DfmEc1VDxu4yyC7wy6K1Hs90nka" crossorigin="anonymous"></script>
<title>Insert title here</title>
</head>
<body>
	<script type="text/javascript">
		Kakao.init("c5baeb0b4d356d31c09ae9ef3d132934");
		
		console.log(Kakao.isInitialized());
		
		Kakao.Auth.setAccessToken('${accessToken}');
		
		Kakao.API.request({
			  url: '/v2/user/me',
			})
			  .then(function(response) {
			    console.log(response);
			  })
			  .catch(function(error) {
			    console.log(error);
			  });
	</script>
</body>
</html>