package com.gs24.website.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gs24.website.domain.OrderVO;
import com.gs24.website.persistence.OrderMapper;

import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class OrderServiceImple implements OrderService {

    @Autowired
    private OrderMapper orderHistoryMapper;
    
    @Autowired
    private ConvenienceFoodService convenienceFoodService;

    @Override
    public void insertOrder(OrderVO order) {
        orderHistoryMapper.insertOrder(order);
    }

    @Override
    public List<OrderVO> getAllOrders() {
        return orderHistoryMapper.selectAllOrders();
    }

    @Override
    public List<OrderVO> getOrdersByOwnerId(String ownerId) {
        return orderHistoryMapper.selectOrdersByOwnerId(ownerId);
    }

    @Override
    public void approveOrder(int orderId) {
        // 주문 정보 가져오기
        OrderVO order = orderHistoryMapper.selectOrderById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("해당 주문을 찾을 수 없습니다: " + orderId);
        }

        // 승인 처리
        order.setApprovalStatus(1);
        orderHistoryMapper.updateApprovalStatus(order);


        // 승인된 주문을 convenienceFood에 추가
        convenienceFoodService.createConvenienceFood(order.getFoodId(), order.getOrderAmount(), order.getOwnerId());
    }

    @Override
    public void rejectOrder(int orderId) {
        // 주문 정보 가져오기
        OrderVO order = orderHistoryMapper.selectOrderById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("해당 주문을 찾을 수 없습니다: " + orderId);
        }

        // 거절 처리
        order.setApprovalStatus(2); // 거절 상태
        orderHistoryMapper.updateApprovalStatus(order);
        log.info("주문 거절 처리 완료, 주문 ID: " + orderId);
    }

    @Override
    public void updateApprovalStatus(int orderId, int approvalStatus) {
        OrderVO order = new OrderVO();
        order.setOrderId(orderId);
        order.setApprovalStatus(approvalStatus);
        orderHistoryMapper.updateApprovalStatus(order);
    }

    @Override
    public OrderVO getOrderById(int orderId) {
        return orderHistoryMapper.selectOrderById(orderId);
    }
}
