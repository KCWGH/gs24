package com.gs24.website.service;

import java.util.List;

import com.gs24.website.domain.GiftCardVO;
import com.gs24.website.util.Pagination;

public interface GiftCardService {

	int grantGiftCard(GiftCardVO giftCardVO);

	List<GiftCardVO> getGiftCardListByFoodType(String memberId, String foodType);

	GiftCardVO getGiftCardDetail(int giftCardId);

	int birthdayGiftCardDupCheck(String memberId);

	int dupCheckGiftCardNameAndMemberId(String GiftCardName, String memberId);

	List<GiftCardVO> getPagedAllGiftCards(String memberId, Pagination pagination);

	List<GiftCardVO> getPagedUnusedGiftCards(String memberId, Pagination pagination);

	List<GiftCardVO> getPagedExpiredGiftCards(String memberId, Pagination pagination);

	List<GiftCardVO> getPagedUsedGiftCards(String memberId, Pagination pagination);

	int getAllCount(String memberId);

	int getAvailableCount(String memberId);

	int getExpiredCount(String memberId);

	int getUsedCount(String memberId);

	int useGiftCard(int giftCardId, int preorderId);

	void deleteExpiredGiftCards();

}
