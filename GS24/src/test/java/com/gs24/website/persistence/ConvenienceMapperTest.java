package com.gs24.website.persistence;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.gs24.website.config.RootConfig;
import com.gs24.website.domain.ConvenienceFoodVO;
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
	
	@Test
	public void test() {
		selectFood();
	}
	
	void select() {
		List<ConvenienceVO> list = convenienceMapper.selectAllConvenience();
		log.info(list);
	}
	
	void insert() {
		ConvenienceVO convenienceVO = new ConvenienceVO();
		
		convenienceVO.setConvenienceId(0);
		convenienceVO.setOwnerId("nmbgsp95");
		
		convenienceMapper.insertConvenience(convenienceVO);
	}
	
	void foodInsert() {
		ConvenienceFoodVO convenienceFoodVO = new ConvenienceFoodVO();
		convenienceFoodVO.setConvenienceId(2);
		convenienceFoodVO.setFoodId(85);
		convenienceFoodVO.setFoodStock(5);
		
		convenienceFoodMapper.insertConvenienceFood(convenienceFoodVO);
	}
	
	void selectFood() {
		log.info(convenienceFoodMapper.selectAllConvenienceFood());
	}
}
