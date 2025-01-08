package com.gs24.website.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.gs24.website.config.RecaptchaConfig;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class RecaptchaServiceImple implements RecaptchaService {

	private RecaptchaConfig recaptchaConfig;

	@Autowired
	public RecaptchaServiceImple(RecaptchaConfig recaptchaConfig) {
		this.recaptchaConfig = recaptchaConfig;
	}

	private static final String RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

	@Override
	public boolean verifyRecaptcha(String recaptchaToken) {
		RestTemplate restTemplate = new RestTemplate();
		String url = RECAPTCHA_VERIFY_URL + "?secret=" + recaptchaConfig.getSecretKey() + "&response=" + recaptchaToken;
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

		log.info(response.getBody());
		// 응답에서 success 필드를 확인하여 검증 결과를 반환
		return response.getBody().contains("\"success\": true");
	}

}
