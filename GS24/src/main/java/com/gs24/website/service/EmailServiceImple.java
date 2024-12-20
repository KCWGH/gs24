package com.gs24.website.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

import com.gs24.website.config.EmailConfig;

@Service
public class EmailServiceImple implements EmailService {

	private static final Map<String, String> verificationCodes = new HashMap<>();

	@Override
	public void sendVerificationEmail(String toEmail) throws MessagingException {
		String verificationCode = generateVerificationCode();
		verificationCodes.put(toEmail, verificationCode);

		Session session = EmailConfig.getMailSession();
		MimeMessage message = new MimeMessage(session);

		message.setFrom(new InternetAddress("nmbgsp95@naver.com"));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
		message.setSubject("<GS24> 이메일 인증번호");
		message.setText("당신의 인증번호는 " + verificationCode + " 입니다.");

		Transport.send(message);
	}

	@Override
	public boolean verifyCode(String email, String code) {
		String storedCode = verificationCodes.get(email);
		return storedCode != null && storedCode.equals(code);
	}

	private String generateVerificationCode() {
		Random rand = new Random();
		StringBuilder code = new StringBuilder();
		for (int i = 0; i < 6; i++) {
			code.append(rand.nextInt(10));
		}
		return code.toString();
	}
}
