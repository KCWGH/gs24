package com.gs24.website.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gs24.website.domain.EarlyBirdCouponVO;
import com.gs24.website.service.EarlyBirdCouponService;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping(value = "/earlybird-coupon")
@Log4j
public class EarlyBirdCouponRESTController {

	@Autowired
	private EarlyBirdCouponService earlyBirdCouponService;

	@GetMapping("/")
	private ResponseEntity<Map<String, Object>> getEarlyBirdCouponList() {
		log.info("getEarlyBirdCouponList()");
		Map<String, Object> response = new HashMap<>();
		List<EarlyBirdCouponVO> couponList = earlyBirdCouponService.getEarlyBirdCouponList();

		response.put("couponList", couponList);

		return ResponseEntity.ok(response);
	}
}