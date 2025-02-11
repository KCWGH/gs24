package com.gs24.website.persistence;

import java.util.List;

import com.gs24.website.domain.ImgVO;

public interface ImgThumnailMapper {
	int insertImgThumnail(ImgVO imgVO);
	
	ImgVO selectImgThumnailById(int imgThumnailId);
	
	ImgVO selectImgThumnailByFoodId(int foodId);
	
	List<ImgVO> selectAllImagThumnail();
	
	int updateImgFood(ImgVO imgVO);
	
	int deleteImgThumnail(int foodId);
	
	List<ImgVO> selectOldThumnail();
}
