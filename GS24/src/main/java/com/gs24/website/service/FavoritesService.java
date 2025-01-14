package com.gs24.website.service;

import java.util.List;

import com.gs24.website.domain.FavoritesVO;

public interface FavoritesService {
	
	int addFavorites(FavoritesVO favoritesVO);
	
	List<FavoritesVO> list(String memberId);
	
	int countFavoritesByMemberId(String memberId);
	
	int isAddedCheck(String memberId, int foodId);
	
	int deleteFavorites(String memberId, int foodId);
}
