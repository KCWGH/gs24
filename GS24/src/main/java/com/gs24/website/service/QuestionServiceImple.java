package com.gs24.website.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gs24.website.domain.QuestionVO;
import com.gs24.website.persistence.QuestionMapper;
import com.gs24.website.util.Pagination;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class QuestionServiceImple implements QuestionService {

	@Autowired
	QuestionMapper questionMapper;

	@Override
	public int createQuestion(QuestionVO questionVO) {
		log.info("createQuestion()");
		int result = questionMapper.insertQuestion(questionVO);
		return result;
	}

	@Override
	public List<QuestionVO> getAllQuestion() {
		log.info("getAllQuestion()");
		return questionMapper.selectQuestionList();
	}

	@Override
	public QuestionVO getQuestionById(int questionId) {
		log.info("getQuestionById()");
		return questionMapper.selectQuestionOne(questionId);
	}

	@Override
	public int updateQuestion(QuestionVO questionVO) {
		log.info("updateQuestion()");
		return questionMapper.updateQuestion(questionVO);
	}

	@Override
	public int deleteQuestion(int questionId) {
		log.info("getNoticeById()");
		return questionMapper.deleteQuestion(questionId);
	}

	@Override
	public List<QuestionVO> getPagingQuestions(Pagination pagination) {
		log.info("getPagingQuestion()");
		return questionMapper.selectQuestionListByPagination(pagination);
	}

	@Override
	public int getTotalCount() {
		log.info("getTotalCount()");
		return questionMapper.selectQuestionTotalCount();
	}

	@Override
	public List<QuestionVO> getQuestionListByMemberId(String memberId) {

		return questionMapper.selectQuestionListByMemberId(memberId);
	}
	
	@Override
	public List<QuestionVO> getPagingQuestionsByMemberId(String memberId, Pagination pagination) {
		return questionMapper.selectQuestionListByPaginationBymemberId(pagination);
	}

	@Override
	public int countQuestionByMemberId(String memberId) {
		return questionMapper.countQuestionListByMemberId(memberId);
	}
}
