package com.gs24.website.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gs24.website.domain.FoodVO;
import com.gs24.website.domain.ImgVO;
import com.gs24.website.domain.ReviewVO;
import com.gs24.website.persistence.FavoritesMapper;
import com.gs24.website.persistence.FoodMapper;
import com.gs24.website.persistence.ImgFoodMapper;
import com.gs24.website.persistence.ImgReviewMapper;
import com.gs24.website.persistence.ReviewMapper;
import com.gs24.website.util.PageMaker;
import com.gs24.website.util.Pagination;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class FoodServiceImple implements FoodService {

	@Autowired
	private FoodMapper foodMapper;

	@Autowired
	private ImgFoodMapper imgFoodMapper;

	@Autowired
	private ReviewMapper reviewMapper;

	@Autowired
	private ImgReviewMapper imgReviewMapper;

	@Autowired
	private FavoritesMapper favoritesMapper;

	@Override
	public int createFood(FoodVO foodVO) {
		log.info("createFood()");
		int result = foodMapper.insertFood(foodVO);

		List<ImgVO> imgList = foodVO.getImgList();

		for (ImgVO imgVO : imgList) {
			imgFoodMapper.insertImgFood(imgVO);
		}

		return result;
	}

	@Override
	public List<FoodVO> getAllFood() {
		log.info("getAllFood()");
		List<FoodVO> foodList = foodMapper.selectFoodList();
		for (FoodVO i : foodList) {
			List<ImgVO> imgList = imgFoodMapper.selectImgFoodByFoodId(i.getFoodId());
			i.setImgList(imgList);
		}
		return foodList;
	}

	@Override
	public FoodVO getFoodById(int foodId) {
		log.info("getFoodById");
		FoodVO foodVO = foodMapper.selectFoodById(foodId);
		foodVO.setImgList(imgFoodMapper.selectImgFoodByFoodId(foodId));
		return foodVO;
	}

	public FoodVO getFirstFoodId() {
		log.info("getFirstFoodId()");
		return foodMapper.selectFirstFoodId();
	}

	@Override
	public int updateFood(FoodVO foodVO) {
		log.info("updateFood()");
		log.info(foodVO);
		int result = foodMapper.updateFood(foodVO);

		List<ImgVO> imgList = foodVO.getImgList();

		imgFoodMapper.deleteImgFood(foodVO.getFoodId());

		for (ImgVO imgVO : imgList) {
			imgVO.setForeignId(foodVO.getFoodId());
			imgFoodMapper.insertImgFood(imgVO);
		}

		return result;
	}

	@Override
	public int updateFoodStock(int foodId, int foodStock) {
		log.info("updateFoodStock()");
		int result = foodMapper.updateFoodStock(foodId, foodStock);
		return result;
	}

	@Override
	public int updateFoodPrice(int foodId, int foodPrice) {
		log.info("updateFoodPrice()");
		int result = foodMapper.updateFoodPrice(foodId, foodPrice);
		return result;
	}

	@Override
	public int updateFoodProteinFatCarb(int foodId, int foodProtein, int foodFat, int foodCarb) {
		log.info("updateFoodProteinFatCarb()");
		int result = foodMapper.updateFoodProteinFatCarb(foodId, foodProtein, foodFat, foodCarb);
		return result;
	}

	@Override
	public int updateFoodAmountByPreorderAmount(int foodId, int preorderAmount) {
		log.info("updateFoodAmountByPreorderAmount()");
		int result = foodMapper.updateFoodAmountByPreorderAmount(foodId, preorderAmount);
		return result;
	}

	@Override
	public int deleteFood(int foodId) {
		log.info("deleteFood()");
		int result = foodMapper.deleteFood(foodId);

		imgFoodMapper.deleteImgFood(foodId);

		reviewMapper.deleteReviewByFoodId(foodId);

		favoritesMapper.deleteAllFavorites(foodId);

		return result;
	}

	@Override
	public List<FoodVO> getPaginationFood(Pagination pagination) {
		log.info("getPaginationFood()");
		List<FoodVO> list = foodMapper.selectFoodPagination(pagination);
		log.info(list);
		for (FoodVO foodVO : list) {
			foodVO.setImgList(imgFoodMapper.selectImgFoodByFoodId(foodVO.getFoodId()));
			log.info(foodVO.getImgList());
		}
		return list;
	}

	@Override
	public int getFoodTotalCount(Pagination pagination) {
		log.info("getFoodTotalCount()");
		int result = foodMapper.selectFoodTotalCount(pagination);
		log.info("FoodTotalCount : " + result);
		return result;
	}

	@Override
	public Object[] getDetailData(int foodId, Pagination pagination) {
		log.info("getDetailData()");
		Object[] detailData = new Object[3];
		FoodVO foodVO = foodMapper.selectFoodById(foodId);

		foodVO.setImgList(imgFoodMapper.selectImgFoodByFoodId(foodId));

		List<ReviewVO> reviewList = reviewMapper.selectReviewPagination(foodId, pagination.getStart(),
				pagination.getEnd());
		for (ReviewVO vo : reviewList) {
			List<ImgVO> list = imgReviewMapper.selectImgReviewByReviewId(vo.getReviewId());
			vo.setImgList(list);
		}

		PageMaker pageMaker = new PageMaker();
		pageMaker.setPagination(pagination);
		pageMaker.setTotalCount(reviewMapper.selectTotalCountByFoodId(foodId));

		detailData[0] = foodVO;
		detailData[1] = reviewList;
		detailData[2] = pageMaker;

		log.info(reviewList.size());
		return detailData;
	}

	@Override
	public List<String> getFoodTypeList() {
		List<String> list = foodMapper.selectFoodType();
		list.add("전체");
		return list;
	}
}
