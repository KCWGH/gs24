package com.gs24.website.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gs24.website.domain.CouponVO;
import com.gs24.website.persistence.CouponMapper;
import com.gs24.website.persistence.CouponQueueMapper;
import com.gs24.website.util.Pagination;

import lombok.extern.log4j.Log4j;

@Service

@Log4j
public class CouponServiceImple implements CouponService {

	@Autowired
	private CouponMapper couponMapper;
	
	@Autowired
	private CouponQueueMapper couponQueueMapper;

	@Override
	public int validateAndPublishCoupon(CouponVO couponVO) {
		if (couponVO.getCouponName().equals("")) {
			String foodType = couponVO.getFoodType();
			String value = "";
			switch (couponVO.getDiscountType()) {
			case 'A':
				value = couponVO.getAmount() + "원";
				break;
			case 'P':
				value = couponVO.getPercentage() + "%";
				break;
			}
			couponVO.setCouponName(foodType + " " + value + " 할인권");
		}

		if (couponVO.getCouponExpiredDate() == null) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.YEAR, 100);
			couponVO.setCouponExpiredDate(calendar.getTime());
		}

		switch (couponVO.getDiscountType()) {
		case 'A':
			if (couponVO.getAmount() < 1000) {
				return -1; // 정액권은 최소 1000원이어야 함
			}
			break;
		case 'P':
			if (couponVO.getPercentage() < 5) {
				return -2; // 비율권은 최소 5%이어야 함
			}
			break;
		}

		// 쿠폰 발행
		return couponMapper.insertCoupon(couponVO);
	}

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

	@Override
	public List<CouponVO> getPagedCouponList(Pagination pagination) {
		pagination.setPageSize(10);
		return couponMapper.selectPagedCoupons(pagination);
	}

	@Override
	public int getTotalCount() {
		return couponMapper.selectTotalCount();
	}

	@Override
	public int modifyCoupon(CouponVO couponVO) {
		if (couponVO.getCouponName().equals("")) {
			String foodType = couponVO.getFoodType();
			String value = "";
			switch (couponVO.getDiscountType()) {
			case 'A':
				value = couponVO.getAmount() + "원";
				break;
			case 'P':
				value = couponVO.getPercentage() + "%";
				break;
			}
			couponVO.setCouponName(foodType + " " + value + " 할인권");
		}

		if (couponVO.getCouponExpiredDate() == null) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.YEAR, 100);
			couponVO.setCouponExpiredDate(calendar.getTime());
		}
		return couponMapper.updateCoupon(couponVO);
	}

	@Override
	@Transactional(value = "transactionManager")
	public int deleteCoupon(int couponId) {
		couponQueueMapper.deleteEachQueues(couponId);
		return couponMapper.deleteCoupon(couponId);
	}

}
