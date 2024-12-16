package com.gs24.website.persistence;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gs24.website.domain.MemberVO;

@Mapper
public interface MemberMapper {
	int dupCheckId(String memberId);

	int dupCheckEmail(String email);

	int dupCheckPhone(String phone);

	int insertUser(MemberVO memberVO);

	int login(@Param("memberId") String memberId, @Param("password") String password);

	MemberVO select(String memberId);

	String findId(String email);

	int update(MemberVO memberVO);
	
	int delete(String memberId);
}
