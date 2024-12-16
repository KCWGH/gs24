package com.gs24.website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.gs24.website.util.uploadImgFoodUtil;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping(value = "/imgfood")
@Log4j
public class ImgFoodController {
	
	
	@GetMapping("/register")
	public void registerGET() {
		log.info("registerGET");
	}
	
	@PostMapping("/register")
	public void registerPOST(MultipartFile file) {
		log.info("registerPOST() ����");
		log.info(file.getOriginalFilename());
		String realName = uploadImgFoodUtil.RealName(file.getOriginalFilename());
		log.info(realName);
		
	}
}
