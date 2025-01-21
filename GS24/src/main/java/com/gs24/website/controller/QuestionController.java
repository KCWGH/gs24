package com.gs24.website.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gs24.website.domain.CustomUser;
import com.gs24.website.domain.MemberVO;
import com.gs24.website.domain.QuestionDTO;
import com.gs24.website.domain.QuestionVO;
import com.gs24.website.service.FoodService;
import com.gs24.website.service.MemberService;
import com.gs24.website.service.QuestionService;
import com.gs24.website.util.PageMaker;
import com.gs24.website.util.Pagination;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping(value = "/question")
@Log4j
public class QuestionController {
	
	@Autowired
	private FoodService foodService;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private MemberService memberService;

	// 전체 게시글 데이터를 list.jsp 페이지로 전송
	@GetMapping("/list")
	public void list(Model model, Pagination pagination) {
		log.info("list()");
		log.info("pagination = " + pagination);

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof CustomUser) {
			CustomUser customUser = (CustomUser) principal;
			MemberVO memberVO = customUser.getMemberVO();
			model.addAttribute("memberId", memberVO.getMemberId());
			model.addAttribute("memberVO", memberVO);

		}

		// DTO 리스트 가져오기 (예: 페이지네이션을 고려한 전체 질문 리스트)
		List<QuestionDTO> questionDTOList = questionService.getPagingQuestions(pagination);

		// DTO 리스트를 VO 리스트로 변환
		List<QuestionVO> questionVOList = new ArrayList<>();
		for (QuestionDTO questionDTO : questionDTOList) {
			QuestionVO questionVO = toEntity(questionDTO); // DTO를 VO로 변환
			questionVOList.add(questionVO);
		}
		log.info("QuestionVOList = " + questionVOList);

		// 페이징 처리
		PageMaker pageMaker = new PageMaker();
		pageMaker.setPagination(pagination);
		pageMaker.setTotalCount(questionService.getTotalCount());

		// 모델에 데이터 추가
		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("questionList", questionVOList); // VO 리스트를 JSP로 전달
	}

	// DTO -> VO 변환 메서드
	public QuestionVO toEntity(QuestionDTO questionDTO) {
		QuestionVO questionVO = new QuestionVO();
		questionVO.setQuestionId(questionDTO.getQuestionId());
		questionVO.setMemberId(questionDTO.getMemberId());
		questionVO.setFoodType(questionDTO.getFoodType());
		questionVO.setQuestionTitle(questionDTO.getQuestionTitle());
		questionVO.setQuestionContent(questionDTO.getQuestionContent());
		questionVO.setIsAnswered(questionDTO.getIsAnswered());
		questionVO.setQuestionSecret(questionDTO.isQuestionSecret());
		questionVO.setQuestionDateCreated(questionDTO.getQuestionDateCreated());
		return questionVO;
	}

	 // register.jsp 호출
    @GetMapping("/register")
    public void registerGET(Model model) {
        log.info("registerGET()");
        
        List<String> foodType = foodService.getFoodTypeList();
        model.addAttribute("foodTypeList", foodType);
        
        // 로그인한 사용자 정보 가져오기
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomUser) {
            CustomUser customUser = (CustomUser) principal;
            String memberId = customUser.getUsername(); // CustomUser의 username

            // 회원 정보 가져오기
            MemberVO memberVO = memberService.getMember(memberId);
            model.addAttribute("memberId", memberId);
            model.addAttribute("memberVO", memberVO);
        } else {
            log.info("registerGET() - 인증되지 않은 사용자");
        }
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
	public void detail(Model model, Integer questionId) {
		log.info("detail()");

		// 게시글 정보 조회
		QuestionDTO questionDTO = questionService.getQuestionById(questionId);

		model.addAttribute("questionDTO", questionDTO);
		model.addAttribute("questionAttachList", questionDTO.getQuestionAttachList());
	}

	// 게시글 번호를 전송받아 상세 게시글 조회
	// 조회된 게시글 데이터를 modify.jsp로 전송
	@GetMapping("/modify")
	public void modifyGET(Model model, Integer questionId) {
		log.info("modifyGET() - questionId = " + questionId); // questionId가 제대로 전달되었는지 확인
		
		List<String> foodType = foodService.getFoodTypeList();
		
		
		QuestionDTO questionDTO = questionService.getQuestionById(questionId);
		log.info("modifyGET() - 조회된 questionDTO = " + questionDTO); // 조회된 questionDTO 확인
		if (questionDTO != null) {
			model.addAttribute("questionDTO", questionDTO);
		}
		log.info("Uploaded Files: " + questionDTO.getQuestionAttachList());
		model.addAttribute("foodTypeList", foodType);
	}

	// modify.jsp에서 데이터를 전송받아 게시글 수정
	@PostMapping("/modify")
	public String modifyPOST(QuestionDTO questionDTO) {
		log.info("modifyPOST()");
		log.info("questionDTO = " + questionDTO);
		
		int result = questionService.modifyQuestion(questionDTO);
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
	public void myListGET(Model model, Pagination pagination) {
		log.info("myListGET()");

		// 로그인한 사용자 정보 가져오기
		 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        if (principal instanceof User) {
	            User user = (User) principal;
	            String username = user.getUsername(); // 로그인한 사용자의 아이디

	            // 로그인한 사용자의 ID를 기준으로 질문 목록을 가져오기
	            List<QuestionVO> myQuestionList = questionService.getQuestionListByMemberId(username);
	            log.info(myQuestionList);

	            model.addAttribute("myQuestionList", myQuestionList); // 사용자가 작성한 질문 목록
	            model.addAttribute("username", username); // 로그인한 사용자 정보를 모델에 추가
	        } else {
	            log.info("myListGET() - 인증되지 않은 사용자");
	        }
	    }	
}
