package com.gs24.website.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gs24.website.domain.CustomUser;
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
		List<String> foodType = foodService.getFoodTypeList();
		model.addAttribute("foodTypeList", foodType);
	}

	@GetMapping("/purchase")
	public void purchaseGET(Model model) {
		log.info("purchaseGET()");
		List<String> foodType = foodService.getFoodTypeList();
		model.addAttribute("foodTypeList", foodType);
	}
	
	@PostMapping("/purchase")
	public String purchasePOST(@ModelAttribute GiftCardVO giftCardVO, Model model, RedirectAttributes redirectAttributes) {
		log.info("purchasePOST()");
		log.info(giftCardVO);
		if (giftCardVO.getGiftCardName().equals("")) { // 이름을 따로 입력하지 않았으면
			String foodType = giftCardVO.getFoodType();

			String value = giftCardVO.getBalance() + "원";

			giftCardVO.setGiftCardName(foodType + " " + value + " 금액권");
		} else if (giftCardVO.getGiftCardName().equals("생일 축하 기프트카드")) {
			redirectAttributes.addFlashAttribute("message", "사용할 수 없는 기프트카드 이름입니다.");
			return "redirect:/giftcard/purchase";
		}
		if (memberService.dupCheckMemberId(giftCardVO.getMemberId()) == 1 && giftCardVO.getBalance() >= 1000) {
			int result = giftCardService.grantGiftCard(giftCardVO);
			log.info(result + "개 기프트카드 제공 완료");
		} else if (giftCardVO.getBalance() < 1000) {
			redirectAttributes.addFlashAttribute("message", "기프트카드 금액은 1000원 이상이어야 합니다.");
			return "redirect:/giftcard/purchase";
		} else {
			redirectAttributes.addFlashAttribute("message", "존재하지 않는 일반회원 아이디입니다. 기프트카드 제공에 실패했습니다.");
			return "redirect:/giftcard/purchase";
		}
		redirectAttributes.addFlashAttribute("message", "기프트카드 구매 완료 :)");
		return "redirect:/giftcard/purchase";

	}

	@PostMapping("/grant")
	public String grantPOST(@ModelAttribute GiftCardVO giftCardVO, Model model, RedirectAttributes redirectAttributes) {
		log.info("grantPOST()");
		log.info(giftCardVO);
		if (giftCardVO.getGiftCardName().equals("")) { // 이름을 따로 입력하지 않았으면
			String foodType = giftCardVO.getFoodType();

			String value = giftCardVO.getBalance() + "원";

			giftCardVO.setGiftCardName(foodType + " " + value + " 금액권");
		} else if (giftCardVO.getGiftCardName().equals("생일 축하 기프트카드")) {
			redirectAttributes.addFlashAttribute("message", "사용할 수 없는 기프트카드 이름입니다.");
			return "redirect:/giftcard/grant";
		}
		if (memberService.dupCheckMemberId(giftCardVO.getMemberId()) == 1 && giftCardVO.getBalance() >= 1000) {
			int result = giftCardService.grantGiftCard(giftCardVO);
			log.info(result + "개 기프트카드 제공 완료");
		} else if (giftCardVO.getBalance() < 1000) {
			redirectAttributes.addFlashAttribute("message", "기프트카드 금액은 1000원 이상이어야 합니다.");
			return "redirect:/giftcard/grant";
		} else {
			redirectAttributes.addFlashAttribute("message", "존재하지 않는 회원 아이디입니다. 기프트카드 제공에 실패했습니다.");
			return "redirect:/giftcard/grant";
		}
		redirectAttributes.addFlashAttribute("message", "기프트카드 제공 완료 :)");
		return "redirect:/giftcard/grant";

	}

	@GetMapping("/list")
	public String list(Model model, @AuthenticationPrincipal CustomUser customUser) {
		log.info("list()");
		String memberId = customUser.getUsername();
		System.out.println(memberId);
		if (memberId != null) {
			MemberVO memberVO = memberService.getMember(memberId);
			memberVO.setPassword(null);
			model.addAttribute("memberVO", memberVO);
		}
		return "giftcard/list";
	}

	@GetMapping("/detail")
	public void detailGET(@AuthenticationPrincipal CustomUser customUser, Model model, int giftCardId) {
		log.info("detailGET()");
		String memberId = customUser.getUsername();
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
