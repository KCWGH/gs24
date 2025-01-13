package com.gs24.website.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import com.gs24.website.domain.GiftCardVO;
import com.gs24.website.domain.PreorderVO;
import com.gs24.website.service.CouponService;
import com.gs24.website.service.FoodService;
import com.gs24.website.service.GiftCardService;
import com.gs24.website.service.PreorderService;

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
	private FoodService foodService;

	@GetMapping("/create")
	public String createGET(Model model, int foodId, HttpSession session, RedirectAttributes redirectAttributes) {
		log.info("createGET()");
		String memberId = (String) session.getAttribute("memberId");
		FoodVO foodVO = preorderService.getFoodInfo(foodId);
		if (foodVO.getFoodStock() <= 0) {
			redirectAttributes.addFlashAttribute("message", "재고가 없어 예약할 수 없어요!");
			return "redirect:/food/list";
		}
		model.addAttribute("foodVO", foodVO);
		List<CouponVO> couponList = couponService.getCouponListByFoodType(foodVO.getFoodType());
		List<GiftCardVO> giftCardList = giftCardService.getGiftCardList(memberId);
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

			// 오늘 날짜
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);

			// 2일 후 날짜
			calendar.add(Calendar.DATE, 2);
			Date twoDaysLater = calendar.getTime();

			// 2주 후 날짜
			calendar.add(Calendar.DATE, 14);
			Date twoWeeksLater = calendar.getTime();

			if (pickupDate.before(twoDaysLater) || pickupDate.after(twoWeeksLater)) {
				throw new Exception("예약 날짜는 오늘을 기준으로 2일 후에서 2주 사이여야 합니다.");
			}

			int foodStock = foodService.getFoodById(preorderVO.getFoodId()).getFoodStock();
			if (preorderVO.getPreorderAmount() < 1 || foodStock < preorderVO.getPreorderAmount()) {
				throw new Exception("예약 수량은 1보다 작거나 총 재고량보다 많을 수 없습니다.");
			}
			preorderVO.setPickupDate(pickupDate);

			int giftCardId;
			int couponId;

			boolean useGiftCard = !giftCardIdString.equals("");
			boolean useCoupon = !couponIdString.equals("");

			if (useGiftCard && useCoupon) { // 둘 다 사용했다면
				log.info("둘다씀");
				giftCardId = Integer.parseInt(giftCardIdString);
				couponId = Integer.parseInt(couponIdString);
				GiftCardVO giftCardVO = giftCardService.getGiftCardDetail(giftCardId);
				CouponVO couponVO = couponService.getCouponByCouponId(couponId);
				if (giftCardVO == null || couponVO == null) {
					throw new Exception("존재하지 않는 기프트카드 ID 또는 쿠폰 ID입니다.");
				} else {
					int result = preorderService.createPreorder(preorderVO, giftCardId, couponId);
					if (result == 1) {
						log.info("createPOST()");
						redirectAttributes.addFlashAttribute("message", "기프트카드, 쿠폰을 적용해 예약했습니다.");
					} else {
						redirectAttributes.addFlashAttribute("message", "음식 재고 또는 쿠폰 수량이 부족합니다. 예약이 실패했습니다.");
					}
				}
			} else if (useGiftCard) { // 기프트카드만 사용했다면
				log.info("깊카만씀");
				giftCardId = Integer.parseInt(giftCardIdString);
				GiftCardVO giftCardVO = giftCardService.getGiftCardDetail(giftCardId);
				if (giftCardVO == null) {
					throw new Exception("존재하지 않는 기프트카드 ID입니다.");
				} else {
					int result = preorderService.createPreorderWithGiftCard(preorderVO, giftCardId);
					if (result == 1) {
						log.info("createPOST()");
						redirectAttributes.addFlashAttribute("message", "기프트카드를 사용해 예약했습니다.");
					} else {
						redirectAttributes.addFlashAttribute("message", "음식 재고가 부족합니다. 예약이 실패했습니다.");
					}
				}
			} else if (useCoupon) { // 쿠폰만 사용했다면
				log.info("쿠폰만씀");
				couponId = Integer.parseInt(couponIdString);
				CouponVO couponVO = couponService.getCouponByCouponId(couponId);
				if (couponVO == null) {
					throw new Exception("존재하지 않는 쿠폰 ID입니다.");
				} else {
					int result = preorderService.createPreorder(preorderVO, couponId);
					if (result == 1) {
						log.info("createPOST()");
						redirectAttributes.addFlashAttribute("message", "쿠폰을 사용해 예약했습니다.");
					} else {
						redirectAttributes.addFlashAttribute("message", "음식 재고 또는 쿠폰 수량이 부족합니다. 예약이 실패했습니다.");
					}
				}
			} else { // 모두 사용하지 않았다면
				log.info("모두안씀");
				int result = preorderService.createPreorder(preorderVO);
				if (result == 1) {
					log.info("createPOST()");
					redirectAttributes.addFlashAttribute("message", "예약에 성공했습니다.");
				} else {
					redirectAttributes.addFlashAttribute("message", "음식 재고가 부족합니다. 예약이 실패했습니다.");
				}
			}

			return "redirect:/food/list";

		} catch (Exception e) {
			log.error(e.getMessage());
			redirectAttributes.addFlashAttribute("message", "잘못된 값이 입력되었습니다. 예약이 실패했습니다.");
			return "redirect:/food/list"; // 기타 예외
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
