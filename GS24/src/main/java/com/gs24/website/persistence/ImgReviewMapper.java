package com.gs24.website.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gs24.website.domain.ImgVO;

@Mapper
public interface ImgReviewMapper {
	
	int insertImgReview(ImgVO imgVO);
	
	int insertImgReviewList(List<ImgVO> ImgList);
	
	List<ImgVO> selectImgReviewByReviewId(int reviewId);
	
	ImgVO selectImgReviewById(int ImgReviewId);
	
	List<ImgVO> selectAllImgReview();
	
	int updateImgReview(ImgVO imgVO);
	
	int deleteImgReviewById(int ImgReviewId);
	
	int deleteImgReviewByReviewId(int reviewId);
	
	List<ImgVO> selectOldReview();
}
