package com.gs24.website.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.gs24.website.domain.GiftCardVO;
import com.gs24.website.persistence.GiftCardMapper;
import com.gs24.website.util.Pagination;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class GiftCardServiceImple implements GiftCardService {

	@Autowired
	private GiftCardMapper giftCardMapper;

	@Override
	public int grantGiftCard(GiftCardVO giftCardVO) {
		int result = giftCardMapper.insertGiftCard(giftCardVO);
		return result;
	}

	@Override
	public List<GiftCardVO> getGiftCardListByFoodType(String memberId, String foodType) {
		return giftCardMapper.selectListByMemberIdAndFoodType(memberId, foodType);
	}

	@Override
	public int dupCheckGiftCardNameAndMemberId(String giftCardName, String memberId) {
		return giftCardMapper.countByGiftCardAndMemberId(giftCardName, memberId);
	}

	@Override
	public GiftCardVO getGiftCardDetail(int giftCardId) {
		GiftCardVO giftCardVO = giftCardMapper.selectDetailByGiftCardId(giftCardId);
		return giftCardVO;
	}

	@Override
	public int getAllCount(String memberId) {
		return giftCardMapper.selectTotalCount(memberId);

	}

	@Override
	public int getAvailableCount(String memberId) {
		return giftCardMapper.selectUnusedCount(memberId);
	}

	@Override
	public int getExpiredCount(String memberId) {
		return giftCardMapper.selectExpiredCount(memberId);
	}

	@Override
	public int getUsedCount(String memberId) {
		return giftCardMapper.selectUsedCount(memberId);
	}

	@Override
	public List<GiftCardVO> getPagedAllGiftCards(String memberId, Pagination pagination) {
		return giftCardMapper.selectListByPagination(pagination);
	}

	@Override
	public List<GiftCardVO> getPagedUnusedGiftCards(String memberId, Pagination pagination) {
		return giftCardMapper.selectUnusedListByPagination(pagination);
	}

	@Override
	public List<GiftCardVO> getPagedExpiredGiftCards(String memberId, Pagination pagination) {
		return giftCardMapper.selectExpiredListByPagination(pagination);
	}

	@Override
	public List<GiftCardVO> getPagedUsedGiftCards(String memberId, Pagination pagination) {
		return giftCardMapper.selectUsedListByPagination(pagination);
	}

	@Override
	public int birthdayGiftCardDupCheck(String memberId) {
		return giftCardMapper.birthdayGiftCardDupCheck(memberId);
	}

	@Override
	public int useGiftCard(int giftCardId, int preorderId) {
		return giftCardMapper.useGiftCard(giftCardId, preorderId);
	}

	@Override
	@Scheduled(cron = "0 0 0 * * *")
	public void deleteExpiredGiftCards() {
		int result = giftCardMapper.deleteExpiredGiftCards();
		if (result == 1) {
			log.info("deleteExpiredGiftCards()");
		}
	}

}
