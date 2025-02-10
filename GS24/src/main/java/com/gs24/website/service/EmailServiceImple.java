package com.gs24.website.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
import com.gs24.website.util.EmailVerificationCodeCheck;

@Service
public class EmailServiceImple implements EmailService {

	private static final Map<String, EmailVerificationCodeCheck> verificationCodes = new HashMap<>();
	private static final int CODE_EXPIRATION_MINUTES = 2;

	@Override
	public void sendVerificationEmail(String toEmail) throws MessagingException {
		String verificationCode = generateVerificationCode();
		EmailVerificationCodeCheck codeWithTimestamp = new EmailVerificationCodeCheck(verificationCode,
				LocalDateTime.now());
		verificationCodes.put(toEmail, codeWithTimestamp);

		Session session = EmailConfig.getMailSession();
		MimeMessage message = new MimeMessage(session);

		String fromAddress = EmailConfig.getUsername();
		message.setFrom(new InternetAddress(fromAddress));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
		message.setSubject("[GS24] 이메일 인증번호");
		message.setText("당신의 인증번호는 " + verificationCode + " 입니다.");

		Transport.send(message);
	}

	@Override
	public boolean verifyCode(String email, String code) {
		EmailVerificationCodeCheck codeWithTimestamp = verificationCodes.get(email);

		if (codeWithTimestamp == null) {
			return false; // 인증번호가 없으면 실패
		}

		String storedCode = codeWithTimestamp.getCode();
		LocalDateTime timestamp = codeWithTimestamp.getTimestamp();

		// 인증번호가 2분 이상 지난 경우
		if (ChronoUnit.MINUTES.between(timestamp, LocalDateTime.now()) > CODE_EXPIRATION_MINUTES) {
			verificationCodes.remove(email); // 만료된 인증번호는 삭제
			return false; // 인증번호가 만료됨
		}

		// 인증번호 비교
		return storedCode.equals(code);
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
