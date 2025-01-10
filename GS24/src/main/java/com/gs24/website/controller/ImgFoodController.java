package com.gs24.website.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gs24.website.domain.ImgFoodVO;
import com.gs24.website.domain.ReviewVO;
import com.gs24.website.service.ImgFoodService;
import com.gs24.website.service.ReviewService;
import com.gs24.website.util.GetImgUtil;
import com.gs24.website.util.uploadImgFoodUtil;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("/Img")
@Log4j
public class ImgFoodController {

	@Autowired
	private ImgFoodService imgFoodService;

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private String uploadPath;

	@GetMapping("/Food")
	public ResponseEntity<byte[]> getFoodImage(Integer foodId){
		log.info("getFoodImage()");
		log.info(foodId);
		ImgFoodVO imgFoodVO = imgFoodService.getImgFoodById(foodId);

		String path = uploadPath + File.separator + imgFoodVO.getImgFoodPath();

		ResponseEntity<byte[]> entity = GetImgUtil.getImage(path);

		return entity;
	}

	@GetMapping("/Review")
	public ResponseEntity<byte[]> getReviewImage(Integer reviewId){
		log.info("getReviewImage()");

		String filePath = reviewService.getReviewByReviewId(reviewId).getReviewImgPath();

		String path = uploadPath + File.separator + filePath;

		ResponseEntity<byte[]> entity = GetImgUtil.getImage(path);

		return entity;
	}

	@GetMapping("/regist")
	public ResponseEntity<byte[]> foodRegister(String filePath){
		log.info("foodRegister()");
		log.info("File Path : " + filePath);
		String path = uploadPath + File.separator + filePath;

		ResponseEntity<byte[]> entity = GetImgUtil.getImage(path);

		return entity;

	}
	
	@PostMapping("/{reviewId}")
	public int dragImage(MultipartFile[] files,@PathVariable int reviewId){
		log.info("dragImage()");
		log.info(reviewId);
		ReviewVO reviewVO = reviewService.getReviewByReviewId(reviewId);
		for (MultipartFile file : files) {
			log.info(file.getOriginalFilename());
			uploadImgFoodUtil.updateFile(new ReviewVO(), uploadPath, file, "ReviewNO"+reviewId, uploadImgFoodUtil.subStrExtension(reviewVO.getReviewImgPath()), uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));
			reviewService.updateReview(reviewVO, file);
		}
		return reviewId;
	}
}