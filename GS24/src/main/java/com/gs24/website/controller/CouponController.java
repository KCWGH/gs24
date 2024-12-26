package com.gs24.website.controller;

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
import com.gs24.website.util.Pagination;

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
		int result = couponService.grantCoupon(couponVO);
		log.info(result + "개 쿠폰 제공 완료");
	}

	@GetMapping("/list")
	public String list(Model model, Pagination pagination, HttpSession session) {
		log.info("list()");
		String memberId = (String) session.getAttribute("memberId");
		if (memberId != null) {
			MemberVO memberVO = memberService.getMember(memberId);
			memberVO.setPassword(null);
			memberVO.setPhone(null);
			memberVO.setEmail(null);
			memberVO.setBirthday(null);
			model.addAttribute("memberVO", memberVO);
			return "coupon/list";
		}
		return "redirect:../member/login";
	}

	@GetMapping("/detail")
	public void detailGET(HttpSession session, Model model, int couponId) {
		log.info("detailGET()");
		String memberId = (String) session.getAttribute("memberId");
		if (memberId != null) {
			MemberVO memberVO = memberService.getMember(memberId);
			model.addAttribute("memberVO", memberVO);
		}
		CouponVO couponVO = couponService.getCouponDetail(couponId);
		model.addAttribute("couponVO", couponVO);
	}

} // end BoardController
