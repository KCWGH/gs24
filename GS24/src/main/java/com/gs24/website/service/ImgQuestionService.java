package com.gs24.website.service;

import java.util.List;

import com.gs24.website.domain.ImgQuestionVO;

public interface ImgQuestionService {
	int createImgQuestion(ImgQuestionVO imgQuestionVO);
	ImgQuestionVO getImgQuestionById(int imgQuestionId);
	List<Integer> getAllImgQuestion();
	int updateImgQuestion(ImgQuestionVO imgQuestionVO);
	int deleteImgQuestion(int imgQuestionId);

}
