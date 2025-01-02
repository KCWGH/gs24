package com.gs24.website.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gs24.website.domain.ImgFoodVO;
import com.gs24.website.service.ImgFoodService;
import com.gs24.website.service.ReviewService;
import com.gs24.website.util.GetImgUtil;

import lombok.extern.log4j.Log4j;

@Controller
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
	public ResponseEntity<byte[]> getFoodImage(Integer foodId) throws IOException {
		log.info("getFoodImage()");
		log.info(foodId);
		ImgFoodVO imgFoodVO = imgFoodService.getImgFoodById(foodId);

		String path = uploadPath + File.separator + imgFoodVO.getImgFoodPath();

		ResponseEntity<byte[]> entity = GetImgUtil.getImage(path);

		return entity;
	}

	@GetMapping("/Review")
	public ResponseEntity<byte[]> getReviewImage(Integer reviewId) throws IOException {
		log.info("getReviewImage()");

		String filePath = reviewService.getReviewByReviewId(reviewId).getReviewImgPath();

		String path = uploadPath + File.separator + filePath;

		ResponseEntity<byte[]> entity = GetImgUtil.getImage(path);

		return entity;
	}

	@GetMapping("/regist")
	public ResponseEntity<byte[]> foodRegister(String filePath) throws IOException {
		log.info("foodRegister()");
		log.info("File Path : " + filePath);
		String path = uploadPath + File.separator + filePath;

		ResponseEntity<byte[]> entity = GetImgUtil.getImage(path);

		return entity;

	}
}