package com.gs24.website.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gs24.website.domain.FavoritesVO;
import com.gs24.website.persistence.FavoritesMapper;
import com.gs24.website.util.Pagination;

@Service
public class FavoritesServiceImple implements FavoritesService {

	@Autowired
	private FavoritesMapper favoritesMapper;

	@Override
	public int addFavorites(FavoritesVO favoritesVO) {
		return favoritesMapper.insertFavorites(favoritesVO);
	}

	@Override
	public List<FavoritesVO> getPagedfavoritesByMemberId(String memberId, Pagination pagination) {
		return favoritesMapper.selectFavoritesListByPagination(pagination);
	}

	@Override
	public int countFavoritesByMemberId(String memberId) {
		return favoritesMapper.countFavoritesByMemberId(memberId);
	}

	@Override
	public int isAddedCheck(String memberId, int foodId) {
		return favoritesMapper.countFavoritesByMemberIdAndFoodId(memberId, foodId);
	}

	@Override
	public int deleteFavorites(String memberId, int foodId) {
		return favoritesMapper.deleteEachFavorite(memberId, foodId);
	}

}
