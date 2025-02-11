package com.gs24.website.service;

import java.util.List;

import com.gs24.website.domain.ImgVO;

public interface ImgService {
	int getNextReviewId();
	
	int getNextFoodId();
	
	ImgVO getImgReviewById(int imgId);
	
	ImgVO getImgFoodById(int imgId);
	
	List<ImgVO> getReviewImgListByReviewId(int reviewId);
	
	List<ImgVO> getFoodImgListByFoodId(int foodId);
	
	ImgVO getThumnailByFoodId(int foodId);
}
