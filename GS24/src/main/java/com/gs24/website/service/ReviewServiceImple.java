package com.gs24.website.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gs24.website.domain.ReviewVO;
import com.gs24.website.persistence.ReviewMapper;

import lombok.extern.log4j.Log4j;
@Service
@Log4j
public class ReviewServiceImple implements ReviewService{
	
	@Autowired
	private ReviewMapper reviewMapper;
	
	@Override
	public int createReview(ReviewVO reviewVO) {
		log.info("createReview()");
		int result = reviewMapper.insertReview(reviewVO);
		return result;
	}

	@Override
	public List<ReviewVO> getAllReviewByFoodId(int foodId) {
		log.info("getAllReviewByReviewId()");
		List<ReviewVO> list = reviewMapper.selectReviewByFoodId(foodId);
		return list;
	}

	@Override
	public int updateReview(ReviewVO reviewVO) {
		log.info("updateReview");
		int result = reviewMapper.updateReview(reviewVO);
		return result;
	}

	@Override
	public int deleteReview(int reviewId) {
		log.info("deleteReview");
		int result = reviewMapper.deleteReview(reviewId);
		return result;
	}

}
