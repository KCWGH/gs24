package com.gs24.website.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gs24.website.domain.OrderHistoryVO;
import com.gs24.website.persistence.OrderHistoryMapper;

@Service
public class OrderHistoryServiceImple implements OrderHistoryService {
	
	@Autowired
	private OrderHistoryMapper orderHistoryMapper;	
	
	@Override
	public void insertOrder(OrderHistoryVO order) {
		orderHistoryMapper.insertOrder(order);
		
	}

	@Override
	public List<OrderHistoryVO> getAllOrders() {
		return orderHistoryMapper.selectAllOrders();
	}

	@Override
	public List<OrderHistoryVO> getOrdersByOwnerId(String ownerId) {
		return orderHistoryMapper.selectOrdersByOwnerId(ownerId);
	}

	

}
