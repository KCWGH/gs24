package com.gs24.website.service;

import java.util.List;

import com.gs24.website.domain.QuestionVO;
import com.gs24.website.util.Pagination;

public interface QuestionService {
	int createQuestion(QuestionVO questionVO); // 게시글 등록
	
	List<QuestionVO> getAllQuestion(); // 전체 게시글 조회
	
	QuestionVO getQuestionById(int questionId); // 특정 게시글 조회
	
	int updateQuestion(QuestionVO questionVO); // 특정 게시글 수정

	int deleteQuestion(int questionId); // 특정 게시글 삭제
	
	List<QuestionVO> getPagingQuestions(Pagination pagination); // 전체 게시글 페이징 처리
	
	int getTotalCount();
<<<<<<< Updated upstream
=======
	
	List<QuestionVO> getQuestionListByMemberId(String memberId); // 개인이 작성한 게시글
>>>>>>> Stashed changes
}
