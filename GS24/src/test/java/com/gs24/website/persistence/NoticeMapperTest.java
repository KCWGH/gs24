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
public class NoticeMapperTest {

	@Autowired
	private NoticeMapper noticeMapper;

	@Test
	public void test() {		
	}	

<<<<<<< HEAD
	private void testInsertNotice() {
		log.info("testInsertNotice()");

	}

//	private void testInsertNotice() {
//		log.info("testInsertNotice()");
//		NoticeVO vo = new NoticeVO(0, "guest","testTitle","testContent", null);
//		int result = noticeMapper.insert(vo);
//		log.info(result + "�뻾 �궫�엯");
//	}

=======
>>>>>>> c10c874fbe2a4197563ffb7d351cdee7e02242a6

}
