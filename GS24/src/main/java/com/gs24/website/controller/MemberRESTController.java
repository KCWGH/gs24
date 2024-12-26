package com.gs24.website.controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.gs24.website.domain.MemberVO;
import com.gs24.website.service.EmailVerificationService;
import com.gs24.website.service.MemberService;

import lombok.extern.log4j.Log4j;

@Log4j
public class MemberRESTController {
<<<<<<< Updated upstream
	@Autowired
	private MemberService memberService; // MemberService 사용
	@Autowired
	private EmailVerificationService emailVerificationService;

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
=======

    @Autowired
    private MemberService memberService; // MemberService 사용
    @Autowired
    private EmailVerificationService emailVerificationService;

    // 아이디 중복 체크
    @PostMapping("/dupcheckid")
    public ResponseEntity<String> dupcheckidPOST(@RequestParam String memberId) {
        log.info("dupCheckIdPOST()");
        int result = memberService.dupCheckId(memberId); // 서비스 레이어 사용
        return result == 1 ? ResponseEntity.ok("1") : ResponseEntity.ok("0");
    }
>>>>>>> Stashed changes

    // 이메일 중복 체크
    @PostMapping("/dupcheckemail")
    public ResponseEntity<String> dupcheckemailPOST(@RequestParam String email) {
        log.info("dupCheckEmailPOST()");
        int result = memberService.dupCheckEmail(email); // 서비스 레이어 사용
        return result == 1 ? ResponseEntity.ok("1") : ResponseEntity.ok("0");
    }

<<<<<<< Updated upstream
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
=======
    // 전화번호 중복 체크
    @PostMapping("/dupcheckphone")
    public ResponseEntity<String> dupcheckphonePOST(@RequestParam String phone) {
        log.info("dupCheckPhonePOST()");
        int result = memberService.dupCheckPhone(phone);
        return result == 1 ? ResponseEntity.ok("1") : ResponseEntity.ok("0");
    }
>>>>>>> Stashed changes

    // 이메일로 아이디 찾기
    @PostMapping("/findid")
    public ResponseEntity<String> findidPOST(@RequestParam String email) {
        log.info("findIdPOST()");
        if (email.isEmpty()) {
            return ResponseEntity.ok("");
        }

        int dupCheck = memberService.dupCheckEmail(email);
        return dupCheck == 0 ? ResponseEntity.ok("do not exist") : ResponseEntity.ok("exist");
    }

    // 비밀번호 수정
    @PostMapping("/updatePw")
    public ResponseEntity<String> updatePwPOST(@RequestBody MemberVO memberVO) {
        int result = memberService.updateMemberPassword(memberVO);
        return result == 0 ? ResponseEntity.ok("Update Fail") : ResponseEntity.ok("Update Success");
    }

    // 이메일 수정
    @PostMapping("/updateEmail")
    public ResponseEntity<String> updateEmailPOST(@RequestBody MemberVO memberVO) {
        if (memberVO.getEmail().equals(memberService.findEmailById(memberVO.getMemberId()))) {
            return ResponseEntity.ok("Update Fail - Same Email");
        }

        int dupCheck = memberService.dupCheckEmail(memberVO.getEmail());
        if (dupCheck == 0) {
            int result = memberService.updateMemberEmail(memberVO);
            return result == 0 ? ResponseEntity.ok("Update Fail") : ResponseEntity.ok("Update Success");
        } else {
            return ResponseEntity.ok("Update Fail - Duplicated Email");
        }
    }

<<<<<<< Updated upstream
	// 이메일 인증번호 발송
=======
    // 전화번호 수정
    @PostMapping("/updatePhone")
    public ResponseEntity<String> updatePhonePOST(@RequestBody MemberVO memberVO) {
        if (memberVO.getPhone().equals(memberService.findPhoneById(memberVO.getMemberId()))) {
            return ResponseEntity.ok("Update Fail - Same Phone");
        }

        int dupCheck = memberService.dupCheckPhone(memberVO.getPhone());
        if (dupCheck == 0) {
            int result = memberService.updateMemberPhone(memberVO);
            return result == 0 ? ResponseEntity.ok("Update Fail") : ResponseEntity.ok("Update Success");
        } else {
            return ResponseEntity.ok("Update Fail - Duplicated Phone");
        }
    }

    // 이메일 인증번호 발송
>>>>>>> Stashed changes
    @PostMapping("/sendVerificationCode")
    public ResponseEntity<String> sendVerificationCode(@RequestParam String email) {
        try {
            emailVerificationService.sendVerificationCode(email);
            log.info("sendVerificationCodePOST()");
            return ResponseEntity.ok("{\"success\": true}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"success\": false}");
        }
    }

    // 인증번호 확인 후 회원 ID 찾기
    @PostMapping("/verifyCode")
    public ResponseEntity<String> verifyCode(@RequestParam String email, @RequestParam String verificationCode) {
        try {
            String memberId = emailVerificationService.verifyCodeAndFindMemberId(email, verificationCode);
            return memberId != null
                    ? ResponseEntity.ok("{\"success\": true}")
                    : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"success\": false}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"success\": false}");
        }
    }
<<<<<<< Updated upstream
    
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
=======

    // 회원 탈퇴 처리
    @PostMapping("/delete")
    public ResponseEntity<String> deletePOST(@RequestParam String memberId, HttpSession session) {
        int result = memberService.deleteMember(memberId); // 서비스 레이어 사용
        if (result == 0) {
            log.info("삭제 실패");
            return ResponseEntity.ok("Delete Fail");
        }
        session.invalidate();
        log.info("session.invalidate()");
        return ResponseEntity.ok("Delete Success");
    }
>>>>>>> Stashed changes
}
