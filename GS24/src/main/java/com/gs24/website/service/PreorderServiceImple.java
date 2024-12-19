package com.gs24.website.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gs24.website.domain.PreorderVO;
import com.gs24.website.persistence.FoodMapper;
import com.gs24.website.persistence.PreorderMapper;

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
		int updateResult = foodMapper.updateFoodAmountByPreorderAmount(preorderVO.getFoodId(), preorderVO.getPreorderAmount());
		return result;
	}

	@Override
	public List<PreorderVO> getPreorderBymemberId(String memberId) {
		log.info("getPreorderBy");
		List<PreorderVO> list = preorderMapper.selectPreoderByMemberId(memberId);
		log.info(list);
		return list;
	}

	@Override
	public int updateIsPickUp(int preorderId, int isPickUp) {
		log.info("updatePreorderInIsPickUp()");
		int result = preorderMapper.updatePreorderInIsPickUp(preorderId, isPickUp);
		return result;
		
	}

	@Override
	public int updateIsExpiredOrder(int preorderId, int isExpiredOrder) {
		log.info("updatePreorderInIsExpiredOrder()");
		int result = preorderMapper.updatePreorderInIsExpiredOrder(preorderId, isExpiredOrder);
		return result;
	}

	@Override
	@Transactional(value="transactionManager")
	public int deletePreorder(int preorderId) {
		log.info("deletePreorder()");
		int result = preorderMapper.deletePreorderByPreorderId(preorderId);
		return result;
	}
}
