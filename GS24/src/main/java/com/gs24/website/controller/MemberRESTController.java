package com.gs24.website.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gs24.website.domain.MemberVO;
import com.gs24.website.service.EmailVerificationService;
import com.gs24.website.service.MemberService;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping(value = "/member")
@Log4j
public class MemberRESTController {

<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
>>>>>>> ccdce2e7a9f17201e6bb89e46d18852272cf8bfd
	@Autowired
	private MemberService memberService; // MemberService 사용

	@Autowired
	private EmailVerificationService emailVerificationService;

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

	@PostMapping("/update-pw")
	public ResponseEntity<String> updatePwPOST(@RequestBody MemberVO memberVO) {
		int result = memberService.updateMemberPassword(memberVO);
		if (result == 0) { // 업데이트가 안 되면
			log.info("비밀번호 수정 실패");
			return ResponseEntity.ok("Update Fail");
		}

		log.info("updatePwPOST()");
		return ResponseEntity.ok("Update Success");
	}
<<<<<<< HEAD
=======

	@PostMapping("/update-email")
	public ResponseEntity<String> updateEmailPOST(@RequestBody MemberVO memberVO) {
		if (memberVO.getEmail().equals(memberService.findEmailById(memberVO.getMemberId()))) {
			return ResponseEntity.ok("Update Fail - Same Email");
		} else {
			int dupCheck = memberService.dupCheckEmail(memberVO.getEmail());
			if (dupCheck == 0) {
				int result = memberService.updateMemberEmail(memberVO);
				if (result == 0) { // 업데이트가 안 되면
					log.info("이메일 수정 실패");
					return ResponseEntity.ok("Update Fail");
				} else {
					log.info("updateEmailPOST()");
					return ResponseEntity.ok("Update Success");
				}
			} else {
				return ResponseEntity.ok("Update Fail - Duplicated Email");
			}
		}

	}

	@PostMapping("/update-phone")
	public ResponseEntity<String> updatePhonePOST(@RequestBody MemberVO memberVO) {
		if (memberVO.getPhone().equals(memberService.findPhoneById(memberVO.getMemberId()))) {
			return ResponseEntity.ok("Update Fail - Same Phone");
		} else {
			int dupCheck = memberService.dupCheckPhone(memberVO.getPhone());
			if (dupCheck == 0) {
				int result = memberService.updateMemberPhone(memberVO);
				if (result == 0) { // 업데이트가 안 되면
					log.info("휴대폰 번호 수정 실패");
					return ResponseEntity.ok("Update Fail");
				} else {
					log.info("updateEmailPOST()");
					return ResponseEntity.ok("Update Success");
				}
			} else {
				return ResponseEntity.ok("Update Fail - Duplicated Phone");
			}
		}
	}

	// 이메일 인증번호 발송
	@PostMapping("/send-verification-code")
	public ResponseEntity<String> sendVerificationCodePOST(@RequestParam String email) {
		try {
			emailVerificationService.sendVerificationCode(email);
			log.info("sendVerificationCodePOST()");
			return ResponseEntity.ok("Sending Success");
		} catch (Exception e) { // 서버 에러나면
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"success\": false}");
		}
	}

	// 인증번호 확인 후 회원 ID 찾기
	@PostMapping("/find-id")
	public ResponseEntity<String> findIdPOST(@RequestParam String email, @RequestParam String verificationCode) {
		try {
			// 인증번호와 이메일이 맞으면 회원 ID를 찾음
			String memberId = emailVerificationService.verifyCodeAndFindMemberId(email, verificationCode);
			if (memberId != null) {
				log.info("findIdPOST()");
				return ResponseEntity.ok(memberId);
			} else {
				return ResponseEntity.ok(memberId);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"success\": false}"); // 서버 오류 응답
		}
	}

	@PostMapping("/find-pw")
	public ResponseEntity<String> findPwPOST(@RequestParam String memberId, @RequestParam String email,
			@RequestParam String verificationCode) {
		try {
			// 인증번호와 이메일이 맞으면 회원 ID를 찾음
			int result = emailVerificationService.verifyCodeAndFindPw(memberId, email, verificationCode);
			if (result == 1) {
				log.info("findPwPOST()");
				return ResponseEntity.ok("1");
			} else {
				return ResponseEntity.ok("0");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"success\": false}"); // 서버 오류 응답
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

	@PostMapping("/delete")
	public ResponseEntity<String> deletePOST(String memberId, HttpSession session) {
		int result = memberService.deleteMember(memberId); // 서비스 레이어 사용
		if (result == 0) { // 삭제가 안 되면
			log.info("삭제 실패");
			return ResponseEntity.ok("Delete Fail");
		}
		session.invalidate();
		log.info("session.invalidate()");
		log.info("deletePOST()");
		return ResponseEntity.ok("Delete Success");
	}
>>>>>>> ccdce2e7a9f17201e6bb89e46d18852272cf8bfd

	@PostMapping("/update-email")
	public ResponseEntity<String> updateEmailPOST(@RequestBody MemberVO memberVO) {
		if (memberVO.getEmail().equals(memberService.findEmailById(memberVO.getMemberId()))) {
			return ResponseEntity.ok("Update Fail - Same Email");
		} else {
			int dupCheck = memberService.dupCheckEmail(memberVO.getEmail());
			if (dupCheck == 0) {
				int result = memberService.updateMemberEmail(memberVO);
				if (result == 0) { // 업데이트가 안 되면
					log.info("이메일 수정 실패");
					return ResponseEntity.ok("Update Fail");
				} else {
					log.info("updateEmailPOST()");
					return ResponseEntity.ok("Update Success");
				}
			} else {
				return ResponseEntity.ok("Update Fail - Duplicated Email");
			}
		}

	}

	@PostMapping("/update-phone")
	public ResponseEntity<String> updatePhonePOST(@RequestBody MemberVO memberVO) {
		if (memberVO.getPhone().equals(memberService.findPhoneById(memberVO.getMemberId()))) {
			return ResponseEntity.ok("Update Fail - Same Phone");
		} else {
			int dupCheck = memberService.dupCheckPhone(memberVO.getPhone());
			if (dupCheck == 0) {
				int result = memberService.updateMemberPhone(memberVO);
				if (result == 0) { // 업데이트가 안 되면
					log.info("휴대폰 번호 수정 실패");
					return ResponseEntity.ok("Update Fail");
				} else {
					log.info("updateEmailPOST()");
					return ResponseEntity.ok("Update Success");
				}
			} else {
				return ResponseEntity.ok("Update Fail - Duplicated Phone");
			}
		}
	}

	// 이메일 인증번호 발송
	@PostMapping("/send-verification-code")
	public ResponseEntity<String> sendVerificationCodePOST(@RequestParam String email) {
		try {
			emailVerificationService.sendVerificationCode(email);
			log.info("sendVerificationCodePOST()");
			return ResponseEntity.ok("Sending Success");
		} catch (Exception e) { // 서버 에러나면
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"success\": false}");
		}
	}

	// 인증번호 확인 후 회원 ID 찾기
	@PostMapping("/find-id")
	public ResponseEntity<String> findIdPOST(@RequestParam String email, @RequestParam String verificationCode) {
		try {
			// 인증번호와 이메일이 맞으면 회원 ID를 찾음
			String memberId = emailVerificationService.verifyCodeAndFindMemberId(email, verificationCode);
			if (memberId != null) {
				log.info("findIdPOST()");
				return ResponseEntity.ok(memberId);
			} else {
				return ResponseEntity.ok(memberId);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"success\": false}"); // 서버 오류 응답
		}
	}

	@PostMapping("/find-pw")
	public ResponseEntity<String> findPwPOST(@RequestParam String memberId, @RequestParam String email,
			@RequestParam String verificationCode) {
		try {
			// 인증번호와 이메일이 맞으면 회원 ID를 찾음
			int result = emailVerificationService.verifyCodeAndFindPw(memberId, email, verificationCode);
			if (result == 1) {
				log.info("findPwPOST()");
				return ResponseEntity.ok("1");
			} else {
				return ResponseEntity.ok("0");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"success\": false}"); // 서버 오류 응답
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

	@PostMapping("/delete")
	public ResponseEntity<String> deletePOST(String memberId, HttpSession session) {
		int result = memberService.deleteMember(memberId); // 서비스 레이어 사용
		if (result == 0) { // 삭제가 안 되면
			log.info("삭제 실패");
			return ResponseEntity.ok("Delete Fail");
		}
		session.invalidate();
		log.info("session.invalidate()");
		log.info("deletePOST()");
		return ResponseEntity.ok("Delete Success");
	}

>>>>>>> ccdce2e7a9f17201e6bb89e46d18852272cf8bfd
}
