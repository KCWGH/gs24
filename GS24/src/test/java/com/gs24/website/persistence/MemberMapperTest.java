package com.gs24.website.persistence;

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

    @Autowired
    private ForgotPasswordMapper forgotPasswordMapper;

    @Test
    public void test() {
        // 여러 테스트 메서드를 주석 처리하거나 원하는 것만 활성화
        testFindEmail();
    }

    private void testFindEmail() {
        String result = memberMapper.findEmailById("test");
        log.info(result); // 테스트 결과 출력
    }

    private void testUpdate() {
        log.info("testUpdate()");

        MemberVO memberVO = new MemberVO();
        memberVO.setMemberId("test");
        memberVO.setEmail("슈정");
        memberVO.setPhone("수정");
        int result = memberMapper.update(memberVO);
        log.info(result); // 업데이트 결과 출력
    }

    private void testFindId() {
        log.info("testFindId()");
        String memberId = memberMapper.findId("test@naver.com");
        log.info("아이디 찾음 : " + memberId); // 찾은 아이디 출력
    }

    private void testLogin() {
        int result = memberMapper.login("test", "124");
        if (result == 1) {
            log.info("로그인 완료");
        } else {
            log.info("잘못된 id/pw");
        }
    }

    private void testSelect() {
        MemberVO vo = memberMapper.select("nmbgsp95");
        log.info(vo); // 선택된 회원 정보 출력
        log.info(vo.getPhone()); // 전화번호 출력
    }

    private void testInsertUser() {
        log.info("testInsertUser()");

        // 테스트용 사용자 정보 삽입 로직
        MemberVO memberVO = new MemberVO();
        memberVO.setMemberId("test");
        memberVO.setEmail("test@naver.com");
        memberVO.setPhone("123456789");
        int result = memberMapper.insertUser(memberVO);
        log.info("Insert result: " + result); // 삽입 결과 출력
    }
}
