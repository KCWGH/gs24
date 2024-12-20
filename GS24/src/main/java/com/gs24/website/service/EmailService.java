package com.gs24.website.service;

import javax.mail.MessagingException;

public interface EmailService {
	// 이메일 인증번호 전송 메서드
	void sendVerificationEmail(String toEmail) throws MessagingException;

	// 인증번호 검증 메서드
	boolean verifyCode(String email, String code);
}
