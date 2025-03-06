package com.gs24.website.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gs24.website.domain.ConvenienceFoodVO;
import com.gs24.website.domain.CouponQueueVO;
import com.gs24.website.domain.CouponVO;
import com.gs24.website.domain.GiftCardVO;
import com.gs24.website.domain.MemberVO;
import com.gs24.website.domain.PreorderVO;
import com.gs24.website.persistence.ConvenienceFoodMapper;
import com.gs24.website.persistence.CouponMapper;
import com.gs24.website.persistence.CouponQueueMapper;
import com.gs24.website.persistence.GiftCardMapper;
import com.gs24.website.persistence.MemberMapper;
import com.gs24.website.persistence.MembershipMapper;
import com.gs24.website.persistence.PreorderMapper;
import com.gs24.website.persistence.ReviewMapper;
import com.gs24.website.util.Pagination;

import lombok.extern.log4j.Log4j;

@Service
@Transactional(value = "transactionManager")
@Log4j
public class PreorderServiceImple implements PreorderService {

	@Autowired
	private PreorderMapper preorderMapper;

	@Autowired
	private ReviewMapper reviewMapper;

	@Autowired
	private ConvenienceFoodMapper convenienceFoodMapper;

	@Autowired
	private GiftCardMapper giftCardMapper;

	@Autowired
	private CouponMapper couponMapper;

	@Autowired
	private CouponQueueMapper couponQueueMapper;

	@Autowired
	private MemberMapper memberMapper;

	@Autowired
	private MembershipMapper membershipMapper;

	@Override
	public boolean validatePickupDate(Date pickupDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		Calendar minDateCal = (Calendar) calendar.clone();
		minDateCal.add(Calendar.DATE, 2);
		Date twoDaysLater = minDateCal.getTime();

		Calendar maxDateCal = (Calendar) calendar.clone();
		maxDateCal.add(Calendar.DATE, 14);
		Date twoWeeksLater = maxDateCal.getTime();

		return !pickupDate.before(twoDaysLater) && !pickupDate.after(twoWeeksLater);
	}

	@Override
	public boolean validatePreorderAmount(int preorderAmount, int foodAmount) {
		return preorderAmount >= 1 && foodAmount >= preorderAmount;
	}

	@Override
	public String handlePreorderWithDiscounts(PreorderVO preorderVO, String giftCardIdString, String couponIdString) {
		int giftCardId = 0;
		int couponId = 0;

		boolean useGiftCard = !giftCardIdString.isEmpty();
		boolean useCoupon = !couponIdString.isEmpty();

		if (useGiftCard && useCoupon) { // 둘 다 사용했다면
			giftCardId = Integer.parseInt(giftCardIdString);
			couponId = Integer.parseInt(couponIdString);
			GiftCardVO giftCardVO = giftCardMapper.selectDetailByGiftCardId(giftCardId);
			CouponVO couponVO = couponMapper.selectCouponByCouponId(couponId);

			int dupCheck = couponQueueMapper.dupCheckQueueByMemberId(couponId, preorderVO.getMemberId(),
					preorderVO.getFoodId());
			if (giftCardVO == null || couponVO == null) {
				return "존재하지 않는 기프트카드 ID 또는 쿠폰 ID입니다.";
			} else if (dupCheck != 0) {
				return "이 품목에 이미 사용한 쿠폰입니다. 다른 식품을 선택하거나 다른 쿠폰을 선택해 주세요.";
			} else {
				int result = createPreorder(preorderVO, giftCardId, couponId);
				return result == 1 ? "기프트카드, 쿠폰을 적용해 예약했습니다." : "식품 재고 또는 쿠폰 수량이 부족합니다. 예약이 실패했습니다.";
			}
		} else if (useGiftCard) { // 기프트카드만 사용했다면
			giftCardId = Integer.parseInt(giftCardIdString);
			GiftCardVO giftCardVO = giftCardMapper.selectDetailByGiftCardId(giftCardId);
			if (giftCardVO == null) {
				return "존재하지 않는 기프트카드 ID입니다.";
			} else {
				int result = createPreorderWithGiftCard(preorderVO, giftCardId);
				return result == 1 ? "기프트카드를 사용해 예약했습니다." : "식품 재고가 부족합니다. 예약이 실패했습니다.";
			}
		} else if (useCoupon) { // 쿠폰만 사용했다면
			preorderVO.setRefundVal(0);
			couponId = Integer.parseInt(couponIdString);
			CouponVO couponVO = couponMapper.selectCouponByCouponId(couponId);
			int dupCheck = couponQueueMapper.dupCheckQueueByMemberId(couponId, preorderVO.getMemberId(),
					preorderVO.getFoodId());
			if (couponVO == null) {
				return "존재하지 않는 쿠폰 ID입니다.";
			} else if (dupCheck != 0) {
				return "이 품목에 이미 사용한 쿠폰입니다. 다른 식품을 선택하거나 다른 쿠폰을 선택해 주세요.";
			} else {
				int result = createPreorderWithCoupon(preorderVO, couponId);
				return result == 1 ? "쿠폰을 사용해 예약했습니다." : "식품 재고 또는 쿠폰 수량이 부족합니다. 예약이 실패했습니다.";
			}
		} else { // 기프트카드와 쿠폰을 모두 사용하지 않았다면
			preorderVO.setRefundVal(0);
			int result = createPreorder(preorderVO);
			return result == 1 ? "예약에 성공했습니다." : "식품 재고가 부족합니다. 예약이 실패했습니다.";
		}
	}

	@Override
	public int createPreorder(PreorderVO preorderVO) {
		int foodAmount = convenienceFoodMapper
				.selectDetailConvenienceFoodByFoodId(preorderVO.getFoodId(), preorderVO.getConvenienceId())
				.getFoodAmount();
		int preorderAmount = preorderVO.getPreorderAmount();
		if (foodAmount > 0 && foodAmount >= preorderAmount) {
			convenienceFoodMapper.updateFoodAmountByPreorder(preorderVO.getFoodId(), preorderAmount,
					preorderVO.getConvenienceId());
			int result = preorderMapper.insertPreorder(preorderVO);
			return result;
		}
		return 0;
	}

	@Override
	public int createPreorderWithGiftCard(PreorderVO preorderVO, int giftCardId) {
		preorderVO.setAppliedGiftCardId(giftCardId);
		createPreorder(preorderVO);
		int result = giftCardMapper.useGiftCard(giftCardId, preorderVO.getRefundVal());
		return result;
	}

	@Override
	public int createPreorderWithCoupon(PreorderVO preorderVO, int couponId) {
		int foodAmount = convenienceFoodMapper
				.selectDetailConvenienceFoodByFoodId(preorderVO.getFoodId(), preorderVO.getConvenienceId())
				.getFoodAmount();
		int preorderAmount = preorderVO.getPreorderAmount();
		CouponVO couponVO = couponMapper.selectCouponByCouponId(couponId);
		Date couponExpiredDate = couponVO.getCouponExpiredDate();
		Date currentDate = new Date();

		if (foodAmount > 0 && foodAmount >= preorderAmount && couponVO.getCouponAmount() > 0
				&& couponExpiredDate.after(currentDate)) {
			CouponQueueVO couponQueueVO = new CouponQueueVO();
			couponQueueVO.setCouponId(couponId);
			couponQueueVO.setMemberId(preorderVO.getMemberId());
			couponQueueVO.setFoodId(preorderVO.getFoodId());
			int queueResult = couponQueueMapper.insertQueue(couponQueueVO);
			if (queueResult == 1) {
				int useResult = couponMapper.useCoupon(couponId);
				if (useResult == 1) {
					preorderVO.setAppliedCouponId(couponId);
					int createResult = createPreorder(preorderVO);
					return createResult;
				}
			}
		}
		return 0;
	}

	@Override
	public int createPreorder(PreorderVO preorderVO, int giftCardId, int couponId) {
		preorderVO.setAppliedGiftCardId(giftCardId);
		preorderVO.setAppliedCouponId(couponId);
		int createResult = createPreorderWithCoupon(preorderVO, couponId);
		int result = 0;
		if (createResult == 1) {
			result = giftCardMapper.useGiftCard(giftCardId, preorderVO.getRefundVal());
		}
		return result;
	}

	@Override
	public List<PreorderVO> getPreorderByMemberId(String memberId) {
		log.info("getPreorderByMemberId()");
		List<PreorderVO> list = preorderMapper.selectPreoderByMemberId(memberId);
		return list;
	}

	@Override
	public PreorderVO getPreorderOneById(int preorderId) {
		log.info("getPreorderOneById()");
		PreorderVO preorderVO = preorderMapper.selectPreorderOneById(preorderId);
		return preorderVO;
	}

	@Override
	public ConvenienceFoodVO getConvenienceFoodInfo(int foodId, int convenienceId) {
		log.info("getConvenienceFoodInfo()");
		return convenienceFoodMapper.selectConvenienceFoodByFoodIdAndConvenienceId(foodId, convenienceId);
	}

	@Override
	public int updateIsPickUp(int preorderId, int isPickUp) {
		log.info("updatePreorderInIsPickUp()");
		int result = preorderMapper.updatePreorderInIsPickUp(preorderId, isPickUp);
		if (result == 1) {
			PreorderVO preorderVO = preorderMapper.selectPreorderOneById(preorderId);
			membershipMapper.addSpentAmount(preorderVO.getTotalPrice(), preorderVO.getMemberId());
		}
		return result;
	}

	@Override
	public int cancelPreorder(int preorderId, int foodId, int preorderAmount, int refundVal) {
		log.info("cancelPreorder()");
		PreorderVO preorderVO = preorderMapper.selectPreorderOneById(preorderId);
		convenienceFoodMapper.updateFoodAmountByPreorder(preorderVO.getFoodId(), preorderAmount * -1,
				preorderVO.getConvenienceId());
		if (preorderVO.getAppliedGiftCardId() != 0 && preorderVO.getAppliedCouponId() != 0) { // 기프트카드, 쿠폰 둘 다 적용
			giftCardMapper.refundGiftCard(preorderVO.getAppliedGiftCardId(), refundVal);
			couponMapper.refundCoupon(preorderVO.getAppliedCouponId());
			couponQueueMapper.deleteQueue(preorderVO.getAppliedCouponId(), preorderVO.getMemberId());
		} else if (preorderVO.getAppliedGiftCardId() != 0 && preorderVO.getAppliedCouponId() == 0) { // 기프트카드만 적용
			giftCardMapper.refundGiftCard(preorderVO.getAppliedGiftCardId(), refundVal);
		} else if (preorderVO.getAppliedGiftCardId() == 0 && preorderVO.getAppliedCouponId() != 0) { // 쿠폰만 적용
			couponMapper.refundCoupon(preorderVO.getAppliedCouponId());
			couponQueueMapper.deleteQueue(preorderVO.getAppliedCouponId(), preorderVO.getMemberId());
		}
		int result = preorderMapper.updatePreorderInIsExpiredOrder(preorderId, 1);
		return result;
	}

	@Override
	public int deletePreorder(int preorderId) {
		log.info("deletePreorder()");
		int result = preorderMapper.deletePreorderByPreorderId(preorderId);
		return result;
	}

	@Override
	public List<PreorderVO> getPagedPreordersByMemberId(String memberId, Pagination pagination) {
		MemberVO memberVO = memberMapper.selectMemberByMemberId(memberId);
		pagination.setMemberVO(memberVO);
		pagination.setPageSize(10);
		return preorderMapper.selectPagedPreordersByMemberId(pagination);
	}

	@Override
	public int countPreorderByMemberId(String memberId) {
		return preorderMapper.countPreorderByMemberId(memberId);
	}

	@Override
	public List<PreorderVO> getAlreadyPreorder(int foodId) {
		log.info("getAlreadyPreorder()");
		List<PreorderVO> list = preorderMapper.selectAlreadyPreorderByFoodId(foodId);
		return list;
	}

	@Override
	public int countRemainingPreorders(String memberId) {
		return preorderMapper.countNotPickedUpPreorderByMemberId(memberId);
	}

	@Override
	public List<PreorderVO> getNotPickedUpPreorder(Pagination pagination, int convenienceId) {
		log.info("getNotPickUpPreorder()");
		List<PreorderVO> list = preorderMapper.selectNotPickUpPreorder(pagination.getKeyword(),
				pagination.getSortType(), convenienceId);
		return list;
	}

	@Override
	public int countTotalNotPickedUpPreordersByConvenienceId(int convenienceId) {
		int result = preorderMapper.countNotPickUpPreordersByConvenienceId(convenienceId);
		return result;
	}

	@Override
	public boolean getPickedUpFoodIdByMemberId(String memberId, int foodId) {
		log.info("getPickedUpFoodIDByMemberId()");
		List<Integer> list = preorderMapper.selectPickedUpFoodIdByMemberId(memberId);
		if (list.contains(foodId)) {
			if (reviewMapper.hasReview(memberId, foodId) == 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	@Override
	public int updateShowStatus(int preorderId) {
		log.info("upateShowStatus");
		return preorderMapper.updateShowStatus(preorderId);
	}

}
