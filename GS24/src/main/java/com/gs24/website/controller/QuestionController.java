package com.gs24.website.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

	// 전체 게시글 데이터를 list.jsp 페이지로 전송
	@GetMapping("/list")
	public void list(Model model, Pagination pagination) {
		log.info("list()");
		log.info("pagination = " + pagination);
		List<QuestionVO> questionList = questionService.getPagingQuestions(pagination);

		PageMaker pageMaker = new PageMaker();
		pageMaker.setPagination(pagination);
		pageMaker.setTotalCount(questionService.getTotalCount());

		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("questionList", questionList);

	}

	// register.jsp 호출
	@GetMapping("/register")
	public void registerGET() {
		log.info("registerGET()");
	}

	// register.jsp에서 전송받은 게시글 데이터를 저장
	@PostMapping("/register")
	public String registerPOST(QuestionVO questionVO) {
		log.info("registerPOST()");
		log.info("questionVO = " + questionVO.toString());
		int result = questionService.createQuestion(questionVO);
		log.info(result + "행 등록 ");
		return "redirect:/question/list";
	}

	// list.jsp에서 선택된 게시글 번호를 바탕으로 게시글 상세 조회
	// 조회된 게시글 데이터를 detail.jsp로 전송
	@GetMapping("/detail")
	public void detail(Model model, Integer questionId) {
		log.info("detail()");
		QuestionVO questionVO = questionService.getQuestionById(questionId);
		model.addAttribute("questionVO", questionVO);
	}

	// 게시글 번호를 전송받아 상세 게시글 조회
	// 조회된 게시글 데이터를 modify.jsp로 전송
	@GetMapping("/modify")
	public void modifyGET(Model model, Integer questionId) {
		log.info("modifyGET()");
		QuestionVO questionVO = questionService.getQuestionById(questionId);
		model.addAttribute("questionVO", questionVO);
	}

	// modify.jsp에서 데이터를 전송받아 게시글 수정
	@PostMapping("/modify")
	public String modifyPOST(QuestionVO questionVO) {
		log.info("modifyPOST()");
		int result = questionService.updateQuestion(questionVO);
		log.info(result + "행 수정");
		return "redirect:/question/list";
	}

	// detail.jsp에서 boardId를 전송받아 게시글 데이터 삭제
	@PostMapping("/delete")
	public String delete(Integer questionId) {
		log.info("delete()");
		int result = questionService.deleteQuestion(questionId);
		log.info(result + "행 삭제");
		return "redirect:/question/list";
	}

}
