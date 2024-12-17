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
      log.info(memberVO);
      int result = memberService.register(memberVO);
      log.info(result + "개 행 등록 완료");
      if (result == 1) {
         return "redirect:/member/registersuccess";
      }
      return "redirect:/member/registerfail";
   }

   @GetMapping("/registersuccess")
   public void registersuccessGET() {
      log.info("registerSuccessGET()");
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
         HttpSession session = request.getSession();
         session.setAttribute("memberId", memberId);

         MemberVO memberVO = memberService.getMember(memberId); // memberId로 memberVO를 조회
         session.setAttribute("memberVO", memberVO); // memberVO를 세션에 저장

         session.setMaxInactiveInterval(600);
         return "redirect:/food/list";
      } else {
         log.info("로그인 실패");
         return "redirect:/member/loginfail";
      }
   }

   @GetMapping("/loginfail")
   public void loginfailGET() {
      log.info("loginFailGET()");
   }

   @GetMapping("/findid")
   public String findidGET(HttpSession session) {
      if (session.getAttribute("memberId") != null) {
         log.info("findIdGET() - 세션이 이미 존재합니다");
         return "redirect:/food/list";
      }
      log.info("findIdGET()");
      return "/member/findid";
   }


   @GetMapping("/findpw")
   public String findpwGET(HttpSession session) {
      if (session.getAttribute("memberId") != null) {
         log.info("findpwGET - 세션이 이미 존재합니다");
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
         log.info("mypageGET() - 세션이 없습니다");
      }
   }

   @GetMapping("/logout")
   public String logout(HttpSession session) {
      session.invalidate();
      log.info("session.invalidate()");
      return "redirect:login";
   }

} // end BoardController
