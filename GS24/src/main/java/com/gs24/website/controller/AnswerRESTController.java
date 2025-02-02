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

// * RESTful url과 의미
// /reply (POST) : 댓글 추가(insert)
// /reply/all/숫자 (GET) : 해당 글 번호(boardId)의 모든 댓글 검색(select)
// /reply/숫자 (PUT) : 해당 댓글 번호(replyId)의 내용을 수정(update)
// /reply/숫자 (DELETE) : 해당 댓글 번호(replyId)의 내용을 삭제(delete)

@RestController
@RequestMapping(value = "/answer")
@Log4j
public class AnswerRESTController {
	@Autowired
	private AnswerService answerService;
	
	@PostMapping // POST : 댓글 입력
	public ResponseEntity<Integer> createAnswer(@RequestBody AnswerVO answerVO) {
		log.info("createAnswer()");
		
		int result = answerService.createAnswer(answerVO);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	@GetMapping("/all/{questionId}") // GET : 댓글 선택(all)
	public ResponseEntity<List<AnswerVO>> readAllAnswer(
			@PathVariable("questionId") int questionId) {
		// @pathVariable("questionId") : {questionId} 값을 설정된 변수에 저장
		log.info("readAllAnswer()");
		log.info("questionId = " + questionId);
		
		List<AnswerVO> list = answerService.getAllAnswer(questionId);
		// ResponseEntity<T> : T의 타입은 프론트 side로 전송될 데이터의 타입으로 선언
		return new ResponseEntity<List<AnswerVO>>(list, HttpStatus.OK);
	}
	
	 @PutMapping("/{answerId}") // PUT : 댓글 수정
	   public ResponseEntity<Integer> updateAnswer(
	         @PathVariable("answerId") int answerId,
	         @RequestBody String answerContent
	         ){
	      log.info("updateAnswer()");
	      log.info("answerId = " + answerId);
	      int result = answerService.updateAnswer(answerId, answerContent);
	      return new ResponseEntity<Integer>(result, HttpStatus.OK);
	   }
	   
	   @DeleteMapping("/{answerId}/{questionId}") // DELETE : 댓글 삭제
	   public ResponseEntity<Integer> deleteAnswer(
	         @PathVariable("answerId") int answerId, 
	         @PathVariable("questionId") int questionId) {
	      log.info("deleteAnswer()");
	      log.info("answerId = " + answerId);
	      
	      int result = answerService.deleteAnswer(answerId, questionId);
	      
	      return new ResponseEntity<Integer>(result, HttpStatus.OK);
	   }
	
}
