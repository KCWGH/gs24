package com.gs24.website.util;

import org.springframework.security.oauth2.core.user.OAuth2User;

import com.gs24.website.domain.Oauth2UserInfoVO;

public interface Oauth2UserInfo {
	Oauth2ClientName getClinetName();
	
	Oauth2UserInfoVO getuserInfo(OAuth2User user);
}
