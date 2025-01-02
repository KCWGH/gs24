<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>글 작성 페이지</title>
</head>
<body>
    <h2>글 작성 페이지</h2>
    <form action="register" method="POST">
        <div>
            <!-- 비밀글 체크박스 -->
            <input class="form-check-input" type="checkbox" name="questionSecret" id="secret">
            <label for="secret" class="form-check-label">비밀글 설정</label>
        </div>
        <div>
            <p>제목 :</p>
            <input type="text" name="questionTitle" placeholder="제목 입력" maxlength="20" required>
        </div>
        <div>
            <p>식품 :</p>
            <input type="text" name="foodName" placeholder="식품 입력" maxlength="20" required>
        </div>  
        <div>
            <p>작성자 :</p>
            <input type="text" name="memberId" value="${memberId}" maxlength="10" readonly>
        </div>
        <div>
            <p>내용 :</p>
            <textarea rows="20" cols="120" name="questionContent" placeholder="내용 입력" maxlength="300" required></textarea>
        </div>
        <div>
            <input type="submit" value="등록">
        </div>
    </form>
</body>
</html>
