package com.gs24.website.persistence;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gs24.website.domain.MemberVO;

@Mapper
public interface MemberMapper {
	int insertUser(MemberVO memberVO);

	int login(@Param("memberId") String memberId, @Param("password") String password);

	MemberVO select(String memberId);

<<<<<<< HEAD
<<<<<<< HEAD
	int update(MemberVO memberVO);
=======
=======
>>>>>>> ccdce2e7a9f17201e6bb89e46d18852272cf8bfd
	int updatePassword(MemberVO memberVO);
	
	int updatePassword(@Param("memberId") String memberId, @Param("password") String password);

	int updateEmail(MemberVO memberVO);

	int updatePhone(MemberVO memberVO);
<<<<<<< HEAD
>>>>>>> ccdce2e7a9f17201e6bb89e46d18852272cf8bfd
=======
>>>>>>> ccdce2e7a9f17201e6bb89e46d18852272cf8bfd

	int delete(String memberId);

	int dupCheckId(String memberId);

	int dupCheckEmail(String email);

	int dupCheckPhone(String phone);

	String findId(String email);

	String findEmailById(String memberId);

	String findPhoneById(String memberId);

	String findPwById(String memberId);

<<<<<<< HEAD
<<<<<<< HEAD
	int verifyMemberByIdAndEmail(@Param("memberId") String memberId, @Param("email") String email);
	
=======
	int isExistMemberByIdAndEmail(@Param("memberId") String memberId, @Param("email") String email);
>>>>>>> ccdce2e7a9f17201e6bb89e46d18852272cf8bfd
=======
	int isExistMemberByIdAndEmail(@Param("memberId") String memberId, @Param("email") String email);
>>>>>>> ccdce2e7a9f17201e6bb89e46d18852272cf8bfd
}
