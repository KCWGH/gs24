package com.gs24.website.service;

import java.util.List;

import com.gs24.website.domain.AnswerVO;


public interface AnswerService {
	
	int createAnswer(AnswerVO answerVO); 
	
	List<AnswerVO> getAllAnswer(int questionId); 
	
	AnswerVO getNoticeById(int noticeId); 
	
	int updateNotice(int answerId, String answerContent); 
	
	int deleteNotice(int answerId, int questionId); 
	
	
	
	
}
