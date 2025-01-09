package com.gs24.website.util;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EmailVerificationCodeCheck {
	private String code;
	private LocalDateTime timestamp;
}