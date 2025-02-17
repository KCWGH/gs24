package com.gs24.website.persistence;

import org.apache.ibatis.annotations.Param;

public interface MembershipMapper {
	int insertMembership(String memberId);

	String[] selectSilverMember();

	String[] selectGoldMember();

	int deleteMembership(String memberId);

	int membershipEvaluation();

	int membershipPromotion();

	int initializeSpentAmount();

	int addSpentAmount(@Param("spentAmount")int spentAmount, @Param("memberId") String memberId);
}
