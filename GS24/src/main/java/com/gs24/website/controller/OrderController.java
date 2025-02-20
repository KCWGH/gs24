package com.gs24.website.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.gs24.website.domain.OrderVO;

import com.gs24.website.service.OrderService;

import lombok.extern.log4j.Log4j;

@Log4j
@Controller
@RequestMapping("/orders")	
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/list")
    public String getAllOrders(Model model) {
    	log.info("getAllOrders");
        List<OrderVO> orderList = orderService.getAllOrders();
        model.addAttribute("orderList", orderList);
        return "orders/list";  
    }

    @PostMapping("/approve")
    @ResponseBody
    public String approveOrder(@RequestParam("orderId") int orderId) {
        log.info("approveOrder - 주문 승인: " + orderId);
        
        orderService.approveOrder(orderId);

        return "success";
    }

    @PostMapping("/reject")
    @ResponseBody
    public String rejectOrder(@RequestParam int orderId) {
        log.info("rejectOrder 요청, orderId: " + orderId);
        
        orderService.rejectOrder(orderId);
        return "success"; 
    }

}
