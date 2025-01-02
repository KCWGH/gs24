package com.gs24.website.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gs24.website.domain.MemberVO;
import com.gs24.website.service.EmailService;
import com.gs24.website.service.MemberService;
import com.gs24.website.service.PreorderService;
import com.gs24.website.service.QuestionService;
import com.gs24.website.service.ReviewService;
import com.gs24.website.util.PageMaker;
import com.gs24.website.util.Pagination;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping(value = "/member")
@Log4j
public class MemberRESTController {

	@Autowired
	private MemberService memberService; // MemberService 사용

	@Autowired
	private EmailService emailService;

	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private PreorderService preorderService;

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
	public ResponseEntity<String> verifyCodeID(@RequestParam("email") String email, @RequestParam("code") String code) {
		if (emailService.verifyCode(email, code)) {
			String memberId = memberService.findId(email);
			return ResponseEntity.ok(memberId);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증번호가 일치하지 않습니다.");
		}
	}

	@PostMapping("/verifyCode-PW")
	public ResponseEntity<String> verifyCodePW(@RequestParam("email") String email, @RequestParam("code") String code) {
		if (emailService.verifyCode(email, code)) {
			return ResponseEntity.ok("");
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

	@GetMapping("/myReviews")
	public ResponseEntity<Map<String, Object>> reviewList(Pagination pagination, HttpSession session) {
		return postListGET("myReviews", pagination, session);
	}

	@GetMapping("/myQuestions")
	public ResponseEntity<Map<String, Object>> questionList(Pagination pagination, HttpSession session) {
		return postListGET("myQuestions", pagination, session);
	}

	@GetMapping("/myPreorders")
	public ResponseEntity<Map<String, Object>> preorderList(Pagination pagination, HttpSession session) {
		return postListGET("myPreorders", pagination, session);
	}

	@GetMapping("/myNotifications")
	public ResponseEntity<Map<String, Object>> notificationList(Pagination pagination, HttpSession session) {
		return postListGET("myNotifications", pagination, session);
	}

	public ResponseEntity<Map<String, Object>> postListGET(String type, Pagination pagination, HttpSession session) {
		log.info("postListGET()");
		String memberId = (String) session.getAttribute("memberId");
		Map<String, Object> response = new HashMap<>();
		if (memberId != null) {
			MemberVO memberVO = memberService.getMember(memberId);
			memberVO.setPassword(null);
			memberVO.setPhone(null);
			memberVO.setEmail(null);
			memberVO.setBirthday(null);
			pagination.setMemberVO(memberVO);
			pagination.setPageSize(5);
			PageMaker pageMaker = new PageMaker();
			pageMaker.setPagination(pagination);
			List<?> postList = null;

			switch (type) {
			case "myReviews":
				postList = reviewService.getAllReviewByMemberId(memberId, pagination);
				pageMaker.setTotalCount(reviewService.countReviewByMemberId(memberId));
				break;
			case "myQuestions":
				postList = questionService.getPagingQuestionsByMemberId(memberId, pagination);
				pageMaker.setTotalCount(questionService.countQuestionByMemberId(memberId));
				break;
			case "myPreorders":
				postList = preorderService.getPreorderBymemberId(memberId);
				pageMaker.setTotalCount(preorderService.countPreorderByMemberId(memberId));
				break;
//			case "myNotifications": TODO : 알림 기능 만들면 구현
//				postList = reviewService.getPagedAllCoupons(memberId, pagination);
//				pageMaker.setTotalCount(reviewService.getAllCount(memberId));
//				break;
			}
			response.put("pageMaker", pageMaker);
			response.put("postList", postList);
		} else {
			response.put("message", "Member not found");
		}
		return ResponseEntity.ok(response);
	}

}
