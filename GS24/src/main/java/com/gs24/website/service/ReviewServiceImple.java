package com.gs24.website.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gs24.website.domain.ReviewVO;
import com.gs24.website.persistence.FoodMapper;
import com.gs24.website.persistence.ReviewMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ReviewServiceImple implements ReviewService {

	@Autowired
	private ReviewMapper reviewMapper;

	@Autowired
	private FoodMapper foodMapper;

	@Override
	@Transactional("transactionManager()")
	public int createReview(ReviewVO reviewVO) {
		log.info("createReview()");
		int result = reviewMapper.insertReview(reviewVO);
		List<ReviewVO> list = reviewMapper.selectReviewByFoodId(reviewVO.getFoodId());

		int totalReviewRating = 0;
		int size = list.size();
		for (ReviewVO i : list) {
			totalReviewRating += i.getReviewRating();
		}
		log.info(list.size());
		log.info(totalReviewRating / size);
		foodMapper.updateFoodAvgRatingByFoodId(reviewVO.getFoodId(), totalReviewRating / size);
		foodMapper.updateFoodReviewCntByFoodId(reviewVO.getFoodId(), size);
		return result;
	}

	@Override
	public List<ReviewVO> getAllReviewByFoodId(int foodId) {
		log.info("getAllReviewByReviewId()");
		List<ReviewVO> list = reviewMapper.selectReviewByFoodId(foodId);
		return list;
	}

	@Override
	@Transactional("transactionManager()")
	public int updateReview(ReviewVO reviewVO) {
		log.info("updateReview");
		int result = reviewMapper.updateReview(reviewVO);
		
		List<ReviewVO> list = reviewMapper.selectReviewByFoodId(reviewVO.getFoodId());
		int size = list.size();
		
		log.info(list);
		int totalReviewRating = 0;

		for (ReviewVO i : list) {
			totalReviewRating += i.getReviewRating();
		}
		log.info(size);
		log.info(totalReviewRating / size);
		foodMapper.updateFoodAvgRatingByFoodId(reviewVO.getFoodId(), totalReviewRating / size);

		return result;
	}

	@Override
	@Transactional("transactionManager()")
	public int deleteReview(int reviewId, int foodId) {
		log.info("deleteReview");
		int result = reviewMapper.deleteReview(reviewId);
		
		List<ReviewVO> list = reviewMapper.selectReviewByFoodId(foodId);
		log.info(list);
		int totalReviewRating = 0;
		int size = list.size();
		
		for (ReviewVO i : list) {
			totalReviewRating += i.getReviewRating();
		}
		log.info(size);
		log.info(totalReviewRating / size);
		foodMapper.updateFoodAvgRatingByFoodId(foodId, totalReviewRating / size);
		foodMapper.updateFoodReviewCntByFoodId(foodId, list.size());
		return result;
	}

}
