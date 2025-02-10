package com.gs24.website.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gs24.website.domain.FoodListVO;

import retrofit2.http.PartMap;

@Mapper
public interface FoodListMapper {
	int insertFood(FoodListVO foodListVO);
	
	List<FoodListVO> selectAllFood();
	
	FoodListVO selectFoodById(int foodId);
	
	int updateFoodById(FoodListVO foodListVO);
	
	int deleteFoodById(int foodId);
	
	int updateFoodStockByFoodAmount(@Param("foodId") int foodId, @Param("foodAmount") int foodAmount);
	
	int updateFoodTotalRatingFoodReviewCntByFoodId(@Param("avgRating") int avgRating, @Param("reviewCnt") int reviewCnt, @Param("foodId") int foodId);
}
