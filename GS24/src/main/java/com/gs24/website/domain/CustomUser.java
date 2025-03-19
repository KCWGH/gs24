package com.gs24.website.domain;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Getter;

@Getter
public class CustomUser extends User { // User 클래스 상속

	private static final long serialVersionUID = 1L;

	private MemberVO memberVO;
	private OwnerVO ownerVO;
	private AdminVO adminVO;

	public CustomUser(MemberVO memberVO, Collection<? extends GrantedAuthority> authorities) {
		// User 클래스 생성자에 username, password, authorities를 적용
		super(memberVO.getMemberId(), memberVO.getPassword(), authorities);
		this.memberVO = memberVO;
	}

	public CustomUser(OwnerVO ownerVO, Collection<? extends GrantedAuthority> authorities) {
		// User 클래스 생성자에 username, password, authorities를 적용
		super(ownerVO.getOwnerId(), ownerVO.getPassword(), authorities);
		this.ownerVO = ownerVO;
	}

	public CustomUser(AdminVO adminVO, Collection<? extends GrantedAuthority> authorities) {
		// User 클래스 생성자에 username, password, authorities를 적용
		super(adminVO.getAdminId(), adminVO.getPassword(), authorities);
		this.adminVO = adminVO;
	}
	
	public String getName() {
		return memberVO.getMemberId();
	}
	
	public String getNickname() {
		return memberVO.getNickname();
	}
}
