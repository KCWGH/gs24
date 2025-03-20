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
import com.gs24.website.service.SseServiceImple;
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

	@Autowired
	private SseServiceImple sseService;

	@GetMapping("/list")
	public String getAllOrders(Model model, Pagination pagination) {
		log.info("getAllOrders");

		pagination.setPageSize(10);

		PageMaker pageMaker = createPageMaker(pagination, orderService.countTotalOrders());

		List<OrderVO> orderList = orderService.getAllPagedOrders(pagination);

		OrderWithFoodDetails(orderList);

		model.addAttribute("orderList", orderList);
		model.addAttribute("pageMaker", pageMaker);
		return "orders/list";
	}

	@PostMapping("/approve")
	@ResponseBody
	public String approveOrder(@RequestParam("orderId") int orderId) {
		OrderVO order = orderService.getOrderById(orderId);
		if (order == null)
			return "fail";

		orderService.approveOrder(orderId);
		sseService.sendNotification(order.getOwnerId(), "발주번호: " + orderId + " 승인");

		return "success";
	}

	@PostMapping("/reject")
	@ResponseBody
	public String rejectOrder(@RequestParam int orderId) {
		OrderVO order = orderService.getOrderById(orderId);
		orderService.rejectOrder(orderId);

		sseService.sendNotification(order.getOwnerId(), "발주번호: " + orderId + " 거절");
		return "success";
	}

	@GetMapping("/ownerList")
	public String getOrdersByOwner(Authentication auth, Model model, Pagination pagination) {
		String ownerId = auth.getName();
		int convenienceId = convenienceService.getConvenienceIdByOwnerId(ownerId);
		OwnerVO ownerVO = ownerService.getOwner(ownerId);

		pagination.setPageSize(10);
		pagination.setOwnerVO(ownerVO);

		PageMaker pageMaker = createPageMaker(pagination, orderService.countOrdersByOwnerId(ownerId));

		List<OrderVO> ordersByOwner = orderService.getPagedOrdersByOwnerId(ownerId, pagination);

		OrderWithFoodDetails(ordersByOwner);

		model.addAttribute("convenienceId", convenienceId);
		model.addAttribute("ordersByOwner", ordersByOwner);
		model.addAttribute("pageMaker", pageMaker);

		return "orders/ownerList";
	}

	private void OrderWithFoodDetails(List<OrderVO> orderList) {
		for (OrderVO order : orderList) {
			order.setFoodName(foodService.getFoodNameByFoodId(order.getFoodId()));
			order.setFoodType(foodService.getFoodTypeByFoodId(order.getFoodId()));
		}
	}

	private PageMaker createPageMaker(Pagination pagination, int totalCount) {
		PageMaker pageMaker = new PageMaker();
		pageMaker.setPagination(pagination);
		pageMaker.setTotalCount(totalCount);
		return pageMaker;
	}
}
