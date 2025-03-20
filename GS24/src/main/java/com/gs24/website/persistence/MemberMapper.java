package com.gs24.website.persistence;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gs24.website.domain.MemberVO;

@Mapper
public interface MemberMapper {
	int insertMember(MemberVO memberVO);

	MemberVO selectMemberByMemberId(String memberId);

	MemberVO selectMemberByEmail(String email);
	
	String selectMemberIdByEmail(String email);

	String selectEmailByMemberId(String memberId);

	String selectPhoneByMemberId(String memberId);

	int selectGradeByMemberId(String memberId);

	int countMemberByMemberId(String memberId);

	int countMemberByEmail(String email);

	int countMemberByPhone(String phone);

	int countMemberByMemberIdAndEmail(@Param("memberId") String memberId, @Param("email") String email);

	int updateMemberPassword(MemberVO memberVO);

	int updateMemberPassword(@Param("memberId") String memberId, @Param("password") String password);

	int updateMemberEmail(MemberVO memberVO);

	int updateMemberPhone(MemberVO memberVO);

	int deleteMemberByMemberId(String memberId);

	int activateMember(String memberId);
	
	int insertSocial(@Param("accountId") int accountId,@Param("socialId") String socialId ,@Param("provider") String provider,@Param("email") String email);
	
	int selectAccountId();
}
