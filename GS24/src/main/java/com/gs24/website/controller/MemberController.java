package com.gs24.website.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gs24.website.domain.MemberVO;
import com.gs24.website.service.MemberService;

import lombok.extern.log4j.Log4j;

@Controller // @Component
@RequestMapping(value = "/member")
@Log4j
public class MemberController {

    @Autowired
    private MemberService memberService;

<<<<<<< Updated upstream
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
=======
    // 회원 가입 페이지
    @GetMapping("/register")
    public String registerGET(HttpSession session) {
        log.info("registerGET()");
        if (session.getAttribute("memberId") != null) {
            log.info("이미 로그인 상태");
            return "redirect:/food/list";
        }
        return "/member/register";
    }

    // 회원 가입 처리
    @PostMapping("/register")
    public String registerPOST(@ModelAttribute MemberVO memberVO) {
        log.info("registerPOST()");
        log.info(memberVO);
        int result = memberService.register(memberVO);
        log.info(result + "개 행 등록 완료");
        if (result == 1) {
            return "redirect:/member/registersuccess";
        }
        return "redirect:/member/registerfail";
    }

    // 회원 가입 성공 페이지
    @GetMapping("/registersuccess")
    public void registersuccessGET() {
        log.info("registerSuccessGET()");
    }
>>>>>>> Stashed changes

    // 회원 가입 실패 페이지
    @GetMapping("/registerfail")
    public void registerfailGET() {
        log.info("registerFailGET()");
    }

    // 로그인 페이지
    @GetMapping("/login")
    public String loginGET(HttpSession session) {
        if (session.getAttribute("memberId") != null) {
            log.info("loginGET() - 세션이 이미 존재합니다");
            return "redirect:/food/list";
        }
        return "/member/login";
    }

<<<<<<< Updated upstream
	@GetMapping("/login")
	public String loginGET(HttpSession session) {
		if (session.getAttribute("memberId") != null) {
			log.info("loginGET() - 세션이 이미 존재합니다");
			return "redirect:/food/list";
		}
		log.info("loginGET()");
		return "/member/login";
	}
=======
    // 로그인 처리
    @PostMapping("/login")
    public String loginPOST(String memberId, String password, HttpServletRequest request) {
        log.info("loginPOST()");
        int result = memberService.login(memberId, password);

        if (result == 1) {
            log.info("로그인 성공");
            HttpSession session = request.getSession();
            session.setAttribute("memberId", memberId);

            MemberVO memberVO = memberService.getMember(memberId);
            session.setAttribute("memberVO", memberVO);
            session.setMaxInactiveInterval(600); // 세션 유지시간 설정
            return "redirect:/food/list";
        } else {
            log.info("로그인 실패");
            return "redirect:/member/loginfail";
        }
    }
>>>>>>> Stashed changes

    // 로그인 실패 페이지
    @GetMapping("/loginfail")
    public void loginfailGET() {
        log.info("loginFailGET()");
    }

    // 아이디 찾기 페이지
    @GetMapping("/findid")
    public String findidGET(HttpSession session) {
        if (session.getAttribute("memberId") != null) {
            log.info("findIdGET() - 세션이 이미 존재합니다");
            return "redirect:/food/list";
        }
        return "/member/findid";
    }

    // 비밀번호 찾기 페이지
    @GetMapping("/findpw")
    public String findpwGET(HttpSession session) {
        if (session.getAttribute("memberId") != null) {
            log.info("findpwGET - 세션이 이미 존재합니다");
            return "redirect:/food/list";
        }
        return "/member/findpw";
    }

    // 인증 코드 확인 페이지
    @GetMapping("/verifycode")
    public void verifycodeGET() {
        log.info("verifyCodeGET()");
    }

    // 마이페이지
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

<<<<<<< Updated upstream
	@GetMapping("/login-fail")
	public void loginfailGET() {
		log.info("loginFailGET()");
	}

	@GetMapping("/find-id")
	public String findIdGET(HttpSession session) {
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
	
	@GetMapping("/verifycode")
	public void verifycodeGET() {
		log.info("verifyCodeGET()");
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

} // end BoardController
=======
    // 로그아웃 처리
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        log.info("session.invalidate()");
        return "redirect:../food/list";
    }
}
>>>>>>> Stashed changes
