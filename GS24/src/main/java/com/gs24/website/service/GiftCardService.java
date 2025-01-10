package com.gs24.website.service;

import java.util.List;

import com.gs24.website.domain.GiftCardVO;
import com.gs24.website.util.Pagination;

public interface GiftCardService {

	int grantGiftCard(GiftCardVO giftCardVO);

	List<GiftCardVO> getGiftCardList(String memberId);

	GiftCardVO getGiftCardDetail(int giftCardId);

	int birthdayGiftCardDupCheck(String memberId);

	int dupCheckGiftCardNameAndMemberId(String GiftCardName, String memberId);

	List<GiftCardVO> getPagedAllGiftCards(String memberId, Pagination pagination);

	List<GiftCardVO> getPagedAvailableGiftCards(String memberId, Pagination pagination);

	List<GiftCardVO> getPagedExpiredGiftCards(String memberId, Pagination pagination);

	List<GiftCardVO> getPagedUsedGiftCards(String memberId, Pagination pagination);

	int getAllCount(String memberId);

	int getAvailableCount(String memberId);

	int getExpiredCount(String memberId);

	int getUsedCount(String memberId);

	void deleteExpiredGiftCards();

}
