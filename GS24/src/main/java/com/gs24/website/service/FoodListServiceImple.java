package com.gs24.website.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gs24.website.domain.FoodListVO;
import com.gs24.website.domain.ImgVO;
import com.gs24.website.persistence.FoodListMapper;
import com.gs24.website.persistence.ImgFoodMapper;
import com.gs24.website.persistence.ImgThumbnailMapper;
import com.gs24.website.util.Pagination;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class FoodListServiceImple implements FoodListService {

	@Autowired
	private FoodListMapper foodListMapper;

	@Autowired
	private ImgFoodMapper imgFoodMapper;

	@Autowired
	private ImgThumbnailMapper imgThumbnailMapper;

	@Override
	public int createFood(FoodListVO foodListVO) {
		log.info("createFood()");
		int result = foodListMapper.insertFood(foodListVO);

		imgThumbnailMapper.insertImgThumbnail(foodListVO.getImgThumbnail());
		if (foodListVO.getImgList() != null) {
			for (ImgVO vo : foodListVO.getImgList()) {
				imgFoodMapper.insertImgFood(vo);
			}
		}
		return result;
	}

	@Override
	public List<FoodListVO> getAllFood(Pagination pagination) {
		log.info("getAllFood()");
		return foodListMapper.selectAllFoodByPagination(pagination);
	}

	@Override
	public FoodListVO getFoodById(int foodId) {
		log.info("getFoodById()");
		FoodListVO foodListVO = foodListMapper.selectFoodById(foodId);

		ImgVO imgVO = imgThumbnailMapper.selectImgThumbnailByFoodId(foodId);
		List<ImgVO> list = imgFoodMapper.selectImgFoodByFoodId(foodId);

		foodListVO.setImgThumbnail(imgVO);
		foodListVO.setImgList(list);

		return foodListVO;
	}

	@Override
	public int updateFoodById(FoodListVO foodListVO) {
		log.info("updateFoodById()");

		int result = foodListMapper.updateFoodById(foodListVO);

		imgThumbnailMapper.deleteImgThumbnail(foodListVO.getFoodId());
		imgFoodMapper.deleteImgFood(foodListVO.getFoodId());

		foodListVO.getImgThumbnail().setForeignId(foodListVO.getFoodId());
		imgThumbnailMapper.insertImgThumbnail(foodListVO.getImgThumbnail());

		List<ImgVO> list = foodListVO.getImgList();

		if (foodListVO.getImgList() != null) {
			for (ImgVO vo : list) {
				vo.setForeignId(foodListVO.getFoodId());
				imgFoodMapper.insertImgFood(vo);
			}
		}

		return result;

	}

	@Override
	public int deleteFoodById(int foodId) {
		log.info("deleteFoodById()");
		int result = foodListMapper.deleteFoodById(foodId);
		return result;
	}

	@Override
	public int updateFoodStockByFoodAmount(int foodId, int foodAmount) {
		log.info("updateFoodStockByFoodAmount()");

		int result = foodListMapper.updateFoodStockByFoodAmount(foodId, foodAmount);

		return result;
	}

	@Override
	public int checkFoodAmountStatus(int foodId) {
		log.info("checkFoodAmountStatus()");

		int result = foodListMapper.checkFoodAmountStatus(foodId);

		return result;
	}

	@Override
	public int countTotalFood() {
		return foodListMapper.countTotalFood();
	}
}
