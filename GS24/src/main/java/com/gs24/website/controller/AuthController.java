package com.gs24.website.controller;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j;

@Controller // @Component
@RequestMapping(value = "/auth")
@Log4j
public class AuthController {

	@GetMapping("/accessDenied")
	public void accessDenied(Authentication auth, Model model) {
		// Authentication : 현재 사용자의 인증 정보를 갖고 있음
		log.info("accessDenied()");
		model.addAttribute("msg", "권한이 없습니다.");
	}

	@GetMapping("/login")
	public String loginGET(Principal principal, String error, String logout, Model model) {
		log.info("loginGET()");
		if (error != null) {
			model.addAttribute("errorMsg", "아이디 또는 비밀번호가 잘못되었습니다.\\n아이디와 비밀번호를 정확히 입력해 주세요.");
		}
		if (principal != null) {
			return "redirect:/convenience/list";
		}
		return "/auth/login";

	}

}
