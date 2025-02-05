<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<!-- css 파일 불러오기 -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/css/questionAttach.css">
    <title>글 작성 페이지</title>
</head>
<body>
    <h2>글 작성 페이지</h2>
    <form id="registerForm" action="register" method="POST">
        <div>
            <!-- 비밀글 체크박스 -->
            <input class="form-check-input" type="checkbox" name="questionSecret" id="secret">
            <label for="secret" class="form-check-label">비밀글 설정</label>
        </div>

		  <div>
		    <label for="ownerAddress">매장 선택</label>	    
		    <select id="ownerId" name="ownerId">
    			<option value="" selected disabled>선택하세요</option>
    			<option value="전체">전체</option>
    				<c:forEach var="owner" items="${ownerVOList}">
        				<option value="${owner.ownerId}">${owner.address}</option>
    				</c:forEach>
			</select>		          
		</div>

        <div>                
             <label for="foodType">음식 종류</label>
                <select id="foodType" name="foodType" required>
                    <option value="" selected disabled>선택하세요</option>
                    <c:forEach var="food" items="${foodTypeList}">
                        <option value="${food}">${food}</option>
                    </c:forEach>
                </select>                              
        </div>  
        <div>
            <p>제목 : <input type="text" name="questionTitle" placeholder="제목 입력" maxlength="20" ></p>
        </div>
        <div>
            <p>작성자 : <input type="text" name="memberId" value="${memberId}" maxlength="10" readonly></p>   
        </div>
        <div>
            <p>내용 :</p>
            <textarea rows="20" cols="120" name="questionContent" placeholder="내용 입력" maxlength="300" ></textarea>
        </div>
        <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
    </form>
    
    <hr>
    
	<div class="questionAttach-upload">
		<h2>첨부 파일 업로드</h2>
		<p>* 첨부 파일은 최대 3개까지 가능합니다.</p>
		<p>* 최대 용량은 10MB 입니다.</p>
		<input type="file" id="questionAttachInput" name="files" multiple="multiple"><br>
		<h2>선택한 첨부 파일 정보 :</h2>
		<div class="questionAttach-list"></div>
	</div>
	
	<div class="questionAttachFile-list"></div>
        
		<button id="registerQuestion">등록</button>
        
        <script	src="${pageContext.request.contextPath }/resources/js/questionAttach.js"></script>
        
        <script>
    	 // ajaxSend() : AJAX 요청이 전송되려고 할 때 실행할 함수를 지정
		// ajax 요청을 보낼 때마다 CSRF 토큰을 요청 헤더에 추가하는 코드
		$(document).ajaxSend(function(e, xhr, opt){
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
			
			xhr.setRequestHeader(header, token);
		});
			
		$(document).ready(function() {
			let questionAttach;
			// regsiterForm 데이터 전송
			$('#registerQuestion').click(function() {
				event.preventDefault();  //  기본 제출 동작 방지

		        var title = $('input[name="questionTitle"]').val();
		        var content = $('textarea[name="questionContent"]').val();
		        var foodType = $('select[name="foodType"]').val();
		        var owner = $('select[name="ownerId"]').val();

		        if (title.trim() === '') {
		            alert("제목을 입력해주세요.");
		            return;
		        }
		        if (content.trim() === '') {
		            alert("내용을 입력해주세요.");
		            return;
		        }
		        if (!foodType) { 
		            alert("음식 종류를 선택해주세요.");
		            return;
		        }
		        if (!owner) {
		        	alert("매장을 선택해주세요.");
		        	return;
		        }
	            
				// form 객체 참조
				var registerForm = $('#registerForm');
				
				var i = 0;
							
				// questionAttachFile-list의 각 input 태그 접근
				$('.questionAttachFile-list input[name="questionAttach"]').each(function(){
					console.log(this);
					// JSON questionAttach 데이터를 object 변경
					questionAttach = JSON.parse($(this).val());
					
					// attachPath input 생성
					var inputPath = $('<input>').attr('type', 'hidden')
							.attr('name', 'questionAttachList[' + i + '].questionAttachPath');
					inputPath.val(questionAttach.questionAttachPath);
					
					// attachRealName input 생성
					var inputRealName = $('<input>').attr('type', 'hidden')
							.attr('name', 'questionAttachList[' + i + '].questionAttachRealName');
					inputRealName.val(questionAttach.questionAttachRealName);
					
					// attachChgName input 생성
					var inputChgName = $('<input>').attr('type', 'hidden')
							.attr('name', 'questionAttachList[' + i + '].questionAttachChgName');
					inputChgName.val(questionAttach.questionAttachChgName);
					
					// attachExtension input 생성
					var inputExtension = $('<input>').attr('type', 'hidden')
							.attr('name', 'questionAttachList[' + i + '].questionAttachExtension');
					inputExtension.val(questionAttach.questionAttachExtension);
					
					// form에 태그 추가
					registerForm.append(inputPath);
					registerForm.append(inputRealName);
					registerForm.append(inputChgName);
					registerForm.append(inputExtension);
					
					i++;
				});
				registerForm.submit();
			});

		});
		
		
	</script>
</body>
</html>
