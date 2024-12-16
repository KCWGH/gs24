package com.gs24.website.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gs24.website.config.RootConfig;
import com.gs24.website.domain.FoodVO;
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
		vo.setFoodId(0);
		vo.setFoodId(7);
		vo.setImgFoodPath("C:\\Users\\sdedu\\Desktop\\gsproject\\GS24\\ImgFood식품 사진\\7번식품.png");
		vo.setImgFoodRealName("이거 어디서 나온거냐");
		vo.setImgFoodChgName("7번식품");
		vo.setImgFoodExtension("png");
		vo.setImgFoodDateCreated(null);
		imgFoodMapper.insertImgFood(vo);
	}
	
	public void update() {
		
	}
	
	
	public void updateFoodProteinFatCarb() {
		
	}
	
	public void selectById(int foodId) {
		
	}
	
	public void selectList() {
		
	}
	
	public void delete() {
		
	}
}
