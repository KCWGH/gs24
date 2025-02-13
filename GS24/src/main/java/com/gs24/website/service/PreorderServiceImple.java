package com.gs24.website.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gs24.website.domain.ConvenienceFoodVO;
import com.gs24.website.domain.CouponQueueVO;
import com.gs24.website.domain.CouponVO;
import com.gs24.website.domain.PreorderVO;
import com.gs24.website.persistence.ConvenienceFoodMapper;
import com.gs24.website.persistence.CouponMapper;
import com.gs24.website.persistence.CouponQueueMapper;
import com.gs24.website.persistence.GiftCardMapper;
import com.gs24.website.persistence.MembershipMapper;
import com.gs24.website.persistence.PreorderMapper;
import com.gs24.website.util.Pagination;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class PreorderServiceImple implements PreorderService {

	@Autowired
	private PreorderMapper preorderMapper;

	@Autowired
	private ConvenienceFoodMapper convenienceFoodMapper;

	@Autowired
	private GiftCardMapper giftCardMapper;

	@Autowired
	private CouponMapper couponMapper;

	@Autowired
	private CouponQueueMapper couponQueueMapper;

	@Autowired
	private MembershipMapper membershipMapper;

	@Override
	public int createPreorder(PreorderVO preorderVO) {
		log.info("createPreorder()");
		int foodAmount = convenienceFoodMapper
				.selectDetailConvenienceFoodByFoodId(preorderVO.getFoodId(), preorderVO.getConvenienceId())
				.getFoodAmount();
		int preorderAmount = preorderVO.getPreorderAmount();
		if (foodAmount > 0 && foodAmount >= preorderAmount) {
			System.out.println(preorderVO.getFoodId());
			System.out.println(preorderAmount);
			System.out.println(preorderVO.getConvenienceId());
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
		int createResult = createPreorder(preorderVO);
		int result = 0;
		if (createResult == 1) {
			result = giftCardMapper.useGiftCard(giftCardId, preorderVO.getPreorderId());
		}
		return result;
	}

	@Override
	public int createPreorder(PreorderVO preorderVO, int couponId) {
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
		int createResult = createPreorder(preorderVO, couponId);
		int result = 0;
		if (createResult == 1) {
			result = giftCardMapper.useGiftCard(giftCardId, preorderVO.getPreorderId());
		}
		return result;
	}

	@Override
	public List<PreorderVO> getPreorderBymemberId(String memberId) {
		log.info("getPreorderByMemberId()");
		List<PreorderVO> list = preorderMapper.selectPreoderByMemberId(memberId);
		log.info(list);
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
	public int cancelPreorder(int preorderId, int foodId, int preorderAmount) {
		log.info("cancelPreorder()");
		PreorderVO preorderVO = preorderMapper.selectPreorderOneById(preorderId);
		convenienceFoodMapper.updateFoodAmountByPreorder(preorderVO.getFoodId(), preorderAmount * -1,
				preorderVO.getConvenienceId());
		if (preorderVO.getAppliedGiftCardId() != 0 && preorderVO.getAppliedCouponId() != 0) { // 기프트카드, 쿠폰 둘 다 적용
			giftCardMapper.refundGiftCard(preorderVO.getAppliedGiftCardId(), preorderId);
			couponMapper.refundCoupon(preorderVO.getAppliedCouponId());
			couponQueueMapper.deleteQueue(preorderVO.getAppliedCouponId(), preorderVO.getMemberId());
		} else if (preorderVO.getAppliedGiftCardId() != 0 && preorderVO.getAppliedCouponId() == 0) { // 기프트카드만 적용
			giftCardMapper.refundGiftCard(preorderVO.getAppliedGiftCardId(), preorderId);
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
		return preorderMapper.selectPreorderBymemberIdPagination(pagination);
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
		List<PreorderVO> list = preorderMapper.selectNotPickUpPreorder(pagination.getKeyword(),pagination.getSortType(), convenienceId);
		return list;
	}

	@Override
	public int getCountNotPickedUpPreorderByPagination(Pagination pagination) {
		log.info("getCountNotPickUpPreorderByPagination()");
		int result = preorderMapper.countNotPickUpPreorderByPagination(pagination);
		return result;
	}

	@Override
	public List<Integer> getPickedUpFoodIdByMemberId(String memberId) {
		log.info("getPickedUpFoodIDByMemberId()");
		List<Integer> list = preorderMapper.selectPickedUpFoodIdByMemberId(memberId);
		return list;
	}

}
