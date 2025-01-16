package com.gs24.website.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestParam;

import com.gs24.website.domain.ImgVO;

@Mapper
public interface ImgFoodMapper {
	int insertImgFood(ImgVO imgFoodVO);
	
	ImgVO selectImgFoodById(int ImgId);
	
	List<ImgVO> selectImgFoodByFoodId(@RequestParam("foreignId") int foodId);
	
	List<ImgVO> selectAllImagFood();
	
	int updateImgFood(ImgVO imgFoodVO);
	
	int deleteImgFood(int foodId);
	
	List<ImgVO> selectOldFood();
}
