package com.gs24.website.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gs24.website.domain.GiftCardVO;
import com.gs24.website.domain.MemberVO;
import com.gs24.website.service.GiftCardService;
import com.gs24.website.service.MemberService;
import com.gs24.website.service.OwnerService;
import com.gs24.website.util.PageMaker;
import com.gs24.website.util.Pagination;

@RestController
@RequestMapping(value = "/giftcard")
public class GiftCardRESTController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private OwnerService ownerService;

	@Autowired
	private GiftCardService giftCardService;

	@PostMapping("/dup-check-id")
	public ResponseEntity<String> dupcheckIdPOST(String memberId) {
		if (memberService.dupCheckMemberId(memberId) != 1 && ownerService.dupCheckOwnerId(memberId) == 1) {
			return ResponseEntity.ok("2");
		} else if (memberService.dupCheckMemberId(memberId) == 1) {
			return ResponseEntity.ok("1");
		}
		return ResponseEntity.ok("0");
	}

	@GetMapping("/list-unused")
	public ResponseEntity<Map<String, Object>> availableList(Pagination pagination, Authentication auth) {
		return getCouponList("unused", pagination, auth);
	}

	@GetMapping("/list-expired")
	public ResponseEntity<Map<String, Object>> expiredList(Pagination pagination, Authentication auth) {
		return getCouponList("expired", pagination, auth);
	}

	@GetMapping("/list-used")
	public ResponseEntity<Map<String, Object>> usedList(Pagination pagination, Authentication auth) {
		return getCouponList("used", pagination, auth);
	}

	@GetMapping("/list-all")
	public ResponseEntity<Map<String, Object>> allList(Pagination pagination, Authentication auth) {
		return getCouponList("all", pagination, auth);
	}

	private ResponseEntity<Map<String, Object>> getCouponList(String type, Pagination pagination, Authentication auth) {
		String memberId = auth.getName();
		Map<String, Object> response = new HashMap<>();
		MemberVO memberVO = memberService.getMember(memberId);
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

		return ResponseEntity.ok(response);
	}

}
