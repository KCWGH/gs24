package com.gs24.website.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.gs24.website.domain.FoodVO;
import com.gs24.website.domain.MemberVO;
import com.gs24.website.service.FoodService;
import com.gs24.website.service.MemberService;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/food")
@Log4j
public class FoodController {

	@Autowired
	private MemberService memberService;
	@Autowired
	private FoodService foodService;

	// register.jsp
	@GetMapping("/list")
	public void listGET(HttpSession session, Model model) {
		//TODO 페이징처리 해야해 꼭!!!!!
		log.info("listGET()");
		String memberId = (String) session.getAttribute("memberId");
		if (memberId != null) {
			MemberVO memberVO = memberService.getMember(memberId);
			model.addAttribute("memberVO", memberVO);
		}
		List<FoodVO> FoodList = foodService.getAllFood();
		model.addAttribute("FoodList", FoodList);
	}

	@GetMapping("/register")
	public void registerGET() {
		log.info("registerGET()");
	}

	@PostMapping("/register")
	public String registerPOST(FoodVO foodVO, MultipartFile file) {
		log.info("registerPOST()");
		log.info(foodVO);
		log.info(file.getOriginalFilename());
		foodService.createFood(foodVO, file);

		return "redirect:/food/list";
	}

	@GetMapping("/detail")
	public void detailGET(Model model, Integer foodId) {
		log.info("detailGET()");
		FoodVO foodVO = foodService.getFoodById(foodId);
		model.addAttribute("FoodVO", foodVO);

	}

	@GetMapping("/update")
	public void updateGET(Model model, Integer foodId) {
		log.info("updateGET()");
		FoodVO foodVO = foodService.getFoodById(foodId);
		model.addAttribute("FoodVO", foodVO);
	}

	@PostMapping("/update")
	public String updatePOST(FoodVO foodVO, MultipartFile file) {
		log.info("updatePOST()");
		int result = foodService.updateFood(foodVO, file);


		return "redirect:/food/list";
	}

	@GetMapping("/delete")
	public String delete(int foodId) {
		log.info("deleteFood()");
		foodService.deleteFood(foodId);

		return "redirect:/food/list";
	}
}