package com.gs24.website.config;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

public class EmailConfig {

    public static Session getMailSession() {
        // SMTP 서버 설정 (네이버 메일)
        String host = "smtp.naver.com";  // 네이버 SMTP 서버
        String port = "587";  // TLS 사용
        String username = "nmbgsp95@naver.com";  // 네이버 이메일 주소
        String password = "1aledma2!A";  // 네이버 이메일 비밀번호
        
        // 이메일 세션 구성
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");  // TLS 사용
        
        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }
}

