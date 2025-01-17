package com.gs24.website.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.gs24.website.domain.GiftCardVO;
import com.gs24.website.domain.MemberVO;
import com.gs24.website.persistence.GiftCardMapper;
import com.gs24.website.persistence.MemberMapper;
import com.gs24.website.util.Pagination;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class GiftCardServiceImple implements GiftCardService {

	@Autowired
	private GiftCardMapper giftCardMapper;

	@Autowired
	private MemberMapper memberMapper;

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
	public String birthdayGiftCardDupCheckAndGrant() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String memberId = authentication.getName();
		MemberVO memberVO = memberMapper.select(memberId);
		int dupCheck = giftCardMapper.birthdayGiftCardDupCheck(memberId);
		if (dupCheck != 1 && memberVO.getMemberRole() == 1) {
			int checkGranted = birthdayGiftCard(memberId);
			if (checkGranted == 1) {
				return "생일 축하 기프트카드가 발급되었습니다.\\n기프트카드함에서 확인해보세요.";
			}
		}
		return null;
	}

	private int birthdayGiftCard(String memberId) {
		Calendar currentCalendar = Calendar.getInstance();
		Calendar birthdayCalendar = Calendar.getInstance();

		Date currentDate = new Date();
		Date birthday = memberMapper.select(memberId).getBirthday();

		currentCalendar.setTime(currentDate);
		birthdayCalendar.setTime(birthday);

		int currentMonth = currentCalendar.get(Calendar.MONTH);
		int currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH);
		int birthdayMonth = birthdayCalendar.get(Calendar.MONTH);
		int birthdayDay = birthdayCalendar.get(Calendar.DAY_OF_MONTH);

		if (currentMonth == birthdayMonth && currentDay == birthdayDay) {
			GiftCardVO giftCardVO = new GiftCardVO();
			giftCardVO.setGiftCardName("생일 축하 기프트카드");
			giftCardVO.setBalance(10000);
			giftCardVO.setFoodType("전체");
			giftCardVO.setMemberId(memberId);

			Calendar expirationCalendar = Calendar.getInstance();
			expirationCalendar.setTime(currentDate);
			expirationCalendar.add(Calendar.MONTH, 1);
			Date oneMonthLater = expirationCalendar.getTime();
			giftCardVO.setGiftCardExpiredDate(oneMonthLater);

			return giftCardMapper.insertGiftCard(giftCardVO);
		}
		return 0;
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
