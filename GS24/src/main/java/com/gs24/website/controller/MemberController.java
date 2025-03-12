package com.gs24.website.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gs24.website.domain.MemberVO;
import com.gs24.website.service.ConvenienceService;
import com.gs24.website.service.MemberService;
import com.gs24.website.service.RecaptchaService;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping(value = "/member")
@Log4j
public class MemberController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private ConvenienceService convenienceService;

	@Autowired
	private RecaptchaService recaptchaService;

	@GetMapping("/register")
	public String registerGET(Authentication auth, Model model, RedirectAttributes redirectAttributes) {
		if (auth != null) {
			if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MEMBER"))) {
				return "redirect:/convenience/list";
			} else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_OWNER"))) {
				int convenienceId = convenienceService.getConvenienceIdByOwnerId(auth.getName());
				model.addAttribute("convenienceId", convenienceId);
				return "redirect:/convenienceFood/list?convenienceId=" + convenienceId;
			} else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
				return "redirect:/admin/console";
			}
		}
		return "/member/register";
	}

	@PostMapping("/register")
	public String registerMemberPOST(@ModelAttribute MemberVO memberVO, String recaptchaToken,
			RedirectAttributes redirectAttributes) {
		boolean isRecaptchaValid = recaptchaService.verifyRecaptcha(recaptchaToken);
		if (!isRecaptchaValid) {
			log.info("reCAPTCHA 검증 실패");
			redirectAttributes.addFlashAttribute("message", "reCAPTCHA 검증에 실패했습니다. 다시 시도해주세요.");
			return "redirect:/member/register";
		}
		int result = memberService.registerMember(memberVO);
		if (result == 1) {
			log.info("registerMemberPOST()");
			redirectAttributes.addFlashAttribute("message", "회원등록 완료.\\n가입한 아이디와 비밀번호로 로그인하세요.");
			return "redirect:/auth/login";
		}
		redirectAttributes.addFlashAttribute("message", "회원등록을 실패했습니다.\\n유효하지 않은 회원정보(중복된 아이디, 패스워드, 전화번호)입니다.");
		return "redirect:/member/register";
	}

	@GetMapping("/myhistory")
	public String myhistoryGET(Authentication auth, Model model) {
		log.info("myhistoryGET()");
		if (auth != null) {
			String memberId = auth.getName();
			model.addAttribute("memberId", memberId);
		}
		return "member/myhistory";
	}

	@GetMapping("/activate")
	public String activateGET(Authentication auth, Model model) {
		log.info("activateGET()");
		if (auth != null) {
			MemberVO memberVO = memberService.getMember(auth.getName());
			model.addAttribute("memberId", memberVO.getMemberId());
			model.addAttribute("email", memberVO.getEmail());
		}
		return "member/activate";
	}

	@PostMapping("/activate")
	public void activateMemberPOST(Authentication auth, RedirectAttributes redirectAttributes,
			HttpServletRequest request, HttpServletResponse response) {
		if (auth != null) {
			int result = memberService.reActivateMember(auth.getName());
			if (result == 1) {
				new SecurityContextLogoutHandler().logout(request, response,
						SecurityContextHolder.getContext().getAuthentication());
				request.getSession().invalidate();
				log.info("activateMemberPOST()");
			}
		}
	}
}
