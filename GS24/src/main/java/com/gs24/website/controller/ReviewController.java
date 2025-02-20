package com.gs24.website.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gs24.website.check.CheckReviewData;
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
	private CheckReviewData checkReviewData;

	@PostMapping("/list")
	@ResponseBody
	public List<ReviewVO> listGET(@RequestBody int foodId) {
		log.info("listGET()");

		List<ReviewVO> reviewList = reviewService.getAllReviewByFoodId(foodId);

		return reviewList;
	}

	@GetMapping("/register")
	public void registerGET(Model model, int foodId, int convenienceId, int preorderId) {
		log.info("registerGET()");
		log.info("foodId : " + foodId + " convenienceId : " + convenienceId + " preorderId : " + preorderId);
		model.addAttribute("foodId", foodId);
		model.addAttribute("preorderId", preorderId);
		model.addAttribute("convenienceId", convenienceId);
	}

	@PostMapping("/register")
	public String registerPOST(Authentication auth, ReviewVO reviewVO, int convenienceId, int status, int preorderId) {
		log.info("registerPOST()");
		log.info(reviewVO);

		// status :
		if (status == 1 && checkReviewData.checkReviewData(auth, reviewVO, convenienceId)) {
			reviewService.createReview(reviewVO, preorderId);
		}

		return "redirect:../convenienceFood/list?convenienceId=" + convenienceId;
	}

	@GetMapping("/delete")
	public String deleteGET(int reviewId, int foodId, int convenienceId) {
		log.info(reviewId);
		log.info("deleteGET()");
		reviewService.deleteReview(reviewId, foodId);

		return "redirect:../convenienceFood/list?convenienceId=" + convenienceId;
	}

	@GetMapping("/update")
	public void updateGET(Model model, int reviewId, int convenienceId) {
		log.info("updateGET()");
		ReviewVO reviewVO = reviewService.getReviewByReviewId(reviewId);
		model.addAttribute("reviewVO", reviewVO);
	}

	@PostMapping("/update")
	public String updatePOST(ReviewVO reviewVO, int convenienceId) {
		log.info("updatePOST()");
		log.info(reviewVO);

		if (checkReviewData.checkReviewData(null, reviewVO, convenienceId)) {
			reviewService.updateReview(reviewVO);
		}

		return "redirect:../convenienceFood/list?convenienceId=" + convenienceId;
	}
}