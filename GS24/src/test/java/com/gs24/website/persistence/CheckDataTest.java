package com.gs24.website.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.gs24.website.check.CheckReviewData;
import com.gs24.website.config.RootConfig;
import com.gs24.website.domain.ReviewVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
@WebAppConfiguration
@Log4j
public class CheckDataTest {
	
//	@Autowired
//	private CheckData checkDataUtil;
//	
	@Autowired
	private CheckReviewData checkReviewData;
	
	@Test
	public void test() {
		ReviewVO reviewVO = new ReviewVO();
		reviewVO.setReviewId(17);
		reviewVO.setFoodId(9);
		reviewVO.setMemberId("pjm2002");
		
		checkReviewData.checkReviewMemberId(reviewVO.getMemberId(), reviewVO.getFoodId());
	}
}
