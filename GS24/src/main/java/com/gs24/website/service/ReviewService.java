package com.gs24.website.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.gs24.website.domain.ReviewVO;

public interface ReviewService {
	int createReview(ReviewVO reviewVO, MultipartFile file);
	
	List<ReviewVO> getAllReviewByFoodId(int foodId);
	
	ReviewVO getReviewByReviewId(int reviewId);
	
	int updateReview(ReviewVO reviewVO);
	
	int deleteReview(int reviewId, int foodId);
}
