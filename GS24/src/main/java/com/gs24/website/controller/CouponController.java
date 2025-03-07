package com.gs24.website.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gs24.website.domain.CouponVO;
import com.gs24.website.service.ConvenienceFoodService;
import com.gs24.website.service.CouponService;
import com.gs24.website.util.PageMaker;
import com.gs24.website.util.Pagination;

@Controller
@RequestMapping(value = "/coupon")
public class CouponController {

	@Autowired
	private ConvenienceFoodService convenienceFoodService;

	@Autowired
	private CouponService couponService;

	@GetMapping("/list")
	public void listGET(Model model, Pagination pagination) {
		List<CouponVO> couponList = couponService.getPagedCouponList(pagination);
		PageMaker pageMaker = new PageMaker();
		pageMaker.setPagination(pagination);
		pageMaker.setTotalCount(couponService.getTotalCount());
		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("couponList", couponList);
	}

	@GetMapping("/publish")
	public void publishGET(Model model) {
		List<String> foodType = convenienceFoodService.getFoodTypeList();
		model.addAttribute("foodTypeList", foodType);
	}

	@PostMapping("/publish")
	public String publishPOST(@ModelAttribute CouponVO couponVO, Model model, RedirectAttributes redirectAttributes) {

		int result = couponService.validateAndPublishCoupon(couponVO);

		if (result == 1) {
			redirectAttributes.addFlashAttribute("message", "쿠폰 발행에 성공했습니다 :)");
		} else {
			String errorMessage = "";
			switch (result) {
			case -1:
				errorMessage = "정액권인 경우 최솟값은 1000원입니다.";
				break;
			case -2:
				errorMessage = "비율권인 경우 최솟값은 5%입니다.";
				break;
			default:
				errorMessage = "쿠폰 발행에 실패했습니다. 다시 시도해 주세요.";
				break;
			}
			redirectAttributes.addFlashAttribute("message", errorMessage);
		}

		return "redirect:/coupon/publish";
	}

	@GetMapping("/modify")
	public void modifyGET(int couponId, Model model) {
		List<String> foodType = convenienceFoodService.getFoodTypeList();
		CouponVO couponVO = couponService.getCouponByCouponId(couponId);
		model.addAttribute("couponVO", couponVO);
		model.addAttribute("foodTypeList", foodType);
	}

	@PostMapping("/modify")
	public String modifyPOST(@ModelAttribute CouponVO couponVO, Model model, RedirectAttributes redirectAttributes) {
		int result = couponService.modifyCoupon(couponVO);
		if (result == 1) {
			redirectAttributes.addFlashAttribute("message", "쿠폰 수정에 성공했습니다 :)");
		} else {
			redirectAttributes.addFlashAttribute("message", "쿠폰 수정에 실패했습니다. 다시 시도해 주세요.");
		}

		return "redirect:/coupon/modify?couponId=" + couponVO.getCouponId();
	}

	@PostMapping("/delete")
	@ResponseBody
	public int deletePOST(int couponId) {
		return couponService.deleteCoupon(couponId);
	}

}
