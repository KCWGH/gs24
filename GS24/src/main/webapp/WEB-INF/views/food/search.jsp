<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<title>Insert title here</title>
<script>
$(document).ready(function() {
	$(document).ajaxSend(function(e, xhr, opt){
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        
        xhr.setRequestHeader(header, token);
     });
    $("#searchBtn").click(function() {
        var foodName = $("#foodInput").val();
        $.ajax({
            url: "../food/search",
            type: "post",
            data: { name: foodName },
            success: function(data) {
                $("#result").html(JSON.stringify(data, null, 2));
            },
            error: function() {
                alert("검색 실패!");
            }
        });
    });
});
</script>
</head>
<body>
<input type="text" id="foodInput" placeholder="식품 이름 입력">
<button id="searchBtn">검색</button>
<pre id="result"></pre>
</body>
</html>