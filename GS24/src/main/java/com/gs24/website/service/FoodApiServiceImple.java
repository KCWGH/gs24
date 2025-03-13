package com.gs24.website.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class FoodApiServiceImple implements FoodApiService {

	private static final String SERVICE_KEY = "tyFTaP6UN5feo34+vx3Ib0v10oWY4+NN4JAysdFOrL2Tsikwiejb4Bgjt8Lg2YKJjhwrA/Dv52NiJadvcgq95g==";

	@Override
	public String searchFood(String foodName) {
		StringBuilder result = new StringBuilder();

		try {
			URI uri = buildUri(foodName);

			HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");

			BufferedReader rd;
			if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
				rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} else {
				rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}

			String line;
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			rd.close();
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"error\": \"API 호출 중 오류 발생\"}";
		}

		return result.toString();
	}

	private URI buildUri(String foodName) throws Exception {
		String baseUrl = "http://api.data.go.kr/openapi/tn_pubr_public_nutri_info_api";

		Map<String, String> params = new HashMap<>();
		params.put("serviceKey", SERVICE_KEY);
		params.put("pageNo", "1");
		params.put("numOfRows", "10");
		params.put("type", "json");
		params.put("foodNm", foodName);

		StringBuilder query = new StringBuilder();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			if (query.length() > 0) {
				query.append("&");
			}
			query.append(URLEncoder.encode(entry.getKey(), "UTF-8")).append("=")
					.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
		}

		return new URI(baseUrl + "?" + query.toString());
	}
}
