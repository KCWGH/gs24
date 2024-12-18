package com.gs24.website.persistence;

import com.gs24.website.domain.EmailVerificationVO;

public interface EmailVerificationMapper {

	// 이메일에 해당하는 회원 존재 여부 확인
	String checkMemberByEmail(String email);

	// 이메일 인증번호 저장
	void saveVerificationCode(EmailVerificationVO verification);

	// 인증번호 확인 후 회원 ID 찾기
	String findMemberIdByVerificationCode(String email, String verificationCode);

	// 인증번호 유효성 체크
	boolean validateVerificationCode(String email, String verificationCode);
}
