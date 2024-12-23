package com.gs24.website.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gs24.website.config.RootConfig;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
@Log4j
public class ReviewMapperTest {
	
	@Autowired
	private ReviewMapper reviewMapper;
	
	@Test
	public void mapperTest() {
		select();
	}
	
	public void insert() {
		
	}
	
	public void update() {
		
	}
	
	public void updateFoodStock() {
		
	}
	

	
	public void select() {
		
	}
	
	public void delete() {
		
	}
}
