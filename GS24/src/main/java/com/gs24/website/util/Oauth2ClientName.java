package com.gs24.website.util;

import java.util.Arrays;

public enum Oauth2ClientName {
	KAKAO("kakao"), NAVER("naver");
	
	private final String clientName;

	Oauth2ClientName(String clientName) {
		this.clientName = clientName;
	}
	
	public static Oauth2ClientName ofName(String clienName) {
		return Arrays.stream(values()).filter(oauth2clientName -> oauth2clientName.clientName.equals(clienName)).findFirst().orElseThrow(() -> new IllegalArgumentException("Not fount Clinet Name"));
	}
	
	public String getClientName() {
		return clientName;
	}
}
