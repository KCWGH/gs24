package com.gs24.website.persistence;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gs24.website.domain.MemberVO;

@Mapper
public interface MemberMapper {

	// 회원 가입 처리
	int insertUser(MemberVO memberVO);

	// 로그인 처리
	int login(@Param("memberId") String memberId, @Param("password") String password);

	// 회원 정보 조회
	MemberVO select(String memberId);

<<<<<<< Updated upstream
	int updatePassword(MemberVO memberVO);
	
	int updatePassword(@Param("memberId") String memberId, @Param("password") String password);

	int updateEmail(MemberVO memberVO);

	int updatePhone(MemberVO memberVO);
=======
	// 회원 정보 수정
>>>>>>> Stashed changes
	int update(MemberVO memberVO);

	// 회원 탈퇴 처리
	int delete(String memberId);

	// 아이디 중복 체크
	int dupCheckId(String memberId);

	// 이메일 중복 체크
	int dupCheckEmail(String email);

	// 전화번호 중복 체크
	int dupCheckPhone(String phone);

	// 이메일을 이용한 아이디 찾기
	String findId(String email);

	// 회원 아이디로 이메일 찾기
	String findEmailById(String memberId);

	// 회원 아이디로 비밀번호 찾기
	String findPwById(String memberId);

<<<<<<< Updated upstream
	int isExistMemberByIdAndEmail(@Param("memberId") String memberId, @Param("email") String email);
	
=======
	// 아이디와 이메일을 이용해 회원이 존재하는지 확인
>>>>>>> Stashed changes
	int verifyMemberByIdAndEmail(@Param("memberId") String memberId, @Param("email") String email);
}
