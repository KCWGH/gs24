package com.gs24.website.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gs24.website.domain.CouponVO;
import com.gs24.website.persistence.CouponMapper;
import com.gs24.website.util.Pagination;

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

	@Override
	public CouponVO getCouponDetail(int couponId) {
		CouponVO couponVO = couponMapper.selectDetailByCouponId(couponId);
		return couponVO;
	}

	@Override
	public int getAllCount(String memberId) {
		return couponMapper.selectTotalCount(memberId);

	}

	@Override
	public int getAvailableCount(String memberId) {
		return couponMapper.selectAvailableCount(memberId);
	}

	@Override
	public int getExpiredCount(String memberId) {
		return couponMapper.selectExpiredCount(memberId);
	}

	@Override
	public int getUsedCount(String memberId) {
		return couponMapper.selectUsedCount(memberId);
	}

	@Override
	public List<CouponVO> getPagedAllCoupons(String memberId, Pagination pagination) {
		return couponMapper.selectListByPagination(pagination);
	}

	@Override
	public List<CouponVO> getPagedAvailableCoupons(String memberId, Pagination pagination) {
		return couponMapper.selectAvailableListByPagination(pagination);
	}

	@Override
	public List<CouponVO> getPagedExpiredCoupons(String memberId, Pagination pagination) {
		return couponMapper.selectExpiredListByPagination(pagination);
	}

	@Override
	public List<CouponVO> getPagedUsedCoupons(String memberId, Pagination pagination) {
		return couponMapper.selectUsedListByPagination(pagination);
	}

	@Override
	public int birthdayCouponDupCheck(String memberId) {
		return couponMapper.birthdayCouponDupCheck(memberId);
	}

}
