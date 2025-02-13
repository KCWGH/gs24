package com.gs24.website.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogoutPageController {

	@RequestMapping("/current")
	public String currentPage(HttpServletRequest request) {
		String referer = request.getHeader("Referer");
		return "redirect:" + referer; // 이전 페이지로 리다이렉트
	}
}
