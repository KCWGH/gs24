package com.gs24.website.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.gs24.website.domain.OrderHistoryVO;
import com.gs24.website.service.ConvenienceFoodService;
import com.gs24.website.service.OrderHistoryService;

import lombok.extern.log4j.Log4j;

@Log4j
@Controller
@RequestMapping("/orders")	
public class OrderHistoryController {

    @Autowired
    private OrderHistoryService orderHistoryService;
    
    @Autowired
    private ConvenienceFoodService convenienceFoodService;

    @GetMapping("/list")
    public String getAllOrders(Model model) {
    	log.info("getAllOrders");
        List<OrderHistoryVO> orderList = orderHistoryService.getAllOrders();
        model.addAttribute("orderList", orderList);
        return "orders/list";  
    }

    @PostMapping("/approve")
    @ResponseBody
    public String approveOrder(@RequestParam int orderId) {
        log.info("approveOrder 요청, orderId: " + orderId);
        
        orderHistoryService.approveOrderHistory(orderId);
        OrderHistoryVO order = orderHistoryService.getOrderById(orderId);

        if (order.getApprovalStatus() == 1) {
            convenienceFoodService.createConvenienceFood(order.getFoodId(), order.getOrderAmount(), order.getOwnerId());
        }

        return "success"; 
    }

    @PostMapping("/reject")
    @ResponseBody
    public String rejectOrder(@RequestParam int orderId) {
        log.info("rejectOrder 요청, orderId: " + orderId);
        
        orderHistoryService.rejectOrderHistory(orderId);
        return "success"; 
    }

}
