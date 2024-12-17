package com.gs24.website.service;

import javax.servlet.http.HttpSession;

public interface ForgotPasswordService {
    // 이메일 인증번호 전송
    boolean sendVerificationCode(String memberId, String email, HttpSession session);
    
    // 인증번호 확인 후 비밀번호 반환
    String verifyCodeAndGetPassword(String memberId, String verificationCode, HttpSession session);
}
