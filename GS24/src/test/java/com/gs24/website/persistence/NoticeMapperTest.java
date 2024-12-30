package com.gs24.website.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gs24.website.config.RootConfig;
import com.gs24.website.domain.NoticeVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class) // �뒪�봽留� JUnit test �뿰寃�
@ContextConfiguration(classes = { RootConfig.class }) // �꽕�젙 �뙆�씪 �뿰寃�
@Log4j
public class NoticeMapperTest {

	@Autowired
	private NoticeMapper noticeMapper;

	@Test
	public void test() {
//		 testInsertNotice();		
	}	

	private void testInsertNotice() {
		log.info("testInsertNotice()");

	}

//	private void testInsertNotice() {
//		log.info("testInsertNotice()");
//		NoticeVO vo = new NoticeVO(0, "guest","testTitle","testContent", null);
//		int result = noticeMapper.insert(vo);
//		log.info(result + "�뻾 �궫�엯");
//	}


}
