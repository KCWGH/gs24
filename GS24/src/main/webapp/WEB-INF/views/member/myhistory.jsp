<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>
	<link rel="stylesheet" href="../resources/css/fonts.css">
    <title>내 활동</title>
    <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
    <script type="text/javascript">
    $(document).ajaxSend(function(e, xhr, opt){
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        
        xhr.setRequestHeader(header, token);
     });
        $(document).ready(function() {
        	let currentPage = 1;
            $("input[name='choice']").change(function() {
                const selectedChoice = $("input[name='choice']:checked").val();
                fetchPostList(selectedChoice, 1);
            });

            $(document).on("click", ".pagination a", function(event) {
                event.preventDefault();
                const pageNum = $(this).data("page");
                const selectedChoice = $("input[name='choice']:checked").val();
                fetchPostList(selectedChoice, pageNum);
            });
            $(document).on('click', '.link-in-child', function(event) {
                event.preventDefault();
                let url = $(this).attr('href');
                if (window.opener) {
                    window.opener.location.href = url;
                }
            });
            function formatDate(timestamp, simpleFormat = false) {
                const date = new Date(timestamp);
                const year = date.getFullYear().toString();
                const month = ('0' + (date.getMonth() + 1)).slice(-2);
                const day = ('0' + date.getDate()).slice(-2);

                if (simpleFormat) {
                    return year + '/' + month + '/' + day;
                }

                let hours = date.getHours();
                const minutes = ('0' + date.getMinutes()).slice(-2);
                const period = hours >= 12 ? '오후' : '오전';
                hours = hours % 12 || 12;
                return year + '/' + month + '/' + day + ' ' + period + ' ' + hours + ':' + minutes;
            }


            function fetchPostList(choice, pageNum) {
                $.ajax({
                    url: choice,
                    type: 'GET',
                    data: { pageNum: pageNum },
                    dataType: 'json',
                    success: function(response) {
                        let now = new Date();
                        let postList = response.postList;
                        let pageMaker = response.pageMaker;
                        let postHTML = '';
                        let completedRequests;
                        let foodNames;

                        postHTML += '<div class="post-item">';
                        switch (choice) {
                        case 'myQuestions':
                            if (postList.length == 0) {
                                postHTML += '<br>내가 작성한 질문이 없습니다.';
                                break;
                            }
                            postHTML += '<table><thead><tr><th>작성일시</th><th>식품 종류</th><th>질문 제목</th><th>답변여부</th></tr></thead><tbody>';
                            postList.forEach(function(questionVO) {
                                postHTML += '<tr><td>';
                                postHTML += formatDate(questionVO.questionDateCreated);
                                postHTML += '</td><td>';
                                postHTML += questionVO.foodType;
                                postHTML += '</td><td>';
                                postHTML += '<a href="../question/detail?questionId=' + questionVO.questionId + '" class="link-in-child">';
                                postHTML += questionVO.questionTitle;
                                postHTML += '</a>';
                                postHTML += '</td><td';
                                if (questionVO.isAnswered == 1) {
                                    postHTML += ' style="color:green;">답변됨';
                                } else {
                                    postHTML += ' style="color:red;">미답변';
                                }
                                postHTML += '</td></tr>';
                            });
                            postHTML += '</tbody></table>';
                            break;
                        case 'myPreorders':
                            if (postList.length == 0) {
                                postHTML += '<br>내 예약 내역이 없습니다.';
                                break;
                            }
                            postHTML += '<table><thead><tr><th>예약번호</th><th>품목명</th><th>수량</th><th>수령예정일</th><th>수령여부</th><th>유효여부</th></tr></thead><tbody>';
                            completedRequests = 0;
                            foodNames = {};
                            postList.forEach(function(preorderVO) {
                                $.ajax({
                                    url: 'get-food-name',
                                    type: 'GET',
                                    data: { foodId: preorderVO.foodId },
                                    dataType: 'text',
                                    contentType: 'text/plain; charset=UTF-8',
                                    success: function(foodName) {

                                    	console.log(foodName);
                                        foodNames[preorderVO.foodId] = foodName;
                                        completedRequests++;

                                        if (completedRequests === postList.length) {
                                            postList.forEach(function(preorderVO) {
                                                postHTML += '<tr>';
                                                postHTML += '<td>' + preorderVO.preorderId + '</td>';
                                                postHTML += '<td><a href="../preorder/list?preorderId=' + preorderVO.preorderId + '" class="link-in-child">' + foodNames[preorderVO.foodId] + '</a></td>';
                                                postHTML += '<td>' + preorderVO.preorderAmount + '</td>';
                                                postHTML += '<td>' + formatDate(preorderVO.pickupDate, true) + '</td>';
                                                postHTML += '<td' + (preorderVO.isPickUp === 1 ? ' style="color:green;">수령' : ' style="color:gray;">미수령') + '</td>';

                                                if (preorderVO.isPickUp === 1 && preorderVO.isExpiredOrder === 0) {
                                                    postHTML += '<td style="color:blue;">예약 종료</td>';
                                                } else {
                                                    postHTML += '<td' + (preorderVO.isExpiredOrder === 1 ? ' style="color:red;">예약 취소' : ' style="color:gray;">예약중') + '</td>';
                                                }
                                                postHTML += '</tr>';
                                            });
                                            postHTML += '</tbody></table>';
                                            $(".post-list").html(postHTML);
                                        }
                                    },
                                    error: function(xhr, status, error) {
                                        console.error('Failed to fetch food name: ' + error);
                                    }
                                });
                            });
                            break;

                        case 'myFavorites':
                        	if (postList.length == 0) {
                                postHTML += '<br>내 찜 목록이 없습니다.';
                                break;
                            }
                        	postHTML += '<div class="favorites-container">';
                        	postList.forEach(function(favoritesVO) {  
                        	    postHTML += '<div class="favorites-item">';
                        	    
                        	    postHTML += '<a href="../preorder/create?foodId=' + favoritesVO.foodId + '&convenienceId=' + favoritesVO.convenienceId + '">' + '<img src="../image/foodThumbnail?foodId=' + favoritesVO.foodId + '"></a>';
                        	    postHTML += '</div>';
                        	});
                        	postHTML += '</div>';
                            break;
                        }
                        $(".post-list").html(postHTML);
                        updatePagination(pageMaker, choice, pageNum);
                    },
                    error: function(xhr, status, error) {
                        console.error('AJAX request failed: ' + error);
                    }
                });
            }

            function updatePagination(pageMaker, choice, pageNum) {
                let paginationHtml = '';
                currentPage = pageNum;

                if (pageMaker.isPrev) {
                    paginationHtml += '<li><a href="#" data-page="' + (pageMaker.startNum - 1) + '">이전</a></li>';
                }
                for (let num = pageMaker.startNum; num <= pageMaker.endNum; num++) {
                    const activeClass = num === currentPage ? 'active' : '';
                    paginationHtml += '<li class="' + activeClass + '"><a href="#" data-page="' + num + '">' + num + '</a></li>';
                }
                if (pageMaker.isNext) {
                    paginationHtml += '<li><a href="#" data-page="' + (pageMaker.endNum + 1) + '">다음</a></li>';
                }
                $(".pagination").html(paginationHtml);
            }

            fetchPostList('myFavorites', 1);
        });
    </script>
<style>
/* 전체 페이지 스타일 */
body {
	font-family: 'Pretendard-Regular', sans-serif;
    margin: 0;
    padding: 15px;
    background-color: #f8f9fa;
    text-align: center;
}

/* 제목 스타일 */
h2 {
    color: #333;
    margin-bottom: 5px;
}

/* 안내 문구 */
p {
    color: #888;
    margin-bottom: 10px;
}

a {
	color: black;
}

input[type="radio"]:hover,
label:hover {
    cursor: pointer;
}
.radio-group {
    display: flex;
    justify-content: space-between;
    width: 100%;
    max-width: 500px;
    margin: 10px auto;
}

.radio-group label {
    flex: 1;
    text-align: center;
}

.favorites-container {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 15px;
    margin-top: 15px;
}

.favorites-item {
    background-color: #fff;
    border-radius: 10px;
    padding: 10px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
    text-align: center;
}

.favorites-item img {
    width: 90%;
    height: auto;
    border-radius: 5px;
}

.pagination {
	list-style: none;
	padding: 0;
	display: flex;
	justify-content: center;
	margin-top: 10px;
}

.pagination li {
	display: inline;
	margin: 0 3px;
}

.pagination a {
	text-decoration: none;
	color: black;
	padding: 3px 6px;
	border-radius: 5px;
	transition: background 0.3s;
}

.pagination .active a {
	background: #555;
	color: white;
}

.fixed-buttons {
    display: flex;
    justify-content: space-between;
}

.fixed-buttons button {
	font-family: 'Pretendard-Regular', sans-serif;
    flex: 1;
    margin: 0 5px;
    padding: 8px;
    border: none;
    background: #ddd;
    border-radius: 5px;
    cursor: pointer;
}

.fixed-buttons button:hover {
    background: #bbb;
}


.left-buttons {
    display: flex;
    gap: 10px;
}


.right-button {
    margin-left: auto;
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
</style>
</head>
<body>
        <h2>${memberId}님의 활동</h2>
        <p>각 링크를 클릭하면 메인 페이지에서 해당 페이지가 열립니다.</p>
        <div class="radio-group">
            <label><input type="radio" name="choice" value="myFavorites" checked>내 찜 목록</label>
            <label><input type="radio" name="choice" value="myPreorders">내 예약 내역</label>
            <label><input type="radio" name="choice" value="myQuestions">내 질문</label>
        </div>

        <div class="post-list"></div>

        <ul class="pagination"></ul>
        <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
        <div class="fixed-buttons">
        <div class="right-button">
        <button onclick='location.href="../user/mypage"'>마이페이지</button>
        </div></div>
</body>
</html>
