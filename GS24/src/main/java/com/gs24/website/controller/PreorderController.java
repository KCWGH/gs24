package com.gs24.website.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gs24.website.domain.FoodVO;
import com.gs24.website.domain.PreorderVO;
import com.gs24.website.service.PreorderService;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/preorder")
@Log4j
public class PreorderController {
	
	@Autowired
	private PreorderService preorderService;
	
	@GetMapping("/register")
	public void registerGET(Model model, int foodId, HttpSession session) {
		log.info("registerGET()");
		
		FoodVO foodVO = preorderService.getFoodInfo(foodId);
		model.addAttribute("foodVO", foodVO);
	}
	
	@PostMapping("/register")
	public String registerPOST(PreorderVO preorderVO) {
		log.info("registerPOST()");
		log.info(preorderVO);
		int result = preorderService.createPreorder(preorderVO);
		log.info(result + "row insert and FOOD DB update");
		return "redirect:/food/list";
	}
	
	@GetMapping("/list")
	public void listGET(HttpSession session) {
		log.info("listGET");
		
		String memberId = (String)session.getAttribute("memberId");
		Date nowDate = new Date();
		List<PreorderVO> list = preorderService.getPreorderBymemberId(memberId);
		for(PreorderVO i : list) {
			if(nowDate.after(i.getPickupDate())) {
				log.info("예정일을 지났습니다.");
				// 0 : 아직 지나지 않음 | 1 : 예정일이 지났음
				preorderService.updateIsExpiredOrder(i.getPreorderId(), 1, i);
			} else {
				log.info("예정일이 아직 안지났습니다.");
			}
		}
	}
}
