package com.gs24.website.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gs24.website.domain.FoodListVO;
import com.gs24.website.service.ConvenienceService;
import com.gs24.website.service.FoodListService;
import com.gs24.website.util.PageMaker;
import com.gs24.website.util.Pagination;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/foodlist")
@Log4j
public class FoodListController {

	@Autowired
	private FoodListService foodListService;

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

		log.info("listGET()");
		pagination.setPageSize(10);
		List<FoodListVO> foodListVO = foodListService.getAllFood(pagination);
		PageMaker pageMaker = new PageMaker();
		pageMaker.setPagination(pagination);
		pageMaker.setTotalCount(foodListService.countTotalFood());
		log.info(foodListVO);

		model.addAttribute("foodList", foodListVO);
		model.addAttribute("pageMaker", pageMaker);
	}

	@GetMapping("/delete")
	public String deleteGET(int foodId) {
		log.info("deleteGET()");

		foodListService.deleteFoodById(foodId);

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

	@PostMapping("/checkdelete")
	@ResponseBody
	public String checkDelete(@RequestBody int foodId) {
		log.info("checkDelete()");

		int result = foodListService.checkFoodAmountStatus(foodId);

		if (result > 0) {
			return "failed";
		} else {
			return "success";
		}

	}
}
