package com.gs24.website.persistence;

import org.apache.ibatis.annotations.Param;
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
		// testselect();
		// testlogin();
		// testFindId();
		testUpdate();
	}

	private void testUpdate() {
		log.info("testUpdate()");
		
		MemberVO memberVO = new MemberVO();
		memberVO.setMemberId("test");
		memberVO.setEmail("슈정");
		memberVO.setPhone("수정");
		int result = memberMapper.update(memberVO);
		log.info(result);

	}

	private void testFindId() {
		log.info("testFindId()");
		String memberId = memberMapper.findId("test@naver.com");
		log.info("아이디 찾음 : " + memberId);
	}

	private void testlogin() {
		int result = memberMapper.login("test", "124");
		if (result == 1) {
			log.info("로그인 완료");
		} else {
			log.info("잘못된 id/pw");
		}

	}

	private void testselect() {
		MemberVO vo = memberMapper.select("nmbgsp95");
		log.info(vo);
		log.info(vo.getPhone());
	}

	private void testInsertUser() {
		log.info("testInsertUser()");
	}

}
