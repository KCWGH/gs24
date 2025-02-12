package com.gs24.website.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gs24.website.domain.OrderHistoryVO;

@Mapper
public interface OrderHistoryMapper {
	void insertOrder(OrderHistoryVO order);
		
	List<OrderHistoryVO> selectAllOrders();

	List<OrderHistoryVO> selectOrdersByOwnerId(String ownerId);
}
