package com.gs24.website.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.JsonElement;
import com.gs24.website.service.ConvenienceService;
import com.gs24.website.util.KakaoLoginUtil;

import lombok.extern.log4j.Log4j;

@Controller // @Component
@RequestMapping(value = "/auth")
@Log4j
public class AuthController {

	@Autowired
	private ConvenienceService convenienceService;

	@GetMapping("/accessDenied")
	public void accessDenied(Authentication auth, Model model) {
		// Authentication : 현재 사용자의 인증 정보를 갖고 있음
		log.info("accessDenied()");
		model.addAttribute("msg", "권한이 없습니다.");
	}

	@GetMapping("/login")
	public String loginGET(Authentication auth, String error, String expired, Model model) {
		log.info("loginGET()");
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
				log.info(checkConvenienceId);
				model.addAttribute("checkConvenienceId", checkConvenienceId);
				return "redirect:/convenienceFood/list?convenienceId=" + checkConvenienceId;
			}
		}
		return "/auth/login";

	}

	@GetMapping("/kakao")
	public void kakaoLoginGET(Model model, String code, String error, String error_description, String state) {
		log.info("code : " + code);
		log.info("error code : " + error);
		log.info("error message : " + error_description);
		log.info("state : " + state);
		Map<String, String> headers = KakaoLoginUtil.createMapData("Content-Type",
				"application/x-www-form-urlencoded;charset=utf-8");

		String[] keys = { "code", "redirect_uri", "client_id", "grant_type" };
		Object[] values = { code, "http://localhost:8080/website/auth/kakao", "c5a22c59eb21bd81c32d6836ae978da9",
				"authorization_code" };
		Map<String, String> parameter = KakaoLoginUtil.createMapData(keys, values);

		log.info("토큰 가져오기");
		try {
			JsonElement element = KakaoLoginUtil.requestKakao("POST", "https://kauth.kakao.com/oauth/token", headers,
					parameter);
			String accessToken = KakaoLoginUtil.getElementProperty(element, "access_token");

			// headers.put("Authorization", "Bearer " + accessToken);
			// element = KakaoLoginUtil.requestKakao("GET",
			// "https://kapi.kakao.com/v2/user/me ", headers, null);

			model.addAttribute("accessToken", accessToken);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
