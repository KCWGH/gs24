package com.gs24.website.persistence;

import org.apache.ibatis.annotations.Mapper;

import com.gs24.website.domain.CouponQueueVO;

@Mapper
public interface CouponQueueMapper {
	int insertQueue(CouponQueueVO earlyBirdCouponQueueVO);
}
