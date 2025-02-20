package com.gs24.website.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gs24.website.domain.OrderVO;

@Mapper
public interface OrderMapper {
    void insertOrder(OrderVO order);
    
    List<OrderVO> selectAllOrders();

    List<OrderVO> selectOrdersByOwnerId(String ownerId);

    // 주문 승인 상태 업데이트
    void updateApprovalStatus(OrderVO order);
    
    OrderVO selectOrderById(int orderId);
}
