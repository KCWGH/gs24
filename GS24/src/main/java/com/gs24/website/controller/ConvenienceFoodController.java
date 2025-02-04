package com.gs24.website.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gs24.website.domain.ConvenienceFoodVO;
import com.gs24.website.domain.FoodVO;
import com.gs24.website.domain.ReviewVO;
import com.gs24.website.service.ConvenienceFoodServiceImple;
import com.gs24.website.util.PageMaker;
import com.gs24.website.util.Pagination;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/convenienceFood")
@Log4j
public class ConvenienceFoodController {
	
	@Autowired
	private ConvenienceFoodServiceImple convenienceFoodServiceImple;
	
	@GetMapping("/list")
	public void listGET(Model model, int convenienceId, Pagination pagination) {
		
		log.info("listGET()");
		
		List<ConvenienceFoodVO> list = convenienceFoodServiceImple.getConvenienceFoodByConvenienceId(convenienceId);
		
		log.info(list);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setPagination(pagination);
		
		model.addAttribute("FoodList", list);
		model.addAttribute("pageMaker", pageMaker);
	}
	
	@GetMapping("/detail")
	public void detailGET(Model model, int foodId) {
		log.info("detailGET()");
		
		ConvenienceFoodVO convenienceFoodVO = convenienceFoodServiceImple.getDetailConvenienceFoodByFoodId(foodId);
		List<ReviewVO> reviewList = convenienceFoodServiceImple.getReviewsByFoodId(foodId);
		
		log.info(convenienceFoodVO);
		log.info(reviewList);
		
		model.addAttribute("FoodVO", convenienceFoodVO);
		model.addAttribute("reviewList", reviewList);
	}
}
