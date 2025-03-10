package com.gs24.website.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gs24.website.domain.GiftCardVO;
import com.gs24.website.domain.MemberVO;
import com.gs24.website.service.GiftCardService;
import com.gs24.website.service.MemberService;

@Controller // @Component
@RequestMapping(value = "/giftcard")
public class GiftCardController {

	@Autowired
	private GiftCardService giftCardService;

	@Autowired
	private MemberService memberService;

	@GetMapping("/grant")
	public void grantGET(Authentication auth, Model model) {
		String memberId = auth.getName();
		model.addAttribute("memberId", memberId);
	}

	@GetMapping("/purchase")
	public void purchaseGET(Authentication auth, Model model) {
		String memberId = auth.getName();
		model.addAttribute("memberId", memberId);
	}

	@PostMapping("/purchase")
	public String purchasePOST(@ModelAttribute GiftCardVO giftCardVO, Model model,
			RedirectAttributes redirectAttributes) {
		if (giftCardVO.getGiftCardName().equals("")) { // 이름을 따로 입력하지 않았으면
			String value = giftCardVO.getBalance() + "원";
			giftCardVO.setGiftCardName(value + " 금액권");
		} else if (giftCardVO.getGiftCardName().equals("생일 축하 기프트카드")) {
			redirectAttributes.addFlashAttribute("message", "사용할 수 없는 기프트카드 이름입니다.");
			return "redirect:/giftcard/purchase";
		}
		if (memberService.dupCheckMemberId(giftCardVO.getMemberId()) == 1 && giftCardVO.getBalance() >= 1000
				&& giftCardVO.getBalance() <= 30000) {
			giftCardService.grantGiftCard(giftCardVO);
		} else if (giftCardVO.getBalance() < 1000 || giftCardVO.getBalance() > 30000) {
			redirectAttributes.addFlashAttribute("message", "기프트카드 금액은 1000원 이상 30000원 이하여야 합니다.");
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
		if (giftCardVO.getGiftCardName().equals("")) { // 이름을 따로 입력하지 않았으면
			String value = giftCardVO.getBalance() + "원";
			giftCardVO.setGiftCardName(value + " 금액권");
		} else if (giftCardVO.getGiftCardName().equals("생일 축하 기프트카드")) {
			redirectAttributes.addFlashAttribute("message", "사용할 수 없는 기프트카드 이름입니다.");
			return "redirect:/giftcard/grant";
		}
		if (memberService.dupCheckMemberId(giftCardVO.getMemberId()) == 1 && giftCardVO.getBalance() >= 1000
				&& giftCardVO.getBalance() <= 30000) {
			giftCardService.grantGiftCard(giftCardVO);
		} else if (giftCardVO.getBalance() < 1000 || giftCardVO.getBalance() > 30000) {
			redirectAttributes.addFlashAttribute("message", "기프트카드 금액은 1000원 이상 30000원 이하여야 합니다.");
			return "redirect:/giftcard/grant";
		} else {
			redirectAttributes.addFlashAttribute("message", "존재하지 않는 회원 아이디입니다. 기프트카드 제공에 실패했습니다.");
			return "redirect:/giftcard/grant";
		}
		redirectAttributes.addFlashAttribute("message", "기프트카드 제공 완료 :)");
		return "redirect:/giftcard/grant";

	}

	@GetMapping("/list")
	public String list(Model model, Authentication auth) {
		String memberId = auth.getName();
		if (memberId != null) {
			MemberVO memberVO = memberService.getMember(memberId);
			memberVO.setPassword(null);
			model.addAttribute("memberVO", memberVO);
		}
		return "giftcard/list";
	}

	@GetMapping("/detail")
	public void detailGET(Authentication auth, Model model, int giftCardId) {
		String memberId = auth.getName();
		if (memberId != null) {
			MemberVO memberVO = memberService.getMember(memberId);
			model.addAttribute("memberVO", memberVO);
		}
		GiftCardVO giftCardVO = giftCardService.getGiftCardDetail(giftCardId);
		Date sysDate = new Date();
		model.addAttribute("giftCardVO", giftCardVO);
		model.addAttribute("sysDate", sysDate);
	}

}
