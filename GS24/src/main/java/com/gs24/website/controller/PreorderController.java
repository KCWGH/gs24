package com.gs24.website.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gs24.website.domain.ConvenienceFoodVO;
import com.gs24.website.domain.CouponVO;
import com.gs24.website.domain.GiftCardVO;
import com.gs24.website.domain.PreorderVO;
import com.gs24.website.service.ConvenienceFoodService;
import com.gs24.website.service.CouponService;
import com.gs24.website.service.GiftCardService;
import com.gs24.website.service.PreorderService;
import com.gs24.website.util.PageMaker;
import com.gs24.website.util.Pagination;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/preorder")
@Log4j
public class PreorderController {

	@Autowired
	private PreorderService preorderService;

	@Autowired
	private GiftCardService giftCardService;

	@Autowired
	private CouponService couponService;

	@Autowired
	private ConvenienceFoodService convenienceFoodService;

	@GetMapping("/create")
	public String createGET(Model model, int convenienceId, int foodId, Authentication auth,
			RedirectAttributes redirectAttributes) {
		log.info("createGET()");
		String memberId = auth.getName();

		model.addAttribute("memberId", memberId);

		ConvenienceFoodVO convenienceDetailFoodVO = convenienceFoodService.getConvenienceFoodByFoodId(foodId,
				convenienceId);
		if (convenienceDetailFoodVO.getFoodAmount() <= 0) {
			redirectAttributes.addFlashAttribute("message", "재고가 없어 예약할 수 없어요!");
			return "redirect:/convenienceFood/list?convenienceId=" + convenienceDetailFoodVO.getConvenienceId();
		}
		model.addAttribute("foodVO", convenienceDetailFoodVO);
		List<CouponVO> couponList = couponService.getCouponListByFoodType(convenienceDetailFoodVO.getFoodType());
		List<GiftCardVO> giftCardList = giftCardService.getGiftCardListByFoodType(memberId,
				convenienceDetailFoodVO.getFoodType());
		model.addAttribute("couponList", couponList);
		model.addAttribute("giftCardList", giftCardList);
		log.info("getCouponList()");
		log.info("getGiftCardList()");
		return "/preorder/create";
	}

	@PostMapping("/create")
	public String createPOST(PreorderVO preorderVO, @RequestParam("pickupDate") String pickupDateString,
			@RequestParam("giftCardId") String giftCardIdString, @RequestParam("couponId") String couponIdString,
			RedirectAttributes redirectAttributes) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date pickupDate = sdf.parse(pickupDateString);
			boolean isValidDate = preorderService.validatePickupDate(pickupDate);
			if (!isValidDate) {
				throw new Exception("예약 날짜는 오늘을 기준으로 2일 후에서 2주 사이여야 합니다.");
			}

			int foodAmount = convenienceFoodService
					.getDetailConvenienceFoodByFoodId(preorderVO.getFoodId(), preorderVO.getConvenienceId())
					.getFoodAmount();
			boolean isValidAmount = preorderService.validatePreorderAmount(preorderVO.getPreorderAmount(), foodAmount);
			if (!isValidAmount) {
				throw new Exception("예약 수량은 1보다 작거나 총 재고량보다 많을 수 없습니다.");
			}

			preorderVO.setPickupDate(pickupDate);

			String message = preorderService.handlePreorderWithDiscounts(preorderVO, giftCardIdString, couponIdString);

			redirectAttributes.addFlashAttribute("message", message);
			return "redirect:/convenienceFood/list?convenienceId=" + preorderVO.getConvenienceId();

		} catch (Exception e) {
			log.error(e.getMessage());
			redirectAttributes.addFlashAttribute("message",
					"잘못된 값이 입력되었거나, 이 품목에 이미 사용한 쿠폰입니다.\\n다른 음식을 선택하거나 다른 쿠폰을 선택해 주세요.\\n예약이 실패했습니다.");
			return "redirect:/convenienceFood/list?convenienceId=" + preorderVO.getConvenienceId();
		}
	}

	@GetMapping("/list")
	public void listGET(Authentication auth, Model model) {
		log.info("listGET");

		String memberId = auth.getName();

		model.addAttribute("memberId", memberId);
	}

	@GetMapping("/update")
	public void updateGET(Model model, Pagination pagination, int convenienceId) {
		log.info("updateGET()");

		PageMaker pageMaker = new PageMaker();
		pageMaker.setPagination(pagination);
		pageMaker.setTotalCount(preorderService.getCountNotPickedUpPreorderByPagination(pagination));

		List<PreorderVO> list = preorderService.getNotPickedUpPreorder(pagination, convenienceId);

		log.info(list);

		model.addAttribute("convenienceId", convenienceId);
		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("preorderList", list);
	}
}
