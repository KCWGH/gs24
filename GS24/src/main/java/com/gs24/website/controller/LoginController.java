package com.gs24.website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

	private static final String ALLOWED_BASE_URL = "/"; // 허용할 기본 URL

	@RequestMapping("/auth/login")
	public String loginPage(@RequestParam(value = "returnUrl", required = false) String returnUrl) {
		// returnUrl이 존재하고, 허용된 URL로 시작하는지 확인
		if (returnUrl != null && returnUrl.startsWith(ALLOWED_BASE_URL)) {
			return "redirect:" + returnUrl; // 안전한 URL로 리다이렉트
		}

		// returnUrl이 없거나 허용되지 않은 URL일 경우 기본 로그인 페이지로 이동
		return "login"; // login.jsp 또는 login.html로 이동
	}
}
