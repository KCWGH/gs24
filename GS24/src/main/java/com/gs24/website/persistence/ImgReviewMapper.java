package com.gs24.website.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gs24.website.domain.ImgReviewVO;

@Mapper
public interface ImgReviewMapper {
	
	int insertImgReview(ImgReviewVO imgReviewVO);
	
	List<ImgReviewVO> selectImgReviewByReviewId(int reviewId);
	
	ImgReviewVO selectImgReviewById(int ImgReviewId);
	
	List<ImgReviewVO> selectAllImgReview();
	
	int updateImgReview(ImgReviewVO imgReviewVO);
	
	int deleteImgReviewById(int ImgReviewId);
	
	int deleteImgReviewByReviewId(int reviewId);
	
	List<ImgReviewVO> selectOldReview();
}
