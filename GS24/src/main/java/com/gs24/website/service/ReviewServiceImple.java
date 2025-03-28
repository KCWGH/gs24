package com.gs24.website.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gs24.website.domain.ImgVO;
import com.gs24.website.domain.ReviewRatingVO;
import com.gs24.website.domain.ReviewVO;
import com.gs24.website.persistence.FoodMapper;
import com.gs24.website.persistence.ImgReviewMapper;
import com.gs24.website.persistence.PreorderMapper;
import com.gs24.website.persistence.ReviewMapper;
import com.gs24.website.util.Pagination;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ReviewServiceImple implements ReviewService {

	@Autowired
	private ReviewMapper reviewMapper;
	
	@Autowired
	private PreorderMapper preorderMapper;
	
	@Autowired
	private ImgReviewMapper imgReviewMapper;
	
	@Autowired
	private FoodMapper foodMapper;

	@Override
	public int createReview(ReviewVO reviewVO, int preorderId) {
		log.info("createReview()");

		int result = reviewMapper.insertReview(reviewVO);

		List<ImgVO> imgList = reviewVO.getImgList();
				
		if(imgList != null) {
			for(ImgVO vo : imgList) {
				imgReviewMapper.insertImgReview(vo);
			}
		}
		preorderMapper.updateWriteReview(preorderId);

		ReviewRatingVO reviewRatingVO = reviewMapper.selectTotalRatingReviewCntByFoodId(reviewVO.getFoodId());
		
		foodMapper.updateFoodTotalRatingFoodReviewCntByFoodId(reviewRatingVO.getAvgRating(), reviewRatingVO.getReviewCnt(), reviewVO.getFoodId());
		return result;
	}

	@Override
	public List<ReviewVO> getAllReviewByFoodId(int foodId) {
		log.info("getAllReviewByReviewId()");
		List<ReviewVO> list = reviewMapper.selectReviewByFoodId(foodId);
		
		for(ReviewVO vo : list) {
			vo.setImgList(imgReviewMapper.selectImgReviewByReviewId(vo.getReviewId()));
		}
		
		log.info(list);
		return list;
	}

	@Override
	public ReviewVO getReviewByReviewId(int reviewId) {
		log.info("getReviewByReviewId()");
		ReviewVO reviewVO = reviewMapper.selectReviewByReviewId(reviewId);
		
		List<ImgVO> list = imgReviewMapper.selectImgReviewByReviewId(reviewId);
		
		reviewVO.setImgList(list);
		log.info(reviewVO);
		return reviewVO;
	}

	@Override
	public int updateReview(ReviewVO reviewVO) {
		log.info("updateReview");
		int result = reviewMapper.updateReview(reviewVO);
		
		imgReviewMapper.deleteImgReviewByReviewId(reviewVO.getReviewId());
		
		List<ImgVO> updateList = reviewVO.getImgList();
		
		log.info(updateList);
		
		if(updateList != null) {			
			for(ImgVO vo : updateList) {
				vo.setForeignId(reviewVO.getReviewId());
			}
			imgReviewMapper.insertImgReviewList(updateList);
		}
		
		int foodId = reviewVO.getFoodId();
		ReviewRatingVO ratingVO = reviewMapper.selectTotalRatingReviewCntByFoodId(foodId);
		
		foodMapper.updateFoodTotalRatingFoodReviewCntByFoodId(ratingVO.getAvgRating(), ratingVO.getReviewCnt(), foodId);

		return result;
	}

	@Override
	public int deleteReview(int reviewId, int foodId) {
		log.info("deleteReview");

		int result = reviewMapper.deleteReview(reviewId);
		imgReviewMapper.deleteImgReviewByReviewId(reviewId);
		
		ReviewRatingVO ratingVO = reviewMapper.selectTotalRatingReviewCntByFoodId(foodId);
		foodMapper.updateFoodTotalRatingFoodReviewCntByFoodId(ratingVO.getAvgRating(), ratingVO.getReviewCnt(), foodId);
		
		return result;
	}

	@Override
	public int countReviewByMemberId(String memberId) {
		int result = reviewMapper.countReviewByMemberId(memberId);
		return result;
	}

	@Override
	public List<ReviewVO> getPagedReviewsByMemberId(String memberId, Pagination pagination) {
		log.info("getAllReviewByMemberId()");
		return reviewMapper.selectReviewByMemberIdPagination(pagination);
	}

	@Override
	public List<ReviewVO> getReviewPaginationByFoodId(int foodId, Pagination pagination) {
		log.info("getReviewPaginationByFoodId()");
		List<ReviewVO> list = reviewMapper.selectReviewPagination(foodId, pagination.getStart(), pagination.getEnd());
		for(ReviewVO vo : list) {
			vo.setImgList(imgReviewMapper.selectImgReviewByReviewId(vo.getReviewId()));
		}
		return list;
	}

	@Override
	public int getReviewCountByFoodId(int foodId) {
		log.info("getReviewCountBufoodId()");
		int result = reviewMapper.selectTotalCountByFoodId(foodId);
		return result;
	}

	@Override
	public int deleteReviewByFoodId(int foodId) {
		log.info("deleteReviewByFoodId()");
		int result = reviewMapper.deleteReviewByFoodId(foodId);
		return result;
	}

}