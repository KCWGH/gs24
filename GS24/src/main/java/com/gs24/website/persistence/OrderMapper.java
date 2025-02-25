package com.gs24.website.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gs24.website.domain.OrderVO;
import com.gs24.website.util.Pagination;

@Mapper
public interface OrderMapper {
	void insertOrder(OrderVO order);

	List<OrderVO> selectAllPagedOrders(Pagination pagination);

	List<OrderVO> selectOrdersByOwnerId(String ownerId);

	int countTotalOrders();

	void updateApprovalStatus(OrderVO order);

	OrderVO selectOrderById(int orderId);
	
	int countOrdersByOwner(String ownerId);
	
	List<OrderVO> selectPagedOrdersByOwnerId(@Param("ownerId") String ownerId, @Param("pagination") Pagination pagination);

}
