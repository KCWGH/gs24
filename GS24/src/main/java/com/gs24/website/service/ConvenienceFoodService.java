package com.gs24.website.service;

import java.util.List;

import com.gs24.website.domain.ConvenienceFoodVO;
import com.gs24.website.domain.ReviewVO;

public interface ConvenienceFoodService {
	
	int createConvenienceFood(ConvenienceFoodVO convenienceFoodVO);
	
	List<ConvenienceFoodVO> getAllConvenienceFood();
	
	List<ConvenienceFoodVO> getConvenienceFoodByConvenienceId(int convenienceId);
	
	ConvenienceFoodVO getDetailConvenienceFoodByFoodId(int foodId);
	
	List<ReviewVO> getReviewsByFoodId(int foodId);
}
