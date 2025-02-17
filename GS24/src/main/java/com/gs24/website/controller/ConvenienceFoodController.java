package com.gs24.website.controller;

import java.util.Date;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.gs24.website.domain.ConvenienceDetailFoodVO;
import com.gs24.website.domain.ConvenienceFoodVO;
import com.gs24.website.domain.OrderHistoryVO;
import com.gs24.website.domain.ReviewVO;
import com.gs24.website.service.ConvenienceFoodServiceImple;
import com.gs24.website.service.FavoritesService;
import com.gs24.website.service.GiftCardService;
import com.gs24.website.service.OrderHistoryService;
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

	@Autowired
	private OrderHistoryService orderHistoryService;

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
					int isAdded = favoritesService.isAddedCheck(username, convenienceFoodVO.getFoodId(), convenienceId);
					isAddedMap.put(convenienceFoodVO.getFoodId(), isAdded);
					model.addAttribute("isAddedMap", isAddedMap);
				}
				String birthdayMessage = giftCardService.birthdayGiftCardDupCheckAndGrant();
				if (birthdayMessage != null) {
					model.addAttribute("message", birthdayMessage);
				}
			}
		}

		model.addAttribute("convenienceId", convenienceId);
		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("FoodList", list);
	}

	@GetMapping("/detail")
	public void detailGET(Model model, int foodId, int convenienceId, Authentication auth) {
		log.info("detailGET()");

		ConvenienceDetailFoodVO convenienceFoodVO = convenienceFoodServiceImple.getDetailConvenienceFoodByFoodId(foodId,
				convenienceId);
		List<ReviewVO> reviewList = convenienceFoodServiceImple.getReviewsByFoodId(foodId);

		log.info(convenienceFoodVO);
		log.info(reviewList);

		if (auth != null) {
			model.addAttribute("memberId", auth.getName());
		}

		model.addAttribute("FoodVO", convenienceFoodVO);
		model.addAttribute("reviewList", reviewList);
	}

	@GetMapping("/register")
	public String registerGET(Authentication auth, int foodId, int foodAmount) {
		log.info("registerGET");

		log.info("foodId : " + foodId + "  foodAmount : " + foodAmount);
		String ownerId = auth.getName();

		convenienceFoodServiceImple.createConvenienceFood(foodId, foodAmount, ownerId);

		OrderHistoryVO orderHistory = new OrderHistoryVO();
		orderHistory.setFoodId(foodId); // 음식 ID 설정
		orderHistory.setOrderAmount(foodAmount); // 주문 수량 설정
		orderHistory.setOwnerId(ownerId); // 주문자(소유자) ID 설정
		orderHistory.setOrderDateCreated(new Date()); // 현재 날짜와 시간으로 주문 생성

		orderHistoryService.insertOrder(orderHistory);

		return "redirect:../foodlist/list";
	}

	@GetMapping("/getOrdersAllHistory")
	@ResponseBody
	public List<OrderHistoryVO> getOrderAllHistory() {
		log.info("getOrderAllHistory");
		return orderHistoryService.getAllOrders();

	}
}
