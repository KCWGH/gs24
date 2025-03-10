package com.gs24.website.service;

import com.gs24.website.domain.OrderVO;
import com.gs24.website.util.Pagination;

import java.util.List;


public interface OrderService {
    
    void insertOrder(OrderVO order);
    
    List<OrderVO> getAllPagedOrders(Pagination pagination);
    
    int countTotalOrders();

    List<OrderVO> getOrdersByOwnerId(String ownerId);

    void approveOrder(int orderId);

    void rejectOrder(int orderId);

    void updateApprovalStatus(int orderId, int approvalStatus);
    
    OrderVO getOrderById(int orderId);

	int countOrdersByOwnerId(String ownerId);

	List<OrderVO> getPagedOrdersByOwnerId(String ownerId, Pagination pagination);
}

