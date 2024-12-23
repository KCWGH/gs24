package com.gs24.website.persistence;

import org.apache.ibatis.annotations.Param;

import com.gs24.website.domain.EmailVerificationVO;

public interface EmailVerificationMapper {

	void insertVerificationCode(EmailVerificationVO verification);

	String selectMemberIdByVerificationCode(@Param("email") String email,
			@Param("verificationCode") String verificationCode);

	boolean validateVerificationCode(String email, String verificationCode);

	int selectIsExistByMemberIdAndEmailAndCode(@Param("memberId") String memberId, @Param("email") String email,
			@Param("verificationCode") String verificationCode);
}
