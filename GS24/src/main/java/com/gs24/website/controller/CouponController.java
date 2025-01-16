package com.gs24.website.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gs24.website.domain.CouponVO;
import com.gs24.website.service.CouponService;
import com.gs24.website.service.FoodService;

import lombok.extern.log4j.Log4j;

@Controller // @Component
@RequestMapping(value = "/coupon")
@Log4j
public class CouponController {

	@Autowired
	private FoodService foodService;

	@Autowired
	private CouponService couponService;

	@GetMapping("/publish")
	public void publishGET(Model model) {
		log.info("publishGET()");
		List<String> foodType = foodService.getFoodTypeList();
		model.addAttribute("foodTypeList", foodType);
	}

	@PostMapping("/publish")
	public String publishPOST(@ModelAttribute CouponVO couponVO, Model model, RedirectAttributes redirectAttributes) {

		if (couponVO.getCouponName().equals("")) { // 이름을 따로 입력하지 않았으면
			String foodType = couponVO.getFoodType();

			String value = "";
			switch (couponVO.getDiscountType()) {
			case 'A':
				value = couponVO.getDiscountValue() + "원";
				break;
			case 'P':
				value = couponVO.getDiscountValue() + "%";
				break;
			}
			couponVO.setCouponName(foodType + " " + value + " 할인권");
		}
		if (couponVO.getCouponExpiredDate() == null) {
			Date currentDate = new Date();

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(currentDate);
			calendar.add(Calendar.YEAR, 100);

			Date futureDate = calendar.getTime();
			couponVO.setCouponExpiredDate(futureDate);
		}

		switch (couponVO.getDiscountType()) {
		case 'A':
			if (couponVO.getDiscountValue() >= 1000) {
				int result = couponService.publishCoupon(couponVO);
				if (result == 1) {
					log.info("publishPOST()");
					redirectAttributes.addFlashAttribute("message", "쿠폰 발행에 성공했습니다 :)");
				} else {
					redirectAttributes.addFlashAttribute("message", "쿠폰 발행에 실패했습니다. 다시 시도해 주세요.");
				}
			} else {
				redirectAttributes.addFlashAttribute("message", "쿠폰 발행에 실패했습니다.\\n정액권인 경우 최솟값은 1000원입니다.");
			}
			break;
		case 'P':
			if (couponVO.getDiscountValue() >= 5) {
				int result = couponService.publishCoupon(couponVO);
				if (result == 1) {
					log.info("publishPOST()");
					redirectAttributes.addFlashAttribute("message", "쿠폰 발행에 성공했습니다 :)");
				} else {
					redirectAttributes.addFlashAttribute("message", "쿠폰 발행에 실패했습니다. 다시 시도해 주세요.");
				}
			} else {
				redirectAttributes.addFlashAttribute("message", "쿠폰 발행에 실패했습니다.\\n비율권인 경우 최솟값은 5%입니다.");
			}
			break;
		}

		return "redirect:/coupon/publish";
	}

}
