package com.gs24.website.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gs24.website.domain.FoodVO;

@Mapper
public interface FoodMapper {
	int insertFood(FoodVO foodVO);
	
	FoodVO selectFoodById(int foodId);
	
	FoodVO selectFirstFoodId();
	
	List<FoodVO> selectFoodList();
	
	int updateFood(FoodVO foodVO);
	
	int updateFoodStock(@Param("foodId") int foodId,@Param("foodStock") int foodStock);
	
	int updateFoodPrice(@Param("foodId") int foodId,@Param("foodPrice") int foodPrice);
	
	int updateFoodProteinFatCarb(@Param("foodId") int foodId,@Param("foodProtein") int foodProtein,@Param("fat") int foodFat,@Param("foodCarb") int foodCarb);
	
	int updateFoodAmountByPreorderAmount(@Param("foodId") int foodId, @Param("preorderAmount") int preorderAmount);
	
	int updateFoodAvgRatingByFoodId(@Param("foodId") int foodId, @Param("foodAvgRating") int foodAvgRating);
	
	int updateFoodReviewCntByFoodId(@Param("foodId") int foodId, @Param("foodReviewCnt") int foodReviewCnt);
	
	int deleteFood(int foodId);
}
