package com.gs24.website.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gs24.website.domain.ImgVO;
import com.gs24.website.persistence.ImgFoodMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ImgFoodServiceImple implements ImgFoodService {

	@Autowired
	private ImgFoodMapper imgFoodMapper;

	@Override
	public int createImgFood(ImgVO imgFoodVO) {
		log.info("createImgFood()");
		int result = imgFoodMapper.insertImgFood(imgFoodVO);
		return result;
	}

	@Override
	public ImgVO getImgFoodById(int foodId) {
		log.info("getImgFoodById");
		ImgVO imgFoodVO = imgFoodMapper.selectImgFoodById(foodId);
		log.info(imgFoodVO);
		return imgFoodVO;
	}

	@Override
	public List<ImgVO> getAllImgFood() {
		log.info("getAllImgFood()");
		List<ImgVO> list = imgFoodMapper.selectAllImagFood();
		log.info(list);
		return list;
	}

	@Override
	public int updateImgFood(ImgVO imgFoodVO) {
		log.info("updateImgFood()");
		int result = imgFoodMapper.updateImgFood(imgFoodVO);
		return result;
	}

	@Override
	public int deleteImgFood(int foodId) {
		log.info("deleteImgFood()");
		int result = imgFoodMapper.deleteImgFood(foodId);
		return 0;
	}

}
