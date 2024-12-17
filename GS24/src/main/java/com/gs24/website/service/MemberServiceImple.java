package com.gs24.website.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gs24.website.domain.MemberVO;
import com.gs24.website.persistence.MemberMapper;

@Service
public class MemberServiceImple implements MemberService {

   @Autowired
   private MemberMapper memberMapper;

   @Override
   public int register(MemberVO memberVO) {
      // 회원 가입 처리
      return memberMapper.insertUser(memberVO);
   }

   @Override
   public int login(String memberId, String password) {
      // 로그인 처리
      return memberMapper.login(memberId, password);
   }

   @Override
   public MemberVO getMember(String memberId) {
      // 회원 정보 조회
      return memberMapper.select(memberId);
   }

   @Override
   public String findId(String email) {
      // 이메일을 이용해 아이디 찾기
      return memberMapper.findId(email);
   }

   @Override
   public int updateMember(MemberVO memberVO) {
      // 회원 정보 수정
      return memberMapper.update(memberVO);
   }

   @Override
   public int deleteMember(String memberId) {
      // 회원 탈퇴 처리
      return memberMapper.delete(memberId);
   }

   @Override
   public int dupCheckId(String memberId) {
      // 회원 아이디 중복 체크
      return memberMapper.dupCheckId(memberId); // MemberMapper의 dupCheckId 호출
   }

   @Override
   public int dupCheckEmail(String email) {
      // 이메일 중복 체크
      return memberMapper.dupCheckEmail(email); // MemberMapper의 dupCheckEmail 호출
   }

   @Override
   public int dupCheckPhone(String phone) {
      // 전화번호 중복 체크
      return memberMapper.dupCheckPhone(phone); // MemberMapper의 dupCheckPhone 호출
   }
}
