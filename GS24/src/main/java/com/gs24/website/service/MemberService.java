package com.gs24.website.service;

import com.gs24.website.domain.MemberVO;

public interface MemberService {
	int registerMember(MemberVO memberVO); // 회원가입

	MemberVO getMember(String memberId); // 회원 정보 조회

	String findMemberIdByEmail(String email);

	String findEmailByMemberId(String memberId);

	String findPhoneByMemberId(String memberId);

	int findGrade(String memberId);

	int updateMemberPassword(MemberVO memberVO); // 회원 정보 수정

	int updateMemberPassword(String memberId, String password);

	int updateMemberEmail(MemberVO memberVO); // 회원 정보 수정

	int updateMemberPhone(MemberVO memberVO); // 회원 정보 수정

	int deleteMember(String memberId);

	int dupCheckMemberId(String memberId);
	
	int dupCheckNickname(String nickname);

	int dupCheckMemberEmail(String email);

	int dupCheckMemberPhone(String phone);

	int dupCheckMemberByMemberIdAndMemberEmail(String memberId, String email);

	int reActivateMember(String memberId);

	int findSpentAmount(String memberId);

}
