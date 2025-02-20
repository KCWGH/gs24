package com.gs24.website.controller;

import java.util.Date;
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
import com.gs24.website.domain.OrderHistoryVO;
import com.gs24.website.domain.ReviewVO;
import com.gs24.website.service.ConvenienceFoodService;
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
	private ConvenienceFoodService convenienceFoodService;

	@Autowired
	private FavoritesService favoritesService;

	@Autowired
	private GiftCardService giftCardService;

	@Autowired
	private OrderHistoryService orderHistoryService;

	@GetMapping("/list")
	public void listGET(Authentication auth, Model model, int convenienceId) {
		log.info("listGET()");

		List<ConvenienceFoodVO> list = convenienceFoodService.getConvenienceFoodByConvenienceId(convenienceId);

		log.info(list);

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

	    String ownerId = auth.getName();

	    // 발주 내역 중 해당 foodId가 이미 존재하는지 확인 (기존 발주 내역을 중복으로 추가하지 않기 위해)
	    List<OrderHistoryVO> orderHistoryList = orderHistoryService.getOrdersByOwnerId(ownerId);
	    boolean isApproved = false;

	    // 해당 foodId의 음식이 승인 상태인지 확인
	    for (OrderHistoryVO order : orderHistoryList) {
	        if (order.getFoodId() == foodId && order.getApprovalStatus() == 1) { // 승인된 상태인지 확인
	            isApproved = true;
	            break;
	        }
	    }

	    // 승인된 상품만 발주 처리
	    if (isApproved) {
	        // 주문 내역 생성
	        OrderHistoryVO orderHistory = new OrderHistoryVO();
	        orderHistory.setFoodId(foodId); // 음식 ID 설정
	        orderHistory.setOrderAmount(foodAmount); // 주문 수량 설정
	        orderHistory.setOwnerId(ownerId); // 주문자(소유자) ID 설정
	        orderHistory.setOrderDateCreated(new Date()); // 현재 날짜와 시간으로 주문 생성
	        orderHistory.setApprovalStatus(0); // 승인 대기 상태로 설정 (발주 대기 상태)

	        // 주문 내역을 orders/list에 추가
	        orderHistoryService.insertOrder(orderHistory);

	        // 발주가 성공적으로 처리되었음을 알리는 메시지 또는 리다이렉트
	        return new ResponseEntity<>(1, HttpStatus.OK);
	    } else {
	        // 승인되지 않은 상품일 경우 처리 (예: 오류 메시지 등)
	        return new ResponseEntity<Integer>(0, HttpStatus.BAD_REQUEST); 
	    }
	}
	
	@GetMapping("/updateShowStatus")
	public String updateShowStatus(int foodId, int convenienceId) {
		log.info("updateShowStatus()");
		convenienceFoodService.updateShowStatus(foodId, convenienceId);
		return "redirect:list?convenienceId=" + convenienceId;
	}


	@GetMapping("/getOrdersAllHistory")
	@ResponseBody
	public List<OrderHistoryVO> getOrderAllHistory() {
		log.info("getOrderAllHistory");
		return orderHistoryService.getAllOrders();
		
	}
}