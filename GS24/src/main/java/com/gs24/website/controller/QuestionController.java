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
		log.info(result + "행 삽입");
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
		log.info(result + "행 수정");
		return "redirect:/question/list";
	}

	// detail.jsp
	@PostMapping("/delete")
	public String delete(Integer questionId) {
		log.info("delete()");
		int result = questionService.deleteQuestion(questionId);
		log.info(result + "행 삭제");
		return "redirect:/question/list";
	}
	
	// �궡媛� �옉�꽦�븳 湲�
	@GetMapping("/myList")
	public void myListGET(Model model, Pagination pagination, HttpSession session) {
	    log.info("myListGET()");

	    // �꽭�뀡�뿉�꽌 �쁽�옱 濡쒓렇�씤�븳 �궗�슜�옄 �젙蹂� 媛��졇�삤湲�
	    MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");

	    if (memberVO == null) {
	        log.warn("�꽭�뀡�뿉 memberVO媛� 議댁옱�븯吏� �븡�뒿�땲�떎. 濡쒓렇�씤 �븘�슂.");
//	        return "redirect:/member/login"; // 濡쒓렇�씤 �럹�씠吏�濡� 由щ떎�씠�젆�듃
	    }

	    // 濡쒓렇�씤�븳 �궗�슜�옄�쓽 ID瑜� 湲곗���쑝濡� 吏덈Ц 紐⑸줉�쓣 媛��졇�삤湲�
	//    List<QuestionVO> myQuestionList = questionService.getQuestionsByMemberId(pagination, memberVO.getMemberId());

	    // �럹�씠吏� 泥섎━
	    PageMaker pageMaker = new PageMaker();
	    pageMaker.setPagination(pagination);
//	    pageMaker.setTotalCount(questionService.getTotalCountByMemberId(memberVO.getMemberId())); // �궗�슜�옄媛� �옉�꽦�븳 湲� �닔

	    model.addAttribute("pageMaker", pageMaker);
//      model.addAttribute("myQuestionList", myQuestionList);
	    model.addAttribute("memberVO", memberVO); // �꽭�뀡�뿉�꽌 媛��졇�삩 �궗�슜�옄 �젙蹂대�� 紐⑤뜽�뿉 異붽��
	}

}
