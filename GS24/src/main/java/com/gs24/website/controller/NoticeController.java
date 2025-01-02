package com.gs24.website.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	    public void list(Model model, Pagination pagination,
	                     @RequestParam(value = "noticeTitle", required = false) String noticeTitle,
	                     @RequestParam(value = "noticeContent", required = false) String noticeContent,
	                     @RequestParam(value = "searchType", required = false) String searchType) {

	        log.info("title = " + noticeTitle + ", content = " + noticeContent + ", searchType = " + searchType);

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
	        // 기본: 모든 공지사항 페이징
	        else {
	            noticeList = noticeService.getPagingNotices(pagination);
	            totalCount = noticeService.getTotalCount();
	        }

	        // 페이지네이션 처리
	        PageMaker pageMaker = new PageMaker();
	        pageMaker.setPagination(pagination);
	        pageMaker.setTotalCount(totalCount);

	        // 모델에 데이터 추가
	        model.addAttribute("pageMaker", pageMaker);
	        model.addAttribute("noticeList", noticeList);
	        model.addAttribute("noticeTitle", noticeTitle);
	        model.addAttribute("noticeContent", noticeContent);
	        model.addAttribute("searchType", searchType);
	    }

	@GetMapping("/register")
	public void registerGET() {
		log.info("registerGET()");
	}

	@PostMapping("/register")
	public String registerPOST(NoticeVO noticeVO) {
		log.info("registerPOST()");
		log.info("noticeVO = " + noticeVO.toString());
		int result = noticeService.createNotice(noticeVO);
		log.info(result + "건 등록");
		return "redirect:/notice/list";
	}

	@GetMapping("/detail")
	public void detail(Model model, Integer noticeId) {
		log.info("detail()");
		NoticeVO noticeVO = noticeService.getNoticeById(noticeId);
		model.addAttribute("noticeVO", noticeVO);
	}

	@GetMapping("/modify")
	public void modifyGET(Model model, Integer noticeId) {
		log.info("modifyGET()");
		NoticeVO noticeVO = noticeService.getNoticeById(noticeId);
		model.addAttribute("noticeVO", noticeVO);
	}

	@PostMapping("/modify")
	public String modifyPOST(NoticeVO noticeVO) {
		log.info("modifyPOST()");
		int result = noticeService.updateNotice(noticeVO);
		log.info(result + "건 수정");
		return "redirect:/notice/list";
	}

	@PostMapping("/delete")
	public String delete(Integer noticeId) {
		log.info("delete()");
		int result = noticeService.deleteNotice(noticeId);
		log.info(result + "건 삭제");
		return "redirect:/notice/list";
	}
}
