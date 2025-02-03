package com.gs24.website.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gs24.website.domain.FoodListVO;

@Mapper
public interface FoodListMapper {
	int insertFood(FoodListVO foodListVO);
	
	List<FoodListVO> selectAllFood();
	
	FoodListVO selectFoodById(int foodId);
	
	int updateFoodById(FoodListVO foodListVO);
	
	int deleteFoodById(int foodId);
}
