package com.gs24.website.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gs24.website.domain.CustomUser;
import com.gs24.website.domain.MemberVO;
import com.gs24.website.domain.PreorderVO;
import com.gs24.website.service.MemberService;
import com.gs24.website.service.PreorderService;

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
	public ResponseEntity<List<PreorderVO>> getAllPreOrder(@AuthenticationPrincipal CustomUser customUser,
			Model model) {
		log.info("getAllPreOrder()");
		log.info("mypageGET()");
		String memberId = customUser.getUsername(); // CustomUser의 username

		// 회원 정보 가져오기
		MemberVO memberVO = memberService.getMember(memberId);
		model.addAttribute("memberVO", memberVO);
		List<PreorderVO> list = preorderService.getPreorderBymemberId(memberId);
		log.info(list);
		return new ResponseEntity<List<PreorderVO>>(list, HttpStatus.OK);
	}

	@PostMapping("/cancel")
	public ResponseEntity<Integer> cancelPreOrder(@RequestBody int[] selectedPreorderIds) {
		log.info("cancelPreOrder()");
		Integer result = 0;
		for (int preorderId : selectedPreorderIds) {
			log.info(preorderId);
			PreorderVO preorderVO = preorderService.getPreorderOneById(preorderId);
			log.info(preorderVO);
			result = preorderService.cancelPreorder(preorderVO.getPreorderId(), preorderVO.getFoodId(),
					preorderVO.getPreorderAmount());
		}
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}

	@PostMapping("/delete")
	public ResponseEntity<Integer> deletePreOrder(@RequestBody int[] canceledPreorderIds) {
		log.info("deletePreOrder()");
		Integer result = 0;

		for (int preorderId : canceledPreorderIds) {
			log.info(preorderId);
			result = preorderService.deletePreorder(preorderId);
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
	public ResponseEntity<List<Integer>> pickedUp(Authentication auth) {
		log.info("pickedUp");
		List<Integer> list = null;
		if (auth != null) {

			String memberId = auth.getName();
			list = preorderService.getPickedUpFoodIdByMemberId(memberId);

			log.info(list);
		}

		return new ResponseEntity<List<Integer>>(list, HttpStatus.OK);
	}
}
