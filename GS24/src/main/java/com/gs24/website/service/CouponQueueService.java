package com.gs24.website.service;

import com.gs24.website.domain.CouponQueueVO;

public interface CouponQueueService {

	int queueOne(CouponQueueVO couponQueueVO);
	
	int dupCheckQueueByMemberId(int couponId, String memberId, int foodId);
}
