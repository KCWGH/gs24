package com.gs24.website.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gs24.website.domain.EarlyBirdCouponQueueVO;
import com.gs24.website.persistence.EarlyBirdCouponQueueMapper;

@Service
public class EarlyBirdCouponQueueServiceImple implements EarlyBirdCouponQueueService {

	@Autowired
	private EarlyBirdCouponQueueMapper earlyBirdCouponQueueMapper;

	@Override
	public int queueOne(EarlyBirdCouponQueueVO earlyBirdCouponQueueVO) {
		return earlyBirdCouponQueueMapper.insertQueue(earlyBirdCouponQueueVO);
	}

}
