package com.gs24.website.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gs24.website.domain.OrderVO;
import com.gs24.website.persistence.FoodListMapper;
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
	
	@Autowired
	private FoodListService foodListService;
	
	@Autowired
	private FoodListMapper foodListMapper;

	@Override
	public void insertOrder(OrderVO order) {
        int currentStock = foodListService.getFoodStock(order.getFoodId());
        if (currentStock < order.getOrderAmount()) {
            throw new IllegalArgumentException("재고가 부족합니다. 현재 재고: " + currentStock);
        }
        
        // 재고 차감
        foodListService.updateFoodStockByFoodAmount(order.getFoodId(), order.getOrderAmount());

        // 발주 내역 DB에 저장
        orderMapper.insertOrder(order);
	}

	@Override
	public List<OrderVO> getOrdersByOwnerId(String ownerId) {
		return orderMapper.selectOrdersByOwnerId(ownerId);
	}

	@Override
	public void approveOrder(int orderId) {
		OrderVO order = orderMapper.selectOrderById(orderId);
		if (order == null) {
			throw new IllegalArgumentException("해당 발주를 찾을 수 없습니다: " + orderId);
		}

		order.setApprovalStatus(1);
		orderMapper.updateApprovalStatus(order);

		convenienceFoodService.createConvenienceFood(order.getFoodId(), order.getOrderAmount(), order.getOwnerId());
	}

	@Override
	public void rejectOrder(int orderId) {
		OrderVO order = orderMapper.selectOrderById(orderId);
		if (order == null) {
			throw new IllegalArgumentException("해당 발주를 찾을 수 없습니다: " + orderId);
		}

		order.setApprovalStatus(2);
		orderMapper.updateApprovalStatus(order);
		log.info("발주 거절 처리 완료, 발주 ID: " + orderId);
		
		foodListMapper.restoreFoodStock(order.getFoodId(), order.getOrderAmount());

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

	@Override
	public int countOrdersByOwnerId(String ownerId) {
		return orderMapper.countOrdersByOwner(ownerId);
	}

	@Override
	public List<OrderVO> getPagedOrdersByOwnerId(String ownerId, Pagination pagination) {
		return orderMapper.selectPagedOrdersByOwnerId(pagination);
	}
}
