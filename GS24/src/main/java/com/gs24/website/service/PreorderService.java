package com.gs24.website.service;

import java.util.List;

import com.gs24.website.domain.FoodVO;
import com.gs24.website.domain.PreorderVO;
import com.gs24.website.util.Pagination;

public interface PreorderService {
	int createPreorder(PreorderVO preorderVO);

	int createPreorder(PreorderVO preorderVO, int earlyBirdCouponId);

	List<PreorderVO> getPreorderBymemberId(String memberId);

	PreorderVO getPreorderOneById(int preorderId);

	FoodVO getFoodInfo(int foodId);

	int updateIsPickUp(int preorderId, int isPickUp);

	int updateIsExpiredOrder(int preorderId, int isExpiredOrder, PreorderVO preorderVO);

	int deletePreorder(int preorderId, int foodId, int preorderAmount);

	int deleteOnlyPreoder(int preorderId);

	List<PreorderVO> getPagingPreordersByMemberId(String memberId, Pagination pagination);

	int countPreorderByMemberId(String memberId);

	List<PreorderVO> getAlreadyPreorder(int foodId);
}
