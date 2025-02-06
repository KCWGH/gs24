package com.gs24.website.service;

import java.util.List;

import com.gs24.website.domain.FoodListVO;

public interface FoodListService {
	int createFood(FoodListVO foodListVO);
	
	List<FoodListVO> getAllFood();
	
	FoodListVO getFoodById(int foodId);
	
	int updateFoodById(FoodListVO foodListVO);
	
	int deleteFoodById(int foodId);
	
	int updateFoodStockByFoodAmount(int foodId, int foodAmount);
}
