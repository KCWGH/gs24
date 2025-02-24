package com.gs24.website.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import lombok.extern.log4j.Log4j;

@Log4j
public class KakaoLoginUtil {
	/**
	 * 카카오 로그인 REST API를 쉽게 사용할 수 있도록 작성한 메소드
	 * 
	 * @param method 통신 방식 GET | POST
	 * @param urlAdress 카카오 로그인 API에서 지정한 url 주소
	 * @param headers 카카오 로그인 API 요청에 필요한 헤더들
	 * @param parameter 카카오 르그인 API 요청에 필요한 파라미터들 | 필요한 파라미터가 없다면 null
	 * @return 응답한 데이터를 JsonElement로 객체화 시켜서 반환
	 * 
	 */
	public static JsonElement requestKakao(String method, String urlAdress, Map<String, String> headers, Map<String, String> parameter) throws IOException {
		
		//http 통신을 위한 url 지정
		URL url = new URL(urlAdress);
		//실제 http 통신을 수행하는 HttpURLConnection 생성
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		
		//통신 방식 지정 GET | POST
		connection.setRequestMethod(method);
		//요청문에 데이터를 같이 전송 여부 설정
		connection.setDoOutput(true);
		
		//headers map 데이터의 각 키값을 받아오기 위한 iterater 변수
		Iterator<String> iterator = headers.keySet().iterator();
		//http 헤더 지정
		while(iterator.hasNext()) {
			String key = iterator.next();
			connection.setRequestProperty(key, headers.get(key));
			log.info("" + key);
			log.info(headers.get(key));
		}
		
		if(parameter != null) {
			//parameter map을 http통신 요청에 맞게 변환 -> key1=value1&key2=value2 추가) JSON 방식으로 보내보았지만 실패하여 이와 같은 방식으로 전송함
			String data = "";
			iterator = parameter.keySet().iterator();
			while(iterator.hasNext()) {
				String key = iterator.next();
				data += key + "=" + parameter.get(key) + "&";
			}
			//data 문자열 마지막에 붙은 &을 없애기 위한 코드
			data = data.substring(0, data.length()-1);
			
			log.info(data);
			
			//서버로 전송하기 위한 OutputStream 선언 및 초기화
			try(OutputStream os = connection.getOutputStream()){
				//서버로 보내는 데이터를 바이트 형식으로 변환
				byte[] bs = data.getBytes("utf-8");
				//서버로 전송
				os.write(bs, 0, bs.length);
			}
		}
		
		//전송 결과를 나타내는 responseCode
		//200 정상, 400이상 전송 데이터 오류
		log.info("responseCode : " + connection.getResponseCode());
		
		//서버로부터 응답한 데이터를 읽는 BufferedReader 선언 및 초기화
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	    String line = "";	//그 데이터를 StringBuilder 객체에 저장하기 위한 변수
	    StringBuilder result = new StringBuilder(); //서버의 응답 데이터를 담는 객체
	    
	    //서버로부터의 응답을 한줄씩 읽으며 저장
	    while ((line = bufferedReader.readLine()) != null) {
	    	result.append(line);
	    }
	    //StringBuilder 리소스 해제
	    bufferedReader.close();
	    //서버로부터 응답한 데이터 출력
	    System.out.println("response body : " + result);
	    
	    //JSON으로 응답한 데이터를 객체로 변환시키기 위한 JsonParser 선언
	    com.google.gson.JsonParser jsonParser = new com.google.gson.JsonParser();
	    
	    //JSON 데이터를 JsonElement형태의 객체로 변환
	    JsonElement element = jsonParser.parse(result.toString());
	     
	    return element;
	}
	/**
	 * Map 변수를 만드는 메소드
	 * @return 길이가 1인 Map
	 */
	public static Map<String, String> createMapData(String key, Object value){
		Map<String, String> result = new HashMap<String, String>();
		
		try {
			result.put(key, value.toString());			
		} catch (Exception e) {
			log.error("value 값 변환 도중 에러 발생");
			log.error("String 값으로 변환 할 수 없습니다.");
			return null;
		}
		
		return result;
	}
	
	/**
	 * Map 변수를 만드는 메소드
	 * @param key String[] 형태의 key 값을 받아와 하나씩 넣는다.
	 * @param value Object[] 형태릐 value 값을 받아와 하나씩 넣는다.
	 * @return 길이가 n인 Map
	 */
	public static Map<String, String> createMapData(String[] key, Object[] value){
		Map<String, String> result = new HashMap<String, String>();
		
		if(key.length != value.length) {
			log.error("key 배열의 길이와 value 배열의 길이가 일치하지 않습니다.");
			return null;
		}
			
		try {
			for(int i=0; i<key.length; i++) {				
				result.put(key[i], (String)value[i]);			
			}
		} catch (Exception e) {
			log.error("value 값 변환 도중 에러 발생");
			log.error("String 값으로 변환 할 수 없습니다.");
			return null;
		}
		
		return result;
	}
	
	public static String getElementProperty(JsonElement element, String propertyName) {
		return element.getAsJsonObject().get(propertyName).getAsString();
	}
	
	public static HashMap<String, Object> getUserInfo(String accessToken) {
	    HashMap<String, Object> userInfo = new HashMap<>();
	    String postURL = "https://kapi.kakao.com/v2/user/me";

	    try {
	        URL url = new URL(postURL);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("POST");

	        conn.setRequestProperty("Authorization", "Bearer " + accessToken);

	        int responseCode = conn.getResponseCode();
	        System.out.println("responseCode : " + responseCode);

	        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        String line = "";
	        StringBuilder result = new StringBuilder();

	        while ((line = br.readLine()) != null) {
	            result.append(line);
	        }
	        System.out.println("response body : " + result);
	        
	        com.google.gson.JsonParser jsonParser = new com.google.gson.JsonParser();
	        
	        JsonElement element = jsonParser.parse(result.toString());
	        JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
	        JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

	        String nickname = properties.getAsJsonObject().get("nickname").getAsString();
	        String email = kakaoAccount.getAsJsonObject().get("email").getAsString();

	        userInfo.put("nickname", nickname);
	        userInfo.put("email", email);

	    } catch (IOException exception) {
	        exception.printStackTrace();
	    }

	    return userInfo;
	}
}
