package com.gs24.website.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gs24.website.domain.ReviewVO;

@Mapper
public interface ReviewMapper {
	int insertReview(ReviewVO reviewVO);
	
	List<ReviewVO> selectReviewByFoodId(int foodId);
	
	int updateReview(ReviewVO reviewVO);
	
	int deleteReview(int reviewId);
}
