package com.gs24.website.service;

import java.util.List;

import com.gs24.website.domain.ImgReviewVO;

public interface ImgReviewService {
	
	int createImgReview(ImgReviewVO imgReviewVO);
	
	List<ImgReviewVO> getImgReviewByReviewId(int reviewId);
	
	List<ImgReviewVO> getAllImgReview();
	
	ImgReviewVO getImgReviewById(int ImgReviewId);
	
	int updateImgReview(ImgReviewVO imgReviewVO);
	
	int deleteImgReviewById(int ImgReviewId);
	
	int deleteImgReviewByReviewId(int reviewId);
	
	List<ImgReviewVO> selectOldReview();
}