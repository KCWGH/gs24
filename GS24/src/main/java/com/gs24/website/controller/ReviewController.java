package com.gs24.website.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gs24.website.domain.ReviewVO;
import com.gs24.website.service.ReviewService;
import com.gs24.website.util.PageMaker;
import com.gs24.website.util.Pagination;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/review")
@Log4j
public class ReviewController {

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private String uploadPath;

	@GetMapping("/list")
	public void listGET(Model model,int foodId, Pagination pagination) {
		log.info("listGET()");
		log.info("foodId : " + foodId);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setPagination(pagination);
		pageMaker.setTotalCount(reviewService.getReviewCountByFoodId(foodId));
		
		List<ReviewVO> list = reviewService.getReviewPaginationByFoodId(foodId, pagination);

		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("reviewList", list);
		model.addAttribute("foodId", foodId);
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
		int result = reviewService.deleteReview(reviewId, foodId);

		if(result == 0) {
			log.info(result + "占쏙옙 占쏙옙占쏙옙占실억옙占쏙옙占싹댐옙.");
		} else {
			log.info("占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙占쌩쏙옙占싹댐옙.");	
		}

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
		
		return "redirect:list?foodId=" + reviewVO.getFoodId();
	}
}