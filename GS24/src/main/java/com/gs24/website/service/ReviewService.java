package com.gs24.website.service;

import java.util.List;

import com.gs24.website.domain.ReviewVO;
import com.gs24.website.util.Pagination;

public interface ReviewService {
	int createReview(ReviewVO reviewVO, int preorderId);
	
	List<ReviewVO> getAllReviewByFoodId(int foodId);
	
	ReviewVO getReviewByReviewId(int reviewId);
	
	int updateReview(ReviewVO reviewVO);
	
	int deleteReview(int reviewId, int foodId);
	
	int deleteReviewByFoodId(int foodId);
	
	List<ReviewVO> getPagedReviewsByMemberId(String memberId, Pagination pagination);
	   
	int countReviewByMemberId(String memberId);
	
	List<ReviewVO> getReviewPaginationByFoodId(int foodId, Pagination pagination);
	
	int getReviewCountByFoodId(int foodId);
}
