package com.gs24.website.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gs24.website.domain.GiftCardVO;
import com.gs24.website.domain.MemberVO;
import com.gs24.website.service.FoodService;
import com.gs24.website.service.GiftCardService;
import com.gs24.website.service.MemberService;

import lombok.extern.log4j.Log4j;

@Controller // @Component
@RequestMapping(value = "/giftcard")
@Log4j
public class GiftCardController {

	@Autowired
	private GiftCardService giftCardService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private FoodService foodService;

	@GetMapping("/grant")
	public void grantGET(Model model) {
		log.info("grantGET()");
		String[] foodType = foodService.getFoodTypeList();
		model.addAttribute("foodTypeList", foodType);
	}

	@PostMapping("/grant")
	public String grantPOST(@ModelAttribute GiftCardVO giftCardVO, Model model, RedirectAttributes attributes) {
		log.info("grantPOST()");
		log.info(giftCardVO);
		if (giftCardVO.getGiftCardName().equals("")) { // 이름을 따로 입력하지 않았으면
			String foodType = giftCardVO.getFoodType();

			String value = giftCardVO.getBalance() + "원";

			giftCardVO.setGiftCardName(foodType + " " + value + " 금액권");
		}
		if (memberService.dupCheckId(giftCardVO.getMemberId()) == 1) {
			int result = giftCardService.grantGiftCard(giftCardVO);
			log.info(result + "개 기프트카드 제공 완료");
		} else {
			attributes.addFlashAttribute("message", "존재하지 않는 회원 아이디입니다. 기프트카드 제공에 실패했습니다.");
			return "redirect:/giftcard/grant";
		}
		attributes.addFlashAttribute("message", "기프트카드 제공 완료 :)");
		return "redirect:/giftcard/grant";
	}

	@GetMapping("/list")
	public String list(Model model, HttpSession session) {
		log.info("list()");
		String memberId = (String) session.getAttribute("memberId");
		if (memberId != null) {
			MemberVO memberVO = memberService.getMember(memberId);
			memberVO.setPassword(null);
			memberVO.setPhone(null);
			memberVO.setEmail(null);
			memberVO.setBirthday(null);
			model.addAttribute("memberVO", memberVO);
			return "giftcard/list";
		}
		return "redirect:../member/login";
	}

	@GetMapping("/detail")
	public void detailGET(HttpSession session, Model model, int giftCardId) {
		log.info("detailGET()");
		String memberId = (String) session.getAttribute("memberId");
		if (memberId != null) {
			MemberVO memberVO = memberService.getMember(memberId);
			model.addAttribute("memberVO", memberVO);
		}
		GiftCardVO giftCardVO = giftCardService.getGiftCardDetail(giftCardId);
		Date sysDate = new Date();
		model.addAttribute("giftCardVO", giftCardVO);
		model.addAttribute("sysDate", sysDate);
	}

} // end BoardController
