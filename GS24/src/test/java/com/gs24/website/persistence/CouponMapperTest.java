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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RootConfig.class })
@Log4j
public class CouponMapperTest {

	@Autowired
	private CouponMapper couponMapper;

	@Test
	public void test() {
<<<<<<< HEAD
		// insertCoupon();
=======
		insertCoupon();
>>>>>>> 20091d038ecd342f64f0e1bcb2640eb581d47778
		selectList();
	}

	private void insertCoupon() {
		CouponVO couponVO = new CouponVO();
		couponVO.setCouponName("ÄíÆùÅ×½ºÆ®");
		couponVO.setMemberId("test");
		int result = couponMapper.insertCoupon(couponVO);
		log.info(result + "Çà »ðÀÔ");
	}
	
	private void selectList() {
		List<CouponVO> list = couponMapper.selectList("test");
		for(CouponVO vo : list) {
			log.info(vo);
		}
	}
}
