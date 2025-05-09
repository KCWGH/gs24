package com.gs24.website.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gs24.website.domain.AdminVO;
import com.gs24.website.service.AdminService;
import com.gs24.website.service.ConvenienceService;
import com.gs24.website.service.OwnerService;

@RequestMapping(value = "/admin")
@Controller
public class AdminController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private OwnerService ownerService;

	@Autowired
	private ConvenienceService convenienceService;

	@GetMapping("/register")
	public String registerGET(Authentication auth, Model model, RedirectAttributes redirectAttributes) {
		if (auth != null) {
			if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MEMBER"))) {
				return "redirect:/convenience/list";
			} else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_OWNER"))) {
				int checkConvenienceId = convenienceService.getConvenienceIdByOwnerId(auth.getName());
				model.addAttribute("checkConvenienceId", checkConvenienceId);
				return "redirect:/convenienceFood/list?convenienceId=" + checkConvenienceId;
			}
		}
		return "/admin/register";
	}

	@PostMapping("/register")
	public String registerAdminPOST(@ModelAttribute AdminVO adminVO, RedirectAttributes redirectAttributes) {
		int result = adminService.registerAdmin(adminVO);
		if (result == 1) {
			redirectAttributes.addFlashAttribute("message", "관리자 계정 등록 완료.\\n가입한 아이디와 비밀번호로 로그인하세요.");
			return "redirect:/auth/login";
		}
		redirectAttributes.addFlashAttribute("message", "회원등록을 실패했습니다.\\n유효하지 않은 회원정보(중복된 아이디, 패스워드, 전화번호)입니다.");
		return "redirect:/admin/register";
	}

	@GetMapping("/console")
	public String consoleGET() {
		return "/admin/console";
	}

	@GetMapping("/authorize")
	public String authorizeGET(Model model) {
		String[] unAuthorizedOwnerIds = ownerService.selectUnauthorizedOwners();
		model.addAttribute("unAuthorizedOwnerIds", unAuthorizedOwnerIds);

		return "/admin/authorize";
	}

	@PostMapping("/authorize")
	public void authorizePOST(@RequestBody Map<String, String[]> requestBody) {
		String[] unAuthorizedOwnerIds = requestBody.get("unAuthorizedOwnerIds");
		for (String ownerId : unAuthorizedOwnerIds) {
			ownerService.reActivateOwner(ownerId);
		}
	}

	@GetMapping("/activate")
	public String activateGET(Model model) {
		String[] inActivatedOwnerIds = ownerService.selectActivationRequestedOwners();
		model.addAttribute("inActivatedOwnerIds", inActivatedOwnerIds);

		return "/admin/activate";
	}

	@PostMapping("/activate")
	public void activatePOST(@RequestBody Map<String, String[]> requestBody) {
		String[] inActivatedOwnerIds = requestBody.get("inActivatedOwnerIds");
		for (String ownerId : inActivatedOwnerIds) {
			ownerService.reActivateOwner(ownerId);
		}
	}

	@GetMapping("/auth-option")
	public String authOptionGET() {
		return "/admin/auth-option";
	}
}
