package com.gs24.website.util;

import java.sql.Date;
import java.util.Calendar;
import java.util.Map;

import org.springframework.security.oauth2.core.user.OAuth2User;

import com.gs24.website.domain.MemberVO;
import com.gs24.website.domain.Oauth2UserInfoVO;

public class naverOauth2UserInfo implements Oauth2UserInfo{

	@Override
	public Oauth2ClientName getClinetName() {
		return Oauth2ClientName.NAVER;
	}

	@Override
	public Oauth2UserInfoVO getuserInfo(OAuth2User user) {
		
		Map<String, Object> attibute = user.getAttributes();
		
		@SuppressWarnings("unchecked")
		Map<String, Object> response = (Map<String, Object>) attibute.get("response");
		
		String email = response.get("email").toString();
		
		String number = response.get("mobile").toString();
		
		String oauthId = response.get("id").toString();
		
		String nickname = response.get("name").toString();

		int year = Integer.parseInt(response.get("birthyear").toString()); // 2002
		
		int month = Integer.parseInt(response.get("birthday").toString().split("-")[0]);
		int day = Integer.parseInt(response.get("birthday").toString().split("-")[1]);
		Date date = null;
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, day, 0, 0, 0);

		date = new Date(calendar.getTimeInMillis());
		
		MemberVO memberVO = new MemberVO(oauthId, "", email, number, date, 1, 0, nickname);
		
		return new Oauth2UserInfoVO(memberVO, "naver");
	}

}
