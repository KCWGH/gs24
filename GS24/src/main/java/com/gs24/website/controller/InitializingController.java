package com.gs24.website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class InitializingController {

	@RequestMapping("/")
	public String redirectToMain() {
		return "redirect:/convenience/list";
	}
}
