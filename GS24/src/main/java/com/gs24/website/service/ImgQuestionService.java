package com.gs24.website.service;

import com.gs24.website.domain.ImgQuestionVO;

public interface ImgQuestionService {
	
	 	int createImgQuestion(ImgQuestionVO imgQuestionVO);
	 	
	 	ImgQuestionVO getImgQuestionById(int imgQuestionId);
	 	
	    int updateImgQuestion(ImgQuestionVO imgQuestionVO);
	    
	    int deleteImgQuestion(int imgQuestionId);
}
