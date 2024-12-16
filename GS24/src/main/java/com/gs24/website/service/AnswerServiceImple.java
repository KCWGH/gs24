package com.gs24.website.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gs24.website.domain.AnswerVO;
import com.gs24.website.persistence.AnswerMapper;
import com.gs24.website.persistence.QuestionMapper;


import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class AnswerServiceImple implements AnswerService{
	@Autowired
	private AnswerMapper answerMapper;

	@Autowired
	private QuestionMapper questionMapper;
	
	@Transactional(value = "transactionManager")
	
	@Override
	public int createAnswer(AnswerVO answerVO) {
		log.info("createAnswer()");
		int insertResult = answerMapper.insert(answerVO);
<<<<<<< Updated upstream
		log.info(insertResult + "행 댓글 추가");
		return 1;
=======
		log.info(insertResult + "�뻾 �뙎湲� 異붽�");
		//int updateResult = questionMapper.up
		return 0;
>>>>>>> Stashed changes
	}

	@Override
	public List<AnswerVO> getAllAnswer(int questionId) {
		log.info("getAllAnswer()");
		return answerMapper.selectListByQuestionId(questionId);
	}

	@Override
	public int updateAnswer(int answerId, String answerContent) {
		log.info("updateAnswer()");
		AnswerVO answerVO = new AnswerVO();
		answerVO.setAnswerId(answerId);
		answerVO.setAnswerContent(answerContent);
		return answerMapper.update(answerVO);
	}

	@Transactional(value = "transactionManager")
	@Override
	public int deleteAnswer(int answerId, int questionId) {
		log.info("deleteAnswer()");
		int deleteResult = answerMapper.delete(answerId);
		log.info(deleteResult + "행 댓글 삭제");
		
		return 1;
	}

	

}
