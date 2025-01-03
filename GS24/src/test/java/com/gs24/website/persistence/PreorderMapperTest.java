package com.gs24.website.persistence;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gs24.website.config.RootConfig;
import com.gs24.website.domain.PreorderVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
@Log4j
public class PreorderMapperTest {
	@Autowired
	private ReviewMapper reviewMapper;
	
	@Test
	public void test() {
		update();
	}
	
	public void insert() {
		PreorderVO preorderVO = new PreorderVO();
		preorderVO.setFoodId(1);
		preorderVO.setMemberId("test");
		preorderVO.setPreorderAmount(1);
		preorderVO.setIsPickUp(0);
		preorderVO.setIsExpiredOrder(0);
		Date date = new Date();
		preorderVO.setPickupDate(date);
		
	}
	
	public void update() {
		reviewMapper.updateReviewImgPath("ImgReview\\ReviewNO56.jpg", 56);
	}
	
	public void select() {
		log.info(reviewMapper.selectNextReviewId());
	}
	
	public void delete() {
		
	}
}
