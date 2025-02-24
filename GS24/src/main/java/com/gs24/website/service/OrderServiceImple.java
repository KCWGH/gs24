package com.gs24.website.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gs24.website.domain.OrderVO;
import com.gs24.website.persistence.OrderMapper;
import com.gs24.website.util.Pagination;

import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class OrderServiceImple implements OrderService {

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private ConvenienceFoodService convenienceFoodService;

	@Override
	public void insertOrder(OrderVO order) {
		orderMapper.insertOrder(order);
	}

	@Override
	public List<OrderVO> getOrdersByOwnerId(String ownerId) {
		return orderMapper.selectOrdersByOwnerId(ownerId);
	}

	@Override
	public void approveOrder(int orderId) {
		// 주문 정보 가져오기
		OrderVO order = orderMapper.selectOrderById(orderId);
		if (order == null) {
			throw new IllegalArgumentException("해당 주문을 찾을 수 없습니다: " + orderId);
		}

		// 승인 처리
		order.setApprovalStatus(1);
		orderMapper.updateApprovalStatus(order);

		// 승인된 주문을 convenienceFood에 추가
		convenienceFoodService.createConvenienceFood(order.getFoodId(), order.getOrderAmount(), order.getOwnerId());
	}

	@Override
	public void rejectOrder(int orderId) {
		// 주문 정보 가져오기
		OrderVO order = orderMapper.selectOrderById(orderId);
		if (order == null) {
			throw new IllegalArgumentException("해당 주문을 찾을 수 없습니다: " + orderId);
		}

		// 거절 처리
		order.setApprovalStatus(2); // 거절 상태
		orderMapper.updateApprovalStatus(order);
		log.info("주문 거절 처리 완료, 주문 ID: " + orderId);
	}

	@Override
	public void updateApprovalStatus(int orderId, int approvalStatus) {
		OrderVO order = new OrderVO();
		order.setOrderId(orderId);
		order.setApprovalStatus(approvalStatus);
		orderMapper.updateApprovalStatus(order);
	}

	@Override
	public OrderVO getOrderById(int orderId) {
		return orderMapper.selectOrderById(orderId);
	}

	@Override
	public List<OrderVO> getAllPagedOrders(Pagination pagination) {
		return orderMapper.selectAllPagedOrders(pagination);
	}

	@Override
	public int countTotalOrders() {
		return orderMapper.countTotalOrders();
	}
}
