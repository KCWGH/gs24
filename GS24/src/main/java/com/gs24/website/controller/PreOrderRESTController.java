package com.gs24.website.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<Integer> cancellPreOrder(@RequestBody int[] selectedPreordersId) {
		log.info("cancelPreOrder()");
		Integer result = 0;
		for (int i : selectedPreordersId) {
			log.info(i);
			PreorderVO preorderVO = preorderService.getPreorderOneById(i);
			int preorderAmount = preorderVO.getPreorderAmount();
			int foodId = preorderVO.getFoodId();

			result = preorderService.deletePreorder(i, foodId, preorderAmount);
		}

		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}

	@PostMapping("/delete")
	public ResponseEntity<Integer> deletePreOrder(@RequestBody int[] cancelledPreorderId) {
		log.info("deletePreOrder()");
		Integer result = 0;

		for (int i : cancelledPreorderId) {
			log.info(i);
			result = preorderService.deleteOnlyPreoder(i);
		}

		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
}
