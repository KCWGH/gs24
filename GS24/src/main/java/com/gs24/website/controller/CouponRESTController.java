package com.gs24.website.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gs24.website.domain.CouponVO;
import com.gs24.website.domain.MemberVO;
import com.gs24.website.service.CouponService;
import com.gs24.website.service.MemberService;
import com.gs24.website.util.PageMaker;
import com.gs24.website.util.Pagination;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping(value = "/coupon")
@Log4j
public class CouponRESTController {

	@Autowired
	private MemberService memberService;

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

	@GetMapping("/list-available")
	public ResponseEntity<Map<String, Object>> availableList(Pagination pagination, HttpSession session) {
		return getCouponList("available", pagination, session);
	}

	@GetMapping("/list-expired")
	public ResponseEntity<Map<String, Object>> expiredList(Pagination pagination, HttpSession session) {
		return getCouponList("expired", pagination, session);
	}

	@GetMapping("/list-used")
	public ResponseEntity<Map<String, Object>> usedList(Pagination pagination, HttpSession session) {
		return getCouponList("used", pagination, session);
	}

	@GetMapping("/list-all")
	public ResponseEntity<Map<String, Object>> allList(Pagination pagination, HttpSession session) {
		return getCouponList("all", pagination, session);
	}

	private ResponseEntity<Map<String, Object>> getCouponList(String type, Pagination pagination, HttpSession session) {
		log.info("getCouponList()");
		String memberId = (String) session.getAttribute("memberId");
		Map<String, Object> response = new HashMap<>();

		if (memberId != null) {
			MemberVO memberVO = memberService.getMember(memberId);
			memberVO.setPassword(null);
			memberVO.setPhone(null);
			memberVO.setEmail(null);
			memberVO.setBirthday(null);
			pagination.setMemberVO(memberVO);
			pagination.setPageSize(4);
			PageMaker pageMaker = new PageMaker();
			pageMaker.setPagination(pagination);
			List<CouponVO> couponList = null;

			switch (type) {
			case "available":
				couponList = couponService.getPagedAvailableCoupons(memberId, pagination);
				pageMaker.setTotalCount(couponService.getAvailableCount(memberId));
				break;
			case "expired":
				couponList = couponService.getPagedExpiredCoupons(memberId, pagination);
				pageMaker.setTotalCount(couponService.getExpiredCount(memberId));
				break;
			case "used":
				couponList = couponService.getPagedUsedCoupons(memberId, pagination);
				pageMaker.setTotalCount(couponService.getUsedCount(memberId));
				break;
			case "all":
				couponList = couponService.getPagedAllCoupons(memberId, pagination);
				pageMaker.setTotalCount(couponService.getAllCount(memberId));
				break;
			}
			response.put("pageMaker", pageMaker);
			response.put("couponList", couponList);
		} else {
			response.put("message", "Member not found");
		}

		return ResponseEntity.ok(response);
	}

}
