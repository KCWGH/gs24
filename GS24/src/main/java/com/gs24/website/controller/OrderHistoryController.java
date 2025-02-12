package com.gs24.website.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gs24.website.domain.OrderHistoryVO;
import com.gs24.website.service.OrderHistoryService;

@RestController
@RequestMapping("/orders")
public class OrderHistoryController {

    @Autowired
    private OrderHistoryService orderHistoryService;

    @GetMapping("/list")
    public ResponseEntity<List<OrderHistoryVO>> getAllOrders() {
        List<OrderHistoryVO> orderList = orderHistoryService.getAllOrders();
        return ResponseEntity.ok(orderList);
    }
}
