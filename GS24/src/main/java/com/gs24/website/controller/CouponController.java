package com.gs24.website.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gs24.website.domain.CouponVO;
import com.gs24.website.persistence.CouponMapper;

import lombok.extern.log4j.Log4j;

@RestController // @Component
@RequestMapping(value = "/member/coupon")
@Log4j
public class CouponController {

	@Autowired
	private CouponMapper couponMapper;

	@PostMapping("/register")
	public void registerPOST(@ModelAttribute CouponVO couponVO) {
		log.info("registerPOST()");
		log.info(couponVO);
		int result = couponMapper.insertCoupon(null);
		log.info(result + "개 행 등록 완료");
	}

} // end BoardController
