package com.gs24.website.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gs24.website.domain.PreorderVO;
import com.gs24.website.util.Pagination;

@Mapper
public interface PreorderMapper {
	int insertPreorder(PreorderVO preorderVO);
	
	List<PreorderVO> selectPreoderByMemberId(String memberId);
	
	PreorderVO selectPreorderOneById(int preorderId);
	
	int updatePreorderInIsPickUp(@Param("preorderId") int preorderId,@Param("isPickUp") int isPickUp);
	
	int updatePreorderInIsExpiredOrder(@Param("preorderId") int preorderId,@Param("isExpiredOrder") int isExpiredOrder);
	
	int deletePreorderByPreorderId(int preorderId);
	
	List<PreorderVO> selectPreorderBymemberIdPagination(Pagination pagination);
	
	int countPreorderByMemberId(String memberId);
	
	List<PreorderVO> selectAlreadyPreorderByFoodId(int foodId);
	
	int countNotPickedUpPreorderByMemberId(String memberId);
}
