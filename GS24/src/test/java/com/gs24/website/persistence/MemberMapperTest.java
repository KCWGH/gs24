package com.gs24.website.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gs24.website.config.RootConfig;
import com.gs24.website.domain.MemberVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class) // �뒪�봽留� JUnit test �뿰寃�
@ContextConfiguration(classes = { RootConfig.class }) // �꽕�젙 �뙆�씪 �뿰寃�
@Log4j
public class MemberMapperTest {

	@Autowired
	private MemberMapper memberMapper;

	@Autowired
	private EmailVerificationMapper emailVerificationMapper;
	
	@Test
	public void test() {
		// testInsertUser();
		// testselect();
		// testlogin();
		// testFindId();
<<<<<<< HEAD
<<<<<<< HEAD
		//testUpdate();
		//testverify();
		
		testfindemail();
=======
=======
>>>>>>> ccdce2e7a9f17201e6bb89e46d18852272cf8bfd
		// testUpdate();
		// testverify();

		// testfindemail();

		// testupdatepassword();
		// testupdateEmail();
	}

	private void testupdateEmail() {
		MemberVO memberVO = new MemberVO();
		memberVO.setMemberId("test");
		memberVO.setEmail("new@naver.com");
		int result = memberMapper.updateEmail(memberVO);
		log.info(result + "개 이메일 수정 완료");

	}

	private void testupdatepassword() {
		MemberVO memberVO = new MemberVO();
		memberVO.setMemberId("test");
		memberVO.setPassword("1234");
		int result = memberMapper.updatePassword(memberVO);

		log.info(result + "개 비밀번호 수정 완료");
<<<<<<< HEAD
>>>>>>> ccdce2e7a9f17201e6bb89e46d18852272cf8bfd
=======
>>>>>>> ccdce2e7a9f17201e6bb89e46d18852272cf8bfd
	}

	private void testfindemail() {
		String result = memberMapper.findEmailById("test");
<<<<<<< HEAD
<<<<<<< HEAD
		MemberVO memberVO = new MemberVO();
		memberVO.setMemberId("test");
		memberVO.setEmail("�뒋21");
		memberVO.setPhone("�닔123123123");
		int result1 = memberMapper.update(memberVO);
=======
>>>>>>> ccdce2e7a9f17201e6bb89e46d18852272cf8bfd
=======
>>>>>>> ccdce2e7a9f17201e6bb89e46d18852272cf8bfd
		log.info(result);
	}

	/*
	 * private void testverify() { int result =
	 * forgotPasswordMapper.verifyIdandEmail("test", "test@naver.com");
	 * log.info(result);
	 * 
	 * }
	 */
	/*
	 * private void testUpdate() { log.info("testUpdate()");
	 * 
	 * MemberVO memberVO = new MemberVO(); memberVO.setMemberId("test");
	 * memberVO.setEmail("�뒋�젙"); memberVO.setPhone("�닔�젙"); int result =
	 * memberMapper.update(memberVO); log.info(result);
	 * 
	 * }
	 * 
	 * private void testFindId() { log.info("testFindId()"); String memberId =
	 * memberMapper.findId("test@naver.com"); log.info("�븘�씠�뵒 李얠쓬 : " + memberId); }
	 * 
	 * private void testlogin() { int result = memberMapper.login("test", "124"); if
	 * (result == 1) { log.info("濡쒓렇�씤 �셿猷�"); } else { log.info("�옒紐삳맂 id/pw"); }
	 * 
	 * }
	 * 
	 * private void testselect() { MemberVO vo = memberMapper.select("nmbgsp95");
	 * log.info(vo); log.info(vo.getPhone()); }
	 * 
	 * private void testInsertUser() { log.info("testInsertUser()"); }
	 */

}
