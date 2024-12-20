package com.gs24.website.persistence;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gs24.website.config.RootConfig;
import com.gs24.website.domain.ImgFoodVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
@Log4j
public class ImgFoodTest {
	
	@Autowired
	private ImgFoodMapper imgFoodMapper;
	
	@Test
	public void mapperTest() {
		insert();
	}
	
	public void insert() {
		ImgFoodVO vo =  new ImgFoodVO();
		vo.setFoodId(1);
		vo.setImgFoodPath("C:\\Users\\sdedu\\Desktop\\gsproject\\GS24\\src\\main\\webapp\\ImgFood\\FoodNo1.png");
		vo.setImgFoodRealName("이거 어디서 나온거냐");
		vo.setImgFoodChgName("FoodNo1.png");
		vo.setImgFoodExtension("png");
		Date date = new Date();
		vo.setImgFoodDateCreated(date);
		imgFoodMapper.insertImgFood(vo);
	}
	
	public void update() {
		
	}
	
	
	public void updateFoodProteinFatCarb() {
		
	}
	
	public void selectById(int foodId) {
		
	}
	
	public void selectList() {
		log.info(imgFoodMapper.selectAllImagFood());
	}
	
	public void delete() {
		
	}
}
