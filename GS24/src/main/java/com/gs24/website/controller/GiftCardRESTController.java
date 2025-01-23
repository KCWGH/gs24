package com.gs24.website.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gs24.website.domain.CustomUser;
import com.gs24.website.domain.GiftCardVO;
import com.gs24.website.domain.MemberVO;
import com.gs24.website.service.GiftCardService;
import com.gs24.website.service.MemberService;
import com.gs24.website.util.PageMaker;
import com.gs24.website.util.Pagination;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping(value = "/giftcard")
@Log4j
public class GiftCardRESTController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private GiftCardService giftCardService;

	@PostMapping("/dup-check-id")
	public ResponseEntity<String> dupcheckIdPOST(String memberId) {
		log.info("dupcheckIdPOST()");
		if (memberService.dupCheckId(memberId) == 1 && memberService.checkRole(memberId) == 2) {
			return ResponseEntity.ok("2");
		} else if (memberService.dupCheckId(memberId) == 1 && memberService.checkRole(memberId) == 1) {
			return ResponseEntity.ok("1");
		}
		return ResponseEntity.ok("0");
	}

	@GetMapping("/list-unused")
	public ResponseEntity<Map<String, Object>> availableList(Pagination pagination,
			@AuthenticationPrincipal CustomUser customUser) {
		return getCouponList("unused", pagination, customUser);
	}

	@GetMapping("/list-expired")
	public ResponseEntity<Map<String, Object>> expiredList(Pagination pagination,
			@AuthenticationPrincipal CustomUser customUser) {
		return getCouponList("expired", pagination, customUser);
	}

	@GetMapping("/list-used")
	public ResponseEntity<Map<String, Object>> usedList(Pagination pagination,
			@AuthenticationPrincipal CustomUser customUser) {
		return getCouponList("used", pagination, customUser);
	}

	@GetMapping("/list-all")
	public ResponseEntity<Map<String, Object>> allList(Pagination pagination,
			@AuthenticationPrincipal CustomUser customUser) {
		return getCouponList("all", pagination, customUser);
	}

	private ResponseEntity<Map<String, Object>> getCouponList(String type, Pagination pagination,
			@AuthenticationPrincipal CustomUser customUser) {
		log.info("getCouponList()");
		String memberId = customUser.getUsername();
		Map<String, Object> response = new HashMap<>();

		if (memberId != null) {
			MemberVO memberVO = memberService.getMember(memberId);
			memberVO.setPassword(null);
			memberVO.setPhone(null);
			memberVO.setEmail(null);
			memberVO.setBirthday(null);
			pagination.setMemberVO(memberVO);
			pagination.setPageSize(3);
			PageMaker pageMaker = new PageMaker();
			pageMaker.setPagination(pagination);
			List<GiftCardVO> giftCardList = null;

			switch (type) {
			case "unused":
				giftCardList = giftCardService.getPagedUnusedGiftCards(memberId, pagination);
				pageMaker.setTotalCount(giftCardService.getAvailableCount(memberId));
				break;
			case "expired":
				giftCardList = giftCardService.getPagedExpiredGiftCards(memberId, pagination);
				pageMaker.setTotalCount(giftCardService.getExpiredCount(memberId));
				break;
			case "used":
				giftCardList = giftCardService.getPagedUsedGiftCards(memberId, pagination);
				pageMaker.setTotalCount(giftCardService.getUsedCount(memberId));
				break;
			case "all":
				giftCardList = giftCardService.getPagedAllGiftCards(memberId, pagination);
				pageMaker.setTotalCount(giftCardService.getAllCount(memberId));
				break;
			}
			response.put("pageMaker", pageMaker);
			response.put("giftCardList", giftCardList);
		} else {
			response.put("message", "Member not found");
		}

		return ResponseEntity.ok(response);
	}

}
