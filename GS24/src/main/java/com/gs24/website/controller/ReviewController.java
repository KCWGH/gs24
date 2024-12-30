package com.gs24.website.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gs24.website.domain.ReviewVO;
import com.gs24.website.service.ReviewService;
import com.gs24.website.util.uploadImgFoodUtil;

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
	public void listGET(Model model,int foodId) {
		log.info("listGET()");
		log.info("foodId : " + foodId);
		
		List<ReviewVO> list = reviewService.getAllReviewByFoodId(foodId);
		
		//리뷰 이미지 전체 경로로 바꾼 다음에 보내줘야 함
		model.addAttribute("reviewList", list);
		model.addAttribute("foodId", foodId);
	}
	
	@PostMapping("/register")
	public String registerPOST(ReviewVO reviewVO, MultipartFile file) {
		log.info("registerPOST()");
		log.info(file.getOriginalFilename());
		int result = reviewService.createReview(reviewVO, file);
		
		return "redirect:list?foodId=" + reviewVO.getFoodId();
	}
	
	@GetMapping("delete")
	public String deleteGET(int reviewId, int foodId) {
		log.info(reviewId);
		log.info("deleteGET()");
		int result = reviewService.deleteReview(reviewId, foodId);
		
		if(result == 0) {
			log.info(result + "행 삭제되었습니다.");
		} else {
			log.info("삭제에 실패했습니다.");	
		}
		
		return "redirect:list?foodId=" + foodId;
	}
	
	@PostMapping("update")
	public String updatePOST(ReviewVO reviewVO, MultipartFile file) {
		log.info("updatePOST()");
		log.info(reviewVO);
		log.info(file.getOriginalFilename());
		
		reviewService.updateReview(reviewVO, file);
		
		return "redirect:list?foodId=" + reviewVO.getFoodId();
	}
}
