package com.gs24.website.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gs24.website.domain.NoticeVO;
import com.gs24.website.service.NoticeService;
import com.gs24.website.util.PageMaker;
import com.gs24.website.util.Pagination;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping(value = "/notice")
@Log4j
public class NoticeController {
<<<<<<< Updated upstream
	
	@Autowired
	private NoticeService noticeService;
	
	// 전체 게시글 데이터를 list.jsp 페이지로 전송
	@GetMapping("/list")
	public void list(Model model, Pagination pagination, HttpSession session) {
	    log.info("list()");
	    log.info("pagination = " + pagination);
	    
	    // 세션에서 memberVO 가져오기
	    MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
	    
	    // memberVO가 null이면 경고 로그 출력
	    if (memberVO == null) {
	        log.warn("세션에 memberVO가 존재하지 않습니다. 로그인 필요.");
	    }
	    
	    // 공지사항 목록 가져오기
	    List<NoticeVO> noticeList = noticeService.getPagingNotices(pagination);

	    // 페이지 메이커 생성
	    PageMaker pageMaker = new PageMaker();
	    pageMaker.setPagination(pagination);
	    pageMaker.setTotalCount(noticeService.getTotalCount());

	    // 모델에 값 추가
	    model.addAttribute("pageMaker", pageMaker);
	    model.addAttribute("noticeList", noticeList);
	    
	    // 세션에서 가져온 memberVO를 모델에 추가
	    model.addAttribute("memberVO", memberVO); // memberVO를 JSP로 전달
	}
	
	// register.jsp 호출
=======

	@Autowired
	private NoticeService noticeService;

	@GetMapping("/list")
	public void list(Model model, Pagination pagination, HttpSession session,
			@RequestParam(value = "noticeTitle", required = false) String noticeTitle,
			@RequestParam(value = "noticeContent", required = false) String noticeContent,
			@RequestParam(value = "searchType", required = false) String searchType) {
		log.info(" title = " + noticeTitle + ", content = " + noticeContent + ", searchType = " + searchType);
		List<NoticeVO> noticeList;
		int totalCount;
		if ("title".equals(searchType) && noticeTitle != null && !noticeTitle.isEmpty()) {
			noticeList = noticeService.getNoticesByTitleWithPagination(noticeTitle, pagination);
			totalCount = noticeService.getTotalCountByTitle(noticeTitle);
		} else if ("content".equals(searchType) && noticeContent != null && !noticeContent.isEmpty()) {
			noticeList = noticeService.getNoticesByContentWithPagination(noticeContent, pagination);
			totalCount = noticeService.getTotalCountByContent(noticeContent);
		} else {
			noticeList = noticeService.getPagingNotices(pagination);
			totalCount = noticeService.getTotalCount();
		}
		PageMaker pageMaker = new PageMaker();
		pageMaker.setPagination(pagination);
		pageMaker.setTotalCount(totalCount);
		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("noticeTitle", noticeTitle);
		model.addAttribute("noticeContent", noticeContent);
		model.addAttribute("searchType", searchType);
	}

>>>>>>> Stashed changes
	@GetMapping("/register")
	public void registerGET() {
		log.info("registerGET()");
	}
<<<<<<< Updated upstream
	
	// register.jsp에서 전송받은 게시글 데이터를 저장
=======

>>>>>>> Stashed changes
	@PostMapping("/register")
	public String registerPOST(NoticeVO noticeVO) {
		log.info("registerPOST()");
		log.info("noticeVO = " + noticeVO.toString());
		int result = noticeService.createNotice(noticeVO);
<<<<<<< Updated upstream
		log.info(result + "행 등록 ");
		return "redirect:/notice/list";
	}
	
	// list.jsp에서 선택된 게시글 번호를 바탕으로 게시글 상세 조회
	// 조회된 게시글 데이터를 detail.jsp로 전송
=======
		log.info(result + "건 등록");
		return "redirect:/notice/list";
	}

>>>>>>> Stashed changes
	@GetMapping("/detail")
	public void detail(Model model, Integer noticeId) {
		log.info("detail()");
		NoticeVO noticeVO = noticeService.getNoticeById(noticeId);
		model.addAttribute("noticeVO", noticeVO); 
	}
<<<<<<< Updated upstream
	
	// 게시글 번호를 전송받아 상세 게시글 조회
	// 조회된 게시글 데이터를 modify.jsp로 전송
=======

>>>>>>> Stashed changes
	@GetMapping("/modify")
	public void modifyGET(Model model, Integer noticeId) {
		log.info("modifyGET()");
		NoticeVO noticeVO = noticeService.getNoticeById(noticeId);
		model.addAttribute("noticeVO", noticeVO);
	}
<<<<<<< Updated upstream
	
	// modify.jsp에서 데이터를 전송받아 게시글 수정
=======

>>>>>>> Stashed changes
	@PostMapping("/modify")
	public String modifyPOST(NoticeVO noticeVO) {
		log.info("modifyPOST()");
		int result = noticeService.updateNotice(noticeVO);
		log.info(result + "건 수정");
		return "redirect:/notice/list";
	}
<<<<<<< Updated upstream
	
	// detail.jsp에서 boardId를 전송받아 게시글 데이터 삭제
	@PostMapping("/delete")
	public String delete(Integer noticeId) {
	    log.info("delete()");
	    int result = noticeService.deleteNotice(noticeId);
	    log.info(result + "행 삭제");
	    return "redirect:/notice/list";
=======

	@PostMapping("/delete")
	public String delete(Integer noticeId) {
		log.info("delete()");
		int result = noticeService.deleteNotice(noticeId);
		log.info(result + "건 삭제");
		return "redirect:/notice/list";
>>>>>>> Stashed changes
	}
}
