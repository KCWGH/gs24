package com.gs24.website.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gs24.website.domain.ImgVO;
import com.gs24.website.domain.ReviewVO;
import com.gs24.website.persistence.FoodMapper;
import com.gs24.website.persistence.ImgReviewMapper;
import com.gs24.website.persistence.ReviewMapper;
import com.gs24.website.util.Pagination;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ReviewServiceImple implements ReviewService {

	@Autowired
	private ReviewMapper reviewMapper;
	
	@Autowired
	private ImgReviewMapper imgReviewMapper;

	@Autowired
	private FoodMapper foodMapper;

	@Override
	@Transactional("transactionManager()")
	public int createReview(ReviewVO reviewVO) {
		log.info("createReview()");

		List<ImgVO> imgList = reviewVO.getImgList();
		
		log.info(imgList);
		
		for(ImgVO vo : imgList) {
			imgReviewMapper.insertImgReview(vo);
		}

		int result = reviewMapper.insertReview(reviewVO);
		
		//TODO : ȿ�� ������ ������ �ʿ���
		List<ReviewVO> list = reviewMapper.selectReviewByFoodId(reviewVO.getFoodId());

		int totalReviewRating = 0;
		int size = list.size();
		for (ReviewVO i : list) {
			totalReviewRating += i.getReviewRating();
		}

		log.info("Æò±Õ º°Á¡ : " + totalReviewRating / size);
		foodMapper.updateFoodAvgRatingByFoodId(reviewVO.getFoodId(), totalReviewRating / size);
		foodMapper.updateFoodReviewCntByFoodId(reviewVO.getFoodId(), size);
		return result;
	}

	@Override
	public List<ReviewVO> getAllReviewByFoodId(int foodId) {
		log.info("getAllReviewByReviewId()");
		List<ReviewVO> list = reviewMapper.selectReviewByFoodId(foodId);
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
	@Transactional("transactionManager()")
	public int updateReview(ReviewVO reviewVO) {
		log.info("updateReview");
		int result = reviewMapper.updateReview(reviewVO);
		
		imgReviewMapper.deleteImgReviewByReviewId(reviewVO.getReviewId());
		
		List<ImgVO> updateList = reviewVO.getImgList();
		
		log.info(updateList);
		
		for(ImgVO vo : updateList) {
			log.info(vo);
			vo.setForeignId(reviewVO.getReviewId());
			imgReviewMapper.insertImgReview(vo);
		}
		
		List<ReviewVO> list = reviewMapper.selectReviewByFoodId(reviewVO.getFoodId());
		int size = list.size();

		int totalReviewRating = 0;

		for (ReviewVO i : list) {
			totalReviewRating += i.getReviewRating();
		}

		if (size > 0) {
			log.info("��� ���� : " + totalReviewRating / size);
			foodMapper.updateFoodAvgRatingByFoodId(reviewVO.getFoodId(), totalReviewRating / size);
			foodMapper.updateFoodReviewCntByFoodId(reviewVO.getFoodId(), size);
		} else {
			foodMapper.updateFoodAvgRatingByFoodId(reviewVO.getFoodId(), size);
			foodMapper.updateFoodReviewCntByFoodId(reviewVO.getFoodId(), size);
		}

		return result;
	}

	@Override
	@Transactional("transactionManager()")
	public int deleteReview(int reviewId, int foodId) {
		log.info("deleteReview");

		int result = reviewMapper.deleteReview(reviewId);
		imgReviewMapper.deleteImgReviewByReviewId(reviewId);
		
		List<ReviewVO> list = reviewMapper.selectReviewByFoodId(foodId);

		int totalReviewRating = 0;
		int size = list.size();

		for (ReviewVO i : list) {
			totalReviewRating += i.getReviewRating();
		}
		log.info(size);

		if (size > 0) {
			log.info("��� ���� : " + totalReviewRating / size);
			foodMapper.updateFoodAvgRatingByFoodId(foodId, totalReviewRating / size);
			foodMapper.updateFoodReviewCntByFoodId(foodId, size);
		} else {
			foodMapper.updateFoodAvgRatingByFoodId(foodId, size);
			foodMapper.updateFoodReviewCntByFoodId(foodId, size);
		}
		
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