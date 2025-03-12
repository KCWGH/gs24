package com.gs24.website.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gs24.website.domain.OrderVO;
import com.gs24.website.domain.OwnerVO;
import com.gs24.website.service.ConvenienceService;
import com.gs24.website.service.FoodService;
import com.gs24.website.service.OrderService;
import com.gs24.website.service.OwnerService;
import com.gs24.website.util.PageMaker;
import com.gs24.website.util.Pagination;

import lombok.extern.log4j.Log4j;

@Log4j
@Controller
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OwnerService ownerService;
	
	@Autowired
	private ConvenienceService convenienceService;
	
	@Autowired
	private FoodService foodService;

	@GetMapping("/list")
	public String getAllOrders(Model model, Pagination pagination) {
		log.info("getAllOrders");
		pagination.setPageSize(10);
		PageMaker pageMaker = new PageMaker();
		pageMaker.setPagination(pagination);
		pageMaker.setTotalCount(orderService.countTotalOrders());
		List<OrderVO> orderList = orderService.getAllPagedOrders(pagination);
		
		for (OrderVO order : orderList) {
		        String foodName = foodService.getFoodNameByFoodId(order.getFoodId());
		        order.setFoodName(foodName);
		    }
		
		for (OrderVO order : orderList) {
		    	String foodType = foodService.getFoodTypeByFoodId(order.getFoodId());
		    	order.setFoodType(foodType);
		    }
		 
		model.addAttribute("orderList", orderList);
		model.addAttribute("pageMaker", pageMaker);
		return "orders/list";
	}

	@PostMapping("/approve")
	@ResponseBody
	public String approveOrder(@RequestParam("orderId") int orderId) {
		orderService.approveOrder(orderId);
		return "success";
	}

	@PostMapping("/reject")
	@ResponseBody
	public String rejectOrder(@RequestParam int orderId) {
		orderService.rejectOrder(orderId);
		return "success";
	}
	
	@GetMapping("/ownerList")
	public String getOrdersByOwner(Authentication auth, Model model, Pagination pagination) {               
	    String ownerId = auth.getName();	    
	    int convenienceId = convenienceService.getConvenienceIdByOwnerId(ownerId);
	    OwnerVO ownerVO = ownerService.getOwner(ownerId);
	    
	    PageMaker pageMaker = new PageMaker();
	    pagination.setPageSize(10); 
	    pagination.setOwnerVO(ownerVO);
	    pageMaker.setTotalCount(orderService.countOrdersByOwnerId(ownerId));
	    pageMaker.setPagination(pagination);

	    List<OrderVO> ordersByOwner = orderService.getPagedOrdersByOwnerId(ownerId, pagination);
	    
	    for (OrderVO order : ordersByOwner) {
	        String foodName = foodService.getFoodNameByFoodId(order.getFoodId());
	        order.setFoodName(foodName);
	    }
	    
	    for (OrderVO order : ordersByOwner) {
	    	String foodType = foodService.getFoodTypeByFoodId(order.getFoodId());
	    	order.setFoodType(foodType);
	    }
	    
	    model.addAttribute("convenienceId",convenienceId);
	    model.addAttribute("ordersByOwner", ordersByOwner);
	    model.addAttribute("pageMaker", pageMaker);

	    return "orders/ownerList"; 
	}


}
