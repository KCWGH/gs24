package com.gs24.website.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gs24.website.domain.EarlyBirdCouponVO;
import com.gs24.website.persistence.EarlyBirdCouponMapper;

@Service
public class EarlyBirdCouponServiceImple implements EarlyBirdCouponService {

	@Autowired
	private EarlyBirdCouponMapper earlyBirdCouponMapper;

	@Override
	public int publishCoupon(EarlyBirdCouponVO earlyBirdCouponVO) {
		return earlyBirdCouponMapper.insertEarlyBirdCoupon(earlyBirdCouponVO);
	}

	@Override
	public List<EarlyBirdCouponVO> getEarlyBirdCouponList() {
		return earlyBirdCouponMapper.selectList();
	}

	@Override
	public List<EarlyBirdCouponVO> getEarlyBirdCouponListByFoodType(String foodType) {
		return earlyBirdCouponMapper.selectListByFoodType(foodType);
	}

	@Override
	public int useCoupon(int earlyBirdCouponId) {
		return earlyBirdCouponMapper.useEarlyBirdCoupon(earlyBirdCouponId);
	}

	@Override
	public EarlyBirdCouponVO getEarlyBirdCouponByCouponId(int earlyBirdCouponId) {
		return earlyBirdCouponMapper.selectEarlyBirdCouponByCouponId(earlyBirdCouponId);
	}
}
