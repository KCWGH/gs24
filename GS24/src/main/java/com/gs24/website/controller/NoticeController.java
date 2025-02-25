package com.gs24.website.controller;

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
		log.info("pagination" + pagination);
		pagination.setPageSize(10);
		List<NoticeVO> noticeList = noticeService.getPagedNotices(pagination);

		if (auth != null) {
			String username = auth.getName();
			if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_OWNER"))) {
				int convenienceId = convenienceService.getConvenienceIdByOwnerId(username);
				model.addAttribute("convenienceId", convenienceId);
			}
		}

		PageMaker pageMaker = new PageMaker();
		pageMaker.setPagination(pagination);
		pageMaker.setTotalCount(noticeService.getTotalCount(pagination));

		log.info(pageMaker);
		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("noticeList", noticeList);

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
	public void detail(Authentication auth, Model model, Integer noticeId) {
		log.info("detail()");
		if (auth != null) {
			String username = auth.getName();
			if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_OWNER"))) {
				int convenienceId = convenienceService.getConvenienceIdByOwnerId(username);
				model.addAttribute("convenienceId", convenienceId);
			}
		}
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
}
