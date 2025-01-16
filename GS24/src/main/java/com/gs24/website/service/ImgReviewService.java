package com.gs24.website.service;

import java.util.List;

import com.gs24.website.domain.ImgVO;


public interface ImgReviewService {
	
	int createImgReview(ImgVO imgReviewVO);
	
	List<ImgVO> getImgReviewByReviewId(int reviewId);
	
	List<ImgVO> getAllImgReview();
	
	ImgVO getImgReviewById(int ImgReviewId);
	
	int updateImgReview(ImgVO imgReviewVO);
	
	int deleteImgReviewById(int ImgReviewId);
	
	int deleteImgReviewByReviewId(int reviewId);
	
	List<ImgVO> selectOldReview();
}