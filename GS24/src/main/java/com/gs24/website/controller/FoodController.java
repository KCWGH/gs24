package com.gs24.website.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gs24.website.domain.FoodVO;
import com.gs24.website.service.FavoritesService;
import com.gs24.website.service.FoodService;
import com.gs24.website.service.GiftCardService;
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
	private FavoritesService favoritesService;
	@Autowired
	private GiftCardService giftCardService;

	@GetMapping("/list")
	public void listGET(Authentication auth, Model model, Pagination pagination) {
		log.info("listGET()");
		log.info("pagination" + pagination);
		List<FoodVO> foodList = foodService.getPaginationFood(pagination);
		if (auth != null) {
			String username = auth.getName();
			if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MEMBER"))) {
				model.addAttribute("memberId", username);
				Map<Integer, Integer> isAddedMap = new HashMap<>();
				for (FoodVO foodVO : foodList) {
					// 찜 여부 확인: 이미 찜한 음식은 1, 찜하지 않은 음식은 0
					int isAdded = favoritesService.isAddedCheck(username, foodVO.getFoodId());
					isAddedMap.put(foodVO.getFoodId(), isAdded);
					model.addAttribute("isAddedMap", isAddedMap);
				}
				String birthdayMessage = giftCardService.birthdayGiftCardDupCheckAndGrant();
				if (birthdayMessage != null) {
					model.addAttribute("message", birthdayMessage);
				}
			}
		}

		PageMaker pageMaker = new PageMaker();
		pageMaker.setPagination(pagination);
		pageMaker.setTotalCount(foodService.getFoodTotalCount(pagination));

		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("FoodList", foodList);
	}

	@GetMapping("/register")
	public void registerGET() {
		log.info("registerGET()");
	}

	@PostMapping("/register")
	public String registerPOST(FoodVO foodVO) {
		log.info("registerPOST()");
		log.info(foodVO);
		foodService.createFood(foodVO);

		return "redirect:/food/list";
	}

	@GetMapping("/detail")
	public void detailGET(Model model, Integer foodId, Pagination pagination) {
		log.info("detailGET()");
		Object[] detailData = foodService.getDetailData(foodId, pagination);
		log.info(detailData[2]);

		model.addAttribute("FoodVO", detailData[0]);
		model.addAttribute("reviewList", detailData[1]);
		model.addAttribute("pageMaker", detailData[2]);
	}

	@GetMapping("/update")
	public void updateGET(Model model, Integer foodId) {
		log.info("updateGET()");
		FoodVO foodVO = foodService.getFoodById(foodId);
		log.info(foodVO);
		model.addAttribute("FoodVO", foodVO);
	}

	@PostMapping("/update")
	public String updatePOST(FoodVO foodVO) {
		log.info("updatePOST()");
		foodService.updateFood(foodVO);

		return "redirect:/food/list";
	}

	@GetMapping("/delete")
	public String delete(int foodId) {
		log.info("deleteFood()");
		foodService.deleteFood(foodId);

		return "redirect:/food/list";
	}
}
