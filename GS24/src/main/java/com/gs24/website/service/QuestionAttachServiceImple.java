package com.gs24.website.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gs24.website.domain.QuestionAttach;
import com.gs24.website.persistence.QuestionAttachMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class QuestionAttachServiceImple implements QuestionAttachService {
	
	@Autowired
	private QuestionAttachMapper questionAttachMapper;

	@Override
	public QuestionAttach getQuestionAttachById(int questionAttachId) {
		log.info("getQuestionAttachById()");
		return questionAttachMapper.selectByQuestionAttachId(questionAttachId);
	}	

}
