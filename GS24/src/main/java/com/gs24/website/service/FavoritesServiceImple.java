package com.gs24.website.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gs24.website.domain.FavoritesVO;
import com.gs24.website.domain.MemberVO;
import com.gs24.website.persistence.FavoritesMapper;
import com.gs24.website.persistence.MemberMapper;
import com.gs24.website.util.Pagination;

@Service
public class FavoritesServiceImple implements FavoritesService {

	@Autowired
	private FavoritesMapper favoritesMapper;

	@Autowired
	private MemberMapper memberMapper;

	@Override
	public int addFavorites(FavoritesVO favoritesVO) {
		return favoritesMapper.insertFavorites(favoritesVO);
	}

	@Override
	public List<FavoritesVO> getPagedFavoritesByMemberId(String memberId, Pagination pagination) {
		MemberVO memberVO = memberMapper.selectMemberByMemberId(memberId);
		pagination.setMemberVO(memberVO);
		pagination.setPageSize(4);
		return favoritesMapper.selectFavoritesListByPagination(pagination);
	}

	@Override
	public int countFavoritesByMemberId(String memberId) {
		return favoritesMapper.countFavoritesByMemberId(memberId);
	}

	@Override
	public int isAddedCheck(String memberId, int foodId, int convenienceId) {
		return favoritesMapper.countFavoritesByMemberIdAndFoodIdAndConvenienceId(memberId, foodId, convenienceId);
	}

	@Override
	public int deleteFavorites(String memberId, int foodId, int convenienceId) {
		return favoritesMapper.deleteEachFavorite(memberId, foodId, convenienceId);
	}

}
