package com.gs24.website.persistence;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gs24.website.domain.MemberVO;

@Mapper
public interface MemberMapper {
   int insertUser(MemberVO memberVO);

   int login(@Param("memberId") String memberId, @Param("password") String password);

   MemberVO select(String memberId);

	int update(MemberVO memberVO);

	int delete(String memberId);

	int dupCheckId(String memberId);

	int dupCheckEmail(String email);

	int dupCheckPhone(String phone);

	String findId(String email);

	String findEmailById(String memberId);
	
	String findPwById(String memberId);

	int verifyMemberByIdAndEmail(@Param("memberId") String memberId, @Param("email") String email);
	
}
