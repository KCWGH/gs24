package com.gs24.website.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gs24.website.domain.ImgFoodVO;

@Mapper
public interface ImgFoodMapper {
	int insertImgFood(ImgFoodVO imgFoodVO);
	
	ImgFoodVO selectImgFoodById(int foodId);
	
	String selectImgFoodPathByFoodId(int foodId);
	
	List<ImgFoodVO> selectAllImagFood();
	
	int updateImgFood(ImgFoodVO imgFoodVO);
	
	int deleteImgFood(int foodId);
	
	List<ImgFoodVO> selectOldFood();
}
