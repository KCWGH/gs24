package com.gs24.website.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.gs24.website.domain.CustomOauth2User;
import com.gs24.website.domain.MemberVO;
import com.gs24.website.persistence.MemberMapper;
import com.gs24.website.persistence.MembershipMapper;

public class CustomOauth2UserService extends DefaultOAuth2UserService {

	@Autowired
	private MemberMapper memberMapper;

	@Autowired
	private MembershipMapper membershipMapper;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

		OAuth2User user = super.loadUser(userRequest);

		OAuth2User user2 = null;

		final Map<String, Object> attributes = user.getAttributes();

		final Long oauthId = user.getAttribute("id");

		Map<String, Object> kakaoAccount = user.getAttribute("kakao_account");

		Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

		String email = (String) kakaoAccount.get("email");
		String[] phone = kakaoAccount.get("phone_number").toString().split("-");
		String number = "010-" + phone[1] + "-" + phone[2];
		int year = Integer.parseInt(kakaoAccount.get("birthyear").toString());
		int month = Integer.parseInt(kakaoAccount.get("birthday").toString()) / 100;
		int day = Integer.parseInt(kakaoAccount.get("birthday").toString()) % 100;
		String nickname = profile.get("nickname").toString();
		String provider = userRequest.getClientRegistration().getClientName();

		// Calendar 객체 사용
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, day, 0, 0, 0);

		// Date 객체로 변환
		Date date = new Date(calendar.getTimeInMillis());

		String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
				.getUserNameAttributeName();

		// 이렇게 가져온 데이터가 기존 회원 테이블에 있는지 확인하는 코드
		// 만약 없다면 기존 회원 테이블에 추가 후 하위 테이블 소셜 테이블에도 추가 && 권한도 줘야 한다.
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		String memberId = memberMapper.selectMemberIdByEmail(email);
		if (memberId == null) {
			authorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
			System.out.println("신규 회원입니다.");
			MemberVO memberVO = new MemberVO(oauthId.toString(), "", email, number, date, 1, 0, nickname);
			memberMapper.insertMember(memberVO);
			memberMapper.insertSocial(memberMapper.selectAccountId() - 1, oauthId, provider, email);
			membershipMapper.insertMembership(memberVO.getMemberId());
			user2 = new CustomOauth2User(memberVO, authorities, user.getAttributes(), userNameAttributeName);

		} else {
			MemberVO memberVO = memberMapper.selectMemberByEmail(email);
			authorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
			System.out.println("기존 회원입니다.");
			user2 = new CustomOauth2User(memberVO, authorities, user.getAttributes(), userNameAttributeName);
		}

		return user2;
	}
}
