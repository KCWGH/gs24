package com.gs24.website.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:recaptcha.properties")
public class RecaptchaConfig {

	private String secretKey;

	// 생성자를 통해 properties 파일 로드
	public RecaptchaConfig() {
		loadProperties();
	}

	private void loadProperties() {
		Properties properties = new Properties();
		try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("recaptcha.properties")) {
			if (inputStream == null) {
				throw new IllegalArgumentException("recaptcha.properties 파일을 찾을 수 없습니다.");
			}
			properties.load(inputStream);
			this.secretKey = properties.getProperty("recaptcha.secret.key");
		} catch (IOException e) {
			throw new RuntimeException("recaptcha.properties 파일을 로드하는 중 오류가 발생했습니다.", e);
		}
	}

	// Getter 메서드
	public String getSecretKey() {
		return secretKey;
	}

	// Setter 메서드 (필요한 경우)
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
}
