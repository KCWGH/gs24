<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<!-- css 파일 불러오기 -->
<link rel="stylesheet"
    href="${pageContext.request.contextPath }/resources/css/questionAttach.css">
    
<style type="text/css">
li {
    display: inline-block;
}
</style>
<!-- jquery 라이브러리 import -->
<script src="https://code.jquery.com/jquery-3.7.1.js">
</script>
<meta charset="UTF-8">
<title>${questionDTO.questionTitle }</title>
</head>
<body>
    
<h2>글 보기</h2>
<div>
    <p>글 번호 : ${questionDTO.questionId }</p>
</div>
<div>
    <p>제목 : ${questionDTO.questionTitle }</p>
</div>
<div>
    <p>식품 : ${questionDTO.foodName }</p>
</div>
<div>
    <p>작성자 : ${questionDTO.memberId }</p>
    <!-- boardDateCreated 데이터 포멧 변경 -->
    <fmt:formatDate value="${questionDTO.questionDateCreated }"
                pattern="yyyy-MM-dd HH:mm:ss" var="questionDateCreated"/>
    <p>작성일 : ${questionDateCreated }</p>
</div>
<div>
    <textarea rows="20" cols="120" readonly>${questionDTO.questionContent }</textarea>
</div>

<!-- 게시글 작성자일 때 글 수정 삭제 가능 -->
<button onclick="location.href='list'">글 목록</button>

<!-- Spring Security로 로그인 사용자 정보 처리 -->
<c:if test="${not empty principal}">
    <c:if test="${principal.username == questionDTO.memberId}">
        <button onclick="location.href='modify?questionId=${questionDTO.questionId}'">글 수정</button>
        <button id="deletequestion">글 삭제</button>
    </c:if>
</c:if>

<form id="deleteForm" action="delete" method="POST">
    <input type="hidden" name="questionId" value="${questionDTO.questionId }">
</form>

<input type="hidden" id="questionId" value="${questionDTO.questionId }">

<!-- 관리자 역할일 때만 댓글 작성 가능 -->
<c:if test="${principal != null && principal.authorities[0].authority == 'ROLE_ADMIN'}">
    <div style="text-align: center;">
        <input type="text" id="memberId" value="${principal.username }">
        <input type="text" id="answerContent">
        <button id="btnAdd">작성</button>
    </div>
</c:if>

<!-- 첨부 파일 영역 -->
<div class="questionAttach-upload">
    <div class="questionAttach-view">
        <h2>첨부 파일 리스트</h2>
        <div class="questionAttach-list">
            <c:forEach var="questionAttachDTO" items="${questionAttachList}">
                <c:if test="${not (questionAttachDTO.questionAttachExtension eq 'jpg' or 
                                   questionAttachDTO.questionAttachExtension eq 'jpeg' or 
                                   questionAttachDTO.questionAttachExtension eq 'png' or 
                                   questionAttachDTO.questionAttachExtension eq 'gif')}">
                    <div class="questionAttach_item">
                        <p><a href="../questionAttach/download?questionAttachId=${questionAttachDTO.questionAttachId}">${questionAttachDTO.questionAttachRealName }.${questionAttachDTO.questionAttachExtension}</a></p>
                    </div>
                </c:if>
            </c:forEach>
        </div>
    </div>
</div>

<hr>
<div style="text-align: center;">
    <div id="replies"></div>
</div>

<script type="text/javascript">
$(document).ajaxSend(function(e, xhr, opt){
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		
		xhr.setRequestHeader(header, token);
	});

$('#deletequestion').click(function() {
    // deleteForm 제출
    $('#deleteForm').submit();
});
	
$(document)
    .ready(function() {
        getAllAnswer(); // 함수 호출		

        // 댓글 작성 기능
        $('#btnAdd').click(function() {
            var questionId = $('#questionId').val(); // 게시판 번호 데이터
            var memberId = $('#memberId').val(); // 작성자 데이터
            var answerContent = $('#answerContent').val(); // 댓글 내용
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
                    }
                }
            });
        }); // end btnAdd.click()

        // 게시판 댓글 전체 가져오기
        function getAllAnswer() {
            var questionId = $('#questionId').val();

            var url = '../answer/all/' + questionId;
            $
                .getJSON(
                    url,
                    function(data) {
                        // data : 서버에서 전송받은 list 데이터가 저장되어 있음.
                        // getJSON()에서 json 데이터는 
                        // javascript object로 자동 parsing됨.
                        console.log(data);

                        var list = ''; // 댓글 데이터를 HTML에 표현할 문자열 변수

                        // $(컬렉션).each() : 컬렉션 데이터를 반복문으로 꺼내는 함수
                        $(data)
                            .each(
                                function() {
                                    // this : 컬렉션의 각 인덱스 데이터를 의미
                                    console
                                        .log(this);

                                    // 전송된 answerDateCreated는 문자열 형태이므로 날짜 형태로 변환이 필요
                                    var answerDateCreated = new Date(
                                        this.answerDateCreated);

                                    list += '<div class="answer_item">'
                                            + '<pre>'
                                            + '<input type="hidden" id="answerId" value="'+ this.answerId +'">'
                                            + this.memberId
                                            + '&nbsp;&nbsp;' // 공백
                                            + '<input type="text" id="answerContent" value="'+ this.answerContent +'">'
                                            + '&nbsp;&nbsp;'
                                            + answerDateCreated
                                            + '&nbsp;&nbsp;'
                                            + '<c:if test="${principal != null && principal.authorities[0].authority == 'ROLE_ADMIN'}">'
                                            + '<button class="btn_update" >수정</button>'
                                            + '<button class="btn_delete" >삭제</button>'
                                            + '</c:if>'
                                            + '</pre>'
                                            + '</div>';
                                }); // end each()

                        $('#replies').html(list); // 저장된 데이터를 replies div 표현
                    } // end function()
            ); // end getJSON()
        } // end getAllAnswer()

        // 수정 버튼을 클릭하면 선택된 댓글 수정
        $('#replies')
            .on(
                'click',
                '.answer_item .btn_update',
                function() {
                    console.log(this);

                    // 선택된 댓글의 answerId, answerContent 값을 저장
                    // prevAll() : 선택된 노드 이전에 있는 모든 형제 노드를 접근
                    var answerId = $(this).prevAll(
                        '#answerId').val();
                    var answerContent = $(this)
                        .prevAll(
                            '#answerContent')
                        .val();
                    console.log("선택된 댓글 번호 : "
                            + answerId
                            + ", 댓글 내용 : "
                            + answerContent);

                    // ajax 요청
                    $
                        .ajax({
                            type : 'PUT',
                            url : '../answer/'
                                + answerId,
                            headers : {
                                'Content-Type' : 'application/json'
                            },
                            data : answerContent,
                            success : function(
                                result) {
                                console
                                    .log(result);
                                if (result == 1) {
                                    alert('댓글 수정 성공!');
                                    getAllAnswer();
                                }
                            }
                        });

                }); // end replies.on()

        // 삭제 버튼을 클릭하면 선택된 댓글 삭제
        $('#replies')
            .on(
                'click',
                '.answer_item .btn_delete',
                function() {
                    console.log(this);
                    var questionId = $( '#questionId').val(); // 게시판 번호 데이터
                    var answerId = $(this).prevAll(
                        '#answerId').val();

                    // ajax 요청
                    $
                        .ajax({
                            type : 'DELETE',
                            url : '../answer/'
                                + answerId
                                + '/'
                                + questionId,
                            headers : {
                                'Content-Type' : 'application/json'
                            },
                            success : function(
                                result) {
                                console
                                    .log(result);
                                if (result == 1) {
                                    alert('댓글 삭제 성공!');
                                    getAllAnswer();
                                }
                            }
                        });
                }); // end replies.on()		

    }); // end document()
</script>
</body>
</html>
