package com.gs24.website.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gs24.website.domain.CouponVO;

@Mapper
public interface CouponMapper {
	int insertCoupon(CouponVO couponVO);

	int useCoupon(int couponId);

	List<CouponVO> selectList();

	CouponVO selectCouponByCouponId(int couponId);

	List<CouponVO> selectListByFoodType(String foodType);

	int deleteExpiredCoupons();
}
