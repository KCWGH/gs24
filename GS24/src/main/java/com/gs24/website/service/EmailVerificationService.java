package com.gs24.website.service;

public interface EmailVerificationService {
    // 이메일에 해당하는 회원이 있는지 확인
    boolean isMemberExist(String email);

    // 인증번호 생성 및 이메일 발송
    void sendVerificationCode(String email) throws Exception;

    // 인증번호로 회원 ID 찾기
    String verifyCodeAndFindMemberId(String email, String verificationCode);
}
