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

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/convenience")
@Log4j
public class ConvenienceController {
	
	@Autowired
	private ConvenienceService convenienceService;
	
	@Autowired
	private GiftCardService giftCardService;
	
	@GetMapping("/list")
	public void listGET(Authentication auth, Model model) {
		log.info("listGET()");
		
		List<ConvenienceVO> list = convenienceService.getAllConvenience();
		
		if (auth != null) {
			if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MEMBER"))) {
				String birthdayMessage = giftCardService.birthdayGiftCardDupCheckAndGrant();
				if (birthdayMessage != null) {
					model.addAttribute("message", birthdayMessage);
				}
			}
		}
		
		model.addAttribute("conveniList", list);
	}
}
