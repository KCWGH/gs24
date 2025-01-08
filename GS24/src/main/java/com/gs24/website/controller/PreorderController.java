package com.gs24.website.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gs24.website.domain.EarlyBirdCouponVO;
import com.gs24.website.domain.FoodVO;
import com.gs24.website.domain.PreorderVO;
import com.gs24.website.service.EarlyBirdCouponService;
import com.gs24.website.service.PreorderService;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/preorder")
@Log4j
public class PreorderController {
	
	@Autowired
	private PreorderService preorderService;
	
	@Autowired
	private EarlyBirdCouponService earlyBirdCouponService;
	
	@GetMapping("/register")
	   public void registerGET(Model model, int foodId, HttpSession session) {
	      log.info("registerGET()");

	      FoodVO foodVO = preorderService.getFoodInfo(foodId);
	      model.addAttribute("foodVO", foodVO);

	      log.info("getEarlyBirdCouponList()");
	      List<EarlyBirdCouponVO> couponList = earlyBirdCouponService
	            .getEarlyBirdCouponListByFoodType(foodVO.getFoodType());
	      model.addAttribute("couponList", couponList);
	   }

	   @PostMapping("/register")
	   public String registerPOST(PreorderVO preorderVO, @RequestParam("pickupDate") String pickupDateString) {
	      log.info("registerPOST()");
	      log.info("PreorderVO: " + preorderVO);
	      log.info("pickupDateString:" + pickupDateString);

	      try {
	         // "yyyy-MM-dd" �������� ���� ���ڿ��� Date�� ��ȯ
	         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	         Date pickupDate = sdf.parse(pickupDateString);

	         // ��ȯ�� ��¥�� PreorderVO�� ����
	         preorderVO.setPickupDate(pickupDate);

	         // ���� ���� ����
	         int result = preorderService.createPreorder(preorderVO);
	         log.info(result + " row insert and FOOD DB update");

	      } catch (Exception e) {
	         log.error("Error while converting pickupDate: " + e.getMessage());
	         return "errorPage"; // ���� �������� ����
	      }

	      // ���� �Ϸ� �� ��� �������� �����̷�Ʈ
	      return "redirect:/food/list";
	   }
	
	@GetMapping("/list")
	public void listGET(HttpSession session) {
		log.info("listGET");
		
		String memberId = (String)session.getAttribute("memberId");
		log.info(memberId);
		Date nowDate = new Date();
		List<PreorderVO> list = preorderService.getPreorderBymemberId(memberId);
		for(PreorderVO i : list) {
			if(nowDate.after(i.getPickupDate()) && i.getIsPickUp() == 0) {
				log.info("�������� �������ϴ�.");
				// 0 : ���� ������ ���� | 1 : �������� ������
				preorderService.updateIsExpiredOrder(i.getPreorderId(), 1, i);
			} else {
				log.info("�������� ���� ���������ϴ�.");
			}
		}
	}
	
	@GetMapping("/update")
	public void updateGET(Model model, int foodId) {
		log.info("updateGET()");
		List<PreorderVO> list = preorderService.getAlreadyPreorder(foodId);
		
		model.addAttribute("preorderList", list);
	}
}
