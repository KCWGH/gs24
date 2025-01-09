package com.gs24.website.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gs24.website.domain.CouponVO;
import com.gs24.website.domain.MemberVO;
import com.gs24.website.service.CouponService;
import com.gs24.website.service.FoodService;
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

	@Autowired
	private FoodService foodService;

	@GetMapping("/grant")
	public void grantGET(Model model) {
		log.info("grantGET()");
		String[] foodType = foodService.getFoodTypeList();
		model.addAttribute("foodTypeList", foodType);
	}

	@GetMapping("/grant-fail")
	public void grantFailGET() {
		log.info("grantFailGET()");
	}

	@PostMapping("/grant")
	public String grantPOST(@ModelAttribute CouponVO couponVO, Model model, RedirectAttributes attributes) {
		log.info("grantPOST()");
		log.info(couponVO);
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
		if (memberService.dupCheckId(couponVO.getMemberId()) == 1) {
			int result = couponService.grantCoupon(couponVO);
			log.info(result + "개 쿠폰 제공 완료");
		} else {
			attributes.addFlashAttribute("message", "존재하지 않는 회원 아이디입니다. 쿠폰 제공에 실패했습니다.");
			return "redirect:/coupon/grant";
		}
		attributes.addFlashAttribute("message", "쿠폰 제공 완료 :)");
		return "redirect:/coupon/grant";
	}

	@GetMapping("/list")
	public String list(Model model, HttpSession session) {
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
		Date sysDate = new Date();
		model.addAttribute("couponVO", couponVO);
		model.addAttribute("sysDate", sysDate);
	}

} // end BoardController
