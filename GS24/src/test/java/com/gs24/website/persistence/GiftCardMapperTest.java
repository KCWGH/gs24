package com.gs24.website.persistence;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gs24.website.config.RootConfig;
import com.gs24.website.domain.EarlyBirdCouponVO;
import com.gs24.website.domain.GiftCardVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class) // 스프링 JUnit test 연결
@ContextConfiguration(classes = { RootConfig.class }) // 설정 파일 연결
@Log4j
public class GiftCardMapperTest {

	@Autowired
	private GiftCardMapper giftCardMapper;

	@Autowired
	private EarlyBirdCouponMapper earlyBirdCouponMapper;

	@Test
	public void test() {
		// insertCoupon();
		// dupcheck();
		// insert();
		// use();
		detail();
	}

	private void detail() {
		GiftCardVO giftCardVO = giftCardMapper.selectDetailByGiftCardId(1);
		log.info(giftCardVO.toString());
	}

	private void use() {
		int result = earlyBirdCouponMapper.useEarlyBirdCoupon(1);
		log.info(result + "이에요.");
	}

	private void insert() {
		EarlyBirdCouponVO birdCouponVO = new EarlyBirdCouponVO();
		birdCouponVO.setEarlyBirdCouponName("zz");
		birdCouponVO.setDiscountType('A');
		birdCouponVO.setDiscountValue(1000);
		birdCouponVO.setEarlyBirdCouponAmount(10);
		birdCouponVO.setEarlyBirdCouponExpiredDate(new Date());
		birdCouponVO.setFoodType("음료");
		int result = earlyBirdCouponMapper.insertEarlyBirdCoupon(birdCouponVO);
		log.info(result + "입니다!!~~");
	}

	private void dupcheck() {
		int result = giftCardMapper.countByGiftCardAndMemberId("쿠폰1", "test");

		log.info(result + "입니다");
	}

}
