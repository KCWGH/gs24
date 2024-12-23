package com.gs24.website.service;

import java.util.List;

import com.gs24.website.domain.ReviewVO;

public interface ReviewService {
	int createReview(ReviewVO reviewVO);
	
	List<ReviewVO> getAllReviewByFoodId(int foodId);
	
	int updateReview(ReviewVO reviewVO);
	
	int deleteReview(int reviewId, int foodId);
}
