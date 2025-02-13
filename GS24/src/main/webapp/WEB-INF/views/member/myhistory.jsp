<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>
    <title>내 활동</title>
    <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
    <script type="text/javascript">
    $(document).ajaxSend(function(e, xhr, opt){
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        
        xhr.setRequestHeader(header, token);
     });
        $(document).ready(function() {
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
                        case 'myReviews':
                            if (postList.length == 0){
                                postHTML += '<br>내가 작성한 리뷰가 없습니다.';
                                break;
                            }
                            postHTML += '<table><thead><tr><th>작성일시</th><th>품목명</th><th>리뷰 제목</th><th>별점</th></tr></thead><tbody>';
                            completedRequests = 0;
                            foodNames = {};

                            postList.forEach(function(reviewVO) {
                                $.ajax({
                                    url: 'get-food-name',
                                    type: 'GET',
                                    data: { foodId: reviewVO.foodId },
                                    dataType: 'text',
                                    contentType: 'text/plain; charset=UTF-8',
                                    success: function(foodName) {
                                        foodNames[reviewVO.foodId] = foodName;
                                        completedRequests++;

                                        if (completedRequests === postList.length) {
                                            postList.forEach(function(reviewVO) {
                                                postHTML += '<tr><td>';
                                                postHTML += formatDate(reviewVO.reviewDateCreated);
                                                postHTML += '</td><td>';
                                                postHTML += foodNames[reviewVO.foodId];
                                                postHTML += '</td><td>';
                                                postHTML += '<a href="../food/detail?foodId=' + reviewVO.foodId + '&reviewId=' + reviewVO.reviewId + '" class="link-in-child">';
                                                postHTML += reviewVO.reviewTitle;
                                                postHTML += '</a>';
                                                postHTML += '</td><td>';
                                                postHTML += reviewVO.reviewRating;
                                                postHTML += '점';
                                                postHTML += '</td></tr>';
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

                        case 'myQuestions':
                            if (postList.length == 0) {
                                postHTML += '<br>내가 작성한 질문이 없습니다.';
                                break;
                            }
                            postHTML += '<table><thead><tr><th>작성일시</th><th>음식 종류</th><th>질문 제목</th><th>답변여부</th></tr></thead><tbody>';
                            postList.forEach(function(questionVO) {
                                postHTML += '<tr><td>';
                                postHTML += formatDate(questionVO.questionDateCreated);
                                postHTML += '</td><td>';
                                postHTML += questionVO.foodType;
                                postHTML += '</td><td>';
                                postHTML += '<a href="../question/detail?questionId=' + questionVO.questionId + '" class="link-in-child">';
                                postHTML += questionVO.questionTitle;
                                postHTML += '</a>';
                                postHTML += '</td><td>';
                                if (questionVO.isAnswered == 1) {
                                    postHTML += '답변됨';
                                } else {
                                    postHTML += '미답변';
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
                            postHTML += '<table><thead><tr><th>수령예정일</th><th>품목명</th><th>수량</th><th>수령여부</th><th>유효여부</th></tr></thead><tbody>';
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
                                                postHTML += '<td>' + formatDate(preorderVO.pickupDate, true) + '</td>';
                                                postHTML += '<td><a href="../preorder/list?preorderId=' + preorderVO.preorderId + '" class="link-in-child">' + foodNames[preorderVO.foodId] + '</a></td>';
                                                postHTML += '<td>' + preorderVO.preorderAmount + '</td>';
                                                postHTML += '<td>' + (preorderVO.isPickUp === 1 ? '수령' : '미수령') + '</td>';
                                                postHTML += '<td>' + (preorderVO.isExpiredOrder === 1 ? '만료됨' : '유효함') + '</td>';
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
                        	postHTML += '<br><div class="favorites-container">';
                        	postList.forEach(function(favoritesVO) {  
                        	    postHTML += '<div class="favorites-item">';
                        	    
                        	    postHTML += '<a href="../preorder/create?foodId=' + favoritesVO.foodId + '&convenienceId=' + favoritesVO.convenienceId + '" class="link-in-child">' + '<img src="../image/foodThumnail?foodId=' + favoritesVO.foodId + '"></a>';
                        	    postHTML += '</div>';
                        	});
                        	postHTML += '</div>각 항목을 클릭하면 예약 페이지로 바로 이동합니다.';
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

            function updatePagination(pageMaker, choice, currentPage) {
                let paginationHtml = '';
                if (pageMaker.isPrev) {
                    paginationHtml += '<li><a href="#" data-page="' + (pageMaker.startNum - 1) + '">이전</a></li>';
                }
                for (let num = pageMaker.startNum; num <= pageMaker.endNum; num++) {
                    let activeClass = (num === currentPage) ? ' class="active"' : '';
                    paginationHtml += '<li><a href="#" data-page="' + num + '"' + activeClass + '>' + num + '</a></li>';
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
        .pagination li a.active {
            font-weight: bold;
            color: #007bff;
        }
        ul {
            list-style-type: none;
            padding: 0;
            display: flex;
            justify-content: left;
            gap: 10px;
            margin-top: 20px;
        }

        ul li {
            display: inline-block;
            padding: 5px 10px;
            cursor: pointer;
        }

        ul li.disabled {
            color: #ccc;
            cursor: not-allowed;
        }

        ul li a {
            text-decoration: none;
            color: inherit;
        }

        ul li a:hover {
            color: inherit;
        }
        .favorites-container {
        display: grid;
        grid-template-columns: repeat(2, 1fr); /* 2열로 구성 */
        gap: 10px; /* 사진 간격 */
        justify-items: center; /* 사진 중앙 정렬 */
    }

    .favorites-item {
        width: 100%;
        max-width: 200px; /* 이미지 크기 제한 */
        text-align: center;
    }

    .favorites-item img {
        max-width: 100%; /* 이미지 크기 비율 유지 */
        height: auto;
        border-radius: 8px; /* 이미지 모서리 둥글게 */
        box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); /* 그림자 효과 */

}

    </style>
</head>
<body>
        <h2>${memberId}님의 활동</h2>
        <p>각 링크를 클릭하면 메인 페이지에서 해당 페이지가 열립니다.</p>
        <div>
            <label><input type="radio" name="choice" value="myFavorites" checked>내 찜 목록</label>
            <label><input type="radio" name="choice" value="myPreorders">내 예약 내역</label>
            <label><input type="radio" name="choice" value="myReviews">내가 쓴 리뷰</label>
            <label><input type="radio" name="choice" value="myQuestions">내 질문</label>
        </div>

        <div class="post-list"></div>

        <ul class="pagination"></ul>
        <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
        <button onclick='location.href="../user/mypage"'>마이페이지</button>
</body>
</html>
