package com.gs24.website.service;

import java.util.List;

import com.gs24.website.domain.AnswerVO;


public interface AnswerService {
	
	int createAnswer(AnswerVO answerVO); 
	
	List<AnswerVO> getAllAnswer(int questionId); 	
	
	int updateAnswer(int answerId, String answerContent); 
	
	int deleteAnswer(int answerId, int questionId); 
		
}
