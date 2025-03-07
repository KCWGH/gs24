package com.gs24.website.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gs24.website.domain.CouponVO;
import com.gs24.website.util.Pagination;

@Mapper
public interface CouponMapper {
	int insertCoupon(CouponVO couponVO);

	int useCoupon(int couponId);
	
	int refundCoupon(int couponId);

	List<CouponVO> selectList();
	
	List<CouponVO> selectPagedCoupons(Pagination pagination);

	CouponVO selectCouponByCouponId(int couponId);

	List<CouponVO> selectListByFoodType(String foodType);
	
	int selectTotalCount();
	
	int updateCoupon(CouponVO couponVO);
	
	int deleteCoupon(int couponId);

	int deleteExpiredCoupons();
}
