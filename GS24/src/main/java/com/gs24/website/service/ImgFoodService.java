package com.gs24.website.service;

import java.util.List;

import com.gs24.website.domain.ImgVO;

public interface ImgFoodService {
	int createImgFood(ImgVO imgVO);
	
	ImgVO getImgFoodById(int foodId);
	
	List<ImgVO> getAllImgFood();
	
	int updateImgFood(ImgVO imgVO);
	
	int deleteImgFood(int foodId);
}
