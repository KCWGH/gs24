package com.gs24.website.persistence;

public interface MembershipMapper {
	int insertMembership(String memberId);

	String[] selectSilverMember();

	String[] selectGoldMember();

	int deleteMembership(String memberId);

	int membershipEvaluation();

	int membershipPromotion();

	int initializeSpentAmount();

	int addSpentAmount(int spentAmount, String memberId);
}
