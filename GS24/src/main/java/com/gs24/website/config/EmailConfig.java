package com.gs24.website.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

public class EmailConfig {

	private static Properties props = new Properties();

	static {
		try {
			InputStream input = EmailConfig.class.getClassLoader().getResourceAsStream("email.properties");
			if (input == null) {
				System.out.println("Sorry, unable to find email.properties");
			} else {
				props.load(input);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 이메일 설정 반환
	public static Session getMailSession() {
		String host = props.getProperty("mail.smtp.host");
		String port = props.getProperty("mail.smtp.port");
		String username = props.getProperty("mail.smtp.username");
		String password = props.getProperty("mail.smtp.password");

		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");

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
