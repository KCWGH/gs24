<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <!-- css 파일 불러오기 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/questionAttach.css">
    <!-- jquery 라이브러리 import -->
    <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
    <meta charset="UTF-8">
    <title>${questionVO.questionTitle }</title>
    <%@ include file="../common/header.jsp" %>
</head>
<style>
	  /* 전체 페이지 스타일 */
    body {
    	font-family: 'Pretendard-Regular', sans-serif;
        margin: 0;
        background-color: #f8f9fa;
        font-family: 'Arial', sans-serif;
        display: flex;
        flex-direction: column;
        align-items: center;
        padding: 20px;
        height: 100vh;
        text-align: center;
    }

    h2 {
        color: #333;
        margin-bottom: 20px;
        font-size: 24px;
    }
    
    h3 {
    	color: #333;
    	margin-bottom: 20px;
    	font-size: 24px;
    }

    /* 게시글 컨테이너 */
    .question-container {
    	font-family: 'Pretendard-Regular', sans-serif;
        width: 100%;
        max-width: 800px;
        margin: 20px;
        background: #fff;
        padding: 30px;
        border-radius: 10px;
        box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
        text-align: left;
        box-sizing: border-box;
    }

    .question-container p {
        font-size: 16px;
        color: #444;
        padding: 8px 0;
        border-bottom: 1px solid #ddd;
        margin-bottom: 12px;
    }
    .question-container p strong {
        color: #333;
    }

    .question-content {
    	font-family: 'Pretendard-Regular', sans-serif;
        width: 100%;
        height: 300px;
        padding: 12px;
        border: 1px solid #ccc;
        border-radius: 5px;
        background: #f9f9f9;
        font-size: 16px;
        line-height: 1.5;
        resize: none;
        box-sizing: border-box; /* 부모 요소 너비를 넘지 않도록 설정 */
        overflow-y: auto; /* 스크롤 가능하게 */
        word-wrap: break-word; /* 긴 단어가 잘리지 않도록 */
        margin-top: 20px;
        margin-bottom: 20px;
    }

    /* 버튼 컨테이너 */
    .button-container {
        display: flex;
        justify-content: flex-end;
        gap: 12px;
    }

    /* 버튼 스타일 */
    button {
    	font-family: 'Pretendard-Regular', sans-serif;
        background: #ddd;
        color: black;
        padding: 10px 18px;
        border-radius: 5px;
        border: none;
        cursor: pointer;
        font-size: 16px;
        transition: background 0.3s;
        margin-right: 10px;
    }

    button:hover {
        background: #bbb;
    }

    /* '글 수정' 및 '글 목록' 버튼 스타일 */
    .button-container button:not(#deleteQuestion) {
        background-color: #ddd;
    }

    .button-container button:not(#deleteQuestion):hover {
        background-color: #bbb;
    }

    /* '글 삭제' 버튼 스타일 */
    .button-container button#deleteQuestion {
        background-color: #dc3545;
        color: white;
    }
    .button-container button#deleteQuestion:hover {
            background-color: #c82333;
        }

    /* 첨부 파일 영역 */
    .questionAttach-upload {
        margin-top: 10px;
        width: 100%;
        max-width: 800px;
    }

    .questionAttach-view {
        margin-bottom: 30px;
    }

    .questionAttach-list {
    	font-family: 'Pretendard-Regular', sans-serif;
        margin-top: 10px;
    }

    .questionAttach_item {
        margin: 5px 0;
    }
    
        /* 답변 컨테이너 */
    .answer-container {
        font-family: 'Pretendard-Regular', sans-serif;
        width: 100%;
        max-width: 800px;
        margin: 20px;
        background: #fff;
        padding: 30px;
        border-radius: 10px;
        box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
        text-align: left;
        box-sizing: border-box;
    }

    .answer-container p {
        font-size: 16px;
        color: #444;
        padding: 8px 0;
        border-bottom: 1px solid #ddd;
        margin-bottom: 12px;
    }

    /* 답변 입력 텍스트 박스 */
    .answer-content {
        width: 100%;
        height: 200px;
        padding: 12px;
        border: 1px solid #ccc;
        border-radius: 5px;
        background: #f9f9f9;
        font-size: 16px;
        line-height: 1.5;
        resize: none;
        box-sizing: border-box;
        overflow-y: auto;
        word-wrap: break-word;
        margin-top: 10px;
        margin-bottom: 10px;
    }

    /* 버튼 정렬 */
    .answer-button-container {
        display: flex;
        justify-content: flex-end;
        gap: 12px;
    }

    /* 버튼 공통 스타일 */
    .answer-button-container button {
        background: #ddd;
        color: black;
        padding: 10px 18px;
        border-radius: 5px;
        border: none;
        cursor: pointer;
        font-size: 16px;
        transition: background 0.3s;
    }

    .answer-button-container button:hover {
        background: #bbb;
    }

    /* 수정 및 삭제 버튼 */
    .answer-button-container button.btn_delete {
        background-color: #dc3545;
        color: white;
    }

    .answer-button-container button.btn_delete:hover {
        background-color: #c82333;
    }
</style>
<body>
<div class="question-container">
    <h2>게시글 상세보기</h2>
    
        <p hidden="hidden">글 번호 : ${questionVO.questionId }</p>      
        <p>제목 : ${questionVO.questionTitle }</p>  
        <p>식품 : ${questionVO.foodType }</p>
        <p>작성자 : ${questionVO.memberId }</p>
        
        <!-- questionDateCreated 데이터 포멧 변경 -->
        <fmt:formatDate value="${questionVO.questionDateCreated }" pattern="yyyy-MM-dd HH:mm" var="questionDateCreated"/>
        <p>작성일 : ${questionDateCreated }</p>
 
        <textarea class="question-content" readonly>${questionVO.questionContent }</textarea>
	        
		<div class="button-container">
		<sec:authorize access="hasRole('ROLE_MEMBER')">
	    <button onclick="location.href='list'">글 목록</button>
		</sec:authorize>
		<sec:authorize access="hasRole('ROLE_OWNER')">
		<button onclick="location.href='ownerList'">글 목록</button>
		</sec:authorize>
		
	    <sec:authentication property="principal" var="user"/>
	    <sec:authorize access="isAuthenticated()">
	        <c:if test="${user.username == questionVO.memberId}">
	            <button id="modifyQuestion">글 수정</button>
	            <button id="deleteQuestion">글 삭제</button>
	        </c:if>
	    </sec:authorize>
	    </div>


    <form id="modifyForm" action="modify" method="GET">
        <input type="hidden" name="questionId">    
    </form>

    <form id="deleteForm" action="delete" method="POST">
        <input type="hidden" name="questionId" value="${questionVO.questionId }">
        <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
    </form>

    <input type="hidden" id="questionId" value="${questionVO.questionId }">

    <!-- 첨부 파일 영역 -->
    <div class="questionAttach-upload">
        <div class="questionAttach-view">
            <h3>첨부 파일 리스트</h3>
            <div class="questionAttach-list">
                <c:forEach var="questionAttach" items="${questionAttachList}">
                    <c:if test="${not (questionAttach.questionAttachExtension eq 'jpg' or 
                                       questionAttach.questionAttachExtension eq 'jpeg' or 
                                       questionAttach.questionAttachExtension eq 'png' or 
                                       questionAttach.questionAttachExtension eq 'gif')}">
                        <div class="questionAttach_item">
                            <p><a href="../questionAttach/download?questionAttachId=${questionAttach.questionAttachId}">
                                ${questionAttach.questionAttachRealName }.${questionAttach.questionAttachExtension}</a></p>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </div>
    </div>

	<div class="answer-item">
    <sec:authentication property="principal" var="user"/>
    <sec:authorize access="hasRole('ROLE_OWNER')">
        <c:if test="${user.username == questionVO.ownerId}">
            <c:if test="${questionVO.isAnswered == 0}">
                <div style="text-align: left;">  
                    <input type="hidden" id="memberId" value="${user.username}">
                    <textarea id="answerContent"  rows="12" cols="71"></textarea>
                    <button id="btnAdd">작성</button>
                </div>
            </c:if>
        </c:if>
    </sec:authorize>
	</div>
    <div style="text-align: left;">
        <div id="answer"></div>
    </div>
            <c:if test="${questionVO.isAnswered == 1}">
                <p style="color: gray;">이 게시글에는 이미 답변이 작성되었습니다. 추가 답변을 작성할 수 없습니다.</p>
            </c:if>
</div>
    <script type="text/javascript">
        $(document).ajaxSend(function(e, xhr, opt){
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");

            xhr.setRequestHeader(header, token);
        });

        $("#modifyQuestion").click(function(){
            var modifyForm = $("#modifyForm"); // form 객체 참조

            var questionId = "<c:out value='${questionVO.questionId}' />";

            // 게시글 번호를 input name='questionId' 값으로 적용
            modifyForm.find("input[name='questionId']").val(questionId);                

            modifyForm.submit(); // form 전송
        }); // end click()

        $('#deleteQuestion').click(function() {
            // deleteForm 제출
            $('#deleteForm').submit();
        });

        $(document).ready(function() {
            getAllAnswer(); // 함수 호출        

            // 댓글 작성 기능
            $('#btnAdd').click(function() {
                var questionId = $('#questionId').val(); // 게시판 번호 데이터
                var memberId = $('#memberId').val(); // 작성자 데이터
                var answerContent = $('#answerContent').val(); // 댓글 내용
                var isAnswered = ${questionVO.isAnswered};
                
                if (isAnswered === 1) {
                    alert('이 게시글에는 이미 답변이 작성되었습니다. 추가 답변을 작성할 수 없습니다.');
                    return;
                }
                
                if(answerContent.trim() === '') {
                    alert('내용을 입력해 주세요.');
                    return;
                }
                
                // javascript 객체 생성
                var obj = {
                    'questionId' : questionId,
                    'memberId' : memberId,
                    'answerContent' : answerContent
                }
                console.log(obj);

                // $.ajax로 송수신
                $.ajax({
                    type : 'POST', // 메서드 타입
                    url : '../answer', // url
                    headers : { // 헤더 정보
                        'Content-Type' : 'application/json' // json content-type 설정
                    },
                    data : JSON.stringify(obj), // JSON으로 변환
                    success : function(result) { // 전송 성공 시 서버에서 result 값 전송
                        console.log(result);
                        if (result == 1) {
                            alert('댓글 입력 성공');
                            getAllAnswer(); // 함수 호출    
                            location.reload(); // 페이지 새로 고침
                        }
                    }
                });
            }); // end btnAdd.click()

            // 게시판 댓글 전체 가져오기
            function getAllAnswer() {
                var questionId = $('#questionId').val();

                var url = '../answer/all/' + questionId;
                $.getJSON(url, function(data) {
                    // data : 서버에서 전송받은 list 데이터가 저장되어 있음.
                    // getJSON()에서 json 데이터는 
                    // javascript object로 자동 parsing됨.
                    console.log(data);

                    var list = ''; // 댓글 데이터를 HTML에 표현할 문자열 변수

                    // $(컬렉션).each() : 컬렉션 데이터를 반복문으로 꺼내는 함수
                    $(data).each(function() {
                        // this : 컬렉션의 각 인덱스 데이터를 의미
                        console.log(this);

                        // 전송된 answerDateCreated는 문자열 형태이므로 날짜 형태로 변환이 필요
                        var answerDateCreated = new Date(this.answerDateCreated);

                        list += '<div class="answer_item">'
                            + '<pre>'
                            + '<input type="hidden" id="answerId" value="'+ this.answerId +'">'
                            + "관리자"
                            + '<br>'
                            + '<br>'
                            + '<textarea readonly rows="12" cols="71">' + this.answerContent + '</textarea>'
                            + '<br>'
                            + '<br>'
                            + answerDateCreated
                            + '&nbsp;&nbsp;'
                            + '<sec:authorize access="hasRole(\'ROLE_OWNER\')">'
                            + '<button class="btn_update">수정</button>'
                            + '<button class="btn_delete">삭제</button>'
                            + '</sec:authorize>'
                            + '</pre>'
                            + '</div>';
                    }); // end each()

                    $('#answer').html(list); // 저장된 데이터를 answer div 표현
                }); // end getJSON()
            } // end getAllAnswer()

            // 수정 버튼을 클릭하면 선택된 댓글 수정
            $('#answer').on('click', '.answer_item .btn_update', function() {
                console.log(this);

                // 선택된 댓글의 answerId, answerContent 값을 저장
                // prevAll() : 선택된 노드 이전에 있는 모든 형제 노드를 접근
                var answerId = $(this).prevAll('#answerId').val();
                var answerContent = $(this).prevAll('#answerContent').val();
                console.log("선택된 댓글 번호 : " + answerId + ", 댓글 내용 : " + answerContent);

                // ajax 요청
                $.ajax({
                    type : 'PUT',
                    url : '../answer/' + answerId,
                    headers : {
                        'Content-Type' : 'application/json'
                    },
                    data: JSON.stringify({ answerContent: answerContent }),
                    success : function(result) {
                        console.log(result);
                        if (result == 1) {
                            alert('댓글 수정 성공!');
                            getAllAnswer();
                        }
                    }
                });
            }); // end answer.on()

            // 삭제 버튼을 클릭하면 선택된 댓글 삭제
            $('#answer').on('click', '.answer_item .btn_delete', function() {
                console.log(this);
                var questionId = $('#questionId').val(); // 게시판 번호 데이터
                var answerId = $(this).prevAll('#answerId').val();

                // ajax 요청
                $.ajax({
                    type : 'DELETE',
                    url : '../answer/' + answerId + '/' + questionId,
                    headers : {
                        'Content-Type' : 'application/json'
                    },
                    success : function(result) {
                        console.log(result);
                        if (result == 1) {
                            alert('댓글 삭제 성공!');
                            getAllAnswer();
                        }
                    }
                });
            }); // end answer.on()        
        }); // end document()
    </script>
</body>
</html>