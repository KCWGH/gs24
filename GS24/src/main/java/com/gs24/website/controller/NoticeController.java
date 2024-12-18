package com.gs24.website.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

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
	
	// 전체 게시글 데이터를 list.jsp 페이지로 전송
	@GetMapping("/list")
	public void list(Model model, Pagination pagination, 
	                 HttpSession session, 
	                 @RequestParam(value = "noticeTitle", required = false) String noticeTitle,
	                 @RequestParam(value = "noticeContent", required = false) String noticeContent) {
	    log.info("list() with title = " + noticeTitle + "content" + noticeContent); // 12.18 여기까지 

	    List<NoticeVO> noticeList;
	    int totalCount;

	    if (noticeTitle != null && !noticeTitle.isEmpty()) {
	        // 검색 조건이 있는 경우
	        noticeList = noticeService.getNoticesByTitleWithPagination(noticeTitle, pagination);
	        totalCount = noticeService.getTotalCountByTitle(noticeTitle);
	    } else {
	        // 검색 조건이 없는 경우
	        noticeList = noticeService.getPagingNotices(pagination);
	        totalCount = noticeService.getTotalCount();
	    }

	    // 페이지 메이커 생성
	    PageMaker pageMaker = new PageMaker();
	    pageMaker.setPagination(pagination);
	    pageMaker.setTotalCount(totalCount);

	    // 모델에 데이터 추가
	    model.addAttribute("noticeList", noticeList);
	    model.addAttribute("pageMaker", pageMaker);
	    model.addAttribute("noticeTitle", noticeTitle); // 검색어 전달
	    
	}

	
	// register.jsp 호출
	@GetMapping("/register")
	public void registerGET() {
		log.info("registerGET()");
	}
	
	// register.jsp에서 전송받은 게시글 데이터를 저장
	@PostMapping("/register")
	public String registerPOST(NoticeVO noticeVO) {
		log.info("registerPOST()");
		log.info("noticeVO = " + noticeVO.toString());
		int result = noticeService.createNotice(noticeVO);
		log.info(result + "행 등록 ");
		return "redirect:/notice/list";
	}
	
	// list.jsp에서 선택된 게시글 번호를 바탕으로 게시글 상세 조회
	// 조회된 게시글 데이터를 detail.jsp로 전송
	@GetMapping("/detail")
	public void detail(Model model, Integer noticeId) {
		log.info("detail()");
		NoticeVO noticeVO = noticeService.getNoticeById(noticeId);
		model.addAttribute("noticeVO", noticeVO); 
	}
	
	// 게시글 번호를 전송받아 상세 게시글 조회
	// 조회된 게시글 데이터를 modify.jsp로 전송
	@GetMapping("/modify")
	public void modifyGET(Model model, Integer noticeId) {
		log.info("modifyGET()");
		NoticeVO noticeVO = noticeService.getNoticeById(noticeId);
		model.addAttribute("noticeVO", noticeVO);
	}
	
	// modify.jsp에서 데이터를 전송받아 게시글 수정
	@PostMapping("/modify")
	public String modifyPOST(NoticeVO noticeVO) {
		log.info("modifyPOST()");
		int result = noticeService.updateNotice(noticeVO);
		log.info(result + "행 수정");
		return "redirect:/notice/list";
	}
	
	// detail.jsp에서 boardId를 전송받아 게시글 데이터 삭제
	@PostMapping("/delete")
	public String delete(Integer noticeId) {
	    log.info("delete()");
	    int result = noticeService.deleteNotice(noticeId);
	    log.info(result + "행 삭제");
	    return "redirect:/notice/list";
	}
	
	// 제목으로 게시글 검색
    @GetMapping("/search")
    public String search(Model model, @RequestParam String noticeTitle, Pagination pagination) {
        log.info("search() with title = " + noticeTitle);
        
        // 제목으로 검색한 게시글 리스트 가져오기
        List<NoticeVO> noticeList = noticeService.getNoticesByTitle(noticeTitle);

        // 페이지 메이커 생성
        PageMaker pageMaker = new PageMaker();
        pageMaker.setPagination(pagination);
        pageMaker.setTotalCount(noticeService.getTotalCount());

        // 모델에 데이터 추가
        model.addAttribute("noticeList", noticeList);
        model.addAttribute("pageMaker", pageMaker);
        model.addAttribute("title", noticeTitle);  // 검색어를 JSP로 전달

        return "redirect:/notice/list"; // 목록 페이지로 리턴
    }
}
