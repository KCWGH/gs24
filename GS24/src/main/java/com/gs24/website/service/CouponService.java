package com.gs24.website.service;

import java.util.List;

import com.gs24.website.domain.CouponVO;

public interface CouponService {

	int publishCoupon(CouponVO couponVO);

	List<CouponVO> getCouponList();
	
	List<CouponVO> getCouponListByFoodType(String foodType);
	
	CouponVO getCouponByCouponId(int couponId);

	int useCoupon(int couponId);

}
