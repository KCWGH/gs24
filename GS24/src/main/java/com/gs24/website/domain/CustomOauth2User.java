package com.gs24.website.domain;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

public class CustomOauth2User extends DefaultOAuth2User {
	
	private static final long serialVersionUID = 1L;
	
	private MemberVO memberVO;
	private OwnerVO ownerVO;
	private AdminVO adminVO;
	
	public CustomOauth2User(MemberVO memberVO,Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes,
			String nameAttributeKey) {
		super(authorities, attributes, nameAttributeKey);
		this.memberVO = memberVO;
	}

	public CustomOauth2User(OwnerVO ownerVO,Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes,
			String nameAttributeKey) {
		super(authorities, attributes, nameAttributeKey);
		this.ownerVO = ownerVO;
	}
	
	public CustomOauth2User(AdminVO adminVO,Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes,
			String nameAttributeKey) {
		super(authorities, attributes, nameAttributeKey);
		this.adminVO = adminVO;
	}
	
	public String getNickname() {
		return memberVO.getNickname();
	}
	
	@Override
	public String getName() {
		return memberVO.getMemberId();
	}
}
