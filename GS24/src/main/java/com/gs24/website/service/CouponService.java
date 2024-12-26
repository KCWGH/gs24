package com.gs24.website.service;

import java.util.List;

import com.gs24.website.domain.CouponVO;

public interface CouponService {

	int addCoupon(CouponVO couponVO);

	List<CouponVO> getCouponList(String memberId);
}
