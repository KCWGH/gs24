package com.gs24.website.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gs24.website.domain.ConvenienceVO;
import com.gs24.website.service.ConvenienceService;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/convenience")
@Log4j
public class ConvenienceController {
	
	@Autowired
	private ConvenienceService convenienceService;
	
	@GetMapping("/list")
	public void listGET(Model model) {
		log.info("listGET()");
		
		List<ConvenienceVO> list = convenienceService.getAllConvenience();
		
		model.addAttribute("conveniList", list);
	}
}
