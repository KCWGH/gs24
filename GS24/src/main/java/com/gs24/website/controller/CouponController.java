package com.gs24.website.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gs24.website.domain.CouponVO;
import com.gs24.website.service.CouponService;

import lombok.extern.log4j.Log4j;

@RestController // @Component
@RequestMapping(value = "/coupon")
@Log4j
public class CouponController {

	@Autowired
	private CouponService couponService;

	@PostMapping("/register")
	public void registerPOST(@ModelAttribute CouponVO couponVO) {
		log.info("registerPOST()");
		log.info(couponVO);

		int result = couponService.addCoupon(couponVO);
		log.info(result + "Çà »ðÀÔ");

	}

} // end BoardController
