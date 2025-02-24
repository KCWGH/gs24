package com.gs24.website.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gs24.website.domain.MemberVO;
import com.gs24.website.domain.OwnerVO;
import com.gs24.website.service.ConvenienceService;
import com.gs24.website.service.MemberService;
import com.gs24.website.service.OwnerService;
import com.gs24.website.service.RecaptchaService;

import lombok.extern.log4j.Log4j;

@Controller // @Component
@RequestMapping(value = "/user")
@Log4j
public class UserController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private OwnerService ownerService;

	@Autowired
	private ConvenienceService convenienceService;

	@Autowired
	private RecaptchaService recaptchaService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/register")
	public String registerGET(Authentication auth, Model model) {
		return handleRedirectByRole(auth, model, "/user/register");
	}

	@GetMapping("/find-id")
	public String findIdGET(Authentication auth, Model model) {
		return handleRedirectByRole(auth, model, "/user/find-id");
	}

	@GetMapping("/find-pw")
	public String findPwGET(Authentication auth, Model model) {
		return handleRedirectByRole(auth, model, "/user/find-pw");
	}

	private String handleRedirectByRole(Authentication auth, Model model, String defaultUrl) {
		if (auth == null) {
			return defaultUrl;
		}

		if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MEMBER"))) {
			return "redirect:/convenience/list";
		}

		if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_OWNER"))) {
			int convenienceId = convenienceService.getConvenienceIdByOwnerId(auth.getName());
			model.addAttribute("convenienceId", convenienceId);
			return "redirect:/convenienceFood/list?convenienceId=" + convenienceId;
		}

		return defaultUrl;
	}

	@GetMapping("/mypage")
	public String mypageGET(Authentication auth, Model model) {
		log.info("mypageGET()");
		if (auth != null) {
			String username = auth.getName();
			if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MEMBER"))) {
				MemberVO memberVO = memberService.getMember(username);
				model.addAttribute("userInfo", memberVO);
				model.addAttribute("userRole", "member");
				model.addAttribute("memberGrade", memberService.findGrade(username));
			} else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_OWNER"))) {
				OwnerVO ownerVO = ownerService.getOwner(username);
				model.addAttribute("userInfo", ownerVO);
				model.addAttribute("userRole", "owner");
			} else {
				log.info("검증되지 않은 권한의 사용자입니다.");
				return "../auth/login";
			}
		}
		return "user/mypage";
	}

	@GetMapping("/verify")
	public void verifyPasswordGET(Authentication auth, Model model) {
		log.info("verifyPasswordGET()");
		if (auth != null) {
			String username = auth.getName();
			if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MEMBER"))) {
				MemberVO memberVO = memberService.getMember(username);
				model.addAttribute("userInfo", memberVO);
			} else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_OWNER"))) {
				OwnerVO ownerVO = ownerService.getOwner(username);
				model.addAttribute("userInfo", ownerVO);
			} else {
				log.info("검증되지 않은 권한의 사용자입니다.");
			}
		}
	}

	@PostMapping("/check-pw")
	public String checkPwPOST(String memberId, String ownerId, String password, String recaptchaToken,
			RedirectAttributes redirectAttributes) {
		log.info("loginPOST()");
		boolean isRecaptchaValid = recaptchaService.verifyRecaptcha(recaptchaToken);
		if (!isRecaptchaValid) {
			log.info("reCAPTCHA 검증 실패");
			redirectAttributes.addFlashAttribute("message", "reCAPTCHA 검증에 실패했습니다. 다시 시도해주세요.");
			return "redirect:verify";
		}

		if (memberId != null) {
			MemberVO memberVO = memberService.getMember(memberId); // 회원 정보를 가져옴
			if (memberVO == null) {
				log.info("회원 정보 없음");
				redirectAttributes.addFlashAttribute("message", "존재하지 않는 회원입니다.");
				return "redirect:verify";
			}

			if (passwordEncoder.matches(password, memberVO.getPassword())) {
				return "redirect:change-pw";
			} else {
				log.info("비밀번호 검증 실패");
				redirectAttributes.addFlashAttribute("message", "잘못된 비밀번호입니다.");
				return "redirect:verify";
			}
		} else if (ownerId != null) {
			OwnerVO ownerVO = ownerService.getOwner(ownerId);
			if (ownerVO == null) {
				log.info("회원 정보 없음");
				redirectAttributes.addFlashAttribute("message", "존재하지 않는 회원입니다.");
				return "redirect:verify";
			}
			if (passwordEncoder.matches(password, ownerVO.getPassword())) {
				return "redirect:change-pw";
			} else {
				log.info("비밀번호 검증 실패");
				redirectAttributes.addFlashAttribute("message", "잘못된 비밀번호입니다.");
				return "redirect:verify";
			}
		}
		return "redirect:verify";
	}

	@GetMapping("/change-pw")
	public void changePwGET(Authentication auth, Model model) {
		log.info("changePwGET()");
		if (auth != null) {
			String username = auth.getName();
			if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MEMBER"))) {
				MemberVO memberVO = memberService.getMember(username);
				model.addAttribute("userInfo", memberVO);
			} else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_OWNER"))) {
				OwnerVO ownerVO = ownerService.getOwner(username);
				model.addAttribute("userInfo", ownerVO);
			} else {
				log.info("검증되지 않은 권한의 사용자입니다.");
			}
		}

	}

	@PostMapping("/change-pw")
	public String changePwPOST(String memberId, String ownerId, String password, String recaptchaToken,
			RedirectAttributes redirectAttributes) {
		log.info("changePwPOST()");
		boolean isRecaptchaValid = recaptchaService.verifyRecaptcha(recaptchaToken);
		if (!isRecaptchaValid) {
			log.info("reCAPTCHA 검증 실패");
			redirectAttributes.addFlashAttribute("message", "reCAPTCHA 검증에 실패했습니다. 다시 시도해주세요.");
			return "redirect:change-pw";
		}

		if (memberId != null) {
			int result = memberService.updateMemberPassword(memberId, password);
			if (result == 1) {
				redirectAttributes.addFlashAttribute("message", "비밀번호가 변경되었습니다.");
				return "redirect:mypage";
			}
		} else if (ownerId != null) {
			int result = ownerService.updateOwnerPassword(ownerId, password);
			if (result == 1) {
				redirectAttributes.addFlashAttribute("message", "비밀번호가 변경되었습니다.");
				return "redirect:mypage";
			}
		}
		redirectAttributes.addFlashAttribute("message", "비밀번호 변경 실패입니다.");
		return "redirect:change-pw";
	}

	@GetMapping("/reactivate")
	public String reactivateGET(Authentication auth, Model model) {
		log.info("reactivateGET()");
		if (auth != null) {
			if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MEMBER"))) {
				return "redirect:/convenience/list";
			} else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_OWNER"))) {
				int checkConvenienceId = convenienceService.getConvenienceIdByOwnerId(auth.getName());
				log.info(checkConvenienceId);
				model.addAttribute("checkConvenienceId", checkConvenienceId);
				return "redirect:/convenienceFood/list?convenienceId=" + checkConvenienceId;
			}
		}
		return "/user/reactivate";

	}

	@GetMapping("/terms")
	public void termsGET() {
	}

	@GetMapping("/privacy-policy")
	public void privacypolicyGET() {
	}

	@GetMapping("/customer-service")
	public void customerservice() {
	}

}
