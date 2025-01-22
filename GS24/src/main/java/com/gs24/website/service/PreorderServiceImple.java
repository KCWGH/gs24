package com.gs24.website.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gs24.website.domain.CouponQueueVO;
import com.gs24.website.domain.CouponVO;
import com.gs24.website.domain.FoodVO;
import com.gs24.website.domain.PreorderVO;
import com.gs24.website.persistence.CouponMapper;
import com.gs24.website.persistence.CouponQueueMapper;
import com.gs24.website.persistence.FoodMapper;
import com.gs24.website.persistence.GiftCardMapper;
import com.gs24.website.persistence.PreorderMapper;
import com.gs24.website.util.Pagination;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class PreorderServiceImple implements PreorderService {

	@Autowired
	private PreorderMapper preorderMapper;

	@Autowired
	private FoodMapper foodMapper;

	@Autowired
	private GiftCardMapper giftCardMapper;

	@Autowired
	private CouponMapper couponMapper;

	@Autowired
	private CouponQueueMapper couponQueueMapper;

	@Override
	@Transactional(value = "transactionManager")
	public int createPreorder(PreorderVO preorderVO) {
		log.info("createPreorder()");
		int foodAmount = foodMapper.selectFoodById(preorderVO.getFoodId()).getFoodStock();
		int preorderAmount = preorderVO.getPreorderAmount();
		if (foodAmount > 0 && foodAmount >= preorderAmount) {
			foodMapper.updateFoodAmountByPreorderAmount(preorderVO.getFoodId(), preorderAmount);
			int result = preorderMapper.insertPreorder(preorderVO);
			return result;
		}
		return 0;
	}

	@Override
	@Transactional(value = "transactionManager")
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
	@Transactional(value = "transactionManager")
	public int createPreorder(PreorderVO preorderVO, int couponId) {
		int foodAmount = foodMapper.selectFoodById(preorderVO.getFoodId()).getFoodStock();
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
	@Transactional(value = "transactionManager")
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
	@Transactional("transactionManager()")
	public FoodVO getFoodInfo(int foodId) {
		log.info("getFoodInfo()");
		return foodMapper.selectFoodById(foodId);
	}

	@Override
	public int updateIsPickUp(int preorderId, int isPickUp) {
		log.info("updatePreorderInIsPickUp()");
		int result = preorderMapper.updatePreorderInIsPickUp(preorderId, isPickUp);
		return result;

	}

	@Override
	@Transactional(value = "transactionManager")
	public int cancelPreorder(int preorderId, int foodId, int preorderAmount) {
		log.info("cancelPreorder()");
		foodMapper.updateFoodAmountByPreorderAmount(foodId, preorderAmount * -1);
		PreorderVO preorderVO = preorderMapper.selectPreorderOneById(preorderId);
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

}
