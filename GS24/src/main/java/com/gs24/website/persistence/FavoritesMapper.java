package com.gs24.website.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gs24.website.domain.FavoritesVO;
import com.gs24.website.util.Pagination;

@Mapper
public interface FavoritesMapper {

	int insertFavorites(FavoritesVO favoritesVO);

	List<FavoritesVO> selectFavoritesListByPagination(Pagination pagination);

	int countFavoritesByMemberId(String memberId);

	int countFavoritesByMemberIdAndFoodIdAndConvenienceId(@Param("memberId") String memberId,
			@Param("foodId") int foodId, @Param("convenienceId") int convenienceId);

	int deleteEachFavorite(@Param("memberId") String memberId, @Param("foodId") int foodId,
			@Param("convenienceId") int convenienceId);

	int deleteAllFavorites(int foodId);
}
