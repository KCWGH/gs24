package com.gs24.website.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gs24.website.domain.MemberVO;
import com.gs24.website.persistence.GiftCardMapper;
import com.gs24.website.persistence.MemberMapper;
import com.gs24.website.persistence.MembershipMapper;
import com.gs24.website.persistence.OwnerMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class MemberServiceImple implements MemberService {

	@Autowired
	private MemberMapper memberMapper;

	@Autowired
	private MembershipMapper membershipMapper;

	@Autowired
	private OwnerMapper ownerMapper;

	@Autowired
	private GiftCardMapper giftCardMapper;

	@Lazy
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional(value = "transactionManager")
	public int registerMember(MemberVO memberVO) {
		String memberId = memberVO.getMemberId();
		String password = memberVO.getPassword();
		String encodedPassword = passwordEncoder.encode(password);
		memberVO.setPassword(encodedPassword);
		String email = memberVO.getEmail();
		String phone = memberVO.getPhone();
		if (ownerMapper.countOwnerByOwnerId(memberId) == 0 && dupCheckMemberId(memberId) == 0
				&& dupCheckMemberEmail(email) == 0 && dupCheckMemberPhone(phone) == 0) {
			int result = memberMapper.insertMember(memberVO);
			membershipMapper.insertMembership(memberId);
			return result;
		}
		return 0;
	}

	@Override
	public MemberVO getMember(String memberId) {
		return memberMapper.selectMemberByMemberId(memberId);
	}

	@Override
	public String findMemberIdByEmail(String email) {
		return memberMapper.selectMemberIdByEmail(email);
	}

	@Override
	public int updateMemberPassword(MemberVO memberVO) {
		return memberMapper.updateMemberPassword(memberVO);
	}

	@Override
	public int updateMemberPassword(String memberId, String password) {
		String encodedPassword = passwordEncoder.encode(password);
		password = encodedPassword;
		return memberMapper.updateMemberPassword(memberId, password);
	}

	@Override
	public int updateMemberEmail(MemberVO memberVO) {
		return memberMapper.updateMemberEmail(memberVO);
	}

	@Override
	public int updateMemberPhone(MemberVO memberVO) {
		return memberMapper.updateMemberPhone(memberVO);
	}

	@Override
	@Transactional(value = "transactionManager")
	public int deleteMember(String memberId) {
		if (giftCardMapper.countRemainingGiftCardsByMemberId(memberId) == 0) { // 잔액이 남은 기프트카드가 없을 경우
		} else if (giftCardMapper.countRemainingGiftCardsByMemberId(memberId) != 0) { // 잔액이 남은 기프트카드가 있을 경우
			return 2;
		}
		return memberMapper.deleteMemberByMemberId(memberId);
	}

	@Override
	public int dupCheckMemberId(String memberId) { // 회원 아이디 중복 체크
		return memberMapper.countMemberByMemberId(memberId);
	}

	@Override
	public int dupCheckMemberEmail(String email) { // 이메일 중복 체크
		return memberMapper.countMemberByEmail(email);
	}

	@Override
	public int dupCheckMemberPhone(String phone) { // 전화번호 중복 체크
		return memberMapper.countMemberByPhone(phone);
	}

	@Override
	public String findEmailByMemberId(String memberId) {
		return memberMapper.selectEmailByMemberId(memberId);
	}

	@Override
	public String findPhoneByMemberId(String memberId) {
		return memberMapper.selectPhoneByMemberId(memberId);
	}

	@Override
	public int dupCheckNickname(String nickname) {
		return memberMapper.countMemberByNickname(nickname);
	}

	@Override
	public int dupCheckMemberByMemberIdAndMemberEmail(String memberId, String email) {
		return memberMapper.countMemberByMemberIdAndEmail(memberId, email);
	}

	@Override
	public int findGrade(String memberId) {
		return memberMapper.selectGradeByMemberId(memberId);
	}

	@Override
	public int reActivateMember(String memberId) {
		return memberMapper.activateMember(memberId);
	}

	@Override
	public int findSpentAmount(String memberId) {
		return membershipMapper.selectSpentAmount(memberId);
	}

	@Override
	public int dupCheckSocialAccount(String email) {
		return memberMapper.countSocialAccountByEmail(email);
	}

}
