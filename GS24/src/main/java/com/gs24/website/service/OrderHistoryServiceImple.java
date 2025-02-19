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

    @Override
    public void approveOrderHistory(int orderId) {
        // OrderHistoryVO 객체 생성 후 전달
        OrderHistoryVO order = new OrderHistoryVO();
        order.setOrderId(orderId);
        order.setApprovalStatus(1); // 승인 상태로 설정
        orderHistoryMapper.updateApprovalStatus(order);
    }

    @Override
    public void rejectOrderHistory(int orderId) {
        int approvalStatus = 2;
        // OrderHistoryVO 객체 생성 후 전달
        OrderHistoryVO order = new OrderHistoryVO();
        order.setOrderId(orderId);
        order.setApprovalStatus(approvalStatus);
        orderHistoryMapper.updateApprovalStatus(order);
    }

    @Override
    public void updateApprovalStatus(int orderId, int approvalStatus) {
        OrderHistoryVO order = new OrderHistoryVO();
        order.setOrderId(orderId);
        order.setApprovalStatus(approvalStatus);
        orderHistoryMapper.updateApprovalStatus(order);
    }
    
    @Override
    public OrderHistoryVO getOrderById(int orderId) {
        return orderHistoryMapper.selectOrderById(orderId);
    }
}
