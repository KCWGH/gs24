<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 작성 페이지</title>
</head>
<body>
   <h2>글 작성 페이지</h2>
   <form action="register" method="POST">
   <!-- input 태그의 name은 vo의 멤버 변수 이름과 동일하게 작성 -->
      <div>
         <p>제목 : </p>
         <input type="text" name="questionTitle" 
         placeholder="제목 입력" maxlength="20" required>
      </div>
      <div>
      	<p>식품 : </p>
      	<input type="text" name="foodName" placeholder="식품 입력" maxlength="20" required>
      </div>  
      <div>
         <p>작성자 : </p>
         <input type="text" name="memberId" value="${memberId }" readonly>
      </div>
      
      <div>
         <p>내용 : </p>
         <textarea rows="20" cols="120" name="questionContent" 
         placeholder="내용 입력" maxlength="300" required></textarea>
      </div>
      <div>
         <input type="submit" value="등록">
      </div>
   </form>
   <script>
		$(document).ready(function() {
			// 차단할 확장자 정규식 (exe, sh, php, jsp, aspx, zip, alz)
			var blockedExtensions = /\.(exe|sh|php|jsp|aspx|zip|alz)$/i; 
				
			// 파일 전송 form validation
			$("#attachForm").submit(function(event) {
				var fileInput = $("input[name='file']"); // File input 요소 참조
				var file = fileInput.prop('files')[0]; // file 객체 참조
				var fileName = fileInput.val();	
				
				if (!file) { // file이 없는 경우
					alert("파일을 선택하세요.");
					event.preventDefault();
					return;
				}
				
				if (blockedExtensions.test(fileName)) { // 차단된 확장자인 경우
					alert("이 확장자의 파일은 첨부할 수 없습니다.");
					event.preventDefault();
					return;
				}

				var maxSize = 10 * 1024 * 1024; // 10 MB 
				if (file.size > maxSize) {
					alert("파일 크기가 너무 큽니다. 최대 크기는 10MB입니다.");
					event.preventDefault();
				}
			});
		});
	</script>
</body>
</html>