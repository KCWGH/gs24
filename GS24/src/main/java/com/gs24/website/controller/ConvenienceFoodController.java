package com.gs24.website.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gs24.website.domain.ConvenienceDetailFoodVO;
import com.gs24.website.domain.ConvenienceFoodVO;
import com.gs24.website.domain.OrderVO;
import com.gs24.website.domain.ReviewVO;
import com.gs24.website.service.ConvenienceFoodService;
import com.gs24.website.service.FavoritesService;
import com.gs24.website.service.GiftCardService;
import com.gs24.website.service.OrderService;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/convenienceFood")
@Log4j
public class ConvenienceFoodController {

	@Autowired
	private ConvenienceFoodService convenienceFoodService;

	@Autowired
	private FavoritesService favoritesService;

	@Autowired
	private GiftCardService giftCardService;

	@Autowired
	private OrderService orderService;

	@GetMapping("/list")
	public void listGET(Authentication auth, Model model, int convenienceId) {
		log.info("listGET()");

		List<ConvenienceFoodVO> list = convenienceFoodService.getConvenienceFoodByConvenienceId(convenienceId);

		if (auth != null) {
			String username = auth.getName();
			if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MEMBER"))) {
				model.addAttribute("memberId", username);
				Map<Integer, Integer> isAddedMap = new HashMap<>();
				for (ConvenienceFoodVO convenienceFoodVO : list) {
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
		model.addAttribute("FoodList", list);
	}

	@GetMapping("/detail")
	public void detailGET(Model model, int foodId, int convenienceId, Authentication auth) {
		log.info("detailGET()");

		ConvenienceDetailFoodVO convenienceFoodVO = convenienceFoodService.getDetailConvenienceFoodByFoodId(foodId,
				convenienceId);
		List<ReviewVO> reviewList = convenienceFoodService.getReviewsByFoodId(foodId);

		log.info(convenienceFoodVO);
		log.info(reviewList);

		if (auth != null) {
			if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MEMBER"))) {
				model.addAttribute("memberId", auth.getName());
			} else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_OWNER"))) {
				model.addAttribute("convenienceId", convenienceId);
			}
		}

		model.addAttribute("FoodVO", convenienceFoodVO);
		model.addAttribute("reviewList", reviewList);
	}

	@GetMapping("/register")
	@ResponseBody
	public ResponseEntity<Integer> registerGET(Authentication auth, int foodId, int foodAmount) {
		log.info("registerGET");

		log.info("foodId : " + foodId + ", foodAmount : " + foodAmount);
		String ownerId = auth.getName();

		OrderVO order = new OrderVO();
		order.setFoodId(foodId);
		order.setOrderAmount(foodAmount);
		order.setOwnerId(ownerId);
		order.setApprovalStatus(0); // 대기 상태

		orderService.insertOrder(order);

		return new ResponseEntity<>(1, HttpStatus.OK);
	}

	@GetMapping("/updateShowStatus")
	public String updateShowStatus(int foodId, int convenienceId) {
		log.info("updateShowStatus()");
		convenienceFoodService.updateShowStatus(foodId, convenienceId);
		return "redirect:list?convenienceId=" + convenienceId;
	}
}