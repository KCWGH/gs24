package com.gs24.website.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EmailVerificationVO {
	private String email; // 이메일
	private String verificationCode; // 인증번호
	private String memberId;
}