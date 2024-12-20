package com.gs24.website.persistence;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gs24.website.domain.MemberVO;

@Mapper
public interface MemberMapper {
	int insertUser(MemberVO memberVO);

	int login(@Param("memberId") String memberId, @Param("password") String password);

	MemberVO select(String memberId);

<<<<<<< Updated upstream
	int updatePassword(MemberVO memberVO);
	
	int updatePassword(@Param("memberId") String memberId, @Param("password") String password);

	int updateEmail(MemberVO memberVO);

	int updatePhone(MemberVO memberVO);
=======
	int update(MemberVO memberVO);
>>>>>>> Stashed changes

	int delete(String memberId);

	int dupCheckId(String memberId);

	int dupCheckEmail(String email);

	int dupCheckPhone(String phone);

	String findId(String email);

	String findEmailById(String memberId);
	
	String findPwById(String memberId);

<<<<<<< Updated upstream
	int isExistMemberByIdAndEmail(@Param("memberId") String memberId, @Param("email") String email);
=======
	int verifyMemberByIdAndEmail(@Param("memberId") String memberId, @Param("email") String email);
>>>>>>> Stashed changes
}
