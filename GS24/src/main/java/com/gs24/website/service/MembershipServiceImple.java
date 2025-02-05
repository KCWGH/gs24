package com.gs24.website.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gs24.website.domain.GiftCardVO;
import com.gs24.website.persistence.GiftCardMapper;
import com.gs24.website.persistence.MembershipMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class MembershipServiceImple implements MembershipService {
	
	public MembershipServiceImple() {
        System.out.println("Scheduler Created!");
    }

	@Autowired
	private GiftCardMapper giftCardMapper;

	@Autowired
	private MembershipMapper membershipMapper;

	@Override
	@Transactional(value = "transactionManager")
	@Scheduled(cron = "*/10 * * * * *") // 10초마다 실행
	public void membership() {
		LocalDate lastDayOfMonth = YearMonth.now().atEndOfMonth();
		LocalDateTime lastDateTime = lastDayOfMonth.atTime(23, 59);
		Date giftCardExpiredDate = Date.from(lastDateTime.atZone(ZoneId.systemDefault()).toInstant());

		distributeGiftCards(membershipMapper.selectSilverMember(), "실버 회원 1000원 금액권", 1000, giftCardExpiredDate);
		distributeGiftCards(membershipMapper.selectGoldMember(), "골드 회원 3000원 금액권", 3000, giftCardExpiredDate);

		membershipMapper.membershipEvaluation();
		membershipMapper.membershipPromotion();
		membershipMapper.initializeSpentAmount();
	}

	private void distributeGiftCards(String[] memberIds, String giftCardName, int balance, Date expiredDate) {
		for (String memberId : memberIds) {
			GiftCardVO giftCardVO = new GiftCardVO();
			giftCardVO.setGiftCardExpiredDate(expiredDate);
			giftCardVO.setBalance(balance);
			giftCardVO.setGiftCardName(giftCardName);
			giftCardVO.setFoodType("전체");
			giftCardVO.setMemberId(memberId);
			giftCardMapper.insertGiftCard(giftCardVO);
		}
	}

}