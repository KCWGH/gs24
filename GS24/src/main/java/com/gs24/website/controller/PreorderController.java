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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

	@GetMapping("/create")
	public void createGET(Model model, int foodId, HttpSession session) {
		log.info("createGET()");

		FoodVO foodVO = preorderService.getFoodInfo(foodId);
		model.addAttribute("foodVO", foodVO);

		log.info("getEarlyBirdCouponList()");
		List<EarlyBirdCouponVO> couponList = earlyBirdCouponService
				.getEarlyBirdCouponListByFoodType(foodVO.getFoodType());
		model.addAttribute("couponList", couponList);
	}

	@PostMapping("/create")
	public String createPOST(PreorderVO preorderVO, @RequestParam("pickupDate") String pickupDateString,
			@RequestParam("earlyBirdCouponId") String earlyBirdCouponIdString, RedirectAttributes redirectAttributes) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date pickupDate = sdf.parse(pickupDateString);

			preorderVO.setPickupDate(pickupDate);
			int earlyBirdCouponId;
			int result;
			if (!earlyBirdCouponIdString.equals("")) { // 선착순 쿠폰을 선택했다면
				earlyBirdCouponId = Integer.parseInt(earlyBirdCouponIdString);
				result = preorderService.createPreorder(preorderVO, earlyBirdCouponId);
				if (result == 1) { // 선착순 쿠폰 사용이 성공했다면
					log.info("createPOST()");
					redirectAttributes.addFlashAttribute("message", "선착순 쿠폰을 사용하여 예약이 완료되었습니다.");
				} else {
					log.info("createPOST()");
					redirectAttributes.addFlashAttribute("message", "음식 재고 또는 선착순 쿠폰 수량이 부족합니다. 예약이 실패했습니다.");
				}
			} else { // 선착순 쿠폰을 선택하지 않았다면
				log.info("createPOST()");
				result = preorderService.createPreorder(preorderVO);
				if (result == 1) {
					redirectAttributes.addFlashAttribute("message", "예약이 완료되었습니다.");
				} else {
					redirectAttributes.addFlashAttribute("message", "재고가 부족합니다. 예약이 실패했습니다.");
				}
			}
			return "redirect:/food/list";

		} catch (Exception e) {
			log.error(e.getMessage());
			return "errorPage";
		}

	}

	@GetMapping("/list")
	public void listGET(HttpSession session, Model model) {
		log.info("listGET");

		String memberId = (String) session.getAttribute("memberId");
		Date nowDate = new Date();
		List<PreorderVO> list = preorderService.getPreorderBymemberId(memberId);
		for (PreorderVO i : list) {
			if (nowDate.after(i.getPickupDate())) {
				log.info("�������� �������ϴ�.");
				// 0 : ���� ������ ���� | 1 : �������� ������
				preorderService.updateIsExpiredOrder(i.getPreorderId(), 1, i);
			} else {
				log.info("�������� ���� ���������ϴ�.");
			}
		}

	}
}
