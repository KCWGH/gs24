package com.gs24.website.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.gs24.website.domain.ReviewVO;
import com.gs24.website.util.Pagination;

public interface ReviewService {
	int createReview(ReviewVO reviewVO, MultipartFile file);
	
	List<ReviewVO> getAllReviewByFoodId(int foodId);
	
	ReviewVO getReviewByReviewId(int reviewId);
	
	int updateReview(ReviewVO reviewVO, MultipartFile file);
	
	int deleteReview(int reviewId, int foodId);
	
	int deleteReviewByFoodId(int foodId);
	
	List<ReviewVO> getAllReviewByMemberId(String memberId, Pagination pagination);
	   
	int countReviewByMemberId(String memberId);
	
	List<ReviewVO> getReviewPaginationByFoodId(int foodId, Pagination pagination);
	
	int getReviewCountByFoodId(int foodId);
}
