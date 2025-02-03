package com.gs24.website.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gs24.website.domain.FoodListVO;
import com.gs24.website.domain.ImgVO;
import com.gs24.website.service.FoodListService;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/foodlist")
@Log4j
public class FoodListController {
	
	@Autowired
	private FoodListService foodListService;
	
	@GetMapping("/list")
	public void listGET(Model model) {
		log.info("listGET()");
		List<FoodListVO> foodListVO = foodListService.getAllFood();
		
		log.info(foodListVO);
		
		model.addAttribute("foodList", foodListVO);
	}
	
	@GetMapping("/delete")
	public String deleteGET(int foodId) {
		log.info("deleteGET()");
		
		int result = foodListService.deleteFoodById(foodId);
		
		return "redirect:/foodlist/list";
	}
	
	@GetMapping("/register")
	public void registerGET() {
		log.info("registerGET()");
	}
	
	@PostMapping("/register")
	public String registerPOST(FoodListVO foodListVO) {
		log.info("registerPOST()");
		
		log.info(foodListVO);
		foodListService.createFood(foodListVO);
		
		return "redirect:/foodlist/list";
	}
	
	@GetMapping("/update")
	public void updateGET(Model model, int foodId) {
		log.info("updateGET()");
		
		FoodListVO foodListVO = foodListService.getFoodById(foodId);
		
		model.addAttribute("FoodVO", foodListVO);
	}
	
	@PostMapping("/update")
	public String updatePOST(FoodListVO foodListVO) {
		log.info("updatePOST()");
		
		log.info(foodListVO);
		
		foodListService.updateFoodById(foodListVO);
		
		return "redirect:/foodlist/list";
	}
}
