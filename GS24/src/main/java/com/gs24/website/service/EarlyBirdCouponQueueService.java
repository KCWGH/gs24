package com.gs24.website.service;

import com.gs24.website.domain.EarlyBirdCouponQueueVO;

public interface EarlyBirdCouponQueueService {

	int queueOne(EarlyBirdCouponQueueVO earlyBirdCouponQueueVO);

	void setAmount(int earlyBirdCouponId, int earlyBirdCouponAmount);
}
