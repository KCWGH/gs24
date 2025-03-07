package com.gs24.website.service;

import java.util.List;

import com.gs24.website.domain.ConvenienceDetailFoodVO;
import com.gs24.website.domain.ConvenienceFoodVO;
import com.gs24.website.domain.ReviewVO;
import com.gs24.website.util.Pagination;

public interface ConvenienceFoodService {

	int createConvenienceFood(int foodId, int foodAmount, String ownerId);

	List<ConvenienceFoodVO> getAllConvenienceFood();

	List<ConvenienceFoodVO> getPagedConvenienceFoodsByConvenienceId(int convenienceId, Pagination pagination);
	
	int getTotalCountByConvenienceId(int convenienceId);
	
	int countReviewsByFoodId(int foodId);

	ConvenienceFoodVO getConvenienceFoodByFoodId(int foodId, int convenienceId);

	ConvenienceDetailFoodVO getDetailConvenienceFoodByFoodId(int foodId, int convenienceId);

	List<ReviewVO> getReviewsByFoodId(int foodId, Pagination pagination);

	List<String> getFoodTypeList();

	int updateShowStatus(int foodId, int convenienceId);
	
	String getAddress(int convenienceId);
	
	List<String> getFoodTypeListByConvenienceId(int convenienceId);
	
}
