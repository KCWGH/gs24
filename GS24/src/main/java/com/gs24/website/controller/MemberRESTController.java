package com.gs24.website.controller;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.gs24.website.domain.MemberVO;
import com.gs24.website.service.EmailService;
import com.gs24.website.service.MemberService;

import lombok.extern.log4j.Log4j;
@Log4j
public class MemberRESTController {
	@Autowired
	private MemberService memberService; // MemberService 사용
	@Autowired
	private EmailService emailService;

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

	@PostMapping("/find-id")
	public ResponseEntity<String> sendVerificationEmail(String email) {
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

	// TODO : 비밀번호 찾기 구현

	@PostMapping("/verifyCode")
	public ResponseEntity<String> verifyCode(@RequestParam("email") String email, @RequestParam("code") String code) {
		if (emailService.verifyCode(email, code)) {
			// 인증이 완료되면 해당 이메일로 아이디를 찾기
			String memberId = memberService.findId(email);
			return ResponseEntity.ok("인증번호가 일치합니다. 아이디는 " + memberId + "입니다.");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증번호가 일치하지 않습니다.");
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

	// 이메일 인증번호 발송
    @PostMapping("/sendVerificationCode")
    public ResponseEntity<String> sendVerificationCode(@RequestParam String email) {
        try {
            // 이미 회원 존재 확인 부분은 구현되었으므로 넘어갑니다
            emailVerificationService.sendVerificationCode(email);  // 인증번호 발송
            return ResponseEntity.status(HttpStatus.OK).body("{\"success\": true}");  // 성공 응답
        } catch (Exception e) {
        	log.info("여기임1");// 인증 실패
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"success\": false}");  // 서버 오류 응답
        }
    }
    // 인증번호 확인 후 회원 ID 찾기
    @PostMapping("/verifyCode")
    public ResponseEntity<String> verifyCode(@RequestParam String email, @RequestParam String verificationCode) {
        try {
            // 인증번호와 이메일이 맞으면 회원 ID를 찾음
            String memberId = emailVerificationService.verifyCodeAndFindMemberId(email, verificationCode);
            if (memberId != null) {
                return ResponseEntity.status(HttpStatus.OK).body("{\"success\": true}");  // 인증 성공
            } else {
            	log.info("여기임2");// 인증 실패
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"success\": false}"); 
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"success\": false}");  // 서버 오류 응답
        }
    }
    
	@PostMapping("/delete")
	public ResponseEntity<String> deletePOST(String memberId, HttpSession session) {
		int result = memberService.deleteMember(memberId);
		if (result == 0) { // 삭제가 안 되면
			log.info("삭제 실패");
			return ResponseEntity.ok("Delete Fail");
		}
		session.invalidate();
		log.info("session.invalidate()");
		log.info("deletePOST()");
		return ResponseEntity.ok("Delete Success");
	}
}
