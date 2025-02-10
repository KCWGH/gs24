package com.gs24.website.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

public class EmailConfig {

	private static final Properties props = new Properties();

	static {
		// try-with-resources를 사용하여 InputStream 자동 닫기
		try (InputStream input = EmailConfig.class.getClassLoader().getResourceAsStream("email.properties")) {
			if (input == null) {
				throw new IOException("Unable to find email.properties");
			}
			props.load(input);
		} catch (IOException e) {
			// 예외 처리: 로깅 또는 예외 전파
			e.printStackTrace();
			throw new RuntimeException("Failed to load email configuration", e);
		}
	}

	// 이메일 설정 반환
	public static Session getMailSession() {
		String username = props.getProperty("mail.smtp.username");
		String password = props.getProperty("mail.smtp.password");

		// SMTP 설정이 프로퍼티에 있을 경우만 적용
		props.putIfAbsent("mail.smtp.host", props.getProperty("mail.smtp.host"));
		props.putIfAbsent("mail.smtp.port", props.getProperty("mail.smtp.port"));
		props.putIfAbsent("mail.smtp.auth", "true");
		props.putIfAbsent("mail.smtp.starttls.enable", "true");

		return Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
	}

	// username을 반환
	public static String getUsername() {
		return props.getProperty("mail.smtp.username");
	}
}
