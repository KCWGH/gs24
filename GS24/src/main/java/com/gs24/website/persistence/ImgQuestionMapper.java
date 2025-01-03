package com.gs24.website.persistence;


import com.gs24.website.domain.ImgQuestionVO;


public interface ImgQuestionMapper {
	
	int insertImgQuestion(ImgQuestionVO ImgQuestion);
	
	ImgQuestionVO selectByImgQuestionId(int ImgQuestionId);
	
	int updateImgQuestion(ImgQuestionVO ImgQuestion);
	
    int deleteImgQuestion(int ImgQuestionId);
	
}
