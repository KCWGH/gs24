package com.gs24.website.controller;

<<<<<<< Updated upstream
import javax.servlet.http.HttpSession;
=======
import java.util.List;
>>>>>>> Stashed changes

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
<<<<<<< Updated upstream
import org.springframework.web.bind.annotation.RequestMapping;

import com.gs24.website.domain.MemberVO;
import com.gs24.website.persistence.MemberMapper;

import lombok.extern.log4j.Log4j;

@Controller // @Component
@RequestMapping(value = "/food")
=======
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.gs24.website.domain.FoodVO;
import com.gs24.website.domain.ImgFoodVO;
import com.gs24.website.service.FoodService;
import com.gs24.website.service.ImgFoodService;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/food")
>>>>>>> Stashed changes
@Log4j
public class FoodController {
	
	@Autowired
<<<<<<< Updated upstream
	private MemberMapper memberMapper;

	// register.jsp
	@GetMapping("/list")
	public void listGET(HttpSession session, Model model) {
		log.info("listGET()");
		String memberId = (String) session.getAttribute("memberId");
		if (memberId != null) {
			MemberVO memberVO = memberMapper.select(memberId);
			model.addAttribute("memberVO", memberVO);
		}
	}


} // end BoardController
=======
	private FoodService foodService;
	@Autowired
	private ImgFoodService imgFoodService;
	
	@GetMapping("/list")
	public void list(Model model) {
		log.info("list() 실행");
		List<FoodVO> FoodList = foodService.getAllFood();
		log.info(FoodList);
		model.addAttribute("FoodList", FoodList);
	}
	
	@GetMapping("/register")
	public void registerGET() {
		log.info("registerGET() 실행");
	}
	
	@PostMapping("/register")
	// 여기에 multipartfile file이 온다고 하면 
	public String registerPOST(FoodVO foodVO, MultipartFile file) {
		log.info("registerPOST() 실행");
		log.info(foodVO);
		log.info(file.getOriginalFilename());
		foodService.CreateFood(foodVO);
		return "redirect:/food/list";
	}
	
	@GetMapping("/detail")
	public void detailGET(Model model, Integer foodId) {
		log.info("detailGET() 실행");
		FoodVO foodVO = foodService.getFoodById(foodId);
		model.addAttribute("FoodVO", foodVO);
	}
	
	@GetMapping("/update")
	public void updateGET(Model model, Integer foodId)
	{
		log.info("updateGET() 실행");
		FoodVO foodVO = foodService.getFoodById(foodId);
		model.addAttribute("FoodVO", foodVO);
	}
	
	@PostMapping("/update")
	public String updatePOST(FoodVO foodVO) {
		log.info("updatePOST() 실행");
		int result = foodService.updateFood(foodVO);
		return "redirect:/food/list";
	}
}
>>>>>>> Stashed changes
