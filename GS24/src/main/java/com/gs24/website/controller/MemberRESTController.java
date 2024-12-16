package com.gs24.website.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gs24.website.domain.MemberVO;
import com.gs24.website.service.MemberService;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping(value = "/member")
@Log4j
public class MemberRESTController {

	@Autowired
	private MemberService memberService; // MemberService 사용

	@PostMapping("/dupcheckid")
	public ResponseEntity<String> dupcheckidPOST(String memberId) {
		log.info("dupCheckIdPOST()");
		int result = memberService.dupCheckId(memberId); // 서비스 레이어 사용
		if (result == 1) { // 중복되면
			return ResponseEntity.ok("1");
		}
		return ResponseEntity.ok("0");
	}

	@PostMapping("/dupcheckemail")
	public ResponseEntity<String> dupcheckemailPOST(String email) {
		log.info("dupCheckEmailPOST()");
		int result = memberService.dupCheckEmail(email); // 서비스 레이어 사용
		if (result == 1) { // 중복되면
			return ResponseEntity.ok("1");
		}
		return ResponseEntity.ok("0");
	}

	@PostMapping("/dupcheckphone")
	public ResponseEntity<String> dupcheckphonePOST(String phone) {
		log.info("dupCheckPhonePOST()");
		int result = memberService.dupCheckPhone(phone); // 서비스 레이어 사용
		if (result == 1) { // 중복되면
			return ResponseEntity.ok("1");
		}
		return ResponseEntity.ok("0");
	}

	@PostMapping("/findid")
	public ResponseEntity<String> findidPOST(String email) {
		log.info("findIdPOST()");
		String memberId = memberService.findId(email); // 서비스 레이어 사용
		if (memberId == null) {
			return ResponseEntity.ok("");
		}
		return ResponseEntity.ok(memberId);
	}

	@PostMapping("/update")
	public ResponseEntity<String> updatePOST(@RequestBody MemberVO memberVO) {
		int result = memberService.updateMember(memberVO); // 서비스 레이어 사용
		if (result == 0) { // 업데이트가 안 되면
			log.info("수정 실패");
			return ResponseEntity.ok("Update Fail");
		}

		log.info("updatePOST()");
		return ResponseEntity.ok("Update Success");
	}

	@PostMapping("/delete")
	public ResponseEntity<String> deletePOST(String memberId) {
		int result = memberService.deleteMember(memberId); // 서비스 레이어 사용
		if (result == 0) { // 삭제가 안 되면
			log.info("삭제 실패");
			return ResponseEntity.ok("Delete Fail");
		}

		log.info("deletePOST()");
		return ResponseEntity.ok("Delete Success");
	}

}
