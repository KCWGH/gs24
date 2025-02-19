package com.gs24.website.service;

import com.gs24.website.domain.OrderHistoryVO;

import java.util.List;


public interface OrderHistoryService {
    
    void insertOrder(OrderHistoryVO order);
    
    List<OrderHistoryVO> getAllOrders();

    List<OrderHistoryVO> getOrdersByOwnerId(String ownerId);

    // 주문 승인 처리
    void approveOrderHistory(int orderId);

    // 주문 거절 처리
    void rejectOrderHistory(int orderId);

    // 주문 승인 상태 변경
    void updateApprovalStatus(int orderId, int approvalStatus);
    
    // 주문 ID로 주문 정보 가져오기
    OrderHistoryVO getOrderById(int orderId);
}

