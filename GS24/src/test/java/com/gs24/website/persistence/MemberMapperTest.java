package com.gs24.website.persistence;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gs24.website.config.RootConfig;
import com.gs24.website.domain.MemberVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class) // 스프링 JUnit test 연결
@ContextConfiguration(classes = { RootConfig.class }) // 설정 파일 연결
@Log4j
public class MemberMapperTest {

	@Autowired
	private MemberMapper memberMapper;

	@Test
	public void test() {
		// testInsertUser();
		// testBoardList();
		// testBoardDetailByBoardId();
		// testBoardUpdate(3);
		// testBoardDelete(7);
		testselect();
	}

	private void testselect() {
		MemberVO vo = memberMapper.select("test");
		log.info(vo);
		log.info(vo.getPhone());
	}
	

	private void testInsertUser() {
		log.info("testInsertUser()");
		MemberVO vo = new MemberVO("test","1234","test@naver.com","010-1234-5678", new Date(), 1, 1);
		int result = memberMapper.insertUser(vo);
		log.info(result + "행 삽입");
	}

}
