package com.gs24.website.service;

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
import com.gs24.website.domain.Oauth2UserInfoVO;
import com.gs24.website.persistence.MemberMapper;
import com.gs24.website.persistence.MembershipMapper;
import com.gs24.website.util.KakaoOauth2UserInfo;
import com.gs24.website.util.Oauth2ClientName;
import com.gs24.website.util.naverOauth2UserInfo;

public class CustomOauth2UserService extends DefaultOAuth2UserService {

	@Autowired
	private MemberMapper memberMapper;

	@Autowired
	private MembershipMapper membershipMapper;

	private final KakaoOauth2UserInfo kakaoOauth2UserInfo = new KakaoOauth2UserInfo();

	private final naverOauth2UserInfo naverOauth2UserInfo = new naverOauth2UserInfo();
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

		OAuth2User user = super.loadUser(userRequest);

		OAuth2User user2 = null;
		
		final String clientName = userRequest.getClientRegistration().getClientName();
		
		Oauth2UserInfoVO userInfoVO = null;

		if (Oauth2ClientName.KAKAO.getClientName().equals(clientName)) {
			userInfoVO = kakaoOauth2UserInfo.getuserInfo(user);
		} else if(Oauth2ClientName.NAVER.getClientName().equals(clientName)) {
			userInfoVO = naverOauth2UserInfo.getuserInfo(user);
		}

		String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
				.getUserNameAttributeName();

		// 이렇게 가져온 데이터가 기존 회원 테이블에 있는지 확인하는 코드
		// 만약 없다면 기존 회원 테이블에 추가 후 하위 테이블 소셜 테이블에도 추가 && 권한도 줘야 한다.
		List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_MEMBER"));
		String memberId = memberMapper.selectMemberIdByEmail(userInfoVO.getMemberVO().getEmail());
		if (memberId == null) {
			System.out.println("신규 회원입니다.");
			memberMapper.insertMember(userInfoVO.getMemberVO());
			memberMapper.insertSocial(memberMapper.selectAccountId() - 1, userInfoVO.getMemberVO().getMemberId(),
					userInfoVO.getProvider(), userInfoVO.getMemberVO().getEmail());
			membershipMapper.insertMembership(userInfoVO.getMemberVO().getMemberId());
			user2 = new CustomOauth2User(userInfoVO.getMemberVO(), authorities, user.getAttributes(),
					userNameAttributeName);

		} else {
			MemberVO memberVO = memberMapper.selectMemberByEmail(userInfoVO.getMemberVO().getEmail());
			System.out.println("기존 회원입니다.");
			user2 = new CustomOauth2User(memberVO, authorities, user.getAttributes(), userNameAttributeName);
		}

		return user2;
	}
}
