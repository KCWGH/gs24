package com.gs24.website.service;

public interface RecaptchaService {
	boolean verifyRecaptcha(String recaptchaToken);
}
