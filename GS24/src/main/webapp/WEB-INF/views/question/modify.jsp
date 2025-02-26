<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<!-- css 파일 불러오기 -->
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/questionAttach.css">
<!-- jquery 라이브러리 import -->
<script src="https://code.jquery.com/jquery-3.7.1.js">
</script>
<title>${questionVO.questionTitle }</title>
</head>
<style>
        /* 전체 페이지 스타일 */
        body {
            margin: 0;
            padding: 15px;
            background-color: #f8f9fa;
            text-align: center;
            font-family: 'Arial', sans-serif;
        }

        h2 {
            color: #333;
            margin-bottom: 20px;
        }

        /* 수정 폼 스타일 */
        .form-container {
            width: 80%;
            max-width: 600px;
            margin: 0 auto;
            background: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            text-align: left;
        }

        .form-container p {
            font-size: 16px;
            color: #444;
            margin: 10px 0 5px;
        }
        
        .form-container checkbox-label {
            font-size: 16px;
            color: #444;
            margin: 10px 0 5px;
        }

        .form-container input[type="text"],
        .form-container textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 16px;
            box-sizing: border-box; /* 부모 요소 넘지 않도록 */
            background: #f9f9f9;
        }

        .form-container textarea {
            height: 200px;
            resize: none;
            background: #f9f9f9;
            overflow-y: auto;
            word-wrap: break-word;
        }

        /* 버튼 스타일 */
        .button-container {
            display: flex;
            justify-content: right;
            gap: 10px;
            margin-top: 20px;
        }
        
        .button-container-file {
            display: flex;
            justify-content: left;
            gap: 10px;
            margin-top: 20px;
        }

        .button-container button {
            background: #ddd;
            color: black;
            padding: 8px 15px;
            border-radius: 5px;
            border: none;
            cursor: pointer;
            font-size: 14px;
        }
        
        .button-container-file button {
            background: #ddd;
            color: black;
            padding: 8px 15px;
            border-radius: 5px;
            border: none;
            cursor: pointer;
            font-size: 14px;
        }

        .button-container button:hover {
            background: #bbb;
        }
        
         .button-container-file button:hover {
            background: #bbb;
        }
        
        select {
   		 	display: block;
   			width: 100%; /* 선택박스를 새 줄에 맞게 확장 */
    		margin-bottom: 10px; /* 간격 추가 */
		}
    </style>

<body>

	<h2>게시글 수정</h2>
	
	<div class="form-container">
	<form id="modifyForm" action="modify" method="POST">
			<p hidden="hidden"><strong>글 번호 :</strong> ${questionVO.questionId }</p>
			<input type="hidden" name="questionId" value="${questionVO.questionId}">
			 
			<p><strong>제목 : <input type="text" name="questionTitle" placeholder="제목 입력" maxlength="20" value="${questionVO.questionTitle }" required></strong></p>	
				       
		    <label for="ownerAddress">매장 선택 : </label>	    
		    <select id="ownerId" name="ownerId" required>
			    <option value="" selected disabled>선택하세요</option>   
			    <c:forEach var="owner" items="${ownerVOList}">
			        <option value="${owner.ownerId}" <c:if test="${owner.ownerId eq questionVO.ownerId}">selected</c:if>>
			            ${owner.address}
			        </option>
			    </c:forEach>
			</select>          	       
				       
            <label for="foodType">식품 종류 : </label>
			<select id="foodType" name="foodType" required>
			    <option value="" selected disabled>선택하세요</option>
			    <c:forEach var="food" items="${foodTypeList}">
			        <option value="${food}" <c:if test="${food eq questionVO.foodType}">selected</c:if>>
			            ${food}
			        </option>
			    </c:forEach>
			</select>

                                    
			<p><strong>작성자 : ${questionVO.memberId}</strong></p>	
			<input type="hidden" name="memberId" value="${questionVO.memberId}">

			<p><strong>내용 : </strong></p>
			<textarea rows="20" cols="120" name="questionContent" placeholder="내용 입력" maxlength="300" required>${questionVO.questionContent }</textarea>

		<!-- 기존 첨부 파일 리스트 데이터 구성 -->
		<c:forEach var="questionAttach" items="${questionVO.questionAttachList}" varStatus="status">
    	<input type="hidden" class="input-questionAttach-list" name="questionAttachList[${status.index }].questionAttachPath" value="${questionAttach.questionAttachPath }">
    	<input type="hidden" class="input-questionAttach-list" name="questionAttachList[${status.index }].questionAttachRealName" value="${questionAttach.questionAttachRealName }">
    	<input type="hidden" class="input-questionAttach-list" name="questionAttachList[${status.index }].questionAttachChgName" value="${questionAttach.questionAttachChgName }">
    	<input type="hidden" class="input-questionAttach-list" name="questionAttachList[${status.index }].questionAttachExtension" value="${questionAttach.questionAttachExtension }">
		</c:forEach>
		
	 	<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
	</form>	
	
	
	<!-- 첨부 파일 영역 -->
	<div class="questionAttach-upload">
		<div class="questionAttach-view">
			<h2>첨부 파일 리스트</h2>
			<div class="questionAttach-list">
			<c:forEach var="questionAttach" items="${questionVO.questionAttachList}">
		 		<c:if test="${not (questionAttach.questionAttachExtension eq 'jpg' or 
			    			  questionAttach.questionAttachExtension eq 'jpeg' or 
			    			  questionAttach.questionAttachExtension eq 'png' or 
			    			  questionAttach.questionAttachExtension eq 'gif')}">
			    	<div class="questionAttach_item">
			    	<p><a href="../questionAttach/download?questionAttachId=${questionAttach.questionAttachId }">
			    	${questionAttach.questionAttachRealName }.${questionAttach.questionAttachExtension }</a></p>
			    	</div>
			    </c:if>
			</c:forEach>		
			</div>
		</div>
		
		<div class="questionAttach-modify" style="display : none;">
			<h2>첨부 파일 업로드</h2>
			<p>* 첨부 파일은 최대 3개까지 가능합니다.</p>
			<p>* 최대 용량은 10MB 입니다.</p>
			<input type="file" id="questionAttachInput" name="files" multiple="multiple"><br>
			<h2>선택한 첨부 파일 정보 :</h2>
			<div class="questionAttach-list"></div>
		</div>		
	</div>
	
	<div class="questionAttachFile-list"></div>
	
	<div class="button-container-file">
	<button id="change-upload">파일 변경</button>
	</div>
	
	<div class="button-container">
        <button id="modifyQuestion">수정 완료</button>
        <button type="button" onclick="location.href='list'">취소</button>
    </div>
	
	</div>
	<script	src="${pageContext.request.contextPath }/resources/js/questionAttach.js"></script>
	
	<script>
	 // ajaxSend() : AJAX 요청이 전송되려고 할 때 실행할 함수를 지정
	// ajax 요청을 보낼 때마다 CSRF 토큰을 요청 헤더에 추가하는 코드
	$(document).ajaxSend(function(e, xhr, opt){
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		
		xhr.setRequestHeader(header, token);
	});
	
	$(document).ready(function(){
    	var questionAttach;
	    // 파일 변경 버튼 클릭 시
	    $('#change-upload').click(function(){
	    	if(!confirm('기존에 업로드 파일들은 삭제됩니다. 계속 하시겠습니까?')){
	    		return;
	    	}
	        $('.questionAttach-modify').show();
	        $('.questionAttach-view').hide();
	        $('.input-questionAttach-list').remove(); // input-questionAttach-list 삭제
	    });
	
	 // modifyForm 데이터 전송
	    $('#modifyQuestion').click(function() {

	        event.preventDefault();
	        
	        var title = $('input[name="questionTitle"]').val();
	        var content = $('textarea[name="questionContent"]').val();  // textarea의 name 속성 사용
	        var foodType = $('select[name="foodType"]').val();

	        if (title.trim() === '') {
	            alert("제목을 입력해주세요.");
	            return;
	        }
	        if (content.trim() === '') {
	            alert("내용을 입력해주세요.");
	            return;
	        }
	        if (!foodType) { 
	            alert("식품 종류를 선택해주세요.");
	            return;
	        }
	        
	        // form 객체 참조
	        var modifyForm = $('#modifyForm');
	        
	        // questionAttachFile-list의 각 input 태그 접근
	        var i = 0;

	        $('.questionAttachFile-list input[name="questionAttach"]').each(function() {
	            
	        	questionAttach = JSON.parse($(this).val());
	        	
	        	questionAttach.questionId = ${questionVO.questionId};

	            var inputPath = $('<input>').attr('type', 'hidden')
	                .attr('name', 'questionAttachList[' + i + '].questionAttachPath')
	                inputPath.val(questionAttach.questionAttachPath);

	            var inputRealName = $('<input>').attr('type', 'hidden')
	                .attr('name', 'questionAttachList[' + i + '].questionAttachRealName')
	                inputRealName.val(questionAttach.questionAttachRealName);

	            var inputChgName = $('<input>').attr('type', 'hidden')
	                .attr('name', 'questionAttachList[' + i + '].questionAttachChgName')
	                inputChgName.val(questionAttach.questionAttachChgName);

	            var inputExtension = $('<input>').attr('type', 'hidden')
	                .attr('name', 'questionAttachList[' + i + '].questionAttachExtension')
	                inputExtension.val(questionAttach.questionAttachExtension);

	            modifyForm.append(inputPath);
	            modifyForm.append(inputRealName);
	            modifyForm.append(inputChgName);
	            modifyForm.append(inputExtension);

	            i++;
	        });
	        modifyForm.submit();
	    });
	});

</script>
		
</body>
</html>