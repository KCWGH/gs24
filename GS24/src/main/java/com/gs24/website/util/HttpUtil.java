package com.gs24.website.util;

import java.net.URI;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class HttpUtil {
	
	@Autowired
	private RestTemplate restTemplate;
	
	//파라미터가 없냐
	public Map<String, Object> Get(HttpHeaders headers, String url) throws Exception{
//		URI uri = new URI(url);
//		
//		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);
		
//		ResponseEntity<String> resonesEntity = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);
		
		return null;
	}
	
	public Map<String, Object> Get(HttpHeaders headers, MultiValueMap<String, String> bodys, String url) throws Exception{
		URI uri = new URI(url);
		
		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<MultiValueMap<String,String>>(bodys, headers);
		
		ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		ObjectMapper objectMapper = new ObjectMapper();
		TypeReference<Map<String, Object>> typeReference = new TypeReference<Map<String,Object>>() {};
		Map<String, Object> result = objectMapper.readValue(responseEntity.getBody(), typeReference);
		
		return result;
	}
	
	public Map<String, Object> Post(HttpHeaders headers, String url) throws Exception {
		URI uri = new URI(url);
		
		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);
		
		ResponseEntity<String> reponseEntity = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		ObjectMapper objectMapper = new ObjectMapper();
		TypeReference<Map<String, Object>> typeReference = new TypeReference<Map<String,Object>>() {};
		Map<String, Object> result = objectMapper.readValue(reponseEntity.getBody(), typeReference);
		
		return result;
	}
	
	public Map<String, Object> Post(HttpHeaders headers, MultiValueMap<String, String> bodys, String url) throws Exception{
		URI uri = new URI(url);
		
		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<MultiValueMap<String,String>>(bodys, headers);
		
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(uri, httpEntity, String.class);
		
		ObjectMapper objectMapper = new ObjectMapper();
		TypeReference<Map<String, Object>> typeReference = new TypeReference<Map<String,Object>>() {};
		Map<String,Object> result = objectMapper.readValue(responseEntity.getBody(), typeReference);
		
		return result;
	}
}
