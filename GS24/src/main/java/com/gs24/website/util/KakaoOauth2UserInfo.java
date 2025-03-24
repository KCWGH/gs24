package com.gs24.website.util;

import java.sql.Date;
import java.util.Calendar;
import java.util.Map;

import org.springframework.security.oauth2.core.user.OAuth2User;

import com.gs24.website.domain.MemberVO;
import com.gs24.website.domain.Oauth2UserInfoVO;

public class KakaoOauth2UserInfo implements Oauth2UserInfo {

	@Override
	public Oauth2ClientName getClinetName() {
		return Oauth2ClientName.KAKAO;
	}

	@Override
	public Oauth2UserInfoVO getuserInfo(OAuth2User user) {
		Map<String, Object> attribute = null;
		attribute = user.getAttributes();

		@SuppressWarnings("unchecked")
		Map<String, Object> kakaoAccount = (Map<String, Object>) attribute.get("kakao_account");

		@SuppressWarnings("unchecked")
		Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

		String oauthId = user.getAttribute("id").toString();

		String email = kakaoAccount.get("email").toString();

		String nickname = profile.get("nickname").toString();

		String[] phone = kakaoAccount.get("phone_number").toString().split("-");
		String number = "0" + phone[0].substring(4) + "-" + phone[1] + "-" + phone[2];

		int year = Integer.parseInt(kakaoAccount.get("birthyear").toString());

		String birthdayStr = kakaoAccount.get("birthday").toString();

		int month = Integer.parseInt(birthdayStr.substring(0, 2));
		int day = Integer.parseInt(birthdayStr.substring(2, 4));

		Date date = null;
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, day, 0, 0, 0);

		date = new Date(calendar.getTimeInMillis());

		MemberVO memberVO = new MemberVO(oauthId, "", email, number, date, 1, 0, nickname);

		return new Oauth2UserInfoVO(memberVO, "kakao");
	}
}