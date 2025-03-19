package com.gs24.website.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gs24.website.domain.FoodVO;
import com.gs24.website.util.Pagination;

@Mapper
public interface FoodMapper {
	int insertFood(FoodVO foodVO);

	List<FoodVO> selectAllFoodByPagination(Pagination pagination);

	FoodVO selectFoodById(int foodId);

	int countTotalFood();

	int updateFoodById(FoodVO foodVO);

	int deleteFoodById(int foodId);

	int updateFoodStockByFoodAmount(@Param("foodId") int foodId, @Param("foodAmount") int foodAmount);

	int updateFoodTotalRatingFoodReviewCntByFoodId(@Param("avgRating") int avgRating, @Param("reviewCnt") int reviewCnt,
			@Param("foodId") int foodId);

	int checkFoodAmountStatus(int foodId);

	String getFoodNameByFoodId(int fooId);

	int getFoodStock(int foodId);

	String getFoodTypeByFoodId(int fooId);

	void restoreFoodStock(@Param("foodId") int foodId, @Param("amount") int amount);

}
