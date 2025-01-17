package com.gs24.website.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gs24.website.domain.MemberVO;
import com.gs24.website.service.MemberService;
import com.gs24.website.service.RecaptchaService;

import lombok.extern.log4j.Log4j;

@Controller // @Component
@RequestMapping(value = "/auth")
@Log4j
public class AuthController {

   @Autowired
   private MemberService memberService;

   @Autowired
   private RecaptchaService recaptchaService;

   @GetMapping("/accessDenied")
   public void accessDenied(Authentication auth, Model model) {
      // Authentication : 현재 사용자의 인증 정보를 갖고 있음
      log.info("accessDenied()");
      log.info(auth);

      model.addAttribute("msg", "권한이 없습니다.");
   }

   @GetMapping("/register")
   public String registerGET(Principal principal, RedirectAttributes redirectAttributes) {
      if (principal != null) {
           return "redirect:/food/list";
       }
       return "/auth/register";
   }

   @PostMapping("/register")
   public String registerPOST(@ModelAttribute MemberVO memberVO, String recaptchaToken,
         RedirectAttributes redirectAttributes) {
      log.info("registerPOST()");

      boolean isRecaptchaValid = recaptchaService.verifyRecaptcha(recaptchaToken);
      if (!isRecaptchaValid) {
         log.info("reCAPTCHA 검증 실패");
         redirectAttributes.addFlashAttribute("message", "reCAPTCHA 검증에 실패했습니다. 다시 시도해주세요.");
         return "redirect:/auth/register";
      }

      int result = memberService.register(memberVO);
      log.info(result + "개 행 등록 완료");
      if (result == 1) {
         redirectAttributes.addFlashAttribute("message", "회원등록 완료.\\n가입한 아이디와 비밀번호로 로그인하세요.");
         return "redirect:/auth/login";
      }
      redirectAttributes.addFlashAttribute("message", "회원등록을 실패했습니다.\\n유효하지 않은 회원정보(중복된 아이디, 패스워드, 전화번호)입니다.");
      return "redirect:/auth/register";
   }

   @GetMapping("/login")
   public String loginGET(Principal principal, String error, String logout, Model model) {
      // error : 에러 발생시 정보 저장
      // logout : 로그아웃 정보 저장
      log.info("loginGET()");
      log.info("error : " + error);
      log.info("logout : " + logout);

      // 에러가 발생한 경우, 에러 메시지를 모델에 추가하여 전달
      if (error != null) {
         model.addAttribute("errorMsg", "아이디 또는 비밀번호가 잘못되었습니다.\\n아이디와 비밀번호를 정확히 입력해 주세요.");
      }

      // 로그아웃이 발생한 경우, 로그아웃 메시지를 모델에 추가하여 전달
      if (logout != null) {
         model.addAttribute("logoutMsg", "로그아웃 되었습니다!");
      }
      
      if (principal != null) {
          return "redirect:/food/list";
      }
      return "/auth/login";

   }

   @GetMapping("/find-id")
   public String findIdGET(Principal principal,RedirectAttributes redirectAttributes) {
      if (principal != null) {
           return "redirect:/food/list";
       }
       return "/auth/find-id";
   }

   @GetMapping("/find-pw")
   public String findPwGET(Principal principal,RedirectAttributes redirectAttributes) {
      if (principal != null) {
           return "redirect:/food/list";
       }
       return "/auth/find-pw";
   }

}
