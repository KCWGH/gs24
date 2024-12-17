package com.gs24.website.persistence;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gs24.website.config.RootConfig;
import com.gs24.website.domain.CouponVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class) // 스프링 JUnit test 연결
@ContextConfiguration(classes = { RootConfig.class }) // 설정 파일 연결
@Log4j
public class CouponMapperTest {

	@Autowired
	private CouponMapper couponMapper;

	@Test
	public void test() {
		//insertCoupon();
		selectList();
	}

	private void insertCoupon() {
		CouponVO couponVO = new CouponVO();
		couponVO.setCouponName("특별 할인 쿠폰");
		couponVO.setMemberId("test");
		int result = couponMapper.insertCoupon(couponVO);
		log.info(result + "개 쿠폰 등록 완료");
	}
	
	private void selectList() {
		List<CouponVO> list = couponMapper.selectList("test");
		for(CouponVO vo : list) {
			log.info(vo);
		}
	}
}
