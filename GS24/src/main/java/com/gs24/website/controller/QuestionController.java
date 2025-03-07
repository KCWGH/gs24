package com.gs24.website.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gs24.website.domain.ConvenienceVO;
import com.gs24.website.domain.MemberVO;
import com.gs24.website.domain.OwnerVO;
import com.gs24.website.domain.QuestionVO;
import com.gs24.website.service.ConvenienceFoodService;
import com.gs24.website.service.ConvenienceService;
import com.gs24.website.service.MemberService;
import com.gs24.website.service.OwnerService;
import com.gs24.website.service.QuestionService;
import com.gs24.website.util.PageMaker;
import com.gs24.website.util.Pagination;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping(value = "/question")
@Log4j
public class QuestionController {

	@Autowired
	private ConvenienceService convenienceService;

	@Autowired
	private ConvenienceFoodService convenienceFoodService;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private OwnerService ownerService;

	// 전체 게시글 데이터를 list.jsp 페이지로 전송
	@GetMapping("/list")
	public void list(Authentication auth, Model model, Pagination pagination) {
		log.info("list()");
		log.info("pagination = " + pagination);
		if (auth != null) {
			String username = auth.getName();
			model.addAttribute("userId", username);

			// 1. memberId 확인
			MemberVO memberVO = memberService.getMember(username);
			if (memberVO != null) {
				model.addAttribute("memberVO", memberVO);
			}
			// 2. ownerId 확인
			else {
				OwnerVO ownerVO = ownerService.getOwner(username);
				if (ownerVO != null) {
					model.addAttribute("ownerVO", ownerVO);
				}
			}
		}

		pagination.setPageSize(10);
		List<QuestionVO> questionList = questionService.getPagedQuestions(pagination);
		log.info("QuestionVOList = " + questionList);

		// 페이징 처리
		PageMaker pageMaker = new PageMaker();
		pageMaker.setPagination(pagination);
		pageMaker.setTotalCount(questionService.getTotalCount());

		// 모델에 데이터 추가
		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("questionList", questionList);
	}

	@GetMapping("/register")
	public void registerGET(Authentication auth, Model model, 
			  @RequestParam(value = "convenienceId", required = false) Integer convenienceId, Pagination pagination) {
		log.info("registerGET()");
		
		List<String> foodTypeList = (convenienceId != null) 
		        ? convenienceFoodService.getFoodTypeListByConvenienceId(convenienceId) 
		        : Collections.emptyList(); 

		    model.addAttribute("foodTypeList", foodTypeList);

		List<OwnerVO> ownerVOList = ownerService.getOwnerVOList();
		model.addAttribute("ownerVOList", ownerVOList);
		
		List<ConvenienceVO> convenienceList = convenienceService.getAllConvenience(pagination);
		model.addAttribute("convenienceList", convenienceList);
		
		if (auth != null) {
			MemberVO memberVO = memberService.getMember(auth.getName());
			model.addAttribute("memberId", auth.getName());
			model.addAttribute("memberVO", memberVO);
		}
	}

	@PostMapping("/register")
	public String registerPOST(QuestionVO questionVO, RedirectAttributes reAttr) {
		log.info("registerPOST()");

		int result = questionService.createQuestion(questionVO);
		log.info(result + "행 등록 ");

		return "redirect:/question/list";
	}

	@GetMapping("/detail")
	public void detail(Authentication auth, Model model, Integer questionId) {
		log.info("detail()");

		if (auth != null) {
			if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_OWNER"))) {
				String ownerId = auth.getName();
				int convenienceId = convenienceService.getConvenienceIdByOwnerId(ownerId);
				model.addAttribute("convenienceId", convenienceId);
			}
		}

		// 게시글 정보 조회
		QuestionVO questionVO = questionService.getQuestionById(questionId);
		model.addAttribute("questionVO", questionVO);
		model.addAttribute("questionAttachList", questionVO.getQuestionAttachList());
	}

	@GetMapping("/modify")
	public void modifyGET(Model model, @RequestParam(value = "convenienceId", required = false) Integer convenienceId,
							Pagination pagination, Integer questionId) {

		List<String> foodTypeList = (convenienceId != null) 
	            ? convenienceFoodService.getFoodTypeListByConvenienceId(convenienceId) 
	            : Collections.emptyList();
	    model.addAttribute("foodTypeList", foodTypeList);
	    
	    List<ConvenienceVO> convenienceList = convenienceService.getAllConvenience(pagination);
	    model.addAttribute("convenienceList", convenienceList);
	    
		QuestionVO questionVO = questionService.getQuestionById(questionId);
		
		List<OwnerVO> ownerVOList = ownerService.getOwnerVOList();
		model.addAttribute("ownerVOList", ownerVOList);

		log.info("modifyGET() - 조회된 questionVO = " + questionVO);
		model.addAttribute("questionVO", questionVO);
		model.addAttribute("foodTypeList", foodTypeList);
	}

	@PostMapping("/modify")
	public String modifyPOST(QuestionVO questionVO) {
		log.info("modifyPOST()");
		
		int result = questionService.modifyQuestion(questionVO);
		log.info(result + "행 수정");
		return "redirect:/question/list";
	}

	@PostMapping("/delete")
	public String delete(Integer questionId) {
		log.info("delete()");
		int result = questionService.deleteQuestion(questionId);
		log.info(result + "행 삭제");
		return "redirect:/question/list";
	}

	@GetMapping("/myList")
	public void myListGET(Authentication auth, Model model, Pagination pagination) {
		log.info("myListGET()");
		String username = auth.getName(); // 로그인한 사용자의 아이디

		// 로그인한 사용자의 ID를 기준으로 질문 목록을 가져오기
		List<QuestionVO> myQuestionList = questionService.getQuestionListByMemberId(username);
		log.info(myQuestionList);

		model.addAttribute("myQuestionList", myQuestionList); // 사용자가 작성한 질문 목록
		model.addAttribute("username", username); // 로그인한 사용자 정보를 모델에 추가

	}

	@GetMapping("/ownerList")
	public void ownerListGET(Authentication auth, Model model, Pagination pagination) {
		log.info("ownerListGET()");
		pagination.setPageSize(10);
		String ownerId = auth.getName();
		pagination.setOwnerVO(ownerService.getOwner(ownerId));
		PageMaker pageMaker = new PageMaker();
		pageMaker.setPagination(pagination);
		pageMaker.setTotalCount(questionService.getTotalCountByOwnerId(ownerId));
		int convenienceId = convenienceService.getConvenienceIdByOwnerId(ownerId);
		List<QuestionVO> questionList = questionService.getPagedQuestionListByOwnerId(ownerId, pagination);

		model.addAttribute("convenienceId", convenienceId);
		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("questionList", questionList);
	}
	
	@GetMapping("/getFoodTypeList")
    @ResponseBody
    public ResponseEntity<List<String>> getFoodTypeList(@RequestParam("convenienceId") int convenienceId) {
        List<String> foodTypeList = convenienceFoodService.getFoodTypeListByConvenienceId(convenienceId);
        return ResponseEntity.ok(foodTypeList);
    }
}
