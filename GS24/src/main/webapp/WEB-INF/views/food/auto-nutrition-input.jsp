<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<link rel="stylesheet" href="../resources/css/fonts.css">
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<title>식품 검색</title>
<style>
body {
	font-family: 'Pretendard-Regular', sans-serif;
    margin: 0;
    padding: 15px;
    background-color: #f8f9fa;
    text-align: center;
}

input[type="text"] {
	font-family: 'Pretendard-Regular', sans-serif;
    padding: 5px;
    border: 1px solid #ddd;
    border-radius: 5px;
    text-align: center;
    font-size: 18px;
}

button {
	font-family: 'Pretendard-Regular', sans-serif;
	background: #ddd;
	color: black;
	padding: 5px 10px;
	border-radius: 5px;
	border: none;
	cursor: pointer;
	font-size: 18px;
}

button:hover {
	background: #bbb;
}

h2 {
    color: #333;
    margin-bottom: 5px;
}

table {
    width: 100%;
    border-collapse: collapse;
    margin: 10px 0;
    font-size: 13px;
}

table th, table td {
    padding: 8px 12px;
    text-align: center;
    border: 1px solid #ddd;
}

table th {
    background-color: #f2f2f2;
    font-weight: bold;
}

table td {
    background-color: #fff;
}

tbody tr:hover {
    border: 2px solid green;
    cursor: pointer;
}

#loading-spinner {
    display: none;
    margin: 20px auto;
    width: 50px;
    height: 50px;
    border: 5px solid #f3f3f3;
    border-top: 5px solid #3498db;
    border-radius: 50%;
    animation: spin 1s linear infinite;
}

@keyframes spin {
    0% { transform: rotate(0deg); }
    100% { transform: rotate(360deg); }
}
</style>
<script>
$(document).ready(function() {
    $(document).ajaxSend(function(e, xhr, opt) {
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        xhr.setRequestHeader(header, token);
    });

    function searchFood() {
        var foodName = $("#foodInput").val();
        if (foodName.trim() === "") {
            alert("식품명을 입력하세요.");
            return;
        }

        $("#result").empty();
        $("#loading-spinner").show();

        $.ajax({
            url: "search",
            type: "get",
            data: { foodName: foodName },
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            dataType: "json",
            beforeSend: function() {
                $("#loading-spinner").show();
            },
            success: function(data) {
                $("#loading-spinner").hide();

                if (data.response && data.response.body && data.response.body.items) {
                    var items = data.response.body.items;
                    var result = "<table border='1' style='width: 100%; text-align: left;' id='resultTable'>";
                    result += "<thead><tr><th>식품명</th><th>식품 종류</th><th>탄수화물</th><th>단백질</th><th>지방</th></tr></thead>";
                    result += "<tbody>";

                    items.forEach(function(item) {
                        result += "<tr class='resultRow'>";
                        result += "<td>" + item.foodNm + "</td>";
                        result += "<td>" + item.foodLv4Nm + "</td>";
                        result += "<td>" + Math.round(item.chocdf) + "</td>";
                        result += "<td>" + Math.round(item.prot) + "</td>";
                        result += "<td>" + Math.round(item.fatce) + "</td>";
                        result += "</tr>";
                    });

                    result += "</tbody></table>";
                    $("#result").html(result);
                } else {
                    alert("검색 결과가 없습니다.");
                }
            },
            error: function() {
                $("#loading-spinner").hide();
                alert("검색 실패!");
            }
        });
    }

    $("#searchBtn").click(function() {
        searchFood();
    });

    $("#foodInput").keydown(function(event) {
        if (event.key === "Enter") {
            searchFood();
        }
    });

    $(document).on('click', '.resultRow', function() {
        var foodName = $(this).find('td').eq(0).text();
        var foodType = $(this).find('td').eq(1).text();
        var carbs = $(this).find('td').eq(2).text();
        var protein = $(this).find('td').eq(3).text();
        var fat = $(this).find('td').eq(4).text();

        window.opener.fillForm(foodName, foodType, carbs, protein, fat);
        window.close();
    });
});
</script>
</head>
<body>
<h2>식품 검색</h2>
<input type="text" name="foodName" id="foodInput" placeholder="식품 이름 입력">
<button id="searchBtn">검색</button>

<div id="loading-spinner"></div>

<div id="result"></div>

<p>출처 : 전국통합식품영양성분정보(가공식품)표준데이터</p>
</body>
</html>
