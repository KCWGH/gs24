package com.gs24.website.service;

import java.util.List;


import com.gs24.website.domain.FoodVO;

public interface FoodService {
	int CreateFood(FoodVO foodVO);
	
	List<FoodVO> getAllFood();
	
	FoodVO getFoodById(int foodId);
	
	int updateFood(FoodVO foodVO);
	
	int updateFoodStock(int foodId, int foodStock);
	
	int updateFoodPrice(int foodId,int foodPrice);
	
	int updateFoodProteinFatCarb(int foodId,int foodProtein,int foodFat, int foodCarb);
	
	int deleteFood(int foodId);
}
