package com.gs24.website.persistence;

import org.apache.ibatis.annotations.Param;

public interface ForgotPasswordMapper {
	int verifyIdandEmail(@Param("memberId") String memberId, @Param("email") String email);
}
