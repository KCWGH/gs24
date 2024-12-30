package com.gs24.website.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
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
@RequestMapping(value = "/member")
@Log4j
public class MemberController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private CouponService couponService;

	// register.jsp
	@GetMapping("/register")
	public String registerGET(HttpSession session) {
		log.info("registerGET()");
		if (session.getAttribute("memberId") != null) {
			log.info("�씠誘� 濡쒓렇�씤 �긽�깭");
			return "redirect:/food/list";
		}
		log.info("濡쒓렇�씤 �럹�씠吏�濡� �씠�룞");
		return "/member/register";
	}

	@PostMapping("/register")
	public String registerPOST(@ModelAttribute MemberVO memberVO) {
		log.info("registerPOST()");
		int result = memberService.register(memberVO);
		log.info(result + "媛� �뻾 �벑濡� �셿猷�");
		if (result == 1) {
			return "redirect:/member/register-success";
		}
		return "redirect:/member/register-fail";
	}

	@GetMapping("/register-success")
	public void registerSuccessGET() {
		log.info("registerSuccessGET()");
	}

	@GetMapping("/register-fail")
	public void registerFailGET() {
		log.info("registerFailGET()");
	}

	@GetMapping("/login")
	public String loginGET(HttpSession session) {
		if (session.getAttribute("memberId") != null) {
			log.info("loginGET() - �꽭�뀡�씠 �씠誘� 議댁옱�빀�땲�떎");
			return "redirect:/food/list";
		}
		log.info("loginGET()");
		return "/member/login";
	}

	@PostMapping("/login")
	public String loginPOST(String memberId, String password, HttpServletRequest request) {
		log.info("loginPOST()");

		int result = memberService.login(memberId, password);

		if (result == 1) {
			log.info("濡쒓렇�씤 �꽦怨�");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date currentDate = new Date();
			Date birthday = memberService.getMember(memberId).getBirthday();

			String formattedCurrentDate = sdf.format(currentDate);
			String formattedBirthday = sdf.format(birthday);

			int isExisting = 0; //= couponService.birthdayCouponDupCheck(memberId);

			if (formattedCurrentDate.equals(formattedBirthday) && isExisting != 1) {
				CouponVO couponVO = new CouponVO();
				couponVO.setCouponName("생일 축하 쿠폰");
				couponVO.setDiscountRate(20);
				couponVO.setMemberId(memberId);

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(currentDate);
				calendar.add(Calendar.MONTH, 1);
				Date oneMonthLater = calendar.getTime();
				couponVO.setCouponExpiredDate(oneMonthLater);

				couponService.grantCoupon(couponVO);
			}

			// �꽭�뀡 �꽕�젙
			HttpSession session = request.getSession();
			session.setAttribute("memberId", memberId);

			MemberVO memberVO = memberService.getMember(memberId);
			session.setAttribute("memberVO", memberVO);

			session.setMaxInactiveInterval(600);

			return "redirect:/food/list";
		} else {
			log.info("濡쒓렇�씤 �떎�뙣");
			return "redirect:/member/login-fail";
		}
	}

	@GetMapping("/login-fail")
	public void loginfailGET() {
		log.info("loginFailGET()");
	}

	@GetMapping("/find-id")
	public String findIdGET(HttpSession session) {
		if (session.getAttribute("memberId") != null) {
			log.info("findIdGET() - �꽭�뀡�씠 �씠誘� 議댁옱�빀�땲�떎");
			return "redirect:/food/list";
		}
		log.info("findIdGET()");
		return "/member/find-id";
	}

	@GetMapping("/find-pw")
	public String findPwGET(HttpSession session) {
		if (session.getAttribute("memberId") != null) {
			log.info("findPwGET - �꽭�뀡�씠 �씠誘� 議댁옱�빀�땲�떎");
			return "redirect:/food/list";
		}
		log.info("findPwGET");
		return "/member/find-pw";
	}

	@GetMapping("/mypage")
	public void mypageGET(HttpSession session, Model model) {
		log.info("mypageGET()");
		String memberId = (String) session.getAttribute("memberId");
		if (memberId != null) {
			MemberVO memberVO = memberService.getMember(memberId);
			model.addAttribute("memberId", memberId);
			model.addAttribute("memberVO", memberVO);
		} else {
			log.info("mypageGET() - �꽭�뀡�씠 �뾾�뒿�땲�떎");
		}
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		log.info("session.invalidate()");
		return "redirect:../food/list";
	}

} // end BoardController
