package com.gs24.website.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gs24.website.domain.FoodListVO;
import com.gs24.website.domain.ImgVO;
import com.gs24.website.persistence.FoodListMapper;
import com.gs24.website.persistence.ImgFoodMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class FoodListServiceImple implements FoodListService{

	@Autowired
	private FoodListMapper foodListMapper;
	
	@Autowired
	private ImgFoodMapper imgFoodMapper;

	@Override
	public int createFood(FoodListVO foodListVO) {
		log.info("createFood()");
		int result = foodListMapper.insertFood(foodListVO);
		for(ImgVO vo : foodListVO.getImgList()) {
			imgFoodMapper.insertImgFood(vo);
		}
		return result;
	}

	@Override
	public List<FoodListVO> getAllFood() {
		log.info("getAllFood()");
		List<FoodListVO> list = foodListMapper.selectAllFood();
		return list;
	}

	@Override
	public FoodListVO getFoodById(int foodId) {
		log.info("getFoodById()");
		FoodListVO foodListVO = foodListMapper.selectFoodById(foodId);
		
		List<ImgVO> list = imgFoodMapper.selectImgFoodByFoodId(foodId);
		
		foodListVO.setImgList(list);
		
		return foodListVO;
	}
	
	@Override
	public int updateFoodById(FoodListVO foodListVO) {
		log.info("updateFoodById()");
		
		int result = foodListMapper.updateFoodById(foodListVO);
		
		List<ImgVO> list = foodListVO.getImgList();
		
		imgFoodMapper.deleteImgFood(foodListVO.getFoodId());
		
		for(ImgVO vo : list) {
			vo.setForeignId(foodListVO.getFoodId());
			imgFoodMapper.insertImgFood(vo);
		}
		
		return result;
		
	}

	@Override
	public int deleteFoodById(int foodId) {
		log.info("deleteFoodById()");
		int result = foodListMapper.deleteFoodById(foodId);
		imgFoodMapper.deleteImgFood(foodId);
		return result;
	}

}
