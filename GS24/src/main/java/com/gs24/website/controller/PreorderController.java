package com.gs24.website.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gs24.website.domain.FoodVO;
import com.gs24.website.domain.PreorderVO;
import com.gs24.website.service.FoodService;
import com.gs24.website.service.ImgFoodService;
import com.gs24.website.service.PreorderService;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/preorder")
@Log4j
public class PreorderController {
	
	@Autowired
	private FoodService foodService;
	
	@Autowired
	private ImgFoodService imgFoodService;
	
	@Autowired
	private PreorderService preorderService;
	
	@GetMapping("/register")
	public void registerGET(Model model, int foodId, HttpSession session) {
		log.info("registerGET()");
		
		FoodVO foodVO = foodService.getFoodById(foodId);
		String ImgPath = imgFoodService.getImgFoodById(foodId).getImgFoodPath();
		String memberId = (String)session.getAttribute("memberId");
		log.info(memberId);
		model.addAttribute("ImgPath", ImgPath);
		model.addAttribute("foodVO", foodVO);
		model.addAttribute("memberId", memberId);
	}
	
	@PostMapping("/register")
	public String registerPOST(PreorderVO preorderVO) {
		log.info("registerPOST()");
		log.info(preorderVO);
		int result = preorderService.createPreorder(preorderVO);
		log.info(result + "row insert and FOOD DB update");
		return "/food/list";
	}
	
	@GetMapping("/list")
	public void listGET() {
		log.info("listGET");
	}
	
	//멤버ID가 있어야 가능한데 조금만 생각하자
	@PostMapping("/delete")
	public void deletePOST(String[] IDs) {
		log.info("deletePOST");
		log.info(IDs.length);
	}
}
