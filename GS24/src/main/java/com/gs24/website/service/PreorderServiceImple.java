package com.gs24.website.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gs24.website.domain.FoodVO;
import com.gs24.website.domain.PreorderVO;
import com.gs24.website.persistence.FoodMapper;
import com.gs24.website.persistence.PreorderMapper;
import com.gs24.website.util.Pagination;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class PreorderServiceImple implements PreorderService{
	
	@Autowired
	private PreorderMapper preorderMapper;
	
	@Autowired
	private FoodMapper foodMapper;
	
	@Override
	@Transactional(value="transactionManager")
	public int createPreorder(PreorderVO preorderVO) {
		log.info("createPreorder()");
		int result = preorderMapper.insertPreorder(preorderVO);
		int preorderAmount = preorderVO.getPreorderAmount();
		int updateResult = foodMapper.updateFoodAmountByPreorderAmount(preorderVO.getFoodId(), preorderAmount);
		return result;
	}

	@Override
	public List<PreorderVO> getPreorderBymemberId(String memberId) {
		log.info("getPreorderByMemberId()");
		List<PreorderVO> list = preorderMapper.selectPreoderByMemberId(memberId);
		log.info(list);
		return list;
	}

	@Override
	public PreorderVO getPreorderOneById(int preorderId) {
		log.info("getPreorderOneById()");
		PreorderVO preorderVO = preorderMapper.selectPreorderOneById(preorderId);
		return preorderVO;
	}
	
	@Override
	@Transactional("transactionManager()")
	public FoodVO getFoodInfo(int foodId) {
		log.info("getFoodInfo()");
		return foodMapper.selectFoodById(foodId);
	}
	
	@Override
	public int updateIsPickUp(int preorderId, int isPickUp) {
		log.info("updatePreorderInIsPickUp()");
		int result = preorderMapper.updatePreorderInIsPickUp(preorderId, isPickUp);
		return result;
		
	}

	@Override
	@Transactional(value="transactionManager")
	public int updateIsExpiredOrder(int preorderId, int isExpiredOrder, PreorderVO preorderVO) {
		log.info("updatePreorderInIsExpiredOrder()");
		int result = preorderMapper.updatePreorderInIsExpiredOrder(preorderId, isExpiredOrder);
		int foodResult = foodMapper.updateFoodAmountByPreorderAmount(preorderVO.getFoodId(), preorderVO.getPreorderAmount() * -1);
		return result;
	}

	@Override
	@Transactional(value="transactionManager")
	public int deletePreorder(int preorderId, int foodId, int preorderAmount) {
		log.info("deletePreorder()");
		int result = preorderMapper.deletePreorderByPreorderId(preorderId);
		int updateresult = foodMapper.updateFoodAmountByPreorderAmount(foodId, preorderAmount * -1);
		return result;
	}
	
	@Override
	public int deleteOnlyPreoder(int preorderId) {
		log.info("onlyDeletePreorder()");
		int result = preorderMapper.deletePreorderByPreorderId(preorderId);
		return result;
	}
	
	@Override
	public List<PreorderVO> getPagingPreordersByMemberId(String memberId, Pagination pagination) {
	   return preorderMapper.selectPreorderBymemberIdPagination(pagination);
	}

	@Override
	public int countPreorderByMemberId(String memberId) {
	   return preorderMapper.countPreorderByMemberId(memberId);
	}
}
