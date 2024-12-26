package com.gs24.website.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gs24.website.domain.MemberVO;
import com.gs24.website.domain.QuestionVO;
import com.gs24.website.service.QuestionService;
import com.gs24.website.util.PageMaker;
import com.gs24.website.util.Pagination;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping(value = "/question")
@Log4j
public class QuestionController {
	@Autowired
	private QuestionService questionService;

	@GetMapping("/list")
	public void list(Model model, Pagination pagination, HttpSession session) {
		log.info("list()");
		log.info("pagination = " + pagination);
		List<QuestionVO> questionList = questionService.getPagingQuestions(pagination);
		
		MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
		
	    if (memberVO == null) {
	    }

		PageMaker pageMaker = new PageMaker();
		pageMaker.setPagination(pagination);
		pageMaker.setTotalCount(questionService.getTotalCount());

		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("questionList", questionList);
	    model.addAttribute("memberVO", memberVO);

	}

	@GetMapping("/register")
	public void registerGET() {
		log.info("registerGET()");
	}

	@PostMapping("/register")
	public String registerPOST(QuestionVO questionVO) {
		log.info("registerPOST()");
		log.info("questionVO = " + questionVO.toString());
		int result = questionService.createQuestion(questionVO);
		log.info(result + "건 게시글 추가");
		return "redirect:/question/list";
	}

	@GetMapping("/detail")
	public void detail(Model model, Integer questionId, HttpSession session) {
		log.info("detail()");
		
		MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
		
		if (memberVO == null) {
	    }
		
		QuestionVO questionVO = questionService.getQuestionById(questionId);
		model.addAttribute("questionVO", questionVO);
		
	    model.addAttribute("memberVO", memberVO);
	}

	@GetMapping("/modify")
	public void modifyGET(Model model, Integer questionId) {
		log.info("modifyGET()");
		QuestionVO questionVO = questionService.getQuestionById(questionId);
		model.addAttribute("questionVO", questionVO);
	}

	// modify.jsp
	@PostMapping("/modify")
	public String modifyPOST(QuestionVO questionVO) {
		log.info("modifyPOST()");
		int result = questionService.updateQuestion(questionVO);
		log.info(result + "�� ����");
		return "redirect:/question/list";
	}

	// detail.jsp
	@PostMapping("/delete")
	public String delete(Integer questionId) {
		log.info("delete()");
		int result = questionService.deleteQuestion(questionId);
		log.info(result + "�� ����");
		return "redirect:/question/list";
	}
	
	@GetMapping("/myList")
	public void myListGET(Model model, Pagination pagination, HttpSession session) {
	    log.info("myListGET()");

	    // 세션에서 현재 로그인한 사용자 정보 가져오기
	    MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");

	    // memberVO가 null일 경우 처리
	    if (memberVO == null) {
	        log.info("세션에 memberVO가 존재하지 않습니다. 로그인 필요.");
	        model.addAttribute("errorMessage", "로그인 후 이용할 수 있습니다.");
	        return;
	    }

	    // 로그인한 사용자의 ID를 기준으로 질문 목록을 가져오기
	    List<QuestionVO> myQuestionList = questionService.getQuestionListByMemberId(memberVO.getMemberId());
	    log.info(myQuestionList);	    

	    
	    model.addAttribute("myQuestionList", myQuestionList); // 사용자가 작성한 질문 목록
	    model.addAttribute("memberVO", memberVO); // 세션에서 가져온 사용자 정보를 모델에 추가
	}




}
