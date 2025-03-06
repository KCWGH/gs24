package com.gs24.website.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gs24.website.domain.GiftCardVO;
import com.gs24.website.util.Pagination;

@Mapper
public interface GiftCardMapper {

	int insertGiftCard(GiftCardVO giftCardVO);

	List<GiftCardVO> selectListByMemberIdAndFoodType(@Param("memberId") String memberId,
			@Param("foodType") String foodType);

	GiftCardVO selectDetailByGiftCardId(int giftCardId);

	int birthdayGiftCardDupCheck(String memberId);

	int countByGiftCardAndMemberId(@Param("giftCardName") String giftCardName, @Param("memberId") String memberId);

	int countRemainingGiftCardsByMemberId(String memberId);

	int selectTotalCount(String memberId);

	int selectUnusedCount(String memberId);

	int selectExpiredCount(String memberId);

	int selectUsedCount(String memberId);

	List<GiftCardVO> selectListByPagination(Pagination pagination);

	List<GiftCardVO> selectUnusedListByPagination(Pagination pagination);

	List<GiftCardVO> selectExpiredListByPagination(Pagination pagination);

	List<GiftCardVO> selectUsedListByPagination(Pagination pagination);

	int useGiftCard(@Param("giftCardId") int giftCardId, @Param("refundVal") int refundVal);

	int refundGiftCard(@Param("giftCardId") int giftCardId, @Param("refundVal") int refundVal);

	int deleteExpiredGiftCards();

	int deleteTotallyUsedGiftCards();

}
