package com.gs24.website.controller;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gs24.website.domain.MemberVO;
import com.gs24.website.domain.OwnerVO;
import com.gs24.website.service.EmailService;
import com.gs24.website.service.MemberService;
import com.gs24.website.service.OwnerService;
import com.gs24.website.service.PreorderService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping(value = "/user")
@Log4j
public class UserRESTController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private OwnerService ownerService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private PreorderService preorderService;

	@PostMapping("/dup-check-id")
	public ResponseEntity<String> dupcheckidPOST(String memberId, String ownerId) {
		log.info("dupCheckIdPOST()");
		String username = memberId;
		if (memberId == null) {
			username = ownerId;
		}
		int memberResult = memberService.dupCheckMemberId(username);
		int ownerResult = ownerService.dupCheckOwnerId(username);
		if (memberResult == 1 || ownerResult == 1) { // 중복되면
			return ResponseEntity.ok("1");
		}
		return ResponseEntity.ok("0");
	}

	@PostMapping("/dup-check-email")
	public ResponseEntity<String> dupcheckemailPOST(String email) {
		log.info("dupCheckEmailPOST()");
		int memberResult = memberService.dupCheckMemberEmail(email);
		int ownerResult = ownerService.dupCheckOwnerEmail(email);
		if (memberResult == 1 || ownerResult == 1) { // 중복되면
			return ResponseEntity.ok("1");
		}
		return ResponseEntity.ok("0");
	}

	@PostMapping("/dup-check-phone")
	public ResponseEntity<String> dupcheckphonePOST(String phone) {
		log.info("dupCheckPhonePOST()");
		int memberResult = memberService.dupCheckMemberPhone(phone);
		int ownerResult = ownerService.dupCheckOwnerPhone(phone);
		if (memberResult == 1 || ownerResult == 1) { // 중복되면
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
		int dupCheck = memberService.dupCheckMemberEmail(email);
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
		int dupCheck = memberService.dupCheckMemberByMemberIdAndMemberEmail(memberId, email);
		if (dupCheck == 0) { // 해당 이메일이 db에 없으면
			return ResponseEntity.ok("do not exist");
		}
		return ResponseEntity.ok("exist");
	}

	@PostMapping("/find-id")
	public ResponseEntity<String> sendVerificationEmailID(String email) {
		int memberResult = memberService.dupCheckMemberEmail(email);
		int ownerResult = ownerService.dupCheckOwnerEmail(email);
		if (memberResult == 0 && ownerResult == 0) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("do not exist");
		}
		try {
			emailService.sendVerificationEmail(email);
			return ResponseEntity.ok("Sending Code Success");
		} catch (MessagingException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Sending Code Fail");
		}
	}

	@PostMapping("/find-pw")
	public ResponseEntity<String> sendVerificationEmailPW(String username, String email) {
		int memberResult = 0;
		int ownerResult = 0;

		memberResult = memberService.dupCheckMemberByMemberIdAndMemberEmail(username, email);
		ownerResult = ownerService.dupCheckOwnerByOwnerIdAndOwnerEmail(username, email);
		if (memberResult == 0 && ownerResult == 0) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("do not exist");
		}
		try {
			emailService.sendVerificationEmail(email);
			return ResponseEntity.ok("Sending Code Success");
		} catch (MessagingException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Sending Code Fail");
		}
	}

	@PostMapping("/verifyCode-ID")
	public ResponseEntity<String> verifyCodeID(@RequestBody VerifyCodeRequest verifyCodeRequest) {
		String email = verifyCodeRequest.getEmail();
		String code = verifyCodeRequest.getCode();

		String memberResult = memberService.findMemberIdByEmail(email);
		String ownerResult = ownerService.findOwnerIdByEmail(email);

		if (emailService.verifyCode(email, code)) {
			if (memberResult != null) {
				return ResponseEntity.ok(memberResult);
			} else if (ownerResult != null) {
				return ResponseEntity.ok(ownerResult);
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("do not exist");
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
	public ResponseEntity<String> findAndUpdatePwPOST(String username, String email, String password) {
		String memberResult = memberService.findMemberIdByEmail(email);
		String ownerResult = ownerService.findOwnerIdByEmail(email);
		if ((memberResult != null && !memberResult.equals(username))
				|| (ownerResult != null && !ownerResult.equals(username))) {
			log.info("다른 아이디 입력함");
			return ResponseEntity.ok("Update Fail");
		}
		int result = 0;
		if (memberResult != null) {
			result = memberService.updateMemberPassword(username, password);
		} else {
			result = ownerService.updateOwnerPassword(username, password);
		}
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

	@PostMapping("/update-member-email")
	public ResponseEntity<String> updateMemberEmailPOST(@RequestBody MemberVO memberVO) {
		if (memberVO.getEmail().equals(memberService.findEmailByMemberId(memberVO.getMemberId()))) {
			return ResponseEntity.ok("Update Fail - Same Email");
		} else {
			int dupCheck = memberService.dupCheckMemberEmail(memberVO.getEmail());
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

	@PostMapping("/update-owner-email")
	public ResponseEntity<String> updateOwnerEmailPOST(@RequestBody OwnerVO ownerVO) {
		if (ownerVO.getEmail().equals(ownerService.findEmailByOwnerId(ownerVO.getOwnerId()))) {
			return ResponseEntity.ok("Update Fail - Same Email");
		} else {
			int dupCheck = ownerService.dupCheckOwnerEmail(ownerVO.getEmail());
			if (dupCheck == 0) {
				int result = ownerService.updateOwnerEmail(ownerVO);
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

	@PostMapping("/update-member-phone")
	public ResponseEntity<String> updateMemberPhonePOST(@RequestBody MemberVO memberVO) {
		if (memberVO.getPhone().equals(memberService.findPhoneByMemberId(memberVO.getMemberId()))) {
			return ResponseEntity.ok("Update Fail - Same Phone");
		} else {
			int dupCheck = memberService.dupCheckMemberPhone(memberVO.getPhone());
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

	@PostMapping("/update-owner-phone")
	public ResponseEntity<String> updateOwnerPhonePOST(@RequestBody OwnerVO ownerVO) {
		if (ownerVO.getPhone().equals(ownerService.findPhoneByOwnerId(ownerVO.getOwnerId()))) {
			return ResponseEntity.ok("Update Fail - Same Phone");
		} else {
			int dupCheck = ownerService.dupCheckOwnerPhone(ownerVO.getPhone());
			if (dupCheck == 0) {
				int result = ownerService.updateOwnerPhone(ownerVO);
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

	@PostMapping("/delete-member")
	public ResponseEntity<String> deleteMemberPOST(String memberId, HttpServletRequest request,
			HttpServletResponse response) {
		int checkPreorders = preorderService.countRemainingPreorders(memberId);
		if (checkPreorders == 0) {
			int result = memberService.deleteMember(memberId);
			if (result == 1) {
				new SecurityContextLogoutHandler().logout(request, response,
						SecurityContextHolder.getContext().getAuthentication());
				request.getSession().invalidate();
				return ResponseEntity.ok("Delete Success");
			} else if (result == 2) {
				return ResponseEntity.ok("Delete Fail - Remaining Giftcard Balances");
			} else {
				return ResponseEntity.ok("Delete Fail");
			}
		}
		return ResponseEntity.ok("Delete Fail - Remaining Preorders");
	}

	@PostMapping("/delete-owner")
	public ResponseEntity<String> deleteOwnerPOST(String ownerId, HttpServletRequest request,
			HttpServletResponse response) {
		int result = ownerService.deleteOwner(ownerId);
		if (result == 1) {
			new SecurityContextLogoutHandler().logout(request, response,
					SecurityContextHolder.getContext().getAuthentication());
			request.getSession().invalidate();
			return ResponseEntity.ok("Delete Success");
		}
		return ResponseEntity.ok("Delete Fail - Remaining Preorders");
	}
}
