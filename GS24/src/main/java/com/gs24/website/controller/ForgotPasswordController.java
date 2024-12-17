package com.gs24.website.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gs24.website.service.ForgotPasswordService;

@RestController
@RequestMapping("/member/findpw")
public class ForgotPasswordController {

	private final ForgotPasswordService forgotPasswordService;

	@Autowired
	public ForgotPasswordController(ForgotPasswordService forgotPasswordService) {
		this.forgotPasswordService = forgotPasswordService;
	}

	// 인증번호 전송 요청 (Ajax 요청 처리)
	@PostMapping("/sendcode")
	public ResponseEntity<?> sendVerificationCode(@RequestParam String memberId, @RequestParam String email,
			HttpSession session) {
		boolean success = forgotPasswordService.sendVerificationCode(memberId, email, session);

		if (success) {
			return ResponseEntity.ok().body("{\"success\": true}");
		} else {
			return ResponseEntity.ok().body("{\"success\": false}");
		}
	}

	// 인증번호 검증 후 비밀번호 반환 (Ajax 요청 처리)
	@PostMapping("/verifycode")
	public ResponseEntity<?> verifyCodeAndGetPassword(@RequestParam String memberId,
			@RequestParam String verificationCode, HttpSession session) {
		String password = forgotPasswordService.verifyCodeAndGetPassword(memberId, verificationCode, session);

		if (password != null) {
			session.invalidate();
			return ResponseEntity.ok().body("{\"success\": true, \"password\": \"" + password + "\"}");
		} else {
			return ResponseEntity.ok().body("{\"success\": false}");
		}
	}
}
