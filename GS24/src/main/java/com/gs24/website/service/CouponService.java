package com.gs24.website.service;

import java.util.List;

import com.gs24.website.domain.CouponVO;
import com.gs24.website.util.Pagination;

public interface CouponService {
	int validateAndPublishCoupon(CouponVO couponVO);

	int publishCoupon(CouponVO couponVO);

	List<CouponVO> getCouponList();
	
	List<CouponVO> getPagedCouponList(Pagination pagination);

	List<CouponVO> getCouponListByFoodType(String foodType);

	CouponVO getCouponByCouponId(int couponId);
	
	int getTotalCount();
	
	int modifyCoupon(CouponVO couponVO);

	int useCoupon(int couponId);
	
	int deleteCoupon(int couponId);

	void deleteExpiredCoupons();

}
