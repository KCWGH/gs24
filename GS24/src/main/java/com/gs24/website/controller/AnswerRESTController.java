package com.gs24.website.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gs24.website.domain.AnswerVO;
import com.gs24.website.service.AnswerService;

import lombok.extern.log4j.Log4j;


@RestController
@RequestMapping(value = "/answer")
@Log4j
public class AnswerRESTController {
	@Autowired
	private AnswerService answerService;
	
	@PostMapping
	public ResponseEntity<Integer> createAnswer(@RequestBody AnswerVO answerVO) {
		log.info("createAnswer()");
		
		int result = answerService.createAnswer(answerVO);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	@GetMapping("/all/{questionId}")
	public ResponseEntity<List<AnswerVO>> readAllAnswer(
			@PathVariable("questionId") int questionId) {

		log.info("readAllAnswer()");
		log.info("questionId = " + questionId);
		
		List<AnswerVO> list = answerService.getAllAnswer(questionId);

		return new ResponseEntity<List<AnswerVO>>(list, HttpStatus.OK);
	}
	
	 @PutMapping("/{answerId}")
	   public ResponseEntity<Integer> updateAnswer(
	         @PathVariable("answerId") int answerId,
	         @RequestBody String answerContent
	         ){
	      log.info("updateAnswer()");
	      log.info("answerId = " + answerId);
	      int result = answerService.updateAnswer(answerId, answerContent);
	      return new ResponseEntity<Integer>(result, HttpStatus.OK);
	   }
	   
	   @DeleteMapping("/{answerId}/{questionId}")
	   public ResponseEntity<Integer> deleteAnswer(
	         @PathVariable("answerId") int answerId, 
	         @PathVariable("questionId") int questionId) {
	      log.info("deleteAnswer()");
	      log.info("answerId = " + answerId);
	      
	      int result = answerService.deleteAnswer(answerId, questionId);
	      
	      return new ResponseEntity<Integer>(result, HttpStatus.OK);
	   }
	
}
