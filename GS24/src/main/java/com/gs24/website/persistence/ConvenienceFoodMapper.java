package com.gs24.website.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gs24.website.domain.ConvenienceFoodVO;

@Mapper
public interface ConvenienceFoodMapper {
	int insertConvenienceFood(ConvenienceFoodVO convenienceFoodVO);
	
	List<ConvenienceFoodVO> selectAllConvenienceFood();
	
	List<ConvenienceFoodVO> selectConvenienceFoodByConvenienceId(int convenienceId);
	
	ConvenienceFoodVO selectDetailConvenienceFoodByFoodId(int foodId);
}
