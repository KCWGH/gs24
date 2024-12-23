package com.gs24.website.service;

import com.gs24.website.domain.MemberVO;

public interface MemberService {
	int register(MemberVO memberVO);

	int login(String memberId, String password);

	MemberVO getMember(String memberId); 

	String findId(String email);

	String findEmailById(String memberId);

	String findPhoneById(String memberId);
	
	int updateMember(MemberVO memberVO);

	int updateMemberPassword(MemberVO memberVO);
	
	int updateMemberPassword(String memberId, String password);

	int updateMemberEmail(MemberVO memberVO); 

	int updateMemberPhone(MemberVO memberVO);

	int deleteMember(String memberId);

	int dupCheckId(String memberId);

	int dupCheckEmail(String email);

	int dupCheckPhone(String phone);

	int dupCheckIdAndEmail(String memberId, String email);
}
