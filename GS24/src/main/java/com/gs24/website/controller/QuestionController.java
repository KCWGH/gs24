package com.gs24.website.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gs24.website.domain.CustomUser;
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
			String userId = auth.getName();
			model.addAttribute("userId", userId);

			// 1. memberId 확인
			MemberVO memberVO = memberService.getMember(userId);
			if (memberVO != null) {
				model.addAttribute("memberVO", memberVO);
			}
			// 2. ownerId 확인
			else {
				OwnerVO ownerVO = ownerService.getOwner(userId);
				if (ownerVO != null) {
					model.addAttribute("ownerVO", ownerVO);
				}
			}
		}

		// 질문 목록 조회
		List<QuestionVO> questionList = questionService.getPagingQuestions(pagination);
		log.info("QuestionVOList = " + questionList);

		// 페이징 처리
		PageMaker pageMaker = new PageMaker();
		pageMaker.setPagination(pagination);
		pageMaker.setTotalCount(questionService.getTotalCount());

		// 모델에 데이터 추가
		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("questionList", questionList);
	}

	// register.jsp 호출
	@GetMapping("/register")
	public void registerGET(Model model) {
		log.info("registerGET()");

		List<String> foodType = convenienceFoodService.getFoodTypeList();
		log.info(foodType);
		model.addAttribute("foodTypeList", foodType);

		// OwnerVO 리스트 가져오기
		List<OwnerVO> ownerVOList = ownerService.getOwnerVOList(); // 변경 필요
		model.addAttribute("ownerVOList", ownerVOList);

		// 로그인한 사용자 정보 가져오기
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof CustomUser) {
			CustomUser customUser = (CustomUser) principal;
			String memberId = customUser.getUsername();

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
	public String registerPOST(QuestionVO questionVO, RedirectAttributes reAttr) {
		log.info("registerPOST()");
		log.info(questionVO.toString());

		int result = questionService.createQuestion(questionVO);
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
		QuestionVO questionVO = questionService.getQuestionById(questionId);
		model.addAttribute("questionVO", questionVO);
		model.addAttribute("questionAttachList", questionVO.getQuestionAttachList());
	}

	@GetMapping("/modify")
	public void modifyGET(Model model, Integer questionId) {
		log.info("modifyGET() - questionId = " + questionId);

		List<String> foodType = convenienceFoodService.getFoodTypeList();
		QuestionVO questionVO = questionService.getQuestionById(questionId);

		log.info("modifyGET() - 조회된 questionVO = " + questionVO);
		model.addAttribute("questionVO", questionVO);
		model.addAttribute("foodTypeList", foodType);
	}

	@PostMapping("/modify")
	public String modifyPOST(QuestionVO questionVO) {
		log.info("modifyPOST()");
		log.info("questionVO = " + questionVO);

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
		if (auth != null) {
			String userId = auth.getName(); // 로그인한 사용자의 아이디
			model.addAttribute("userId", userId);
			if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_OWNER"))) {
				int convenienceId = convenienceService.getConvenienceIdByOwnerId(userId);
				model.addAttribute("convenienceId", convenienceId);
				List<OwnerVO> ownerIdList = ownerService.getOwnerListByUsername(userId);
				List<QuestionVO> myQuestionList = new ArrayList<>();
				for (OwnerVO ownerVO : ownerIdList) {
					List<QuestionVO> questions = questionService.getPagedQuestionListByOwnerId(ownerVO.getOwnerId(),
							pagination);
					myQuestionList.addAll(questions); // 게시글 리스트에 추가
				}
				log.info("해당 owner가 볼 수 있는 게시글 목록: " + myQuestionList);
				
				pagination.setPageSize(10);
				PageMaker pageMaker = new PageMaker();
				pageMaker.setPagination(pagination);
				pageMaker.setTotalCount(questionService.getTotalCount());
				
				model.addAttribute("pageMaker", pageMaker);
				model.addAttribute("ownerIdList", ownerIdList); // 매장 목록 추가
				model.addAttribute("myQuestionList", myQuestionList); // 해당 owner가 볼 수 있는 게시글 목록 추가
			}
		}

	}

}
