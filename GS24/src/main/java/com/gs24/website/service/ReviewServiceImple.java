package com.gs24.website.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.gs24.website.domain.ReviewVO;
import com.gs24.website.persistence.FoodMapper;
import com.gs24.website.persistence.ReviewMapper;
import com.gs24.website.util.Pagination;
import com.gs24.website.util.uploadImgFoodUtil;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ReviewServiceImple implements ReviewService {

	@Autowired
	private ReviewMapper reviewMapper;

	@Autowired
	private FoodMapper foodMapper;

	@Autowired
	private String uploadPath;

	@Override
	@Transactional("transactionManager()")
	public int createReview(ReviewVO reviewVO, MultipartFile file) {
		log.info("createReview()");

		ReviewVO vo = reviewMapper.selectFirstReview();
		log.info(vo);
		String chgName = "ReviewNO" + reviewMapper.selectNextReviewId();
		uploadImgFoodUtil.saveFile(reviewVO, uploadPath, file,
				chgName + "." + uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));

		reviewVO.setReviewImgPath(uploadImgFoodUtil.makeDir(reviewVO) + chgName + "."
				+ uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));

		int result = reviewMapper.insertReview(reviewVO);

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
		log.info(reviewVO);
		return reviewVO;
	}

	@Override
	@Transactional("transactionManager()")
	public int updateReview(ReviewVO reviewVO, MultipartFile file) {
		log.info("updateReview");
		int result = reviewMapper.updateReview(reviewVO);

		if (file != null) {
			String path = reviewMapper.selectReviewByReviewId(reviewVO.getReviewId()).getReviewImgPath();
			int dotIndex = path.lastIndexOf('\\');

			String chgName = path.substring(dotIndex + 1);

			log.info(chgName);

			uploadImgFoodUtil.updateFile(reviewVO, uploadPath, file, uploadImgFoodUtil.subStrName(chgName),
					uploadImgFoodUtil.subStrExtension(chgName),
					uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));
			String imgPath = uploadImgFoodUtil.makeDir(reviewVO) + uploadImgFoodUtil.subStrName(chgName) + "."
					+ uploadImgFoodUtil.subStrExtension(file.getOriginalFilename());
			log.info(imgPath);

			reviewMapper.updateReviewImgPath(imgPath, reviewVO.getReviewId());
		}

		List<ReviewVO> list = reviewMapper.selectReviewByFoodId(reviewVO.getFoodId());
		int size = list.size();

		int totalReviewRating = 0;

		for (ReviewVO i : list) {
			totalReviewRating += i.getReviewRating();
		}

		log.info("Æò±Õ º°Á¡ : " + totalReviewRating / size);
		foodMapper.updateFoodAvgRatingByFoodId(reviewVO.getFoodId(), totalReviewRating / size);

		return result;
	}

	@Override
	@Transactional("transactionManager()")
	public int deleteReview(int reviewId, int foodId) {
		log.info("deleteReview");

		ReviewVO reviewVO = reviewMapper.selectReviewByReviewId(reviewId);

		int dotIndex = reviewVO.getReviewImgPath().lastIndexOf('\\');

		String chgName = reviewVO.getReviewImgPath().substring(dotIndex + 1);

		log.info(chgName);

		uploadImgFoodUtil.deleteFile(reviewVO, uploadPath, chgName);

		int result = reviewMapper.deleteReview(reviewId);

		List<ReviewVO> list = reviewMapper.selectReviewByFoodId(foodId);

		int totalReviewRating = 0;
		int size = list.size();

		for (ReviewVO i : list) {
			totalReviewRating += i.getReviewRating();
		}
		log.info(size);

		if (size > 0) {
			log.info("Æò±Õ º°Á¡ : " + totalReviewRating / size);
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
	public List<ReviewVO> getAllReviewByMemberId(String memberId, Pagination pagination) {
		log.info("getAllReviewByMemberId()");
		return reviewMapper.selectReviewByMemberIdPagination(pagination);
	}

}