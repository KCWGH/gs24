package com.gs24.website.service;

public interface EmailVerificationService {
	
	void sendVerificationCode(String email) throws Exception;

	String verifyCodeAndFindMemberId(String email, String verificationCode);
	
	int verifyCodeAndFindPw(String memberId, String email, String verificationCode);
}
