package com.gs24.website.service;

import com.gs24.website.domain.ImgVO;

public interface ImgService {
	int getNextReviewId();
	
	int getNextFoodId();
	
	ImgVO getImgReviewById(int imgId);
	
	ImgVO getImgFoodById(int imgId);
}
