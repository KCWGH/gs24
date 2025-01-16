//package com.gs24.website.service;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.gs24.website.domain.ImgReviewVO;
//import com.gs24.website.persistence.ImgReviewMapper;
//
//import lombok.extern.log4j.Log4j;
//
//@Service
//@Log4j
//public class ImgReviewServiceImple implements ImgReviewService{
//	
//	@Autowired
//	private ImgReviewMapper imgReviewMapper;
//	
//	@Override
//	public int createImgReview(ImgReviewVO imgReviewVO) {
//		log.info("createImgReview()");
//		int result = imgReviewMapper.insertImgReview(imgReviewVO);
//		return result;
//	}
//
//	@Override
//	public List<ImgReviewVO> getImgReviewByReviewId(int reviewId) {
//		log.info("getImgReviewByReviewId()");
//		List<ImgReviewVO> list = imgReviewMapper.selectImgReviewByReviewId(reviewId);
//		return list;
//	}
//
//	@Override
//	public List<ImgReviewVO> getAllImgReview() {
//		log.info("selectAllImgReview()");
//		List<ImgReviewVO> list = imgReviewMapper.selectAllImgReview();
//		return list;
//	}
//
//	@Override
//	public ImgReviewVO getImgReviewById(int ImgReviewId) {
//		log.info("getImgReviewById()");
//		ImgReviewVO imgReviewVO = imgReviewMapper.selectImgReviewById(ImgReviewId);
//		return imgReviewVO;
//	}
//
//	@Override
//	public int updateImgReview(ImgReviewVO imgReviewVO) {
//		log.info("updateImgReview()");
//		int result = updateImgReview(imgReviewVO);
//		return result;
//	}
//
//	@Override
//	public int deleteImgReviewById(int ImgReviewId) {
//		log.info("deleteImgReviewById()");
//		int result = imgReviewMapper.deleteImgReviewById(ImgReviewId);
//		return result;
//	}
//
//	@Override
//	public List<ImgReviewVO> selectOldReview() {
//		log.info("selectOldReview()");
//		List<ImgReviewVO> list = imgReviewMapper.selectOldReview();
//		return list;
//	}
//
//	@Override
//	public int deleteImgReviewByReviewId(int reviewId) {
//		log.info("deleteImgReviewByReviewId()");
//		int result = imgReviewMapper.deleteImgReviewByReviewId(reviewId);
//		return result;
//	}
//
//}
