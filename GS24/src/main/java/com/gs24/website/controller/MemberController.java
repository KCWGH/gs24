package com.gs24.website.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
=======
>>>>>>> ccdce2e7a9f17201e6bb89e46d18852272cf8bfd
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gs24.website.domain.MemberVO;
import com.gs24.website.service.MemberService;

import lombok.extern.log4j.Log4j;

@Controller // @Component
@RequestMapping(value = "/member")
@Log4j
public class MemberController {

<<<<<<< HEAD
   @Autowired
   private MemberService memberService;

   // register.jsp
   @GetMapping("/register")
   public String registerGET(HttpSession session) {
      log.info("registerGET()");
      if (session.getAttribute("memberId") != null) {
         log.info("�씠誘� 濡쒓렇�씤 �긽�깭");
         return "redirect:/food/list";
      }
      log.info("濡쒓렇�씤 �럹�씠吏�濡� �씠�룞");
      return "/member/register";
   }
   
   @PostMapping("/register")
   public String registerPOST(@ModelAttribute MemberVO memberVO) {
      log.info("registerPOST()");
      log.info(memberVO);
      int result = memberService.register(memberVO);
      log.info(result + "媛� �뻾 �벑濡� �셿猷�");
      if (result == 1) {
         return "redirect:/member/registersuccess";
      }
      return "redirect:/member/registerfail";
   }
=======
	@Autowired
	private MemberService memberService;

	// register.jsp
	@GetMapping("/register")
	public String registerGET(HttpSession session) {
		log.info("registerGET()");
		if (session.getAttribute("memberId") != null) {
			log.info("이미 로그인 상태");
			return "redirect:/food/list";
		}
		log.info("로그인 페이지로 이동");
		return "/member/register";
	}

	@PostMapping("/register")
	public String registerPOST(@ModelAttribute MemberVO memberVO) {
		log.info("registerPOST()");
		int result = memberService.register(memberVO);
		log.info(result + "개 행 등록 완료");
		if (result == 1) {
			return "redirect:/member/register-success";
		}
		return "redirect:/member/register-fail";
	}
>>>>>>> ccdce2e7a9f17201e6bb89e46d18852272cf8bfd

	@GetMapping("/register-success")
	public void registerSuccessGET() {
		log.info("registerSuccessGET()");
	}

<<<<<<< HEAD
   @GetMapping("/login")
   public String loginGET(HttpSession session) {
      if (session.getAttribute("memberId") != null) {
         log.info("loginGET() - �꽭�뀡�씠 �씠誘� 議댁옱�빀�땲�떎");
         return "redirect:/food/list";
      }
      log.info("loginGET()");
      return "/member/login";
   }
   
   @PostMapping("/login")
   public String loginPOST(String memberId, String password, HttpServletRequest request) {
      log.info("loginPOST()");
=======
	@GetMapping("/register-fail")
	public void registerFailGET() {
		log.info("registerFailGET()");
	}

	@GetMapping("/login")
	public String loginGET(HttpSession session) {
		if (session.getAttribute("memberId") != null) {
			log.info("loginGET() - 세션이 이미 존재합니다");
			return "redirect:/food/list";
		}
		log.info("loginGET()");
		return "/member/login";
	}

	@PostMapping("/login")
	public String loginPOST(String memberId, String password, HttpServletRequest request) {
		log.info("loginPOST()");

		int result = memberService.login(memberId, password);

		if (result == 1) {
			log.info("로그인 성공");

			// 세션 설정
			HttpSession session = request.getSession();
			session.setAttribute("memberId", memberId);

			MemberVO memberVO = memberService.getMember(memberId);
			session.setAttribute("memberVO", memberVO);

			session.setMaxInactiveInterval(600);

			return "redirect:/food/list?loginSuccess=true";
		} else {
			log.info("로그인 실패");
			return "redirect:/member/loginfail";
		}
	}
>>>>>>> ccdce2e7a9f17201e6bb89e46d18852272cf8bfd

	@GetMapping("/loginfail")
	public void loginfailGET() {
		log.info("loginFailGET()");
	}

<<<<<<< HEAD
      if (result == 1) {
         log.info("濡쒓렇�씤 �꽦怨�");
         HttpSession session = request.getSession();
         session.setAttribute("memberId", memberId);
      }
      return "redirect:/food/list";
    }

	


	@PostMapping("/update")
	@ResponseBody
	public ResponseEntity<Integer> mypagePOST(MemberVO memberVO) {
		int result = memberService.updateMember(memberVO);
		if (result == 0) {
		    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(0);
		}
		return ResponseEntity.ok(result);

	}

   @GetMapping("/loginfail")
   public void loginfailGET() {
      log.info("loginFailGET()");
   }

   @GetMapping("/findid")
   public String findidGET(HttpSession session) {
      if (session.getAttribute("memberId") != null) {
         log.info("findIdGET() - �꽭�뀡�씠 �씠誘� 議댁옱�빀�땲�떎");
         return "redirect:/food/list";
      }
      log.info("findIdGET()");
      return "/member/findid";
   }


   @GetMapping("/findpw")
   public String findpwGET(HttpSession session) {
      if (session.getAttribute("memberId") != null) {
         log.info("findpwGET - �꽭�뀡�씠 �씠誘� 議댁옱�빀�땲�떎");
         return "redirect:/food/list";
      }
      log.info("findpwGET");
      return "/member/findpw";
   }

   @GetMapping("/mypage")
   public void mypageGET(HttpSession session, Model model) {
      log.info("mypageGET()");
      String memberId = (String) session.getAttribute("memberId");
      if (memberId != null) {
         MemberVO memberVO = memberService.getMember(memberId);
         model.addAttribute("memberId", memberId);
         model.addAttribute("memberVO", memberVO);
      } else {
         log.info("mypageGET() - �꽭�뀡�씠 �뾾�뒿�땲�떎");
      }
   }

   @GetMapping("/logout")
   public String logout(HttpSession session) {
      session.invalidate();
      log.info("session.invalidate()");
      return "redirect:login";
   }
=======
	@GetMapping("/find-id")
	public String findIdGET(HttpSession session) {
		if (session.getAttribute("memberId") != null) {
			log.info("findIdGET() - 세션이 이미 존재합니다");
			return "redirect:/food/list";
		}
		log.info("findIdGET()");
		return "/member/find-id";
	}

	@GetMapping("/find-pw")
	public String findPwGET(HttpSession session) {
		if (session.getAttribute("memberId") != null) {
			log.info("findPwGET - 세션이 이미 존재합니다");
			return "redirect:/food/list";
		}
		log.info("findPwGET");
		return "/member/find-pw";
	}

	@GetMapping("/mypage")
	public void mypageGET(HttpSession session, Model model) {
		log.info("mypageGET()");
		String memberId = (String) session.getAttribute("memberId");
		if (memberId != null) {
			MemberVO memberVO = memberService.getMember(memberId);
			model.addAttribute("memberId", memberId);
			model.addAttribute("memberVO", memberVO);
		} else {
			log.info("mypageGET() - 세션이 없습니다");
		}
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		log.info("session.invalidate()");
		return "redirect:../food/list";
	}
>>>>>>> ccdce2e7a9f17201e6bb89e46d18852272cf8bfd

} // end BoardController
