package com.gs24.website.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.gs24.website.domain.CustomUser;
import com.gs24.website.domain.MemberVO;
import com.gs24.website.persistence.MemberMapper;

import lombok.extern.log4j.Log4j;

@Log4j
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private MemberMapper memberMapper;

	// 전송된 username으로 사용자 정보를 조회하고, UserDetails에 저장하여 리턴하는 메서드
	@Override
	public UserDetails loadUserByUsername(String username) {
		log.info("loadUserByUsername()");
		log.info(username);

		MemberVO memberVO = memberMapper.select(username);

		// 조회된 회원 정보가 없을 경우 예외 처리
		if (memberVO == null) {
			throw new UsernameNotFoundException("UsernameNotFound");
		}
		
		if (memberVO.getIsEnabled() != 1) {
			throw new UsernameNotFoundException("User is not active");
		}

		List<GrantedAuthority> authorities = new ArrayList<>();
		
		String roleName = null;
		if (memberVO.getMemberRole() == 1) { // 1: 일반회원
			roleName = "ROLE_MEMBER";
		} else if (memberVO.getMemberRole() == 2) { // 2: 점주
			roleName = "ROLE_OWNER";
		}
		authorities.add(new SimpleGrantedAuthority(roleName));

		// UserDetails 객체를 생성하여 회원 정보와 역할 정보를 담아 반환
		return new CustomUser(memberVO, authorities);
	}
}
