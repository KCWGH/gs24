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
import com.gs24.website.domain.NoticeVO;
import com.gs24.website.service.NoticeService;
import com.gs24.website.util.PageMaker;
import com.gs24.website.util.Pagination;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping(value = "/notice")
@Log4j
public class NoticeController {
	
	@Autowired
	private NoticeService noticeService;
	
	@GetMapping("/list")
<<<<<<< Updated upstream
	public void list(Model model, Pagination pagination, HttpSession session) {
	    log.info("list()");
	    log.info("pagination = " + pagination);
	    
	    MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
	    
	    if (memberVO == null) {
	    	
=======
	public void list(Model model, Pagination pagination, 
	                 HttpSession session, 
	                 @RequestParam(value = "noticeTitle", required = false) String noticeTitle,
	                 @RequestParam(value = "noticeContent", required = false) String noticeContent,
	                 @RequestParam(value = "searchType", required = false) String searchType) {
	    log.info("list() with title = " + noticeTitle + ", content = " + noticeContent + ", searchType = " + searchType); 

	    List<NoticeVO> noticeList;
	    int totalCount;

	    // 제목으로 검색
	    if ("title".equals(searchType) && noticeTitle != null && !noticeTitle.isEmpty()) {
	        noticeList = noticeService.getNoticesByTitleWithPagination(noticeTitle, pagination);
	        totalCount = noticeService.getTotalCountByTitle(noticeTitle);
	    } 
	    // 내용으로 검색
	    else if ("content".equals(searchType) && noticeContent != null && !noticeContent.isEmpty()) {
	        noticeList = noticeService.getNoticesByContentWithPagination(noticeContent, pagination);
	        totalCount = noticeService.getTotalCountByContent(noticeContent);
	    } 
	    // 제목과 내용 없이 전체 목록 조회
	    else {
	        noticeList = noticeService.getPagingNotices(pagination);
	        totalCount = noticeService.getTotalCount();
>>>>>>> Stashed changes
	    }
	    
	    List<NoticeVO> noticeList = noticeService.getPagingNotices(pagination);

	    PageMaker pageMaker = new PageMaker();
	    pageMaker.setPagination(pagination);
	    pageMaker.setTotalCount(noticeService.getTotalCount());

	    model.addAttribute("pageMaker", pageMaker);
<<<<<<< Updated upstream
	    model.addAttribute("noticeList", noticeList);
	    
	    model.addAttribute("memberVO", memberVO);
	}
	
=======
	    model.addAttribute("noticeTitle", noticeTitle); // 검색어 전달
	    model.addAttribute("noticeContent", noticeContent); // 검색어 전달
	    model.addAttribute("searchType", searchType); // 검색 유형 전달
	}
	
	// 등록 페이지 호출
>>>>>>> Stashed changes
	@GetMapping("/register")
	public void registerGET() {
		log.info("registerGET()");
	}
<<<<<<< Updated upstream
	
=======

	// 등록 처리
>>>>>>> Stashed changes
	@PostMapping("/register")
	public String registerPOST(NoticeVO noticeVO) {
		log.info("registerPOST()");
		log.info("noticeVO = " + noticeVO.toString());
		int result = noticeService.createNotice(noticeVO);
		log.info(result + "�� ����");
		return "redirect:/notice/list";
	}
<<<<<<< Updated upstream

=======
	
	// 상세 조회
>>>>>>> Stashed changes
	@GetMapping("/detail")
	public void detail(Model model, Integer noticeId) {
		log.info("detail()");
		NoticeVO noticeVO = noticeService.getNoticeById(noticeId);
		model.addAttribute("noticeVO", noticeVO); 
	}
	
<<<<<<< Updated upstream
=======
	// 수정 페이지 호출
>>>>>>> Stashed changes
	@GetMapping("/modify")
	public void modifyGET(Model model, Integer noticeId) {
		log.info("modifyGET()");
		NoticeVO noticeVO = noticeService.getNoticeById(noticeId);
		model.addAttribute("noticeVO", noticeVO);
	}
	
<<<<<<< Updated upstream
=======
	// 수정 처리
>>>>>>> Stashed changes
	@PostMapping("/modify")
	public String modifyPOST(NoticeVO noticeVO) {
		log.info("modifyPOST()");
		int result = noticeService.updateNotice(noticeVO);
		log.info(result + "�� ����");
		return "redirect:/notice/list";
	}
	
<<<<<<< Updated upstream
=======
	// 삭제 처리
>>>>>>> Stashed changes
	@PostMapping("/delete")
	public String delete(Integer noticeId) {
	    log.info("delete()");
	    int result = noticeService.deleteNotice(noticeId);
	    log.info(result + "�� ����");
	    return "redirect:/notice/list";
	}
<<<<<<< Updated upstream
}
=======
}
>>>>>>> Stashed changes
