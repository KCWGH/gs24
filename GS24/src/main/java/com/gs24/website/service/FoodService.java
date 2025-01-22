package com.gs24.website.service;

import java.util.List;

import com.gs24.website.domain.FoodVO;
import com.gs24.website.util.Pagination;

public interface FoodService {
	int createFood(FoodVO foodVO);
	
	List<FoodVO> getAllFood();
	
	FoodVO getFoodById(int foodId);
	
	FoodVO getFirstFoodId();
	
	int updateFood(FoodVO foodVO);
	
	int updateFoodStock(int foodId, int foodStock);
	
	int updateFoodPrice(int foodId,int foodPrice);
	
	int updateFoodProteinFatCarb(int foodId,int foodProtein,int foodFat, int foodCarb);
	
	int updateFoodAmountByPreorderAmount(int foodId, int preorderAmount);
	
	int deleteFood(int foodId);
	
	List<String> getFoodTypeList();
	
	List<FoodVO> getPaginationFood(Pagination pagination);
	
	int getFoodTotalCount(Pagination pagination);
	
	Object[] getDetailData(int foodId, Pagination pagination);
}
