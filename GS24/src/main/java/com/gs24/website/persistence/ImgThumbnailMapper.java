package com.gs24.website.persistence;

import java.util.List;

import com.gs24.website.domain.ImgVO;

public interface ImgThumbnailMapper {
	int insertImgThumbnail(ImgVO imgVO);
	
	ImgVO selectImgThumbnailById(int imgThumbnailId);
	
	ImgVO selectImgThumbnailByFoodId(int foodId);
	
	List<ImgVO> selectAllImagThumbnail();
	
	int updateImgFood(ImgVO imgVO);
	
	int deleteImgThumbnail(int foodId);
	
	List<ImgVO> selectOldThumbnail();
}
