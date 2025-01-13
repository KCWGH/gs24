package com.gs24.website.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.gs24.website.config.RootConfig;
import com.gs24.website.domain.ImgReviewVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
@WebAppConfiguration
@Log4j
public class ImgReviewTest {
	@Autowired
	private ImgReviewMapper imgReviewMapper;
	
	@Test
	public void test() {
		insert();
	}
	
	public void insert() {
		ImgReviewVO imgReviewVO = new ImgReviewVO();
		imgReviewVO.setImgReviewId(0);
		imgReviewVO.setReviewId(74);
		imgReviewVO.setImgReviewRealName("Çªµù");
		imgReviewVO.setImgReviewChgName("7ec26639-1162-4228-80c1-00bbf61f4fd5");
		imgReviewVO.setImgReviewExtension("jpg");
		imgReviewVO.setImgReviewPath("ImgReview\\ReviewNO74\\");
		imgReviewMapper.insertImgReview(imgReviewVO);
	}

}
