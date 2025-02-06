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
import org.springframework.web.bind.annotation.RequestMapping;

import com.gs24.website.domain.ConvenienceDetailFoodVO;
import com.gs24.website.domain.ConvenienceFoodVO;
import com.gs24.website.domain.ReviewVO;
import com.gs24.website.service.ConvenienceFoodServiceImple;
import com.gs24.website.service.FavoritesService;
import com.gs24.website.service.GiftCardService;
import com.gs24.website.util.PageMaker;
import com.gs24.website.util.Pagination;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/convenienceFood")
@Log4j
public class ConvenienceFoodController {

	@Autowired
	private ConvenienceFoodServiceImple convenienceFoodServiceImple;

	@Autowired
	private FavoritesService favoritesService;

	@Autowired
	private GiftCardService giftCardService;

	@GetMapping("/list")
	public void listGET(Authentication auth, Model model, int convenienceId, Pagination pagination) {
		log.info("listGET()");

		List<ConvenienceFoodVO> list = convenienceFoodServiceImple.getConvenienceFoodByConvenienceId(convenienceId);

		log.info(list);

		PageMaker pageMaker = new PageMaker();
		pageMaker.setPagination(pagination);

		if (auth != null) {
			String username = auth.getName();
			if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MEMBER"))) {
				model.addAttribute("memberId", username);
				Map<Integer, Integer> isAddedMap = new HashMap<>();
				for (ConvenienceFoodVO convenienceFoodVO : list) {
					// 찜 여부 확인: 이미 찜한 음식은 1, 찜하지 않은 음식은 0
					int isAdded = favoritesService.isAddedCheck(username, convenienceFoodVO.getFoodId());
					isAddedMap.put(convenienceFoodVO.getFoodId(), isAdded);
					model.addAttribute("isAddedMap", isAddedMap);
					System.out.println(isAdded);
				}
				String birthdayMessage = giftCardService.birthdayGiftCardDupCheckAndGrant();
				if (birthdayMessage != null) {
					model.addAttribute("message", birthdayMessage);
				}
			}
		}

		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("FoodList", list);
	}

	@GetMapping("/detail")
	public void detailGET(Model model, int foodId) {
		log.info("detailGET()");
		
		ConvenienceDetailFoodVO convenienceFoodVO = convenienceFoodServiceImple.getDetailConvenienceFoodByFoodId(foodId);
		List<ReviewVO> reviewList = convenienceFoodServiceImple.getReviewsByFoodId(foodId);
		
		log.info(convenienceFoodVO);
		log.info(reviewList);
		
		model.addAttribute("FoodVO", convenienceFoodVO);
		model.addAttribute("reviewList", reviewList);
	}
	
	@GetMapping("/register")
	public String registerGET(Authentication auth, int foodId, int foodAmount) {
		log.info("registerGET");
		
		log.info("foodId : " + foodId + "  foodAmount : " + foodAmount);
		String ownerId = auth.getName();
		
		convenienceFoodServiceImple.createConvenienceFood(foodId, foodAmount, ownerId);
		
		return "redirect:../foodlist/list";
	}
}
