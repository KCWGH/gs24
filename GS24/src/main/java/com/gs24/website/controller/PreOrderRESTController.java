package com.gs24.website.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gs24.website.domain.PreorderVO;
import com.gs24.website.service.PreorderService;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("/preorder")
@Log4j
public class PreOrderRESTController {
	
	@Autowired
	private PreorderService preorderService;
	
	@GetMapping("/all/{memberId}")
	public ResponseEntity<List<PreorderVO>> getAllPreOrder(@PathVariable String memberId) {
		log.info("getAllPreOrder()");
		List<PreorderVO> list = preorderService.getPreorderBymemberId(memberId);
		log.info(list);
		return new ResponseEntity<List<PreorderVO>>(list, HttpStatus.OK);
	}
}
