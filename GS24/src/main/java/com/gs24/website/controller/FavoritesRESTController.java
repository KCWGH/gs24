package com.gs24.website.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gs24.website.domain.FavoritesVO;
import com.gs24.website.service.FavoritesService;

@RestController
@RequestMapping(value = "/favorites")
public class FavoritesRESTController {

	@Autowired
	private FavoritesService favoritesService;

	@PostMapping("/add")
	public ResponseEntity<String> addFavoritesPOST(FavoritesVO favoritesVO, RedirectAttributes redirectAttributes) {
		int result = favoritesService.addFavorites(favoritesVO);
		if (result == 1) {
			return ResponseEntity.ok("1");
		} else {
			return ResponseEntity.ok("0");
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<String> deleteFavoritesPOST(FavoritesVO favoritesVO, RedirectAttributes redirectAttributes) {
		int result = favoritesService.deleteFavorites(favoritesVO.getMemberId(), favoritesVO.getFoodId(),
				favoritesVO.getConvenienceId());
		if (result == 1) {
			return ResponseEntity.ok("1");
		} else {
			return ResponseEntity.ok("0");
		}
	}
}
