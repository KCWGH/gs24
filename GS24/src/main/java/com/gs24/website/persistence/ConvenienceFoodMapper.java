package com.gs24.website.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gs24.website.domain.ConvenienceDetailFoodVO;
import com.gs24.website.domain.ConvenienceFoodVO;
import com.gs24.website.util.Pagination;

@Mapper
public interface ConvenienceFoodMapper {
	int insertConvenienceFood(@Param("foodId") int foodId, @Param("foodAmount") int foodAmount,
			@Param("convenienceId") int convenienceId);

	List<ConvenienceFoodVO> selectAllConvenienceFood();

	List<ConvenienceFoodVO> selectPagedConvenienceFoodByConvenienceId(@Param("pagination") Pagination pagination,@Param("isOwner") int isOwner);
	
	int countTotalFoodsByConvenienceId(@Param("pagination") Pagination pagination, @Param("isOwner") int isOwner);

	ConvenienceFoodVO selectConvenienceFoodByFoodIdAndConvenienceId(@Param("foodId") int foodId,
			@Param("convenienceId") int convenienceId);

	ConvenienceDetailFoodVO selectDetailConvenienceFoodByFoodId(@Param("foodId") int foodId,
			@Param("convenienceId") int convenienceId);

	int checkHasFood(@Param("foodId") int foodId, @Param("convenienceId") int convenienceId);

	int updateFoodAmountByInsert(@Param("foodId") int foodId, @Param("foodAmount") int foodAmount,
			@Param("convenienceId") int convenienceId);

	int updateFoodAmountByPreorder(@Param("foodId") int foodId, @Param("preorderAmount") int preorderAmount,
			@Param("convenienceId") int convenienceId);

	List<String> selectFoodType();
	
	int updateShowStatus(@Param("foodId") int foodId, @Param("convenienceId") int convenienceId);
	
	String selectAddress(int convenienceId);
	
	List<ConvenienceFoodVO> selectFoodsByConvenienceId(int convenienceId);
	
	List<String> selectFoodTypeListByConvenienceId(int convenienceId);
}
