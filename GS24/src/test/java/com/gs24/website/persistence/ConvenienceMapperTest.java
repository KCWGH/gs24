package com.gs24.website.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.gs24.website.config.RootConfig;
import com.gs24.website.domain.ConvenienceVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
@WebAppConfiguration
@Log4j
public class ConvenienceMapperTest {
	
	@Autowired
	private ConvenienceMapper convenienceMapper;
	
	@Autowired ConvenienceFoodMapper convenienceFoodMapper;
	
	@Autowired
	private FoodMapper foodMapper;
	
	@Test
	public void test() {
		select();
	}
	
	void select() {
		log.info(convenienceMapper.selectAddressByConvenienceId(1));
	}
	
	void insert() {
		ConvenienceVO convenienceVO = new ConvenienceVO();
		
		convenienceVO.setConvenienceId(0);
		convenienceVO.setOwnerId("nmbgsp95");
		
		convenienceMapper.insertConvenience(convenienceVO);
	}
	
	void checkFood() {
		log.info(convenienceFoodMapper.checkHasFood(85, 5));
	}
	
//	void selectFood() {
//		log.info(convenienceFoodMapper.selectConvenienceFoodByConvenienceId(1));
//	}
	
	void getId() {
		log.info(convenienceMapper.selectConvenienceIdByOwnerId("testpjm2002"));
	}
	
	void check() {
		log.info(foodMapper.checkFoodAmountStatus(10));
	}
}
