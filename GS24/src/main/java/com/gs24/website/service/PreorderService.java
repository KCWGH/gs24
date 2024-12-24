package com.gs24.website.service;

import java.util.List;

import com.gs24.website.domain.PreorderVO;

public interface PreorderService {
	int createPreorder(PreorderVO preorderVO);
	
	List<PreorderVO> getPreorderBymemberId(String memberId);
	
	int updateIsPickUp(int preorderId, int isPickUp);
	
	int updateIsExpiredOrder(int preorderId, int isExpiredOrder);
	
	int deletePreorder(int preorderId);
}
