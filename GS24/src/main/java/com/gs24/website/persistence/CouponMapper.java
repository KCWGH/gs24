package com.gs24.website.persistence;

import java.util.List;

import com.gs24.website.domain.CouponVO;

public interface CouponMapper {

	int insertCoupon(CouponVO couponVO);

	List<CouponVO> selectList(String memberId);

	CouponVO selectDetailByCouponId(int couponId);

	int isExistByCouponName(String couponName);

	int selectTotalCount();

}
