package com.gs24.website.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.gs24.website.config.RootConfig;
import com.gs24.website.domain.ImgVO;
import com.gs24.website.domain.PreorderVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
@WebAppConfiguration
@Log4j
public class PreorderMapperTest {
	@Autowired
	private PreorderMapper preorderMapper;
	
	@Autowired
	private ReviewMapper reviewMapper;
	
	@Autowired
	private ImgFoodMapper  imgFoodMapper;
	
	@Autowired
	private ConvenienceMapper convenienceMapper;
	
	@Test
	public void test() {
		select();
	}
	
	public void insert() {
		List<ImgVO> ImgList = new ArrayList<ImgVO>();
		for(int i = 0; i < 3; i++)
		{
			ImgVO imgVO = new ImgVO();
			imgVO.setForeignId(i);
			imgVO.setImgRealName("테스트" + i);
			imgVO.setImgChgName("바뀐이름" + i);
			imgVO.setImgExtension(".test"+i);
			imgVO.setImgPath("가상의경로"+i);
			
			ImgList.add(imgVO);
		}
		imgFoodMapper.insertImgFoodList(ImgList);
	}
	
	public void update() {
		//preorderMapper.updatePreorderInIsPickUp(1, 1);
		preorderMapper.updatePreorderInIsExpiredOrder(1, 1);
	}
	
	public void select() {
		log.info(convenienceMapper.selectAddressByConvenienceId(1));
	}
	
	public void delete() {
		preorderMapper.deletePreorderByPreorderId(2);
	}
}
