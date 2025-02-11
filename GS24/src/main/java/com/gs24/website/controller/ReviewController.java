package com.gs24.website.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gs24.website.domain.ReviewVO;
import com.gs24.website.service.ReviewService;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/review")
@Log4j
public class ReviewController {

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private String uploadPath;

	@PostMapping("/list")
	@ResponseBody
	public List<ReviewVO> listGET(@RequestBody int foodId) {
		log.info("listGET()");

		List<ReviewVO> reviewList = reviewService.getAllReviewByFoodId(foodId);

		return reviewList;
	}

	@GetMapping("/register")
	public void registerGET(Model model, int foodId) {
		log.info("registerGET()");

		model.addAttribute("foodId", foodId);
	}

	@PostMapping("/register")
	public String registerPOST(ReviewVO reviewVO) {
		log.info("registerPOST()");
		log.info(reviewVO);
		reviewService.createReview(reviewVO);

		return "redirect:../food/list";
	}

	@GetMapping("/delete")
	public String deleteGET(int reviewId, int foodId) {
		log.info(reviewId);
		log.info("deleteGET()");
		reviewService.deleteReview(reviewId, foodId);

		return "redirect:../food/list";
	}

	@GetMapping("/update")
	public void updateGET(Model model, int reviewId) {
		log.info("updateGET()");
		ReviewVO reviewVO = reviewService.getReviewByReviewId(reviewId);
		model.addAttribute("reviewVO", reviewVO);
	}

	@PostMapping("/update")
	public String updatePOST(ReviewVO reviewVO) {
		log.info("updatePOST()");
		log.info(reviewVO);

		reviewService.updateReview(reviewVO);

		return "redirect:../food/detail?foodId=" + reviewVO.getFoodId();
	}
}