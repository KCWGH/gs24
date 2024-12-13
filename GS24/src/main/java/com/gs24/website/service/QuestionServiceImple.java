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
	      int result = questionMapper.insert(questionVO);
	      return result;
	}

	@Override
	public List<QuestionVO> getAllQuestion() {
		 log.info("getAllQuestion()");
	     return questionMapper.selectList();
	}

	@Override
	public QuestionVO getQuestionById(int questionId) {
		log.info("getQuestionById()");
	    return questionMapper.selectOne(questionId);
	}

	@Override
	public int updateQuestion(QuestionVO questionVO) {
		 log.info("updateQuestion()");
	     return questionMapper.update(questionVO);
	}

	@Override
	public int deleteQuestion(int questionId) {
		log.info("getNoticeById()");
	    return questionMapper.delete(questionId);
	}


	@Override
	public List<QuestionVO> getPagingQuestions(Pagination pagination) {
		 log.info("getPagingQuestion()");
	     return questionMapper.selectListByPagination(pagination);
	}

	@Override
	public int getTotalCount() {
		 log.info("getTotalCount()");
	     return questionMapper.selectTotalCount();
	}

}
