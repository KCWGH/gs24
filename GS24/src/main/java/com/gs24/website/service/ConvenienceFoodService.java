package com.gs24.website.service;

import java.util.List;

import com.gs24.website.domain.ConvenienceDetailFoodVO;
import com.gs24.website.domain.ConvenienceFoodVO;
import com.gs24.website.domain.ReviewVO;

public interface ConvenienceFoodService {

	int createConvenienceFood(int foodId, int foodAmount, String ownerId);

	List<ConvenienceFoodVO> getAllConvenienceFood();

	List<ConvenienceFoodVO> getConvenienceFoodByConvenienceId(int convenienceId);

	ConvenienceFoodVO getConvenienceFoodByFoodId(int foodId, int convenienceId);

	ConvenienceDetailFoodVO getDetailConvenienceFoodByFoodId(int foodId, int convenienceId);

	List<ReviewVO> getReviewsByFoodId(int foodId);

	List<String> getFoodTypeList();
	
	int updateShowStatus(int foodId, int convenienceId,int showStatus);
}
