package com.gs24.website.service;

import java.util.List;

import com.gs24.website.domain.CouponVO;
import com.gs24.website.util.Pagination;

public interface CouponService {

	int grantCoupon(CouponVO couponVO);

	List<CouponVO> getCouponList(String memberId);

	CouponVO getCouponDetail(int couponId);
	
	int birthdayCouponDupCheck(String memberId);

	int dupCheckCouponNameAndMemberId(String couponName, String memberId);

	List<CouponVO> getPagedAllCoupons(String memberId, Pagination pagination);

	List<CouponVO> getPagedAvailableCoupons(String memberId, Pagination pagination);

	List<CouponVO> getPagedExpiredCoupons(String memberId, Pagination pagination);

	List<CouponVO> getPagedUsedCoupons(String memberId, Pagination pagination);

	int getAllCount(String memberId);

	int getAvailableCount(String memberId);

	int getExpiredCount(String memberId);

	int getUsedCount(String memberId);

}
