package com.gs24.website.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.gs24.website.domain.FoodVO;

public interface FoodService {
	int createFood(FoodVO foodVO, MultipartFile file);
	
	List<FoodVO> getAllFood();
	
	FoodVO getFoodById(int foodId);
	
	FoodVO getFirstFoodId();
	
	int updateFood(FoodVO foodVO, MultipartFile file);
	
	int updateFoodStock(int foodId, int foodStock);
	
	int updateFoodPrice(int foodId,int foodPrice);
	
	int updateFoodProteinFatCarb(int foodId,int foodProtein,int foodFat, int foodCarb);
	
	int updateFoodAmountByPreorderAmount(int foodId, int preorderAmount);
	
	int deleteFood(int foodId);
}
