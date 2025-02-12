package com.gs24.website.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import com.gs24.website.service.CouponQueueService;
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
	private CouponQueueService couponQueueService;

	@Autowired
	private ConvenienceFoodService convenienceFoodService;

	@GetMapping("/create")
	public String createGET(Model model, int convenienceId, int foodId, Authentication auth,
			RedirectAttributes redirectAttributes) {
		log.info("createGET()");
		log.info("mypageGET()");
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
			int foodAmount = convenienceFoodService
					.getDetailConvenienceFoodByFoodId(preorderVO.getFoodId(), preorderVO.getConvenienceId())
					.getFoodAmount();
			if (preorderVO.getPreorderAmount() < 1 || foodAmount < preorderVO.getPreorderAmount()) {
				throw new Exception("예약 수량은 1보다 작거나 총 재고량보다 많을 수 없습니다.");
			}
			preorderVO.setPickupDate(pickupDate);

			int giftCardId;
			int couponId;

			boolean useGiftCard = !giftCardIdString.equals("");
			boolean useCoupon = !couponIdString.equals("");

			if (useGiftCard && useCoupon) { // 둘 다 사용했다면
				giftCardId = Integer.parseInt(giftCardIdString);
				couponId = Integer.parseInt(couponIdString);
				GiftCardVO giftCardVO = giftCardService.getGiftCardDetail(giftCardId);
				CouponVO couponVO = couponService.getCouponByCouponId(couponId);
				int dupCheck = couponQueueService.dupCheckQueueByMemberId(couponId, preorderVO.getMemberId(),
						preorderVO.getFoodId());
				if (giftCardVO == null || couponVO == null) {
					redirectAttributes.addFlashAttribute("message", "존재하지 않는 기프트카드 ID 또는 쿠폰 ID입니다.");
					throw new Exception("존재하지 않는 기프트카드 ID 또는 쿠폰 ID입니다.");
				} else if (dupCheck != 0) {
					redirectAttributes.addFlashAttribute("message", "이 품목에 이미 사용한 쿠폰입니다. 다른 음식을 선택하거나 다른 쿠폰을 선택해 주세요.");
					throw new Exception("이 품목에 이미 사용한 쿠폰입니다. 다른 음식을 선택하거나 다른 쿠폰을 선택해 주세요.");
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
				couponId = Integer.parseInt(couponIdString);
				CouponVO couponVO = couponService.getCouponByCouponId(couponId);
				int dupCheck = couponQueueService.dupCheckQueueByMemberId(couponId, preorderVO.getMemberId(),
						preorderVO.getFoodId());
				if (couponVO == null) {
					throw new Exception("존재하지 않는 쿠폰 ID입니다.");
				} else if (dupCheck != 0) {
					redirectAttributes.addFlashAttribute("message", "이 품목에 이미 사용한 쿠폰입니다. 다른 음식을 선택하거나 다른 쿠폰을 선택해 주세요.");
					throw new Exception("이 품목에 이미 사용한 쿠폰입니다. 다른 음식을 선택하거나 다른 쿠폰을 선택해 주세요.");
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
				System.out.println("요기임");
				int result = preorderService.createPreorder(preorderVO);
				System.out.println("예약 결과 : " + result);
				if (result == 1) {
					log.info("createPOST()");
					redirectAttributes.addFlashAttribute("message", "예약에 성공했습니다.");
				} else {
					redirectAttributes.addFlashAttribute("message", "음식 재고가 부족합니다. 예약이 실패했습니다.");
				}
			}

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
		List<PreorderVO> list = preorderService.getPreorderBymemberId(memberId);

		model.addAttribute("memberId", memberId);
		//model.addAttribute("preorderList", list);
	}

	@GetMapping("/update")
	public void updateGET(Model model, Pagination pagination) {
		log.info("updateGET()");

		PageMaker pageMaker = new PageMaker();
		pageMaker.setPagination(pagination);
		pageMaker.setTotalCount(preorderService.getCountNotPickedUpPreorderByPagination(pagination));

		List<PreorderVO> list = preorderService.getNotPickedUpPreorder(pagination);

		log.info(list);

		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("preorderList", list);
	}
}
