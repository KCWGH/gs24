package com.gs24.website.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gs24.website.config.RootConfig;
import com.gs24.website.domain.FoodVO;
import com.gs24.website.util.Pagination;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
@Log4j
public class FoodMapperTest {
	
	@Autowired
	private FoodMapper foodMapper;
	
	@Test
	public void mapperTest() {
		selectPagination();
	}
	
	public void insert() {
		FoodVO foodVO = new FoodVO();
		foodVO.setFoodType("보존 식품3");
		foodVO.setFoodName("식량");
		foodVO.setFoodStock(1);
		foodVO.setFoodPrice(1000);
		foodVO.setFoodAvgRating(3);
		foodVO.setFoodProtein(1000);
		foodVO.setFoodFat(1000);
		foodVO.setFoodCarb(1000);
		int result = foodMapper.insertFood(foodVO);
		log.info(result);
	}
	
	public void update() {
		FoodVO foodVO = new FoodVO();
		foodVO.setFoodId(1);
		foodVO.setFoodStock(1);
		foodVO.setFoodPrice(1000);
		foodVO.setFoodProtein(1000);
		foodVO.setFoodFat(1000);
		foodVO.setFoodCarb(1000);
		int result = foodMapper.updateFood(foodVO);
		log.info(result);
	}
	
	public void updateFoodStock() {
		int result = foodMapper.updateFoodStock(1, 100);
		log.info(result);
	}
	
	public void updateFoodPrice() {
		int result = foodMapper.updateFoodPrice(1, 20000);
		log.info(result);
	}
	
	public void updateFoodProteinFatCarb() {
		int result = foodMapper.updateFoodProteinFatCarb(1, 0, 0, 0);
		log.info(result);
	}
	
	public void selectById(int foodId) {
		log.info(foodMapper.selectFoodById(foodId));
	}
	
	public void selectList() {
		log.info(foodMapper.selectFoodList());
	}
	
	public void selectPagination() {
		Pagination pagination = new Pagination();
		pagination.setSortType("rowPrice");
		log.info(foodMapper.selectFoodPagination(pagination));
	}
	
	public void selectFoodTotalCount() {
		
	}
	
	public void delete() {
		int result = foodMapper.deleteFood(2);
		log.info(result);
	}
}
