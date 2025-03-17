package com.gs24.website.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.gs24.website.domain.AdminVO;
import com.gs24.website.domain.CustomUser;
import com.gs24.website.domain.MemberVO;
import com.gs24.website.domain.OwnerVO;
import com.gs24.website.persistence.AdminMapper;
import com.gs24.website.persistence.MemberMapper;
import com.gs24.website.persistence.OwnerMapper;

import lombok.extern.log4j.Log4j;

@Log4j
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private MemberMapper memberMapper;

	@Autowired
	private OwnerMapper ownerMapper;

	@Autowired
	private AdminMapper adminMapper;

	// 전송된 username으로 사용자 정보를 조회하고, UserDetails에 저장하여 리턴하는 메서드
	@Override
	public UserDetails loadUserByUsername(String username) {
		log.info("loadUserByUsername()");
		log.info(username);

		// 1. MEMBER 테이블에서 사용자 조회
		MemberVO memberVO = memberMapper.selectMemberByMemberId(username);

		if (memberVO != null && memberVO.getIsEnabled() == 1) {
			// 회원이 활성화된 경우
			List<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority("ROLE_MEMBER")); // ROLE_MEMBER 역할 부여
			return new CustomUser(memberVO, authorities);
		} else if (memberVO != null && memberVO.getIsEnabled() == 0) {
			List<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority("ROLE_DEACTIVATED_MEMBER"));
			return new CustomUser(memberVO, authorities);
		}

		// 2. OWNER 테이블에서 사용자 조회
		OwnerVO ownerVO = ownerMapper.selectOwnerByOwnerId(username);

		if (ownerVO != null && ownerVO.getIsEnabled() == 1) {
			// 소유자가 활성화된 경우
			List<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority("ROLE_OWNER")); // ROLE_OWNER 역할 부여
			return new CustomUser(ownerVO, authorities);
		} else if (ownerVO != null && ownerVO.getIsEnabled() == 0) {
			List<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority("ROLE_DEACTIVATED_OWNER"));
			return new CustomUser(ownerVO, authorities);
		} else if (ownerVO != null && ownerVO.getIsEnabled() == 2) {
			List<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority("ROLE_ACTIVATION_REQUESTED_OWNER"));
			return new CustomUser(ownerVO, authorities);
		} else if (ownerVO != null && ownerVO.getIsEnabled() == -1) {
			List<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority("ROLE_UNAUTHERIZED_OWNER"));
			return new CustomUser(ownerVO, authorities);
		}

		AdminVO adminVO = adminMapper.selectAdminByAdminId(username);

		if (adminVO != null) {
			// 소유자가 활성화된 경우
			List<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN")); // ROLE_OWNER 역할 부여
			return new CustomUser(adminVO, authorities);
		}

		// 3. 회원 또는 소유자가 존재하지 않거나 활성화되지 않은 경우 예외 처리
		throw new UsernameNotFoundException("Username is not found or User is not active");
	}
}
