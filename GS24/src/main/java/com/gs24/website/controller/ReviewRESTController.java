package com.gs24.website.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gs24.website.domain.ReviewVO;
import com.gs24.website.service.ReviewService;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("/review")
@Log4j
public class ReviewRESTController {
	
	@Autowired
	private ReviewService reviewService;
	
	@PostMapping
	public ResponseEntity<Integer> createReview(@RequestBody ReviewVO reviewVO){
		log.info("createReview()");
		log.info(reviewVO);
		Integer result = reviewService.createReview(reviewVO);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	@GetMapping("/all/{foodId}")
	public ResponseEntity<List<ReviewVO>> getAllReviewByFoodId(@PathVariable int foodId){
		log.info("getAllReviewByFoodId()");
		List<ReviewVO> list = reviewService.getAllReviewByFoodId(foodId);
		log.info(list);
		return new ResponseEntity<List<ReviewVO>>(list, HttpStatus.OK);
	}
	
	@PutMapping("/{reviewId}")
	public ResponseEntity<Integer> updateReview(@RequestBody ReviewVO reviewVO){
		log.info("updateReview()");
		log.info(reviewVO);
		Integer result = reviewService.updateReview(reviewVO);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	@DeleteMapping("/{reviewId}/{foodId}")
	public ResponseEntity<Integer> deleteReview(@PathVariable int reviewId, @PathVariable int foodId){
		log.info("deleteReview()");
		Integer result = reviewService.deleteReview(reviewId, foodId);
		
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
}
