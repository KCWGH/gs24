package com.gs24.website.persistence;

import org.apache.ibatis.annotations.Param;

import com.gs24.website.domain.EmailVerificationVO;

public interface EmailVerificationMapper {

	// 이메일 인증번호 저장
	void insertVerificationCode(EmailVerificationVO verification);

	// 인증번호 확인 후 회원 ID 찾기
	String selectMemberIdByVerificationCode(@Param("email") String email,
			@Param("verificationCode") String verificationCode);

	// 인증번호 유효성 체크
	boolean validateVerificationCode(String email, String verificationCode);

	int selectIsExistByMemberIdAndEmailAndCode(@Param("memberId") String memberId, @Param("email") String email,
			@Param("verificationCode") String verificationCode);
}
