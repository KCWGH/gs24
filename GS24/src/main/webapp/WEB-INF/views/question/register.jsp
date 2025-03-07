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
    <%@ include file="../common/header.jsp" %>
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

    <h2>글 작성 페이지</h2>
    
    <div class="form-container">
    <form id="registerForm" action="register" method="POST">

            <!-- 비밀글 체크박스 -->
            <input class="form-check-input" type="checkbox" name="questionSecret" id="secret">
            <label for="secret" class="form-check-label">비밀글 설정</label>
			<br>
			<br>	    
			<label for="ownerId">매장 선택</label>	    
			<select id="convenienceSelect" name="ownerId" onchange="updateFoodTypeList()">
			    <option value="" selected disabled>선택하세요</option>   			
			    <c:forEach var="convenience" items="${convenienceList}">
			        <c:forEach var="owner" items="${ownerVOList}">
			            <c:if test="${convenience.ownerId == owner.ownerId}">
			                <option value="${owner.ownerId}" data-convenience-id="${convenience.convenienceId}">
			                    ${owner.address} (${convenience.convenienceId}호점)
			                </option>
			            </c:if>
			        </c:forEach>
			    </c:forEach>
			</select> 
			
			<label for="foodType">식품 종류</label>
			<select id="foodTypeSelect" name="foodType" required>
			    <option value="" selected disabled>선택하세요</option>
			</select>     

            <p><strong>제목 : <input type="text" name="questionTitle" placeholder="제목 입력" maxlength="20" ></strong></p>

            <p><strong>작성자 : <input type="text" name="memberId" value="${memberId}" maxlength="10" readonly></strong></p>   


            <p><strong>내용 : </strong></p>
            <textarea rows="20" cols="120" name="questionContent" placeholder="내용 입력" maxlength="300" ></textarea>

       		<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
    </form>   
    
	<div class="questionAttach-upload">
		<h2>첨부 파일 업로드</h2>
		<p>* 첨부 파일은 최대 3개까지 가능합니다.</p>
		<p>* 최대 용량은 10MB 입니다.</p>
		<input type="file" id="questionAttachInput" name="files" multiple="multiple"><br>
		<h2>선택한 첨부 파일 정보 :</h2>
		<div class="questionAttach-list"></div>
	</div>
	
	<div class="questionAttachFile-list"></div>
	
	
    <div class="button-container">
		<button id="registerQuestion">등록</button>
	</div>
		
	</div>
        <script	src="${pageContext.request.contextPath }/resources/js/questionAttach.js"></script>
        
        <script type="text/javascript">
		$(document).ajaxSend(function(e, xhr, opt){
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
			
			xhr.setRequestHeader(header, token);
		});
    	 
		 function updateFoodTypeList() {
		        var selectBox = document.getElementById("convenienceSelect");
		        var selectedOption = selectBox.options[selectBox.selectedIndex];
		        var convenienceId = selectedOption.getAttribute("data-convenience-id");

		        console.log("선택된 convenienceId: " + convenienceId);

		        
		        if (!convenienceId) {
		            return;
		        }

		        fetch("/website/question/getFoodTypeList?convenienceId=" + convenienceId)
		            .then(response => response.json())
		            .then(data => {
		                var foodTypeSelect = document.getElementById("foodTypeSelect");
		                foodTypeSelect.innerHTML = ""; // 기존 옵션 제거

		                var defaultOption = document.createElement("option");
		                defaultOption.value = "";
		                defaultOption.text = "선택하세요";
		                defaultOption.disabled = true;
		                defaultOption.selected = true;
		                foodTypeSelect.appendChild(defaultOption);

		                data.forEach(function(foodType) {
		                    var option = document.createElement("option");
		                    option.value = foodType;
		                    option.text = foodType;
		                    foodTypeSelect.appendChild(option);
		                });
		            });
		    } 
			
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
		            alert("식품 종류를 선택해주세요.");
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
