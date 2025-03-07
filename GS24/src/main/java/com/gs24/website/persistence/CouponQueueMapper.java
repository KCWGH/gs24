package com.gs24.website.persistence;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gs24.website.domain.CouponQueueVO;

@Mapper
public interface CouponQueueMapper {
	int insertQueue(CouponQueueVO couponQueueVO);

	int dupCheckQueueByMemberId(@Param("couponId") int couponId, @Param("memberId") String memberId,
			@Param("foodId") int foodId);

	int deleteEachQueues(int couponId);

	int deleteQueue(@Param("couponId") int couponId, @Param("memberId") String memberId);

}
