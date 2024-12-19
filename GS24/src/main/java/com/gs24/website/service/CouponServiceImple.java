package com.gs24.website.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.gs24.website.domain.CouponVO;
import com.gs24.website.persistence.CouponMapper;

public class CouponServiceImple implements CouponService {

	@Autowired
	private CouponMapper couponMapper;

	@Override
	public int addCoupon(CouponVO couponVO) {
		int result = couponMapper.insertCoupon(couponVO);
		return result;
	}

	@Override
	public List<CouponVO> getCouponList(String memberId) {
		// TODO Auto-generated method stub
		return null;
	}

}
