package com.gs24.website.service;

import java.util.List;

import com.gs24.website.domain.QuestionVO;
import com.gs24.website.util.Pagination;

public interface QuestionService {
	int createQuestion(QuestionVO questionVO);

	List<QuestionVO> getAllQuestion();

	QuestionVO getQuestionById(int questionId);

	int modifyQuestion(QuestionVO questionVO);

	int deleteQuestion(int questionId);

	List<QuestionVO> getPagedQuestions(Pagination pagination);

	int getTotalCount();
	
	int getTotalCountByOwnerId(String ownerId);

	List<QuestionVO> getQuestionListByMemberId(String memberId);

	List<QuestionVO> getPagedQuestionsByMemberId(String memberId, Pagination pagination);

	int countQuestionByMemberId(String memberId);

	List<QuestionVO> getPagedQuestionListByOwnerId(String ownerId, Pagination pagination);

}
