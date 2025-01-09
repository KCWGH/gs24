package com.gs24.website.controller;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gs24.website.domain.EarlyBirdCouponVO;
import com.gs24.website.service.EarlyBirdCouponQueueService;
import com.gs24.website.service.EarlyBirdCouponService;
import com.gs24.website.service.FoodService;

import lombok.extern.log4j.Log4j;

@Controller // @Component
@RequestMapping(value = "/earlybird-coupon")
@Log4j
public class EarlyBirdCouponController {

	@Autowired
	private FoodService foodService;

	@Autowired
	private EarlyBirdCouponService earlyBirdCouponService;

	@Autowired
	private EarlyBirdCouponQueueService earlyBirdCouponQueueService;

	@GetMapping("/publish")
	public void publishGET(Model model) {
		log.info("publishGET()");
		String[] foodType = foodService.getFoodTypeList();
		model.addAttribute("foodTypeList", foodType);
	}

	@PostMapping("/publish")
	public String publishPOST(@ModelAttribute EarlyBirdCouponVO earlyBirdCouponVO, Model model,
			RedirectAttributes redirectAttributes) {

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
		if (earlyBirdCouponVO.getEarlyBirdCouponExpiredDate() == null) {
			Date currentDate = new Date();

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(currentDate);
			calendar.add(Calendar.YEAR, 100);

			Date futureDate = calendar.getTime();
			earlyBirdCouponVO.setEarlyBirdCouponExpiredDate(futureDate);
		}
		int result = earlyBirdCouponService.publishCoupon(earlyBirdCouponVO);
		if (result == 1) {
			log.info("publishPOST()");
			log.info("쿠폰아이디 : " + earlyBirdCouponVO.getEarlyBirdCouponId());
			earlyBirdCouponQueueService.setAmount(earlyBirdCouponVO.getEarlyBirdCouponId(),
					earlyBirdCouponVO.getEarlyBirdCouponAmount());
			redirectAttributes.addFlashAttribute("message", "선착순 쿠폰 발행에 성공했습니다 :)");
		} else {
			redirectAttributes.addFlashAttribute("message", "선착순 쿠폰 발행에 실패했습니다.");
		}
		return "redirect:/earlybird-coupon/publish";
	}

}
