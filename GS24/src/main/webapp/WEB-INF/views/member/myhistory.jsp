<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>내 활동</title>
    <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
    <script type="text/javascript">
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

            function formatDate(timestamp) {
                const date = new Date(timestamp);
                const year = date.getFullYear().toString().slice(-2);
                const month = ('0' + (date.getMonth() + 1)).slice(-2);
                const day = ('0' + date.getDate()).slice(-2);
                let hours = date.getHours();
                const minutes = ('0' + date.getMinutes()).slice(-2);
                const period = hours >= 12 ? '오후' : '오전';
                hours = hours % 12 || 12;
                return year + '/' + month + '/' + day + ' ' + period + ' ' + hours + '시 ' + minutes + '분';
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

                        postHTML += '<div class="post-item">';
                        switch (choice) {
                            case 'myReviews':
                                if (postList.length == 0){
                                    postHTML += '내가 작성한 리뷰가 없습니다.';
                                    break;
                                }
                                postHTML += '<table><thead><tr><th>작성일시</th><th>리뷰 제목</th><th>별점</th></tr></thead><tbody>';
                                postList.forEach(function(reviewVO) {
                                    postHTML += '<tr><td>';
                                    postHTML += formatDate(reviewVO.reviewDateCreated);
                                    postHTML += '</td><td>';
                                    postHTML += '<a href="../food/detail?foodId=' + reviewVO.foodId + '">';
                                    postHTML += reviewVO.reviewTitle;
                                    postHTML += '</a>';
                                    postHTML += '</td><td>';
                                    postHTML += reviewVO.reviewRating;
                                    postHTML += '점';
                                    postHTML += '</td></tr>';
                                });
                                postHTML += '</tbody></table></div>';
                                break;
                            case 'myQuestions':
                                if (postList.length == 0) {
                                    postHTML += '내가 작성한 질문이 없습니다.';
                                    break;
                                }
                                postHTML += '<table><thead><tr><th>작성일시</th><th>품목명</th><th>질문 제목</th><th>답변여부</th></tr></thead><tbody>';
                                postList.forEach(function(questionVO) {
                                    postHTML += '<tr><td>';
                                    postHTML += formatDate(questionVO.questionDateCreated);
                                    postHTML += '</td><td>';
                                    postHTML += questionVO.foodName;
                                    postHTML += '</td><td>';
                                    postHTML += '<a href="../question/detail?questionId=' + questionVO.questionId + '">';
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
                                    postHTML += '내 예약 내역이 없습니다.';
                                    break;
                                }
                                postHTML += '<table><thead><tr><th>수령예정일</th><th>품목명</th><th>수량</th><th>수령여부</th><th>유효여부</th></tr></thead><tbody>';
                                postList.forEach(function(preorderVO) {
                                    postHTML += '<tr><td>';
                                    postHTML += formatDate(preorderVO.pickupDate);
                                    postHTML += '</td><td>';
                                    postHTML += '<a href="../preorder/list">';
                                    postHTML += preorderVO.foodId;
                                    postHTML += '</a>';
                                    postHTML += '</td><td>';
                                    postHTML += preorderVO.preorderAmount;
                                    postHTML += '</td><td>';
                                    if (preorderVO.isPickUp == 1) {
                                        postHTML += '수령';
                                    } else {
                                        postHTML += '미수령';
                                    }
                                    postHTML += '</td><td>';
                                    if (preorderVO.isExpiredOrder == 1) {
                                        postHTML += '만료됨';
                                    } else {
                                        postHTML += '유효함';
                                    }
                                    postHTML += '</td></tr>';
                                });
                                postHTML += '</tbody></table>';
                                break;
                            case 'myNotifications':
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

            fetchPostList('myReviews', 1);
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
    </style>
</head>
<body>
    <c:if test="${empty memberId}">
        <h3>로그인 필요</h3>
        <a href="login">로그인</a>
        <a href="../food/list">음식 리스트</a>
    </c:if>
    <c:if test="${not empty memberId}">
        <h2>${memberId}님의 활동</h2>
        
        <div>
            <label><input type="radio" name="choice" value="myReviews" checked>내가 쓴 리뷰</label>
            <label><input type="radio" name="choice" value="myQuestions">내 질문</label>
            <label><input type="radio" name="choice" value="myPreorders">내 예약 내역</label>
            <label><input type="radio" name="choice" value="myNotifications">입고 알림 요청 내역</label>
        </div>

        <div class="post-list"></div>

        <ul class="pagination"></ul>

        <a href="../member/mypage">마이페이지</a>
    </c:if>
</body>
</html>
