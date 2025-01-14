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
		int createResult = createPreorder(preorderVO);
		int result = 0;
		if (createResult == 1) {
			result = giftCardMapper.useGiftCard(giftCardId, preorderVO.getPreorderId());
		}
		return result;
	}

	@Override
	@Transactional(value = "transactionManager")
	public int createPreorder(PreorderVO preorderVO, int earlyBirdCouponId) {
		int foodAmount = foodMapper.selectFoodById(preorderVO.getFoodId()).getFoodStock();
		int preorderAmount = preorderVO.getPreorderAmount();
		CouponVO earlyBirdCouponVO = couponMapper.selectCouponByCouponId(earlyBirdCouponId);
		Date earlyBirdCouponExpiredDate = earlyBirdCouponVO.getCouponExpiredDate();
		Date currentDate = new Date();

		if (foodAmount > 0 && foodAmount >= preorderAmount && earlyBirdCouponVO.getCouponAmount() > 0
				&& earlyBirdCouponExpiredDate.after(currentDate)) {
			preorderMapper.insertPreorder(preorderVO);
			foodMapper.updateFoodAmountByPreorderAmount(preorderVO.getFoodId(), preorderAmount);
			couponMapper.useCoupon(earlyBirdCouponId);
			CouponQueueVO earlyBirdCouponQueueVO = new CouponQueueVO();
			earlyBirdCouponQueueVO.setCouponId(earlyBirdCouponId);
			earlyBirdCouponQueueVO.setMemberId(preorderVO.getMemberId());
			int result = couponQueueMapper.insertQueue(earlyBirdCouponQueueVO);
			log.info("createPreorderWithCoupon()");
			return result;
		}
		return 0;
	}

	@Override
	@Transactional(value = "transactionManager")
	public int createPreorder(PreorderVO preorderVO, int giftCardId, int couponId) {
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
	public int updateIsExpiredOrder(int preorderId, int isExpiredOrder, PreorderVO preorderVO) {
		log.info("updatePreorderInIsExpiredOrder()");
		int result = preorderMapper.updatePreorderInIsExpiredOrder(preorderId, isExpiredOrder);
		int foodResult = foodMapper.updateFoodAmountByPreorderAmount(preorderVO.getFoodId(),
				preorderVO.getPreorderAmount() * -1);
		return result;
	}

	@Override
	@Transactional(value = "transactionManager")
	public int deletePreorder(int preorderId, int foodId, int preorderAmount) {
		log.info("deletePreorder()");
		int result = preorderMapper.deletePreorderByPreorderId(preorderId);
		int updateresult = foodMapper.updateFoodAmountByPreorderAmount(foodId, preorderAmount * -1);
		return result;
	}

	@Override
	public int deleteOnlyPreoder(int preorderId) {
		log.info("onlyDeletePreorder()");
		int result = preorderMapper.deletePreorderByPreorderId(preorderId);
		return result;
	}

	@Override
	public List<PreorderVO> getPagingPreordersByMemberId(String memberId, Pagination pagination) {
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

}
