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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gs24.website.domain.EmailVerificationVO;
import com.gs24.website.persistence.EmailVerificationMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class EmailVerificationServiceImple implements EmailVerificationService {

	@Autowired
	private EmailVerificationMapper emailVerificationMapper;

	@Override
	@Transactional
	public void sendVerificationCode(String email) throws Exception {

		String verificationCode = generateVerificationCode();

		EmailVerificationVO verification = new EmailVerificationVO();
		verification.setEmail(email);
		verification.setVerificationCode(verificationCode);
		emailVerificationMapper.insertVerificationCode(verification);
		log.info("�슂洹��엫");
		sendEmail(email, verificationCode);
	}


	private String generateVerificationCode() {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 6; i++) {
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}


	private void sendEmail(String email, String verificationCode) throws MessagingException {

		String host = "smtp.naver.com";
		String port = "587";
		String from = "nmbgsp95@naver.com";
		String username = "nmbgsp95@naver.com";
		String password = "1aledma2!A";

		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");

		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
		message.setSubject("<GS24> �씠硫붿씪 �씤利앸쾲�샇");
		message.setText("�슂泥��븯�떊 �씤利앸쾲�샇�뒗 " + verificationCode + " �엯�땲�떎.");

		Transport.send(message);
	}

	@Override
	public String verifyCodeAndFindMemberId(String email, String verificationCode) {

		String memberId = emailVerificationMapper.selectMemberIdByVerificationCode(email, verificationCode);
		return memberId;
	}

	@Override
	public int verifyCodeAndFindPw(String memberId, String email, String verificationCode) {
		int result = emailVerificationMapper.selectIsExistByMemberIdAndEmailAndCode(memberId, email, verificationCode);
		return result;
	}
}
