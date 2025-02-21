package com.gs24.website.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gs24.website.domain.OwnerVO;
import com.gs24.website.service.ConvenienceService;
import com.gs24.website.service.OwnerService;
import com.gs24.website.service.RecaptchaService;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping(value = "/owner")
@Log4j
public class OwnerController {

	@Autowired
	private OwnerService ownerService;

	@Autowired
	private ConvenienceService convenienceService;

	@Autowired
	private RecaptchaService recaptchaService;

	@GetMapping("/register")
	public String registerGET(Authentication auth, Model model, RedirectAttributes redirectAttributes) {
		if (auth != null) {
			if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MEMBER"))) {
				return "redirect:/convenience/list";
			} else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_OWNER"))) {
				int convenienceId = convenienceService.getConvenienceIdByOwnerId(auth.getName());
				model.addAttribute("convenienceId", convenienceId);
				return "redirect:/convenienceFood/list?convenienceId=" + convenienceId;
			}
		}
		return "/owner/register";
	}

	@PostMapping("/register")
	public String registerOwnerPOST(@ModelAttribute OwnerVO ownerVO, String recaptchaToken,
			RedirectAttributes redirectAttributes) {
		boolean isRecaptchaValid = recaptchaService.verifyRecaptcha(recaptchaToken);
		if (!isRecaptchaValid) {
			log.info("reCAPTCHA 검증 실패");
			redirectAttributes.addFlashAttribute("message", "reCAPTCHA 검증에 실패했습니다. 다시 시도해주세요.");
			return "redirect:/owner/register";
		}
		int result = ownerService.registerOwner(ownerVO);
		if (result == 1) {
			log.info("registerOwnerPOST()");
			redirectAttributes.addFlashAttribute("message", "회원등록 완료.\\n가입한 아이디와 비밀번호로 로그인하세요.");
			return "redirect:/auth/login";
		}
		redirectAttributes.addFlashAttribute("message", "회원등록을 실패했습니다.\\n유효하지 않은 회원정보(중복된 아이디, 패스워드, 전화번호)입니다.");
		return "redirect:/owner/register";
	}

	@PostMapping("/request-activation")
	public String activateOwnerPOST(Authentication auth, RedirectAttributes redirectAttributes,
			HttpServletRequest request, HttpServletResponse response) {
		int result = ownerService.requestActivationOwner(auth.getName());
		if (result == 1) {
			log.info("registerOwnerPOST()");
			new SecurityContextLogoutHandler().logout(request, response,
					SecurityContextHolder.getContext().getAuthentication());
			request.getSession().invalidate();
			redirectAttributes.addFlashAttribute("message", "관리자에게 계정 비활성화 해제를 요청했습니다.");
		}
		return "redirect:/auth/login";
	}
}
