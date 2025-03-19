package com.gs24.website.domain;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

public class CustomOauth2User extends DefaultOAuth2User {

	private static final long serialVersionUID = 1L;

	private MemberVO memberVO;

	public CustomOauth2User(MemberVO memberVO, Collection<? extends GrantedAuthority> authorities,
			Map<String, Object> attributes, String nameAttributeKey) {
		super(authorities, attributes, nameAttributeKey);
		this.memberVO = memberVO;
	}

	public String getNickname() {
		return memberVO.getNickname();
	}

	@Override
	public String getName() {
		return memberVO.getMemberId();
	}
}
