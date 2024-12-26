package com.gs24.website.persistence;

import java.util.List;

import com.gs24.website.domain.CouponVO;
import com.gs24.website.util.Pagination;

public interface CouponMapper {

	int insertCoupon(CouponVO couponVO);

	List<CouponVO> selectList(String memberId);

	CouponVO selectDetailByCouponId(int couponId);
	
	int birthdayCouponDupCheck(String memberId);

	int isExistByCouponName(String couponName);

	int selectTotalCount(String memberId);

	int selectAvailableCount(String memberId);

	int selectExpiredCount(String memberId);

	int selectUsedCount(String memberId);

	List<CouponVO> selectListByPagination(Pagination pagination);

	List<CouponVO> selectAvailableListByPagination(Pagination pagination);

	List<CouponVO> selectExpiredListByPagination(Pagination pagination);

	List<CouponVO> selectUsedListByPagination(Pagination pagination);

}
