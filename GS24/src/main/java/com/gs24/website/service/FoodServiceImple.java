package com.gs24.website.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gs24.website.domain.FoodVO;
import com.gs24.website.persistence.FoodMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class FoodServiceImple implements FoodService{

	@Autowired
	private FoodMapper foodMapper;
	
	@Override
	public int CreateFood(FoodVO foodVO) {
		int result = foodMapper.insertFood(foodVO);
		if(result == 1) {
			log.info("DB에 데이터 등록 성공");
		} else {
			log.info("DB에 데이터 등록 실패");
		}
		return result;
	}

	@Override
	public List<FoodVO> getAllFood() {
		return foodMapper.selectFoodList();
	}

	@Override
	public FoodVO getFoodById(int foodId) {
		return foodMapper.selectFoodById(foodId);
	}

	@Override
	public int updateFood(FoodVO foodVO) {
		int result = foodMapper.updateFood(foodVO);
		return result;
	}

	@Override
	public int updateFoodStock(int foodId, int foodStock) {
		int result = foodMapper.updateFoodStock(foodId, foodStock);
		return result;
	}

	@Override
	public int updateFoodPrice(int foodId, int foodPrice) {
		int result = foodMapper.updateFoodPrice(foodId, foodPrice);
		return result;
	}

	@Override
	public int updateFoodProteinFatCarb(int foodId, int foodProtein, int foodFat, int foodCarb) {
		int result = foodMapper.updateFoodProteinFatCarb(foodId, foodProtein, foodFat, foodCarb);
		return result;
	}

	@Override
	public int deleteFood(int foodId) {
		int result = foodMapper.deleteFood(foodId);
		return result;
	}

}
