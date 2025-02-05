package com.gs24.website.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gs24.website.domain.AdminVO;
import com.gs24.website.service.AdminService;

import lombok.extern.log4j.Log4j;

@Log4j
@RequestMapping(value = "/admin")
@Controller
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@GetMapping("/register")
	public String registerGET(Principal principal, RedirectAttributes redirectAttributes) {
		if (principal != null) {
			return "redirect:/food/list";
		}
		return "/admin/register";
	}

	@PostMapping("/register")
	public String registerAdminPOST(@ModelAttribute AdminVO adminVO, RedirectAttributes redirectAttributes) {
		int result = adminService.registerAdmin(adminVO);
		if (result == 1) {
			log.info("registerMemberPOST()");
			redirectAttributes.addFlashAttribute("message", "회원등록 완료.\\n가입한 아이디와 비밀번호로 로그인하세요.");
			return "redirect:/auth/login";
		}
		redirectAttributes.addFlashAttribute("message", "회원등록을 실패했습니다.\\n유효하지 않은 회원정보(중복된 아이디, 패스워드, 전화번호)입니다.");
		return "redirect:/admin/register";
	}
}
