package com.gs24.website.service;

import java.util.Date;
import java.util.List;

import com.gs24.website.domain.ConvenienceFoodVO;
import com.gs24.website.domain.PreorderVO;
import com.gs24.website.util.Pagination;

public interface PreorderService {
	int createPreorder(PreorderVO preorderVO);

	int createPreorderWithGiftCard(PreorderVO preorderVO, int giftCardId);

	int createPreorderWithCoupon(PreorderVO preorderVO, int couponId);

	int createPreorder(PreorderVO preorderVO, int giftCardId, int couponId);

	List<PreorderVO> getPreorderBymemberId(String memberId);

	PreorderVO getPreorderOneById(int preorderId);

	ConvenienceFoodVO getConvenienceFoodInfo(int foodId, int convenienceId);

	int updateIsPickUp(int preorderId, int isPickUp);

	int cancelPreorder(int preorderId, int foodId, int preorderAmount);

	int deletePreorder(int preorderId);

	List<PreorderVO> getPagedPreordersByMemberId(String memberId, Pagination pagination);

	int countPreorderByMemberId(String memberId);

	List<PreorderVO> getAlreadyPreorder(int foodId);

	int countRemainingPreorders(String memberId);

	List<PreorderVO> getNotPickedUpPreorder(Pagination pagination, int convenienceId);

	int getCountNotPickedUpPreorderByPagination(Pagination pagination);

	List<Integer> getPickedUpFoodIdByMemberId(String memberId);

	boolean validatePickupDate(Date pickupDate);

	boolean validatePreorderAmount(int preorderAmount, int foodAmount);

	String handlePreorderWithDiscounts(PreorderVO preorderVO, String giftCardIdString, String couponIdString);
}
