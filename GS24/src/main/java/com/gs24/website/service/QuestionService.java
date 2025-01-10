package com.gs24.website.service;

import java.util.List;

import com.gs24.website.domain.QuestionDTO;
import com.gs24.website.domain.QuestionVO;
import com.gs24.website.util.Pagination;

public interface QuestionService {
	int createQuestion(QuestionDTO questionDTO); // 게시글 등록
	
	List<QuestionDTO> getAllQuestion(); // 전체 게시글 조회
	
	QuestionDTO getQuestionById(int questionId); // 특정 게시글 조회
	
	int updateQuestion(QuestionDTO questionVO); // 특정 게시글 수정

	int deleteQuestion(int questionId); // 특정 게시글 삭제
	
	List<QuestionDTO> getPagingQuestions(Pagination pagination); // 전체 게시글 페이징 처리
	
	int getTotalCount();
	
	List<QuestionVO> getQuestionListByMemberId(String memberId); // 개인이 작성한 게시글
	
	List<QuestionDTO> getPagedQuestionsByMemberId(String memberId, Pagination pagination);
	
	int countQuestionByMemberId(String memberId);
}
