package com.gs24.website.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gs24.website.domain.ReviewVO;
import com.gs24.website.util.Pagination;

@Mapper
public interface ReviewMapper {
	int insertReview(ReviewVO reviewVO);

	List<ReviewVO> selectReviewByFoodId(int foodId);

	ReviewVO selectReviewByReviewId(int reviewId);

	ReviewVO selectFirstReview();

	long selectNextReviewId();
	
	int updateReview(ReviewVO reviewVO);

	int updateReviewTitle(@Param("reviewTitle") String reviewTitle, @Param("reviewId") int reviewId);
	
	int updateReviewContent(@Param("reviewContent")String reviewContent, @Param("reviewId")int reviewId);
	
	int updateReviewRating(@Param("reviewRating")int reviewRating, @Param("reviewId")int reviewId);
	
	int updateReviewImgPath(@Param("reviewImgPath")String reviewImgPath, @Param("reviewId")int reviewId);
	
	int deleteReview(int reviewId);
	
	int deleteReviewByFoodId(int foodId);
	
	List<ReviewVO> selectReviewByMemberIdPagination(Pagination pagination);
	   
	int countReviewByMemberId(String memberId);
	
	List<ReviewVO> selectReviewPagination(@Param("foodId")int foodId, @Param("start")int start, @Param("end")int end);
	
	int selectTotalCountByFoodId(int foodId);
	
	int selectAvgRatingByFoodId(int foodId);
}