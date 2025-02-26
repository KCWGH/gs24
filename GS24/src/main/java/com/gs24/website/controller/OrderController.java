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
<<<<<<< Updated upstream
import com.gs24.website.domain.OwnerVO;
=======
>>>>>>> Stashed changes
import com.gs24.website.service.ConvenienceService;
import com.gs24.website.service.OrderService;
import com.gs24.website.service.OwnerService;
import com.gs24.website.util.PageMaker;
import com.gs24.website.util.Pagination;

import lombok.extern.log4j.Log4j;

@Log4j
@Controller
@RequestMapping(value = "/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OwnerService ownerService;
	
	@Autowired
	private ConvenienceService convenienceService;

	@GetMapping("/list")
	public String getAllOrders(Model model, Pagination pagination) {
		log.info("getAllOrders");
		pagination.setPageSize(10);
		PageMaker pageMaker = new PageMaker();
		pageMaker.setPagination(pagination);
		pageMaker.setTotalCount(orderService.countTotalOrders());
		List<OrderVO> orderList = orderService.getAllPagedOrders(pagination);
		model.addAttribute("orderList", orderList);
		model.addAttribute("pageMaker", pageMaker);
		return "orders/list";
	}

	@PostMapping("/approve")
	@ResponseBody
	public String approveOrder(@RequestParam("orderId") int orderId) {
		log.info("approveOrder - 발주 승인: " + orderId);

		orderService.approveOrder(orderId);

		return "success";
	}

	@PostMapping("/reject")
	@ResponseBody
	public String rejectOrder(@RequestParam int orderId) {
		log.info("rejectOrder - 발주 거절: " + orderId);

		orderService.rejectOrder(orderId);
		return "success";
	}
	
	@GetMapping("/ownerList")
	public void getOrdersByOwner(Authentication auth, Model model, Pagination pagination) {               
	    String ownerId = auth.getName();	    
	    int convenienceId = convenienceService.getConvenienceIdByOwnerId(ownerId);
	    log.info("getOrdersByOwner() - 점주별 발주 조회: ownerId=" + ownerId);
<<<<<<< Updated upstream
	    OwnerVO ownerVO = ownerService.getOwner(ownerId);
	    
=======
	 
	    pagination.setPageSize(10);
	    pagination.setOwnerVO(ownerService.getOwner(ownerId));
>>>>>>> Stashed changes
	    PageMaker pageMaker = new PageMaker();
	    pagination.setPageSize(10); // 한 페이지당 10개씩 설정
	    pagination.setOwnerVO(ownerVO);
	    pageMaker.setTotalCount(orderService.countOrdersByOwnerId(ownerId));
	    pageMaker.setPagination(pagination);
<<<<<<< Updated upstream
=======
	    pageMaker.setTotalCount(orderService.countOrdersByOwner(ownerId));
	    int convenienceId = convenienceService.getConvenienceIdByOwnerId(ownerId);
>>>>>>> Stashed changes

	    List<OrderVO> ordersByOwner = orderService.getPagedOrdersByOwnerId(ownerId, pagination);
	    
<<<<<<< Updated upstream
	    model.addAttribute("convenienceId",convenienceId);
=======
	    model.addAttribute("convenienceId", convenienceId);
>>>>>>> Stashed changes
	    model.addAttribute("ordersByOwner", ordersByOwner);
	    model.addAttribute("pageMaker", pageMaker);

	}


}
