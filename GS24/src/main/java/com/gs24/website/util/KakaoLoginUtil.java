package com.gs24.website.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Component
@Getter
@Setter
@Log4j
public class KakaoLoginUtil {
	
	@Autowired
	private HttpUtil httpUtil;
	
	private String clientId = "37a993700004ae9f4806d2f6830189c6";
	private String redirectUri = "http://localhost:8080/website/auth/kakao";
	private String adminKey = "d7ee430e236ccd79d4b99b65bba3ff16";
	/**
	 * 카카오 로그인 API access_token 정보를 가져오기 위한 인가코드 전송 메소드
	 * @param code 카카오 로그인 API 인가코드 받기를 통해 가져온 코드 값
	 * @return Map<String,String> 변수로 반환
	 */
	public Map<String,Object> sendCode(String code) throws Exception {
        // 헤더 설정을 담는 변수 선언
        HttpHeaders headers = new HttpHeaders();
        // Content-Type: application/x-www-form-urlencoded; 설정 추가
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        
        // 바디(파라미터) 설정
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);
        
        Map<String,Object> result = httpUtil.Post(headers, body, "https://kauth.kakao.com/oauth/token");
        
        return result;
    }
	
	/**
	 * access_token을 이용해 로그인한 사용자의 정보를 가져오는 메소드
	 * @param accessToken 카카오 로그인 API를 통해 가져온 access_token 값
	 * @return Map<String, Object> 변수로 반환
	 */
	public Map<String,Object> getUserInfo(String accessToken) throws Exception {
		
		// 헤더 설정을 담는 변수 선언
		HttpHeaders headers = new HttpHeaders();
		// Content-Type: application/x-www-form-urlencoded; 설정 추가
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		// Authorization: Bearer ${ACCESS_TOKEN} 설정 추가
		headers.setBearerAuth(accessToken);
        // 변환된 데이터를 담는 변수 선언
		
        Map<String, Object> result = httpUtil.Post(headers, "https://kapi.kakao.com/v2/user/me");
        
        result = refactorKakaoAccount(result);
        
        // Map 데이터 확인
        log.info("result : " + result);
        
        return result;
		
	}
	
	private Map<String, Object> refactorKakaoAccount(Map<String, Object> result){
		
		@SuppressWarnings("unchecked")
		Map<String, Object> properties = (Map<String, Object>)result.get("properties");
        String nickname = properties.get("nickname").toString();
        
        result.remove("properties");
        result.put("nickname", nickname);
        
        properties.clear();
        
        @SuppressWarnings("unchecked")
		Map<String, Object> kakaoAccount = (Map<String, Object>) result.get("kakao_account");
		
        kakaoAccount.remove("profile");
        kakaoAccount.remove("profile_nickname_needs_agreement");
        kakaoAccount.remove("has_email");
        kakaoAccount.remove("email_needs_agreement");
        kakaoAccount.remove("is_email_valid");
        kakaoAccount.remove("is_email_verified");
        kakaoAccount.remove("has_phone_number");
        kakaoAccount.remove("phone_number_needs_agreement");
        kakaoAccount.remove("has_birthyear");
        kakaoAccount.remove("birthyear_needs_agreement");
        kakaoAccount.remove("has_birthday");
        kakaoAccount.remove("birthday_needs_agreement");
        kakaoAccount.remove("birthday_type");
        kakaoAccount.remove("is_leap_month");
		
		result.remove("kakao_account");
        result.putAll(kakaoAccount);
        
        String[] phone = result.get("phone_number").toString().split("-");
		String number = "010-" + phone[1] + "-" + phone[2];
		
		result.remove("phone_number");
		result.put("phoneNumber", number);
		
		int year = Integer.parseInt(result.get("birthyear").toString());
		int month = Integer.parseInt(result.get("birthday").toString())/100;
		int day = Integer.parseInt(result.get("birthday").toString())%100;
				
		// Calendar 객체 사용
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month-1, day, 0, 0, 0);
        
        // Date 객체로 변환
        Date date = new Date(calendar.getTimeInMillis());
        
		result.remove("birthyear");
		result.remove("birthday");
		result.put("birthday", date);
        
        kakaoAccount.clear();
		
		return result;
	}
}
