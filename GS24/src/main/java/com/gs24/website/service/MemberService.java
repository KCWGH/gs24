package com.gs24.website.service;

import com.gs24.website.domain.MemberVO;

public interface MemberService {
	int register(MemberVO memberVO); // 회원가입

	int login(String memberId, String password); // 로그인

	MemberVO getMember(String memberId); // 회원 정보 조회

	String findId(String email); // 아이디 찾기

	String findEmailById(String memberId);

	String findPhoneById(String memberId);

	int updateMemberPassword(MemberVO memberVO); // 회원 정보 수정
	
	int updateMemberPassword(String memberId, String password);

	int updateMemberEmail(MemberVO memberVO); // 회원 정보 수정

	int updateMemberPhone(MemberVO memberVO); // 회원 정보 수정

	int deleteMember(String memberId); // 회원 탈퇴

	int dupCheckId(String memberId);

	int dupCheckEmail(String email);

	int dupCheckPhone(String phone);

	int dupCheckIdAndEmail(String memberId, String email);
}
