package com.gs24.website.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gs24.website.domain.FoodVO;

@Service
public class FoodRecommendationService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private FoodService foodService;

	public String getRecommendedFoods(int foodId) {
		String url = "http://127.0.0.1:5000/recommend?food_id=" + foodId;
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept-Charset", "UTF-8");
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		return response.getBody();
	}

	public List<Integer> extractFoodIds(String jsonResponse) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(jsonResponse);
		JsonNode recommendationsNode = rootNode.path("recommendations");
		List<Integer> foodIds = new ArrayList<>();
		for (JsonNode recommendation : recommendationsNode) {
			String recommendationText = recommendation.asText();
			String[] parts = recommendationText.split("\\(");
			if (parts.length > 1) {
				String idPart = parts[1].split(",")[0].trim().replace("ID:", "").trim();
				foodIds.add(Integer.parseInt(idPart));
			}
		}
		return foodIds;
	}

	public List<FoodVO> getRecommendedFoodVOList(int foodId) throws IOException {
		String recommendedFoods = this.getRecommendedFoods(foodId);
		List<Integer> extractedFoodIds = this.extractFoodIds(recommendedFoods);
		List<FoodVO> recommendedFoodVO = new ArrayList<>();
		for (Integer recommendedFoodId : extractedFoodIds) {
			FoodVO foodVO = foodService.getFoodById(recommendedFoodId);
			recommendedFoodVO.add(foodVO);
		}
		return recommendedFoodVO;
	}
}
