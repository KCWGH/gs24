package com.gs24.website.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gs24.website.domain.CustomUser;
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

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/mypage")
	public String mypageGET(@AuthenticationPrincipal CustomUser customUser, Model model) {
		log.info("mypageGET()");
		if (customUser != null) {
			String memberId = customUser.getUsername(); // CustomUser의 username

			// 회원 정보 가져오기
			MemberVO memberVO = memberService.getMember(memberId);
			model.addAttribute("memberVO", memberVO);
		} else {
			log.info("mypageGET() - 인증되지 않은 사용자");
		}
		return "member/mypage";
	}

	@GetMapping("/verify")
	public void verifyPasswordGET(@AuthenticationPrincipal CustomUser customUser, Model model) {
		log.info("verifyPasswordGET()");
		if (customUser != null) {
			String memberId = customUser.getUsername(); // CustomUser의 username

			MemberVO memberVO = memberService.getMember(memberId);

			model.addAttribute("memberVO", memberVO);
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
		MemberVO memberVO = memberService.getMember(memberId); // 회원 정보를 가져옴
		if (memberVO == null) {
			log.info("회원 정보 없음");
			redirectAttributes.addFlashAttribute("message", "존재하지 않는 회원입니다.");
			return "redirect:verify";
		}

		if (passwordEncoder.matches(password, memberVO.getPassword())) {
			return "redirect:change-pw";
		} else {
			log.info("비밀번호 검증 실패");
			redirectAttributes.addFlashAttribute("message", "잘못된 비밀번호입니다.");
			return "redirect:verify";
		}
	}

	@GetMapping("/change-pw")
	public void changePwGET(@AuthenticationPrincipal CustomUser customUser, Model model) {
		log.info("changePwGET()");
		String memberId = customUser.getUsername();
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
	public String myhistoryGET(@AuthenticationPrincipal CustomUser customUser, Model model) {
		log.info("myhistoryGET()");
		if (customUser != null) {
			String memberId = customUser.getUsername(); // CustomUser의 username
			model.addAttribute("memberId", memberId);
		} else {
			log.info("mypageGET() - 인증되지 않은 사용자");
		}
		return "member/myhistory";
	}

} // end BoardController
