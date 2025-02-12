package com.gs24.website.service;

import java.util.List;

import com.gs24.website.domain.OrderHistoryVO;

public interface OrderHistoryService {
	
	void insertOrder(OrderHistoryVO order);
	
	List<OrderHistoryVO> getAllOrders();

	List<OrderHistoryVO> getOrdersByOwnerId(String ownerId);

}
