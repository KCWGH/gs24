package com.gs24.website.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gs24.website.domain.QuestionAttach;
import com.gs24.website.domain.QuestionAttachDTO;
import com.gs24.website.persistence.QuestionAttachMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class QuestionAttachServiceImple implements QuestionAttachService {
	@Autowired
	private QuestionAttachMapper questionAttachMapper;

	@Override
	public QuestionAttachDTO getQuestionAttachById(int questionAttachId) {
		log.info("getQuestionAttachById()");
		return toDTO(questionAttachMapper.selectByQuestionAttachId(questionAttachId));
	}

	// QuestionAttach를 QuestionAttachDTO로 변환하는 메서드
	private QuestionAttachDTO toDTO(QuestionAttach questionAttach) {
		QuestionAttachDTO questionAttachDTO = new QuestionAttachDTO();
		questionAttachDTO.setQuestionAttachId(questionAttach.getQuestionAttachId());
		questionAttachDTO.setQuestionId(questionAttach.getQuestionId());
		questionAttachDTO.setQuestionAttachPath(questionAttach.getQuestionAttachPath());
		questionAttachDTO.setQuestionAttachRealName(questionAttach.getQuestionAttachRealName());
		questionAttachDTO.setQuestionAttachChgName(questionAttach.getQuestionAttachChgName());
		questionAttachDTO.setQuestionAttachExtension(questionAttach.getQuestionAttachExtension());
		questionAttachDTO.setQuestionAttachDateCreated(questionAttach.getQuestionAttachDateCreated());

        return questionAttachDTO;
	}

}
