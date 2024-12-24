package com.gs24.website.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gs24.website.domain.ImgFoodVO;
import com.gs24.website.service.ImgFoodService;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/ImgFood")
@Log4j
public class ImgFoodController{
	
	@Autowired
	private ImgFoodService imgFoodService;
	
	
	@GetMapping
	public ResponseEntity<byte[]> getImage(Integer foodId) throws Exception{
		log.info("getImage()");
		log.info(foodId);
		ImgFoodVO imgFoodVO = imgFoodService.getImgFoodById(foodId);
		log.info(imgFoodVO);
		
		File file = new File(imgFoodVO.getImgFoodPath());
		
		InputStream in = null;
		
		in = new FileInputStream(file);
		
		ResponseEntity<byte[]> entity = null;
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.add("Content-type", Files.probeContentType(file.toPath()));
		
		entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.OK);
		
		return entity;
	}
	
}
