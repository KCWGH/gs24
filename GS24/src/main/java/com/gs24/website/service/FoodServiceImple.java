package com.gs24.website.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gs24.website.domain.FoodVO;
import com.gs24.website.domain.ImgVO;
import com.gs24.website.persistence.FoodMapper;
import com.gs24.website.persistence.ImgFoodMapper;
import com.gs24.website.persistence.ImgThumbnailMapper;
import com.gs24.website.util.Pagination;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
@Transactional(value = "transactionManager")
public class FoodServiceImple implements FoodService {

	@Autowired
	private FoodMapper foodMapper;

	@Autowired
	private ImgFoodMapper imgFoodMapper;

	@Autowired
	private ImgThumbnailMapper imgThumbnailMapper;

	@Override
	public int createFood(FoodVO foodVO) {
		log.info("createFood()");
		int result = foodMapper.insertFood(foodVO);

		imgThumbnailMapper.insertImgThumbnail(foodVO.getImgThumbnail());
		if(foodVO.getImgList() != null) {			
			imgFoodMapper.insertImgFoodList(foodVO.getImgList());
		}
		return result;
	}

	@Override
	public List<FoodVO> getAllFood(Pagination pagination) {
		log.info("getAllFood()");
		return foodMapper.selectAllFoodByPagination(pagination);
	}

	@Override
	public FoodVO getFoodById(int foodId) {
		log.info("getFoodById()");
		FoodVO foodVO = foodMapper.selectFoodById(foodId);

		ImgVO imgVO = imgThumbnailMapper.selectImgThumbnailByFoodId(foodId);
		List<ImgVO> list = imgFoodMapper.selectImgFoodByFoodId(foodId);

		foodVO.setImgThumbnail(imgVO);
		foodVO.setImgList(list);

		return foodVO;
	}

	@Override
	public int updateFoodById(FoodVO foodVO) {
		log.info("updateFoodById()");

		int result = foodMapper.updateFoodById(foodVO);

		imgThumbnailMapper.deleteImgThumbnail(foodVO.getFoodId());
		imgFoodMapper.deleteImgFood(foodVO.getFoodId());

		foodVO.getImgThumbnail().setForeignId(foodVO.getFoodId());
		imgThumbnailMapper.insertImgThumbnail(foodVO.getImgThumbnail());

		if(foodVO.getImgList() != null) {
			for(ImgVO imgVO : foodVO.getImgList()) {
				imgVO.setForeignId(foodVO.getFoodId());
			}
			log.info(foodVO.getImgList());
			imgFoodMapper.insertImgFoodList(foodVO.getImgList());
		}

		return result;

	}

	@Override
	public int deleteFoodById(int foodId) {
		log.info("deleteFoodById()");
		int result = foodMapper.deleteFoodById(foodId);
		return result;
	}

	@Override
	public int updateFoodStockByFoodAmount(int foodId, int foodAmount) {
		log.info("updateFoodStockByFoodAmount()");

		int result = foodMapper.updateFoodStockByFoodAmount(foodId, foodAmount);

		return result;
	}

	@Override
	public int checkFoodAmountStatus(int foodId) {
		log.info("checkFoodAmountStatus()");

		int result = foodMapper.checkFoodAmountStatus(foodId);

		return result;
	}

	@Override
	public int countTotalFood() {
		return foodMapper.countTotalFood();
	}

	@Override
	public String getFoodNameByFoodId(int foodId) {
		 return foodMapper.getFoodNameByFoodId(foodId);
	}

	@Override
	public int getFoodStock(int foodId) {
		return foodMapper.getFoodStock(foodId);
	}
	
	@Override
	public String getFoodTypeByFoodId(int foodId) {
		return foodMapper.getFoodTypeByFoodId(foodId);
	}


}
