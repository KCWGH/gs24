package com.gs24.website.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gs24.website.domain.ConvenienceDetailFoodVO;
import com.gs24.website.domain.ConvenienceFoodVO;
import com.gs24.website.domain.ReviewVO;
import com.gs24.website.persistence.ConvenienceFoodMapper;
import com.gs24.website.persistence.ConvenienceMapper;
import com.gs24.website.persistence.FoodListMapper;
import com.gs24.website.persistence.ImgFoodMapper;
import com.gs24.website.persistence.ImgReviewMapper;
import com.gs24.website.persistence.ReviewMapper;
import com.gs24.website.util.Pagination;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
@Transactional(value = "transactionManager")
public class ConvenienceFoodServiceImple implements ConvenienceFoodService {

	@Autowired
	private ConvenienceFoodMapper convenienceFoodMapper;

	@Autowired
	private ConvenienceMapper convenienceMapper;

	@Autowired
	private FoodListMapper foodListMapper;

	@Autowired
	private ImgFoodMapper imgFoodMapper;

	@Autowired
	private ReviewMapper reviewMapper;

	@Autowired
	private ImgReviewMapper imgReviewMapper;

	@Override
	public int createConvenienceFood(int foodId, int foodAmount, String ownerId) {
		log.info("createConvenienceFood()");

		int result = 0;

		int convenienceId = convenienceMapper.selectConvenienceIdByOwnerId(ownerId);
		foodListMapper.updateFoodStockByFoodAmount(foodId, foodAmount);

		if (convenienceFoodMapper.checkHasFood(foodId, convenienceId) == 1) {
			log.info("기존 데이터가 있음!!");
			result = convenienceFoodMapper.updateFoodAmountByInsert(foodId, foodAmount, convenienceId);
			return result;
		}

		result = convenienceFoodMapper.insertConvenienceFood(foodId, foodAmount, convenienceId);

		return result;
	}

	@Override
	public List<ConvenienceFoodVO> getAllConvenienceFood() {
		log.info("getAllConvenienceFood()");

		List<ConvenienceFoodVO> list = convenienceFoodMapper.selectAllConvenienceFood();

		return list;
	}

	@Override
	public List<ConvenienceFoodVO> getPagedConvenienceFoodsByConvenienceId(int convenienceId, Pagination pagination) {
		log.info("getConvenienceFoodByConvenienceId()");
		
		if(pagination.getKeyword() == null)
			pagination.setKeyword("");
		if(pagination.getBottomPrice() == null)
			pagination.setBottomPrice("");
		
		List<ConvenienceFoodVO> list = convenienceFoodMapper.selectPagedConvenienceFoodByConvenienceId(pagination);

		return list;
	}

	@Override
	public ConvenienceFoodVO getConvenienceFoodByFoodId(int foodId, int convenienceId) {
		log.info("getConvenienceFoodByFoodId()");

		ConvenienceFoodVO convenienceFoodVO = convenienceFoodMapper
				.selectConvenienceFoodByFoodIdAndConvenienceId(foodId, convenienceId);

		return convenienceFoodVO;
	}

	@Override
	public ConvenienceDetailFoodVO getDetailConvenienceFoodByFoodId(int foodId, int convenienceId) {
		log.info("getDetailConvenienceFoodByFoodId()");

		ConvenienceDetailFoodVO convenienceFoodVO = convenienceFoodMapper.selectDetailConvenienceFoodByFoodId(foodId,
				convenienceId);

		convenienceFoodVO.setImgList(imgFoodMapper.selectImgFoodByFoodId(foodId));

		return convenienceFoodVO;
	}

	@Override
	public List<ReviewVO> getReviewsByFoodId(int foodId, Pagination pagination) {
		log.info("getReviewsByFoodId()");
		List<ReviewVO> list = reviewMapper.selectReviewPagination(foodId, pagination.getStart(), pagination.getEnd());

		for (ReviewVO reviewVO : list) {
			reviewVO.setImgList(imgReviewMapper.selectImgReviewByReviewId(reviewVO.getReviewId()));
		}

		return list;
	}

	@Override
	public List<String> getFoodTypeList() {
		List<String> list = convenienceFoodMapper.selectFoodType();
		list.add("전체");
		return list;
	}
	
	@Override
	public int updateShowStatus(int foodId, int convenienceId) {
		log.info("updateShowStatus");
		int result = convenienceFoodMapper.updateShowStatus(foodId, convenienceId);
		return result;
	}

	@Override
	public int countReviewsByFoodId(int foodId) {
		return reviewMapper.selectTotalCountByFoodId(foodId);
	}

	@Override
	public int getTotalCountByConvenienceId(int convenienceId) {
		return convenienceFoodMapper.countTotalFoodsByConvenienceId(convenienceId);
	}

}
