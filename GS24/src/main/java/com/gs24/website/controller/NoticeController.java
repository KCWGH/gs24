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
	
	// ��ü �Խñ� �����͸� list.jsp �������� ����
		@GetMapping("/list")
		public void list(Model model, Pagination pagination, HttpSession session) {
		    log.info("list()");
		    log.info("pagination = " + pagination);
		    
		    // ���ǿ��� memberVO ��������
		    MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");

		    // memberVO�� null�̸� ��� �α� ���
		    if (memberVO == null) {
		    	log.warn("���ǿ� memberVO�� �������� �ʽ��ϴ�. �α��� �ʿ�.");
		    }
		 // �������� ��� ��������
		    List<NoticeVO> noticeList = noticeService.getPagingNotices(pagination);	
		    
		    // ������ ����Ŀ ����
		    PageMaker pageMaker = new PageMaker();
		    pageMaker.setPagination(pagination);
		    pageMaker.setTotalCount(noticeService.getTotalCount());
		    
		    // �𵨿� �� �߰�
		    model.addAttribute("pageMaker", pageMaker);
		    model.addAttribute("noticeList", noticeList);
		    // ���ǿ��� ������ memberVO�� �𵨿� �߰�
		    model.addAttribute("memberVO", memberVO); // memberVO�� JSP�� ����
		}
		
		// register.jsp ȣ��
		@GetMapping("/register")
		public void registerGET() {
			log.info("registerGET()");
		}
		
		// register.jsp���� ���۹��� �Խñ� �����͸� ����
		@PostMapping("/register")
		public String registerPOST(NoticeVO noticeVO) {
			log.info("registerPOST()");
			log.info("noticeVO = " + noticeVO.toString());
			int result = noticeService.createNotice(noticeVO);
			return "redirect:/notice/list";
		}
		
		// list.jsp���� ���õ� �Խñ� ��ȣ�� �������� �Խñ� �� ��ȸ
		// ��ȸ�� �Խñ� �����͸� detail.jsp�� ����
		@GetMapping("/detail")
		public void detail(Model model, Integer noticeId) {
			log.info("detail()");
			NoticeVO noticeVO = noticeService.getNoticeById(noticeId);
			model.addAttribute("noticeVO", noticeVO); 
		}
		
		// �Խñ� ��ȣ�� ���۹޾� �� �Խñ� ��ȸ
		// ��ȸ�� �Խñ� �����͸� modify.jsp�� ����
		@GetMapping("/modify")
		public void modifyGET(Model model, Integer noticeId) {
			log.info("modifyGET()");
			NoticeVO noticeVO = noticeService.getNoticeById(noticeId);
			model.addAttribute("noticeVO", noticeVO);
		}
		
		// modify.jsp���� �����͸� ���۹޾� �Խñ� ����
		@PostMapping("/modify")
		public String modifyPOST(NoticeVO noticeVO) {
			log.info("modifyPOST()");
			int result = noticeService.updateNotice(noticeVO);
			log.info(result + "�� ����");
			return "redirect:/notice/list";
		}
		
		// detail.jsp���� boardId�� ���۹޾� �Խñ� ������ ����
		@PostMapping("/delete")
		public String delete(Integer noticeId) {
		    log.info("delete()");
		    int result = noticeService.deleteNotice(noticeId);
		    log.info(result + "�� ����");
		    return "redirect:/notice/list";
		}
}

