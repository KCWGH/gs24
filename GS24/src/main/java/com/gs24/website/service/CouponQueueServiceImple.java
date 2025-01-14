package com.gs24.website.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gs24.website.domain.CouponQueueVO;
import com.gs24.website.persistence.CouponQueueMapper;

@Service
public class CouponQueueServiceImple implements CouponQueueService {

	@Autowired
	private CouponQueueMapper couponQueueMapper;

	@Override
	public int queueOne(CouponQueueVO couponQueueVO) {
		return couponQueueMapper.insertQueue(couponQueueVO);
	}

}
