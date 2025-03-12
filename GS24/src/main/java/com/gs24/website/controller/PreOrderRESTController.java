package com.gs24.website.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gs24.website.domain.MemberVO;
import com.gs24.website.domain.PreorderVO;
import com.gs24.website.service.MemberService;
import com.gs24.website.service.PreorderService;
import com.gs24.website.util.Pagination;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("/preorder")
@Log4j
public class PreOrderRESTController {

	@Autowired
	private PreorderService preorderService;

	@Autowired
	private MemberService memberService;

	@GetMapping("/all/{memberId}")
	public ResponseEntity<List<PreorderVO>> getAllPreOrder(Authentication auth, Model model, int pageNum,
			int pageSize) {
		log.info("getAllPreOrder()");
		String memberId = auth.getName();

		// 회원 정보 가져오기
		MemberVO memberVO = memberService.getMember(memberId);
		model.addAttribute("memberVO", memberVO);
		Pagination pagination = new Pagination();
		pagination.setPageNum(pageNum);
		pagination.setPageSize(pageSize);

		List<PreorderVO> list = preorderService.getPagedPreordersByMemberId(memberId, pagination);
		return new ResponseEntity<List<PreorderVO>>(list, HttpStatus.OK);
	}

	@PostMapping("/cancel")
	public ResponseEntity<Integer> cancelPreOrder(@RequestBody int[] selectedPreorderIds) {
		log.info("cancelPreOrder()");
		Integer result = 0;
		for (int preorderId : selectedPreorderIds) {
			PreorderVO preorderVO = preorderService.getPreorderOneById(preorderId);
			result = preorderService.cancelPreorder(preorderVO.getPreorderId(), preorderVO.getFoodId(),
					preorderVO.getPreorderAmount(), preorderVO.getRefundVal());
		}
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}

	@PostMapping("/delete")
	public ResponseEntity<Integer> deletePreOrder(@RequestBody int[] canceledPreorderIds) {
		log.info("deletePreOrder()");
		Integer result = 0;

		for (int preorderId : canceledPreorderIds) {
			log.info(preorderId);
			result = preorderService.updateShowStatus(preorderId);
		}

		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}

	@PostMapping("/check")
	public ResponseEntity<Integer> checkPreorder(int preorderId) {
		log.info("checkPreorder()");

		Integer result = preorderService.updateIsPickUp(preorderId, 1);

		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}

	@PostMapping("/pickedup")
	public ResponseEntity<Boolean> pickedUp(Authentication auth, int foodId) {
		log.info("pickedUp");
		Boolean isPickedUp = false;
		if (auth != null) {
			String memberId = auth.getName();
			isPickedUp = preorderService.getPickedUpFoodIdByMemberId(memberId, foodId);
			log.info(isPickedUp);
		}

		return new ResponseEntity<Boolean>(isPickedUp, HttpStatus.OK);
	}
}
