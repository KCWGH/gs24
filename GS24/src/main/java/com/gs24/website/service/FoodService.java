package com.gs24.website.service;

import java.util.List;

import com.gs24.website.domain.FoodVO;
import com.gs24.website.util.Pagination;

public interface FoodService {
	int createFood(FoodVO foodVO);

	List<FoodVO> getAllFood(Pagination pagination);

	int countTotalFood();

	FoodVO getFoodById(int foodId);

	int updateFoodById(FoodVO foodVO);

	int deleteFoodById(int foodId);

	int updateFoodStockByFoodAmount(int foodId, int foodAmount);

	// foodId 값과 같은 모든 데이터를(다른 테이블 포함) 삭제해도 되는지 확인하는 쿼리
	int checkFoodAmountStatus(int foodId);
	
	String getFoodNameByFoodId(int foodId);
	
	int getFoodStock(int foodId);
	
	String getFoodTypeByFoodId(int foodId);

}
