package com.gs24.website.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gs24.website.domain.ConvenienceVO;
import com.gs24.website.service.ConvenienceService;
import com.gs24.website.service.GiftCardService;
import com.gs24.website.util.PageMaker;
import com.gs24.website.util.Pagination;

@Controller
@RequestMapping("/convenience")
public class ConvenienceController {

	@Autowired
	private ConvenienceService convenienceService;

	@Autowired
	private GiftCardService giftCardService;

	@GetMapping("/list")
	public String listGET(Authentication auth, Model model, Pagination pagination) {

		List<ConvenienceVO> list = convenienceService.getAllConvenience(pagination);
		PageMaker pageMaker = new PageMaker();
		pageMaker.setPagination(pagination);
		pageMaker.setTotalCount(convenienceService.countAllEnabledConvenience());
		if (auth != null) {
			if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MEMBER"))) {
				String birthdayMessage = giftCardService.birthdayGiftCardDupCheckAndGrant(auth);
				if (birthdayMessage != null) {
					model.addAttribute("message", birthdayMessage);
				}
			} else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_OWNER"))) {
				int checkConvenienceId = convenienceService.getConvenienceIdByOwnerId(auth.getName());
				model.addAttribute("checkConvenienceId", checkConvenienceId);
				return "redirect:/convenienceFood/list?convenienceId=" + checkConvenienceId;
			}
		}
		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("conveniList", list);
		return "/convenience/list";
	}
}
