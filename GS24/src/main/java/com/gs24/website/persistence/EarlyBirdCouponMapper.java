package com.gs24.website.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gs24.website.domain.EarlyBirdCouponVO;

@Mapper
public interface EarlyBirdCouponMapper {
	int insertEarlyBirdCoupon(EarlyBirdCouponVO earlyBirdCouponVO);
	
	int useEarlyBirdCoupon(int earlyBirdCouponId);
	
	List<EarlyBirdCouponVO> selectList();
	
	List<EarlyBirdCouponVO> selectListByFoodType(String foodType);
}
