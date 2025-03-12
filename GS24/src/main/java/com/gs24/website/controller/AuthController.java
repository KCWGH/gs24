package com.gs24.website.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gs24.website.service.ConvenienceService;

@Controller // @Component
@RequestMapping(value = "/auth")
public class AuthController {

	@Autowired
	private ConvenienceService convenienceService;

//	@Autowired
//	private KakaoLoginUtil kakaoLoginUtil;

	@GetMapping("/accessDenied")
	public void accessDenied(Authentication auth, Model model) {
		model.addAttribute("msg", "권한이 없습니다.");
	}

	@GetMapping("/login")
	public String loginGET(Authentication auth, String error, String expired, Model model) {
		if (error != null) {
			model.addAttribute("errorMsg", "아이디 또는 비밀번호가 잘못되었습니다.\\n아이디와 비밀번호를 정확히 입력해 주세요.");
		}
		if (expired != null) {
			model.addAttribute("expiredMsg", "다른 기기에서 로그인하여 로그아웃되었습니다.");
		}
		if (auth != null) {
			if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MEMBER"))) {
				return "redirect:/convenience/list";
			} else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_OWNER"))) {
				int checkConvenienceId = convenienceService.getConvenienceIdByOwnerId(auth.getName());
				model.addAttribute("checkConvenienceId", checkConvenienceId);
				return "redirect:/convenienceFood/list?convenienceId=" + checkConvenienceId;
			} else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
				return "redirect:/admin/console";
			}
		}
		return "/auth/login";

	}

	@GetMapping("/kakao")
	public void kakaoLoginGET(Model model, String code, String error, String error_description, String state)
			throws Exception {

//		Map<String,Object> accessToken = kakaoLoginUtil.sendCode(code);
//		
//		Map<String, Object> userInfo = kakaoLoginUtil.getUserInfo(accessToken.get("access_token").toString());

	}
}
