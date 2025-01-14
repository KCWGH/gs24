package com.gs24.website.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gs24.website.domain.FavoritesVO;
import com.gs24.website.persistence.FavoritesMapper;

@Service
public class FavoritesServiceImple implements FavoritesService {

	@Autowired
	private FavoritesMapper favoritesMapper;

	@Override
	public int addFavorites(FavoritesVO favoritesVO) {
		return favoritesMapper.insertFavorites(favoritesVO);
	}

	@Override
	public List<FavoritesVO> list(String memberId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int countFavoritesByMemberId(String memberId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int isAddedCheck(String memberId, int foodId) {
		return favoritesMapper.countFavoritesByMemberIdAndFoodId(memberId, foodId);
	}

	@Override
	public int deleteFavorites(String memberId, int foodId) {
		return favoritesMapper.deleteFavorites(memberId, foodId);
	}

}
