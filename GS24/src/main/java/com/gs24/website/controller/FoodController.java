package com.gs24.website.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gs24.website.domain.FoodVO;
import com.gs24.website.service.ConvenienceService;
import com.gs24.website.service.FoodApiService;
import com.gs24.website.service.FoodService;
import com.gs24.website.util.PageMaker;
import com.gs24.website.util.Pagination;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/food")
@Log4j
public class FoodController {

	@Autowired
	private FoodService foodService;

	@Autowired
	private FoodApiService foodApiService;

	@Autowired
	private ConvenienceService convenienceService;

	@GetMapping("/list")
	public void listGET(Authentication auth, Model model, Pagination pagination) {

		if (auth != null) {
			String username = auth.getName();
			if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_OWNER"))) {
				int convenienceId = convenienceService.getConvenienceIdByOwnerId(username);
				model.addAttribute("convenienceId", convenienceId);
			}
		}
		pagination.setPageSize(10);
		List<FoodVO> foodVO = foodService.getPagedAllFood(pagination);
		PageMaker pageMaker = new PageMaker();
		pageMaker.setPagination(pagination);
		pageMaker.setTotalCount(foodService.countTotalFood());

		model.addAttribute("food", foodVO);
		model.addAttribute("pageMaker", pageMaker);
	}

	@GetMapping("/delete")
	public String deleteGET(int foodId) {
		log.info("deleteGET()");

		foodService.deleteFoodById(foodId);

		return "redirect:/food/list";
	}

	@GetMapping("/register")
	public void registerGET() {
		log.info("registerGET()");
	}

	@PostMapping("/register")
	public String registerPOST(FoodVO foodVO) {
		foodService.createFood(foodVO);

		return "redirect:/food/list";
	}

	@GetMapping("/update")
	public void updateGET(Model model, int foodId) {
		log.info("updateGET()");

		FoodVO foodVO = foodService.getFoodById(foodId);

		model.addAttribute("FoodVO", foodVO);
	}

	@PostMapping("/update")
	public String updatePOST(FoodVO foodVO) {
		log.info("updatePOST()");

		log.info(foodVO);

		foodService.updateFoodById(foodVO);

		return "redirect:/food/list";
	}

	@PostMapping("/checkdelete")
	@ResponseBody
	public String checkDelete(@RequestBody int foodId) {
		log.info("checkDelete()");

		int result = foodService.checkFoodAmountStatus(foodId);

		if (result > 0) {
			return "failed";
		} else {
			return "success";
		}
	}

	@GetMapping("/auto-nutrition-input")
	public void searchPageGET() {
	}

	@GetMapping("/search")
	public ResponseEntity<String> searchFood(@RequestParam("foodName") String foodName) {
		try {
			String result = foodApiService.searchFood(foodName);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.status(500).body("{\"error\": \"서버 오류\"}");
		}
	}
}
