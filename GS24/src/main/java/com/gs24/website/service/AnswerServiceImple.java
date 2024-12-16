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
public class AnswerServiceImple implements AnswerService {
	@Autowired
	private AnswerMapper answerMapper;

	@Autowired
	private QuestionMapper questionMapper;

	@Transactional(value = "transactionManager")

	@Override
	public int createAnswer(AnswerVO answerVO) {
		log.info("createAnswer()");
		int insertResult = answerMapper.insert(answerVO);
		log.info(insertResult + "행 댓글 추가");
		int updateResult = questionMapper.update(null);
		return 0;
	}

	@Override
	public List<AnswerVO> getAllAnswer(int questionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AnswerVO getNoticeById(int noticeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateNotice(int answerId, String answerContent) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteNotice(int answerId, int questionId) {
		// TODO Auto-generated method stub
		return 0;
	}

}
