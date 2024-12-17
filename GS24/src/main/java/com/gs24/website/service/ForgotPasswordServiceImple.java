package com.gs24.website.service;

import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gs24.website.persistence.MemberMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ForgotPasswordServiceImple implements ForgotPasswordService {

	@Autowired
	private MemberMapper memberMapper; // memberMapper 의존성 주입

	public boolean sendVerificationCode(String memberId, String email, HttpSession session) {
		// 이메일을 memberMapper에서 가져오는 부분
		String memberEmail = memberMapper.findEmailById(memberId);

		if (!email.equals(memberEmail)) {
			return false; // 이메일이 일치하지 않으면 실패
		}

		// 랜덤 인증번호 생성
		String verificationCode = generateVerificationCode();

		session.setAttribute("verificationCode", verificationCode);
		session.setAttribute("memberId", memberId);

		// 이메일 전송
		sendEmail(email, verificationCode);
		return true;
	}

	@Override
	public String verifyCodeAndGetPassword(String memberId, String verificationCode, HttpSession session) {
		// 세션에서 인증번호와 회원ID 가져오기
		String savedCode = (String) session.getAttribute("verificationCode");
		String savedMemberId = (String) session.getAttribute("memberId");

		if (savedCode != null && savedCode.equals(verificationCode) && savedMemberId.equals(memberId)) {
			// 인증번호가 일치하면 비밀번호 반환
			String password = memberMapper.findPwById(memberId);
			return password;
		} else {
			return null; // 인증번호 불일치
		}
	}

	// 인증번호 생성 메서드
	private String generateVerificationCode() {
		Random random = new Random();
		StringBuilder code = new StringBuilder();
		for (int i = 0; i < 6; i++) {
			code.append(random.nextInt(10)); // 0~9 숫자 생성
		}
		return code.toString();
	}

	// JavaMail을 사용한 이메일 전송
	private void sendEmail(String recipientEmail, String verificationCode) {
		// 메일 서버 설정
		String host = "smtp.naver.com"; // 실제 메일 서버 주소로 변경
		final String username = "nmbgsp95@naver.com"; // 발신자 이메일
		final String password = "1aledma2!A"; // 발신자 이메일 비밀번호

		// SMTP 서버 설정
		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "587"); // TLS 사용 포트
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true"); // TLS 사용 설정

		// 인증을 위한 Session 객체
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			// 이메일 내용 설정
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
			message.setSubject("<GS24> 비밀번호 인증 코드");
			message.setText("당신의 인증 코드는: " + verificationCode + " 입니다.");

			// 이메일 전송
			Transport.send(message);
			log.info("Verification email sent successfully.");
		} catch (MessagingException e) {
			e.printStackTrace();
			log.info("Error sending email.");
		}
	}
}
