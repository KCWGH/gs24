package com.gs24.website.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gs24.website.config.RootConfig;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class) // 스프링 JUnit test 연결
@ContextConfiguration(classes = { RootConfig.class }) // 설정 파일 연결
@Log4j
public class GiftCardMapperTest {

	@Autowired
	private GiftCardMapper giftCardMapper;

	@Autowired
	private CouponMapper earlyBirdCouponMapper;

	@Test
	public void test() {
		// insertCoupon();
		// dupcheck();
		// insert();
		// use();
	}

	private void dupcheck() {
		int result = giftCardMapper.countByGiftCardAndMemberId("쿠폰1", "test");

		log.info(result + "입니다");
	}

}
