package com.gs24.website.controller;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gs24.website.service.EmailService;
import com.gs24.website.service.MemberService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping(value = "/auth")
@Log4j
public class AuthRESTController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private EmailService emailService;

	@PostMapping("/dup-check-id")
	public ResponseEntity<String> dupcheckidPOST(String memberId) {
		log.info("dupCheckIdPOST()");
		int result = memberService.dupCheckId(memberId); // 서비스 레이어 사용
		if (result == 1) { // 중복되면
			return ResponseEntity.ok("1");
		}
		return ResponseEntity.ok("0");
	}

	@PostMapping("/dup-check-email")
	public ResponseEntity<String> dupcheckemailPOST(String email) {
		log.info("dupCheckEmailPOST()");
		int result = memberService.dupCheckEmail(email); // 서비스 레이어 사용
		if (result == 1) { // 중복되면
			return ResponseEntity.ok("1");
		}
		return ResponseEntity.ok("0");
	}

	@PostMapping("/dup-check-phone")
	public ResponseEntity<String> dupcheckphonePOST(String phone) {
		log.info("dupCheckPhonePOST()");
		int result = memberService.dupCheckPhone(phone);
		if (result == 1) { // 중복되면
			return ResponseEntity.ok("1");
		}
		return ResponseEntity.ok("0");
	}

	@PostMapping("/email-exist")
	public ResponseEntity<String> EmailExistPOST(String email) {
		log.info("EmailExistPOST)");
		if (email.equals("")) {
			return ResponseEntity.ok("");
		}
		int dupCheck = memberService.dupCheckEmail(email);
		if (dupCheck == 0) { // 해당 이메일이 db에 없으면
			return ResponseEntity.ok("do not exist");
		}
		return ResponseEntity.ok("exist");
	}

	@PostMapping("/account-exist")
	public ResponseEntity<String> isExistByIdAndEmailPOST(String memberId, String email) {
		log.info("isExistByIdAndEmailPOST()");
		if (email.equals("")) {
			return ResponseEntity.ok("");
		}
		int dupCheck = memberService.dupCheckIdAndEmail(memberId, email);
		if (dupCheck == 0) { // 해당 이메일이 db에 없으면
			return ResponseEntity.ok("do not exist");
		}
		return ResponseEntity.ok("exist");
	}

	@PostMapping("/find-id")
	public ResponseEntity<String> sendVerificationEmailID(String email) {
		int result = memberService.dupCheckEmail(email);
		if (result == 0) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("do not exist");
		}
		try {
			emailService.sendVerificationEmail(email);
			log.info("여기입니다.");
			return ResponseEntity.ok("Sending Code Success");
		} catch (MessagingException e) {
			log.info("아뇨 여기입니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Sending Code Fail");
		}
	}

	@PostMapping("/find-pw")
	public ResponseEntity<String> sendVerificationEmailPW(String memberId, String email) {
		int result = memberService.dupCheckIdAndEmail(memberId, email);
		if (result == 0) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("do not exist");
		}
		try {
			emailService.sendVerificationEmail(email);
			log.info("여기입니다.");
			return ResponseEntity.ok("Sending Code Success");
		} catch (MessagingException e) {
			log.info("아뇨 여기입니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Sending Code Fail");
		}
	}

	@PostMapping("/verifyCode-ID")
	public ResponseEntity<String> verifyCodeID(@RequestBody VerifyCodeRequest verifyCodeRequest) {
		String email = verifyCodeRequest.getEmail();
		String code = verifyCodeRequest.getCode();

		if (emailService.verifyCode(email, code)) {
			String memberId = memberService.findId(email);
			return ResponseEntity.ok(memberId);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Inaccurate or expired code.");
		}
	}

	@PostMapping("/verifyCode-PW")
	public ResponseEntity<String> verifyCodePW(@RequestBody VerifyCodeRequest verifyCodeRequest) {
		String email = verifyCodeRequest.getEmail();
		String code = verifyCodeRequest.getCode();

		if (emailService.verifyCode(email, code)) {
			return ResponseEntity.ok("인증 성공");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Inaccurate or expired code.");
		}
	}

	@PostMapping("/find-update-pw")
	public ResponseEntity<String> findAndUpdatePwPOST(String memberId, String email, String password) {
		String originalId = memberService.findId(email);
		if (!originalId.equals(memberId)) {
			log.info("다른 아이디 입력함");
			return ResponseEntity.ok("Update Fail");
		}
		int result = memberService.updateMemberPassword(memberId, password);
		if (result == 0) { // 업데이트가 안 되면
			log.info("비밀번호 수정 실패");
			return ResponseEntity.ok("Update Fail");
		}

		log.info("findAndUpdatePwPOST()");
		return ResponseEntity.ok("Update Success");
	}

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class VerifyCodeRequest {
		private String email;
		private String code;
	}
}
