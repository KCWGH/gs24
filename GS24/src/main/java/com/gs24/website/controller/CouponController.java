package com.gs24.website.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gs24.website.domain.CouponVO;
import com.gs24.website.domain.MemberVO;
import com.gs24.website.service.CouponService;
import com.gs24.website.service.MemberService;

import lombok.extern.log4j.Log4j;

@Controller // @Component
@RequestMapping(value = "/coupon")
@Log4j
public class CouponController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private CouponService couponService;

	@GetMapping("/grant")
	public void grantGET() {
		log.info("grantGET()");
	}

	@PostMapping("/grant")
	public void grantPOST(@ModelAttribute CouponVO couponVO) {
		log.info("grantPOST()");
		log.info(couponVO);
<<<<<<< HEAD
		int result = couponService.grantCoupon(couponVO);
		log.info(result + "ê°œ ì¿ í° ì œê³µ ì™„ë£Œ");
	}

	@GetMapping("/list")
	public void listGET(HttpSession session, Model model) {
		log.info("listGET()");
		String memberId = (String) session.getAttribute("memberId");
		if (memberId != null) {
			MemberVO memberVO = memberService.getMember(memberId);
			model.addAttribute("memberVO", memberVO);
		}
		List<CouponVO> couponList = couponService.getCouponList(memberId);
		model.addAttribute("couponList", couponList);
=======

		int result = couponService.addCoupon(couponVO);
		log.info(result + "Çà »ðÀÔ");

>>>>>>> 20091d038ecd342f64f0e1bcb2640eb581d47778
	}

} // end BoardController
