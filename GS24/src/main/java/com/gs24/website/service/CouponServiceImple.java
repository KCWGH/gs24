package com.gs24.website.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.gs24.website.domain.CouponVO;
import com.gs24.website.persistence.CouponMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class CouponServiceImple implements CouponService {

	@Autowired
	private CouponMapper couponMapper;

	@Override
	public int publishCoupon(CouponVO couponVO) {
		return couponMapper.insertCoupon(couponVO);
	}

	@Override
	public List<CouponVO> getCouponList() {
		return couponMapper.selectList();
	}

	@Override
	public List<CouponVO> getCouponListByFoodType(String foodType) {
		return couponMapper.selectListByFoodType(foodType);
	}

	@Override
	public int useCoupon(int couponId) {
		return couponMapper.useCoupon(couponId);
	}

	@Override
	public CouponVO getCouponByCouponId(int couponId) {
		return couponMapper.selectCouponByCouponId(couponId);
	}

	@Override
	@Scheduled(cron = "0 0 0 * * *") // 매일 자정
	public void deleteExpiredCoupons() {
		int result = couponMapper.deleteExpiredCoupons();
		if (result == 1) {
			log.info("deleteExpiredCoupons()");
		}
	}
}
