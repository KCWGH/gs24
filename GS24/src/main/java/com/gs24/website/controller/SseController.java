package com.gs24.website.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.gs24.website.service.SseServiceImple;

@Controller
@RequestMapping("/sse")
public class SseController {

	@Autowired
	private SseServiceImple sseService;

	@GetMapping("/subscribe/{username}")
	@ResponseBody
	public SseEmitter subscribe(@PathVariable("username") String username, HttpServletResponse response)
			throws IOException {

		return sseService.subscribe(username);
	}

}
