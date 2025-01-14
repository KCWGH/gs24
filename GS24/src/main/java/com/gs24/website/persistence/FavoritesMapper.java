package com.gs24.website.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gs24.website.domain.FavoritesVO;

@Mapper
public interface FavoritesMapper {

	int insertFavorites(FavoritesVO favoritesVO);

	List<FavoritesVO> selectList(String memberId);

	int countFavoritesByMemberId(String memberId);

	int countFavoritesByMemberIdAndFoodId(@Param("memberId") String memberId, @Param("foodId") int foodId);

	int deleteFavorites(@Param("memberId") String memberId, @Param("foodId") int foodId);
}
