package com.gs24.website.service;

import java.util.List;

import com.gs24.website.domain.FoodVO;
import com.gs24.website.domain.PreorderVO;

public interface PreorderService {
	int createPreorder(PreorderVO preorderVO);
	
	List<PreorderVO> getPreorderBymemberId(String memberId);
	
	PreorderVO getPreorderOneById(int preorderId);
	
	FoodVO getFoodInfo(int foodId);
	
	int updateIsPickUp(int preorderId, int isPickUp);
	
	int updateIsExpiredOrder(int preorderId, int isExpiredOrder, PreorderVO preorderVO);
	
	int deletePreorder(int preorderId, int foodId, int preorderAmount);
	
	int deleteOnlyPreoder(int preorderId);
}
