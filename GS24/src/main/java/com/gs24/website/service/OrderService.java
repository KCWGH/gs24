package com.gs24.website.service;

import com.gs24.website.domain.OrderVO;
import com.gs24.website.util.Pagination;

import java.util.List;


public interface OrderService {
    
    void insertOrder(OrderVO order);
    
    List<OrderVO> getAllPagedOrders(Pagination pagination);
    
    int countTotalOrders();

    List<OrderVO> getOrdersByOwnerId(String ownerId);

    // 주문 승인 처리
    void approveOrder(int orderId);

    // 주문 거절 처리
    void rejectOrder(int orderId);

    // 주문 승인 상태 변경
    void updateApprovalStatus(int orderId, int approvalStatus);
    
    // 주문 ID로 주문 정보 가져오기
    OrderVO getOrderById(int orderId);

	int countOrdersByOwnerId(String ownerId);

	List<OrderVO> getPagedOrdersByOwnerId(String ownerId, Pagination pagination);
}

