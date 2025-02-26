package com.gs24.website.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.extern.log4j.Log4j;

@Component
@Log4j
public class KakaoLoginUtil {
	public String getElementProperty(JsonElement element, String propertyName) {
		return element.getAsJsonObject().get(propertyName).getAsString();
	}
	
	public JsonElement sendCode(String code) throws URISyntaxException {
		
		// POST 요청을 보내는 URL 생성
        URI uri = new URI("https://kauth.kakao.com/oauth/token");
		
        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 바디 설정
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "bedb40cfc8674f74e50967f723dadd18");
        body.add("redirect_uri", "http://192.168.0.161:8080/website/auth/kakao");
        body.add("code", code);

        // HttpEntity 생성 (헤더와 바디 포함)
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
        
        RestTemplate restTemplate = new RestTemplate();
        
        //TODO 오류 발생시 처리 코드 작성 필요
        // API 호출
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(uri, requestEntity, String.class);

        // 응답 확인
        log.info("Response: " + responseEntity.getBody());
        
        JsonParser jsonParser = new JsonParser();
        JsonElement element = jsonParser.parse(responseEntity.getBody());
        return element;
    }
	
	public void getUserInfo(String accessToken) throws URISyntaxException {
		URI uri = new URI("https://kapi.kakao.com/v2/user/me");
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setBearerAuth(accessToken);
		
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);
		
		RestTemplate restTemplate = new RestTemplate();
		
		ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
		
		log.info(responseEntity.getBody());
	}
}
