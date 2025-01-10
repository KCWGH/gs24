package com.gs24.website.service;

import javax.mail.MessagingException;

public interface EmailService {
	void sendVerificationEmail(String toEmail) throws MessagingException;

	boolean verifyCode(String email, String code);
}
