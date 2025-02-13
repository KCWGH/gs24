package com.gs24.website.service;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import com.gs24.website.persistence.ConvenienceMapper;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private ConvenienceMapper convenienceMapper;

	private final RequestCache requestCache = new HttpSessionRequestCache(); // 로그인 전 요청 저장

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// 로그인 전 요청한 URL 확인
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		String redirectURL = (savedRequest != null) ? savedRequest.getRedirectUrl()
				: determineTargetUrl(authentication);
		
		System.out.println("savedRequest : " + savedRequest);
		
		System.out.println("redirectURL : " + redirectURL);
		
		// 사용 후 requestCache에서 제거
		if (savedRequest != null) {
			requestCache.removeRequest(request, response);
		}

		response.sendRedirect(redirectURL);
	}

	private String determineTargetUrl(Authentication authentication) {
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		String redirectURL = "/"; // 기본값 (홈페이지)

		for (GrantedAuthority authority : authorities) {
			String role = authority.getAuthority();

			if (role.equals("ROLE_MEMBER")) {
				redirectURL = "../convenience/list"; // 회원 전용 페이지
				break;
			} else if (role.equals("ROLE_OWNER")) {
				int branchId = convenienceMapper.selectConvenienceIdByOwnerId(authentication.getName());
				redirectURL = "../convenienceFood/list?convenienceId=" + branchId; // 점주 전용 페이지
				break;
			} else if (role.equals("ROLE_ADMIN")) {
				redirectURL = "../foodlist/list"; // 관리자 전용 페이지
				break;
			}
		}
		return redirectURL;
	}
}
