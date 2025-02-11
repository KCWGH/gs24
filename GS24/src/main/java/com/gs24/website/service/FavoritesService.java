package com.gs24.website.service;

import java.util.List;

import com.gs24.website.domain.FavoritesVO;
import com.gs24.website.util.Pagination;

public interface FavoritesService {

	int addFavorites(FavoritesVO favoritesVO);

	int countFavoritesByMemberId(String memberId);

	int isAddedCheck(String memberId, int foodId, int convenienceId);

	int deleteFavorites(String memberId, int foodId, int convenienceId);

	List<FavoritesVO> getPagedFavoritesByMemberId(String memberId, Pagination pagination);
}
