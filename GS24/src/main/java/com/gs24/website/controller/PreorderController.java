package com.gs24.website.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gs24.website.domain.FoodVO;
import com.gs24.website.service.FoodService;
import com.gs24.website.service.ImgFoodService;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/preorder")
@Log4j
public class PreorderController {
	
	@Autowired
	private FoodService foodService;
	
	@Autowired
	private ImgFoodService imgFoodService;
	
	@GetMapping("/register")
	public void registerGET(Model model, int foodId) {
		log.info("registerGET()");
		
		FoodVO foodVO = foodService.getFoodById(foodId);
		String ImgPath = imgFoodService.getImgFoodById(foodId).getImgFoodPath();
		
		model.addAttribute("ImgPath", ImgPath);
		model.addAttribute("foodVO", foodVO);
	}
}
