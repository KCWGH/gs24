package com.gs24.website.service;

import com.gs24.website.domain.ImgFoodVO;
import com.gs24.website.domain.ImgReviewVO;

public interface ImgService {
	long getNextReviewId();
	
	long getNextFoodId();
	
	ImgReviewVO getImgReviewById(int imgReviewId);
	
	ImgFoodVO getImgFoodById(int imgFoodId);
}
