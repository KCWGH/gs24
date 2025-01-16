package com.gs24.website.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gs24.website.domain.FavoritesVO;
import com.gs24.website.domain.FoodVO;
import com.gs24.website.service.FavoritesService;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping(value = "/favorites")
@Log4j
public class FavoritesRESTController {

	@Autowired
	private FavoritesService favoritesService;

	@PostMapping("/add")
	public ResponseEntity<String> addFavoritesPOST(FavoritesVO favoritesVO, FoodVO foodVO,
			RedirectAttributes redirectAttributes) {
		favoritesVO.setFoodId(foodVO.getFoodId());
		favoritesVO.setFoodType(foodVO.getFoodType());
		favoritesVO.setFoodName(foodVO.getFoodName());
		int result = favoritesService.addFavorites(favoritesVO);
		if (result == 1) {
			return ResponseEntity.ok("1");
		} else {
			return ResponseEntity.ok("0");
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<String> deleteFavoritesPOST(String memberId, int foodId,
			RedirectAttributes redirectAttributes) {
		int result = favoritesService.deleteFavorites(memberId, foodId);
		if (result == 1) {
			return ResponseEntity.ok("1");
		} else {
			return ResponseEntity.ok("0");
		}
	}
}
