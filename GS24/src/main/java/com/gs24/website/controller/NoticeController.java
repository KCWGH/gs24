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
	
<<<<<<< HEAD
	// ÀüÃ¼ °Ô½Ã±Û µ¥ÀÌÅÍ¸¦ list.jsp ÆäÀÌÁö·Î Àü¼Û
		@GetMapping("/list")
		public void list(Model model, Pagination pagination, HttpSession session) {
		    log.info("list()");
		    log.info("pagination = " + pagination);
		    
		    // ¼¼¼Ç¿¡¼­ memberVO °¡Á®¿À±â
		    MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");

		    // memberVO°¡ nullÀÌ¸é °æ°í ·Î±× Ãâ·Â
		    if (memberVO == null) {
		    	log.warn("¼¼¼Ç¿¡ memberVO°¡ Á¸ÀçÇÏÁö ¾Ê½À´Ï´Ù. ·Î±×ÀÎ ÇÊ¿ä.");
		    }
		 // °øÁö»çÇ× ¸ñ·Ï °¡Á®¿À±â
		    List<NoticeVO> noticeList = noticeService.getPagingNotices(pagination);	
		    
		    // ÆäÀÌÁö ¸ŞÀÌÄ¿ »ı¼º
		    PageMaker pageMaker = new PageMaker();
		    pageMaker.setPagination(pagination);
		    pageMaker.setTotalCount(noticeService.getTotalCount());
		    
		    // ¸ğµ¨¿¡ °ª Ãß°¡
		    model.addAttribute("pageMaker", pageMaker);
		    model.addAttribute("noticeList", noticeList);
		    // ¼¼¼Ç¿¡¼­ °¡Á®¿Â memberVO¸¦ ¸ğµ¨¿¡ Ãß°¡
		    model.addAttribute("memberVO", memberVO); // memberVO¸¦ JSP·Î Àü´Ş
		}
		
		// register.jsp È£Ãâ
		@GetMapping("/register")
		public void registerGET() {
			log.info("registerGET()");
		}
		
		// register.jsp¿¡¼­ Àü¼Û¹ŞÀº °Ô½Ã±Û µ¥ÀÌÅÍ¸¦ ÀúÀå
		@PostMapping("/register")
		public String registerPOST(NoticeVO noticeVO) {
			log.info("registerPOST()");
			log.info("noticeVO = " + noticeVO.toString());
			int result = noticeService.createNotice(noticeVO);
			return "redirect:/notice/list";
		}
		
		// list.jsp¿¡¼­ ¼±ÅÃµÈ °Ô½Ã±Û ¹øÈ£¸¦ ¹ÙÅÁÀ¸·Î °Ô½Ã±Û »ó¼¼ Á¶È¸
		// Á¶È¸µÈ °Ô½Ã±Û µ¥ÀÌÅÍ¸¦ detail.jsp·Î Àü¼Û
		@GetMapping("/detail")
		public void detail(Model model, Integer noticeId) {
			log.info("detail()");
			NoticeVO noticeVO = noticeService.getNoticeById(noticeId);
			model.addAttribute("noticeVO", noticeVO); 
		}
		
		// °Ô½Ã±Û ¹øÈ£¸¦ Àü¼Û¹Ş¾Æ »ó¼¼ °Ô½Ã±Û Á¶È¸
		// Á¶È¸µÈ °Ô½Ã±Û µ¥ÀÌÅÍ¸¦ modify.jsp·Î Àü¼Û
		@GetMapping("/modify")
		public void modifyGET(Model model, Integer noticeId) {
			log.info("modifyGET()");
			NoticeVO noticeVO = noticeService.getNoticeById(noticeId);
			model.addAttribute("noticeVO", noticeVO);
		}
		
		// modify.jsp¿¡¼­ µ¥ÀÌÅÍ¸¦ Àü¼Û¹Ş¾Æ °Ô½Ã±Û ¼öÁ¤
		@PostMapping("/modify")
		public String modifyPOST(NoticeVO noticeVO) {
			log.info("modifyPOST()");
			int result = noticeService.updateNotice(noticeVO);
			log.info(result + "Çà ¼öÁ¤");
			return "redirect:/notice/list";
		}
		
		// detail.jsp¿¡¼­ boardId¸¦ Àü¼Û¹Ş¾Æ °Ô½Ã±Û µ¥ÀÌÅÍ »èÁ¦
		@PostMapping("/delete")
		public String delete(Integer noticeId) {
		    log.info("delete()");
		    int result = noticeService.deleteNotice(noticeId);
		    log.info(result + "Çà »èÁ¦");
		    return "redirect:/notice/list";
		}
}

=======
	// ì „ì²´ ê²Œì‹œê¸€ ë°ì´í„°ë¥¼ list.jsp í˜ì´ì§€ë¡œ ì „ì†¡
	@GetMapping("/list")
	public void list(Model model, Pagination pagination, HttpSession session) {
	    log.info("list()");
	    log.info("pagination = " + pagination);
	    
	    // ì„¸ì…˜ì—ì„œ memberVO ê°€ì ¸ì˜¤ê¸°
	    MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
	    
	    // memberVOê°€ nullì´ë©´ ê²½ê³  ë¡œê·¸ ì¶œë ¥
	    if (memberVO == null) {
	        log.warn("ì„¸ì…˜ì— memberVOê°€ ì¡´ì¬í•˜ì§€ ì•ŠìŠµë‹ˆë‹¤. ë¡œê·¸ì¸ í•„ìš”.");
	    }
	    
	    // ê³µì§€ì‚¬í•­ ëª©ë¡ ê°€ì ¸ì˜¤ê¸°
	    List<NoticeVO> noticeList = noticeService.getPagingNotices(pagination);

	    // í˜ì´ì§€ ë©”ì´ì»¤ ìƒì„±
	    PageMaker pageMaker = new PageMaker();
	    pageMaker.setPagination(pagination);
	    pageMaker.setTotalCount(noticeService.getTotalCount());

	    // ëª¨ë¸ì— ê°’ ì¶”ê°€
	    model.addAttribute("pageMaker", pageMaker);
	    model.addAttribute("noticeList", noticeList);
	    
	    // ì„¸ì…˜ì—ì„œ ê°€ì ¸ì˜¨ memberVOë¥¼ ëª¨ë¸ì— ì¶”ê°€
	    model.addAttribute("memberVO", memberVO); // memberVOë¥¼ JSPë¡œ ì „ë‹¬
	}
	
	// register.jsp í˜¸ì¶œ
	@GetMapping("/register")
	public void registerGET() {
		log.info("registerGET()");
	}
	
	// register.jspì—ì„œ ì „ì†¡ë°›ì€ ê²Œì‹œê¸€ ë°ì´í„°ë¥¼ ì €ì¥
	@PostMapping("/register")
	public String registerPOST(NoticeVO noticeVO) {
		log.info("registerPOST()");
		log.info("noticeVO = " + noticeVO.toString());
		int result = noticeService.createNotice(noticeVO);
		log.info(result + "í–‰ ë“±ë¡ ");
		return "redirect:/notice/list";
	}
	
	// list.jspì—ì„œ ì„ íƒëœ ê²Œì‹œê¸€ ë²ˆí˜¸ë¥¼ ë°”íƒ•ìœ¼ë¡œ ê²Œì‹œê¸€ ìƒì„¸ ì¡°íšŒ
	// ì¡°íšŒëœ ê²Œì‹œê¸€ ë°ì´í„°ë¥¼ detail.jspë¡œ ì „ì†¡
	@GetMapping("/detail")
	public void detail(Model model, Integer noticeId) {
		log.info("detail()");
		NoticeVO noticeVO = noticeService.getNoticeById(noticeId);
		model.addAttribute("noticeVO", noticeVO); 
	}
	
	// ê²Œì‹œê¸€ ë²ˆí˜¸ë¥¼ ì „ì†¡ë°›ì•„ ìƒì„¸ ê²Œì‹œê¸€ ì¡°íšŒ
	// ì¡°íšŒëœ ê²Œì‹œê¸€ ë°ì´í„°ë¥¼ modify.jspë¡œ ì „ì†¡
	@GetMapping("/modify")
	public void modifyGET(Model model, Integer noticeId) {
		log.info("modifyGET()");
		NoticeVO noticeVO = noticeService.getNoticeById(noticeId);
		model.addAttribute("noticeVO", noticeVO);
	}
	
	// modify.jspì—ì„œ ë°ì´í„°ë¥¼ ì „ì†¡ë°›ì•„ ê²Œì‹œê¸€ ìˆ˜ì •
	@PostMapping("/modify")
	public String modifyPOST(NoticeVO noticeVO) {
		log.info("modifyPOST()");
		int result = noticeService.updateNotice(noticeVO);
		log.info(result + "í–‰ ìˆ˜ì •");
		return "redirect:/notice/list";
	}
	
	// detail.jspì—ì„œ boardIdë¥¼ ì „ì†¡ë°›ì•„ ê²Œì‹œê¸€ ë°ì´í„° ì‚­ì œ
	@PostMapping("/delete")
	public String delete(Integer noticeId) {
	    log.info("delete()");
	    int result = noticeService.deleteNotice(noticeId);
	    log.info(result + "í–‰ ì‚­ì œ");
	    return "redirect:/notice/list";
	}
}
>>>>>>> 5f0e7c57d0a4abf29e5d76e4b4e2974567c8a0d7
