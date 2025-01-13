package com.gs24.website.persistence;

import org.apache.ibatis.annotations.Mapper;

import com.gs24.website.domain.EarlyBirdCouponQueueVO;

@Mapper
public interface EarlyBirdCouponQueueMapper {
	int insertQueue(EarlyBirdCouponQueueVO earlyBirdCouponQueueVO);
}
