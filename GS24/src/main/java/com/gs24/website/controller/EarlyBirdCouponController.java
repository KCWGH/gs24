package com.gs24.website.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gs24.website.domain.EarlyBirdCouponVO;
import com.gs24.website.service.FoodService;

import lombok.extern.log4j.Log4j;

@Controller // @Component
@RequestMapping(value = "/earlybird-coupon")
@Log4j
public class EarlyBirdCouponController {

	@Autowired
	private FoodService foodService;

	@GetMapping("/publish")
	public void publishGET(Model model) {
		log.info("publishGET()");
		String[] foodType = foodService.getFoodTypeList();
		model.addAttribute("foodTypeList", foodType);
	}

	@PostMapping("/publish")
	public void publishPOST(@ModelAttribute EarlyBirdCouponVO earlyBirdCouponVO, Model model, RedirectAttributes attributes) {
		log.info("publishPOST()");
		log.info(earlyBirdCouponVO);
		if (earlyBirdCouponVO.getEarlyBirdCouponName().equals("")) { // 이름을 따로 입력하지 않았으면
			String foodType = earlyBirdCouponVO.getFoodType();

			String value = "";
			switch (earlyBirdCouponVO.getDiscountType()) {
			case 'A':
				value = earlyBirdCouponVO.getDiscountValue() + "원";
				break;
			case 'P':
				value = earlyBirdCouponVO.getDiscountValue() + "%";
				break;
			}
			earlyBirdCouponVO.setEarlyBirdCouponName(foodType + " " + value + " 할인권");
		}
		//int result = EarlyBirdCouponService.publishCoupon(earlyBirdCouponVO);
		//log.info(result + "개 쿠폰 제공 완료");
	}
}
