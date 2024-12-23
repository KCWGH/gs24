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
import com.gs24.website.util.CouponPagination;
import com.gs24.website.util.PageMaker;

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
	public void listGET(HttpSession session, CouponPagination pagination, Model model) {
		log.info("listGET()");
		String memberId = (String) session.getAttribute("memberId");
		if (memberId != null) {
			MemberVO memberVO = memberService.getMember(memberId);
			model.addAttribute("memberVO", memberVO);
		}
		List<CouponVO> couponList = couponService.getCouponList(memberId);
		model.addAttribute("couponList", couponList);
		PageMaker pageMaker = new PageMaker();
	    pageMaker.setPagination(pagination);
	    pageMaker.setTotalCount(couponService.getTotalCount());

	    // 모델에 값 추가
	    model.addAttribute("pageMaker", pageMaker);
	    model.addAttribute("couponList", couponList);
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
