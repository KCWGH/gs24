package com.gs24.website.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gs24.website.domain.MemberVO;
import com.gs24.website.persistence.MemberMapper;

import lombok.extern.log4j.Log4j;

@Controller // @Component
@RequestMapping(value = "/food")
@Log4j
public class FoodController {
	
	@Autowired
	private MemberMapper memberMapper;

	// register.jsp
	@GetMapping("/list")
	public void listGET(HttpSession session, Model model) {
		log.info("listGET()");
		String memberId = (String) session.getAttribute("memberId");
		if (memberId != null) {
			MemberVO memberVO = memberMapper.select(memberId);
			model.addAttribute("memberVO", memberVO);
		}
	}


} // end BoardController
