package com.gs24.website.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gs24.website.domain.NoticeVO;
import com.gs24.website.service.ConvenienceService;
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

	@Autowired
	private ConvenienceService convenienceService;

	@GetMapping("/list")
	public void list(Authentication auth, Model model, Pagination pagination) {
		log.info("list()");
		
		String userRole = getUserRole(auth);
		pagination.setUserRole(userRole);
		
		pagination.setPageSize(10);
        int totalCount = noticeService.getTotalCount(pagination);
        PageMaker pageMaker = createPageMaker(pagination, totalCount);
        
		List<NoticeVO> noticeList = noticeService.getPagedNotices(pagination);

		model.addAttribute("convenienceId", getConvenienceIdIfOwner(auth));
		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("noticeList", noticeList);

	}

	@GetMapping("/register")
	public void registerGET() {
		log.info("registerGET()");
	}

	@PostMapping("/register")
	public String registerPOST(NoticeVO noticeVO, Principal principal) {
	    log.info("registerPOST()");

	    // 현재 로그인한 관리자의 ID 가져오기
	    String adminId = principal.getName();

	    // NoticeVO에 adminId 설정
	    noticeVO.setAdminId(adminId);

	    // DB에 저장
	    int result = noticeService.createNotice(noticeVO);
	    log.info(result + "건 등록 (관리자 ID: " + adminId + ")");

	    return "redirect:/notice/list";
	}

	@GetMapping("/detail")
	public void detail(Authentication auth, Model model, Integer noticeId) {
		log.info("detail()");
		NoticeVO noticeVO = noticeService.getNoticeById(noticeId);
		model.addAttribute("convenienceId", getConvenienceIdIfOwner(auth));
		model.addAttribute("noticeVO", noticeVO);
	}

	@GetMapping("/modify")
	public void modifyGET(Model model, Integer noticeId) {
		log.info("modifyGET()");
		model.addAttribute("noticeVO", noticeService.getNoticeById(noticeId));
	}

	@PostMapping("/modify")
	public String modifyPOST(NoticeVO noticeVO, Pagination pagination, RedirectAttributes reAttr) {
		log.info("modifyPOST()");
		int result = noticeService.updateNotice(noticeVO);
		log.info(result + "건 수정");

		reAttr.addAttribute("pageNum", pagination.getPageNum());
		reAttr.addAttribute("pageSize", pagination.getPageSize());
		reAttr.addAttribute("type", pagination.getType());
		reAttr.addAttribute("keyword", pagination.getKeyword());
		return "redirect:/notice/list";
	}

	@PostMapping("/delete")
	public String delete(Integer noticeId) {
		log.info("delete()");
		int result = noticeService.deleteNotice(noticeId);
		log.info(result + "건 삭제");
		return "redirect:/notice/list";
	}
	
	private PageMaker createPageMaker(Pagination pagination, int totalCount) {
        PageMaker pageMaker = new PageMaker();
        pageMaker.setPagination(pagination);
        pageMaker.setTotalCount(totalCount);
        return pageMaker;
    }
	
	 private Integer getConvenienceIdIfOwner(Authentication auth) {
	        if (auth != null && auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_OWNER"))) {
	            return convenienceService.getConvenienceIdByOwnerId(auth.getName());
	        }
	        return null;
	    }
	 
	 private String getUserRole(Authentication auth) {
		    if (auth != null && auth.getAuthorities() != null) {
		        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
		            return "ROLE_ADMIN";
		        } else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_OWNER"))) {
		            return "ROLE_OWNER";
		        }
		    }
		    return "ROLE_MEMBER"; 
		}
}
