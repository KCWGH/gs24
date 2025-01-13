package com.gs24.website.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gs24.website.domain.GiftCardVO;
import com.gs24.website.util.Pagination;

public interface GiftCardMapper {

	int insertGiftCard(GiftCardVO giftCardVO);

	List<GiftCardVO> selectList(String memberId);

	GiftCardVO selectDetailByGiftCardId(int giftCardId);

	int birthdayGiftCardDupCheck(String memberId);

	int countByGiftCardAndMemberId(@Param("giftCardName") String giftCardName, @Param("memberId") String memberId);

	int selectTotalCount(String memberId);

	int selectAvailableCount(String memberId);

	int selectExpiredCount(String memberId);

	int selectUsedCount(String memberId);

	List<GiftCardVO> selectListByPagination(Pagination pagination);

	List<GiftCardVO> selectAvailableListByPagination(Pagination pagination);

	List<GiftCardVO> selectExpiredListByPagination(Pagination pagination);

	List<GiftCardVO> selectUsedListByPagination(Pagination pagination);

	int deleteExpiredGiftCards();

}
