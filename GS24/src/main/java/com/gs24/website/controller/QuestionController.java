	package com.gs24.website.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gs24.website.domain.MemberVO;
import com.gs24.website.domain.QuestionDTO;
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
	public void list(Model model, Pagination pagination, HttpSession session) {
	    log.info("list()");
	    log.info("pagination = " + pagination);
	    
	    // DTO 리스트 가져오기 (예: 페이지네이션을 고려한 전체 질문 리스트)
	    List<QuestionDTO> questionDTOList = questionService.getPagingQuestions(pagination);
	    
	    // DTO 리스트를 VO 리스트로 변환
	    List<QuestionVO> questionVOList = new ArrayList<>();
	    for (QuestionDTO questionDTO : questionDTOList) {
	        QuestionVO questionVO = toEntity(questionDTO);  // DTO를 VO로 변환
	        questionVOList.add(questionVO);
	        log.info("QuestionVO : " + questionVO);
	    }
	    log.info("QuestionVOList = " + questionVOList);
	    
	    // 세션에서 memberVO를 가져옵니다.
	    MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
	    if (memberVO == null) {
	        log.warn("세션에 memberVO가 존재하지 않습니다. 로그인 필요.");
	    }

	    // 페이징 처리
	    PageMaker pageMaker = new PageMaker();
	    pageMaker.setPagination(pagination);
	    pageMaker.setTotalCount(questionService.getTotalCount());

	    // 모델에 데이터 추가
	    model.addAttribute("pageMaker", pageMaker);
	    model.addAttribute("questionVOList", questionVOList);  // VO 리스트를 JSP로 전달
	    model.addAttribute("memberVO", memberVO);  // 세션에서 가져온 memberVO를 JSP로 전달
	}

	// DTO -> VO 변환 메서드
	public QuestionVO toEntity(QuestionDTO questionDTO) {
	    QuestionVO questionVO = new QuestionVO();
	    questionVO.setQuestionId(questionDTO.getQuestionId());
	    questionVO.setMemberId(questionDTO.getMemberId());
	    questionVO.setFoodName(questionDTO.getFoodName());
	    questionVO.setQuestionTitle(questionDTO.getQuestionTitle());
	    questionVO.setQuestionContent(questionDTO.getQuestionContent());
	    questionVO.setIsAnswered(questionDTO.getIsAnswered());
	    questionVO.setQuestionSecret(questionDTO.isQuestionSecret());
	    questionVO.setQuestionDateCreated(questionDTO.getQuestionDateCreated());
	    return questionVO;
	}

	// register.jsp 호출
	@GetMapping("/register")
	public void registerGET() {
		log.info("registerGET()");
	}

	// 게시글 데이터를 form-data를 전송받아 QuestionService로 전송
	@PostMapping("/register")
	public String registerPOST(QuestionDTO questionDTO, RedirectAttributes reAttr) {
	    log.info("registerPOST()");
	    log.info(questionDTO.toString());
	   
	    int result = questionService.createQuestion(questionDTO);
	    log.info(result + "행 등록 ");

	    // 저장 후 리스트 페이지로 리다이렉트
	    return "redirect:/question/list";
	}
	// list.jsp에서 선택된 게시글 번호를 바탕으로 게시글 상세 조회
	// 조회된 게시글 데이터를 detail.jsp로 전송
	@GetMapping("/detail")
	public void detail(Model model, Integer questionId, HttpSession session) {
	    log.info("detail()");

	    // 세션에서 회원 정보 가져오기
	    MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");

	    // 로그인 여부 확인
	    if (memberVO == null) {
	        log.warn("세션에 memberVO가 존재하지 않습니다. 로그인 필요.");
	    }

	    // 게시글 정보 조회
	    QuestionDTO questionDTO = questionService.getQuestionById(questionId);
	    QuestionVO questionVO = toEntity(questionDTO); // DTO를 VO로 변환
	    
	    model.addAttribute("questionVO", questionVO);
	    model.addAttribute("questionAttachList", questionDTO.getQuestionAttachList());
	    model.addAttribute("memberVO", memberVO); // memberVO를 JSP로 전달
	}


	// 게시글 번호를 전송받아 상세 게시글 조회
	// 조회된 게시글 데이터를 modify.jsp로 전송
	@GetMapping("/modify")
	public void modifyGET(Model model, Integer questionId) {
		log.info("modifyGET()");
		QuestionDTO questionDTO = questionService.getQuestionById(questionId);
		model.addAttribute("questionDTO", questionDTO);
	}

	// modify.jsp에서 데이터를 전송받아 게시글 수정
	@PostMapping("/modify")
	public String modifyPOST(QuestionDTO questionDTO) {
		log.info("modifyPOST()");
		int result = questionService.updateQuestion(questionDTO);
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
