package com.gs24.website.service;

import java.util.List;

import com.gs24.website.domain.EarlyBirdCouponVO;

public interface EarlyBirdCouponService {

	int publishCoupon(EarlyBirdCouponVO earlyBirdCouponVO);

	List<EarlyBirdCouponVO> getEarlyBirdCouponList();
	
	List<EarlyBirdCouponVO> getEarlyBirdCouponListByFoodType(String foodType);

	int useCoupon(int earlyBirdCouponId);

}
