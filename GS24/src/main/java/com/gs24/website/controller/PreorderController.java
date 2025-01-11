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

import com.gs24.website.domain.CouponVO;
import com.gs24.website.domain.FoodVO;
import com.gs24.website.domain.PreorderVO;
import com.gs24.website.service.CouponService;
import com.gs24.website.service.PreorderService;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/preorder")
@Log4j
public class PreorderController {

	@Autowired
	private PreorderService preorderService;

	@Autowired
	private CouponService couponService;

	@GetMapping("/create")
	public String createGET(Model model, int foodId, HttpSession session, RedirectAttributes redirectAttributes) {
		log.info("createGET()");
		FoodVO foodVO = preorderService.getFoodInfo(foodId);
		if (foodVO.getFoodStock() <= 0) {
			redirectAttributes.addFlashAttribute("message", "재고가 없어 예약할 수 없어요!");
			return "redirect:/food/list";
		}
		model.addAttribute("foodVO", foodVO);
		List<CouponVO> couponList = couponService.getCouponListByFoodType(foodVO.getFoodType());
		model.addAttribute("couponList", couponList);
		log.info("getCouponList()");
		return "/preorder/create";
	}

	@PostMapping("/create")
	public String createPOST(PreorderVO preorderVO, @RequestParam("pickupDate") String pickupDateString,
			@RequestParam("couponId") String couponIdString, RedirectAttributes redirectAttributes) {
		try {
			log.info("1:" + pickupDateString);
			log.info("2:" + couponIdString);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date pickupDate = sdf.parse(pickupDateString);

			preorderVO.setPickupDate(pickupDate);
			int couponId;
			int result;
			if (!couponIdString.equals("")) { // 쿠폰을 선택했다면
				couponId = Integer.parseInt(couponIdString);
				result = preorderService.createPreorder(preorderVO, couponId);
				if (result == 1) { // 쿠폰 사용이 성공했다면
					log.info("createPOST()");
					redirectAttributes.addFlashAttribute("message", "쿠폰을 적용해 예약했습니다.");
				} else {
					log.info("createPOST()");
					redirectAttributes.addFlashAttribute("message", "음식 재고 또는 쿠폰 수량이 부족합니다. 예약이 실패했습니다.");
				}
			} else { // 쿠폰을 선택하지 않았다면
				log.info("createPOST()");
				result = preorderService.createPreorder(preorderVO);
				if (result == 1) {
					redirectAttributes.addFlashAttribute("message", "예약이 완료되었습니다.");
				} else {
					redirectAttributes.addFlashAttribute("message", "음식 재고가 부족합니다. 예약이 실패했습니다.");
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
