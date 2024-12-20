package com.gs24.website.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gs24.website.domain.CouponVO;
import com.gs24.website.persistence.CouponMapper;

@Service
public class CouponServiceImple implements CouponService {

	@Autowired
	private CouponMapper couponMapper;

	@Override
	public int grantCoupon(CouponVO couponVO) {
		int result = couponMapper.insertCoupon(couponVO);
		return result;
	}

	@Override
	public List<CouponVO> getCouponList(String memberId) {
		return couponMapper.selectList(memberId);
	}

	@Override
	public int dupCheckCouponName(String couponName) {
		int result = couponMapper.isExistByCouponName(couponName);
		return result;
	}

}
