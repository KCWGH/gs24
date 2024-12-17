package com.gs24.website.service;

import com.gs24.website.domain.MemberVO;

public interface MemberService {
   int register(MemberVO memberVO); // 회원가입

   int login(String memberId, String password); // 로그인

   MemberVO getMember(String memberId); // 회원 정보 조회

   String findId(String email); // 아이디 찾기

   int updateMember(MemberVO memberVO); // 회원 정보 수정

   int deleteMember(String memberId); // 회원 탈퇴

   int dupCheckId(String memberId);

   int dupCheckEmail(String email);

   int dupCheckPhone(String phone);
}
