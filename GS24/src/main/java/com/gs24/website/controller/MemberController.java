package com.gs24.website.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gs24.website.domain.MemberVO;
import com.gs24.website.service.MemberService;
import com.gs24.website.service.RecaptchaService;

import lombok.extern.log4j.Log4j;

@Controller // @Component
@RequestMapping(value = "/member")
@Log4j
public class MemberController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private RecaptchaService recaptchaService;

	@GetMapping("/mypage")
	public void mypageGET(HttpSession session, Model model) {
		log.info("mypageGET()");
		String memberId = (String) session.getAttribute("memberId");
		if (memberId != null) {
			MemberVO memberVO = memberService.getMember(memberId);
			model.addAttribute("memberVO", memberVO);
		} else {
			log.info("mypageGET() - 세션이 없습니다");
		}
	}

	@GetMapping("/verify")
	public void verifyPasswordGET(HttpSession session, Model model) {
		log.info("verifyPasswordGET()");
		String memberId = (String) session.getAttribute("memberId");
		if (memberId != null) {
			MemberVO memberVO = memberService.getMember(memberId);
			memberVO.setPassword(null);
			model.addAttribute("memberVO", memberVO);
		} else {
			log.info("verifyPasswordGET() - 세션이 없습니다");
		}

	}

	@PostMapping("/check-pw")
	public String checkPwPOST(String memberId, String password, String recaptchaToken,
			RedirectAttributes redirectAttributes) {
		log.info("loginPOST()");
		boolean isRecaptchaValid = recaptchaService.verifyRecaptcha(recaptchaToken);
		if (!isRecaptchaValid) {
			log.info("reCAPTCHA 검증 실패");
			redirectAttributes.addFlashAttribute("message", "reCAPTCHA 검증에 실패했습니다. 다시 시도해주세요.");
			return "redirect:verify";
		}
		int result = memberService.login(memberId, password);

		if (result == 1) {
			return "redirect:change-pw";
		} else {
			log.info("검증 실패");
			redirectAttributes.addFlashAttribute("message", "잘못된 비밀번호입니다.");
			return "redirect:verify";
		}
	}

	@GetMapping("/change-pw")
	public void changePwGET(HttpSession session, Model model) {
		log.info("changePwGET()");
		String memberId = (String) session.getAttribute("memberId");
		if (memberId != null) {
			MemberVO memberVO = memberService.getMember(memberId);
			memberVO.setPassword(null);
			model.addAttribute("memberVO", memberVO);
		} else {
			log.info("changePwGET() - 세션이 없습니다");
		}

	}

	@PostMapping("/change-pw")
	public String changePwPOST(String memberId, String password, String recaptchaToken,
			RedirectAttributes redirectAttributes) {
		log.info("changePwPOST()");
		boolean isRecaptchaValid = recaptchaService.verifyRecaptcha(recaptchaToken);
		if (!isRecaptchaValid) {
			log.info("reCAPTCHA 검증 실패");
			redirectAttributes.addFlashAttribute("message", "reCAPTCHA 검증에 실패했습니다. 다시 시도해주세요.");
			return "redirect:change-pw";
		}
		int result = memberService.updateMemberPassword(memberId, password);
		if (result == 1) {
			redirectAttributes.addFlashAttribute("message", "비밀번호가 변경되었습니다.");
			return "redirect:mypage";
		}
		redirectAttributes.addFlashAttribute("message", "비밀번호 변경 실패입니다.");
		return "redirect:change-pw";
	}

	@GetMapping("/myhistory")
	public void myhistoryGET(HttpSession session, Model model) {
		log.info("myhistoryGET()");
		String memberId = (String) session.getAttribute("memberId");
		if (memberId != null) {
			model.addAttribute("memberId", memberId);
		} else {
			log.info("mypageGET() - 세션이 없습니다");
		}
	}

} // end BoardController
