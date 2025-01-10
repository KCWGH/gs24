package com.gs24.website.service;

import java.util.List;

import com.gs24.website.domain.ImgFoodVO;

public interface ImgFoodService {
	int createImgFood(ImgFoodVO imgFoodVO);
	
	ImgFoodVO getImgFoodById(int foodId);
	
	List<ImgFoodVO> getAllImgFood();
	
	int updateImgFood(ImgFoodVO imgFoodVO);
	
	int deleteImgFood(int foodId);
}
