package com.gs24.website.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gs24.website.service.CouponService;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping(value = "/coupon")
@Log4j
public class CouponRESTController {

	@Autowired
	private CouponService couponService;

	@PostMapping("/dup-check-couponName")
	public ResponseEntity<String> dupcheckCouponNamePOST(String couponName) {
		log.info("dupCheckCouponNamePOST()");
		int result = couponService.dupCheckCouponName(couponName);
		if (result == 1) { // 중복되면
			return ResponseEntity.ok("1");
		}
		return ResponseEntity.ok("0");
	}
}
