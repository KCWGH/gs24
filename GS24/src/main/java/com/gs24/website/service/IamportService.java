package com.gs24.website.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IamportService {

	@Value("${iamport.api.key}")
	private String apiKey;

	@Value("${iamport.api.secret}")
	private String apiSecret;

	private final RestTemplate restTemplate;

	public IamportService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	// Iamport API 토큰을 받아오는 메서드
	private String getAccessToken() throws Exception {
		String url = "https://api.iamport.kr/users/getToken";

		// 토큰 요청을 위한 JSON 데이터
		String json = "{ \"imp_key\": \"" + apiKey + "\", \"imp_secret\": \"" + apiSecret + "\" }";

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");

		HttpEntity<String> entity = new HttpEntity<>(json, headers);

		// API 호출
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

		// 응답에서 access_token 추출
		String responseBody = response.getBody();
		// 필요한 JSON 파싱 로직을 추가하여 access_token 추출

		return responseBody; // 실제로는 JSON 파싱 후 token 반환해야 합니다.
	}

	// 결제 요청
	public String requestPayment(String impUid, int amount) throws Exception {
		String token = getAccessToken(); // 토큰 발급 받기

		String url = "https://api.iamport.kr/payments/prepare"; // 결제 준비 API URL
		String json = "{ " + "\"amount\": " + amount + ", " + "\"merchant_uid\": \"" + impUid + "\"" + "}";

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);
		headers.set("Content-Type", "application/json");

		HttpEntity<String> entity = new HttpEntity<>(json, headers);

		// 결제 준비 API 호출
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

		return response.getBody();
	}

	// 결제 확인
	public String verifyPayment(String impUid) throws Exception {
		String token = getAccessToken(); // 토큰 발급 받기

		String url = "https://api.iamport.kr/payments/" + impUid; // 결제 확인 API URL

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);

		HttpEntity<String> entity = new HttpEntity<>(headers);

		// 결제 확인 API 호출
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

		return response.getBody();
	}
}
