package com.gs24.website.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gs24.website.domain.ImgFoodVO;
import com.gs24.website.persistence.ImgFoodMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ImgFoodServiceImple implements ImgFoodService {

	@Autowired
	private ImgFoodMapper imgFoodMapper;

	@Override
	public int createImgFood(ImgFoodVO imgFoodVO) {
		log.info("createImgFood()");
		int result = imgFoodMapper.insertImgFood(imgFoodVO);
		return result;
	}

	@Override
	public ImgFoodVO getImgFoodById(int foodId) {
		log.info("getImgFoodById");
		ImgFoodVO imgFoodVO = imgFoodMapper.selectImgFoodById(foodId);
		log.info(imgFoodVO);
		return imgFoodVO;
	}

	@Override
	public List<ImgFoodVO> getAllImgFood() {
		log.info("getAllImgFood()");
		List<ImgFoodVO> list = imgFoodMapper.selectAllImagFood();
		log.info(list);
		return list;
	}

	@Override
	public int updateImgFood(ImgFoodVO imgFoodVO) {
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
