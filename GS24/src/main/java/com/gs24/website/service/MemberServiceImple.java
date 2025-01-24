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

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class MemberServiceImple implements MemberService {

	@Autowired
	private MemberMapper memberMapper;

	@Autowired
	private MembershipMapper membershipMapper;
	
	@Autowired
	private GiftCardMapper giftCardMapper;

	@Lazy
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional(value = "transactionManager")
	public int register(MemberVO memberVO) {
		String memberId = memberVO.getMemberId();
		String password = memberVO.getPassword();
		String encodedPassword = passwordEncoder.encode(password);
		memberVO.setPassword(encodedPassword);
		String email = memberVO.getEmail();
		String phone = memberVO.getPhone();
		if (dupCheckId(memberId) == 0 && dupCheckEmail(email) == 0 && dupCheckPhone(phone) == 0) {
			if (memberVO.getMemberRole() == 1) {
				membershipMapper.insertMembership(memberId);
			}
			return memberMapper.insertUser(memberVO);
		}
		return 0;
	}

	@Override
	public int login(String memberId, String password) {
		String encodedPassword = passwordEncoder.encode(password);
		return memberMapper.login(memberId, encodedPassword);
	}

	@Override
	public MemberVO getMember(String memberId) {
		return memberMapper.select(memberId);
	}

	@Override
	public String findId(String email) {
		// 이메일을 이용해 아이디 찾기
		return memberMapper.findId(email);
	}

	@Override
	public int updateMemberPassword(MemberVO memberVO) {
		return memberMapper.updatePassword(memberVO);
	}

	@Override
	public int updateMemberPassword(String memberId, String password) {
		String encodedPassword = passwordEncoder.encode(password);
		password = encodedPassword;
		return memberMapper.updatePassword(memberId, password);
	}

	@Override
	public int updateMemberEmail(MemberVO memberVO) {
		return memberMapper.updateEmail(memberVO);
	}

	@Override
	public int updateMemberPhone(MemberVO memberVO) {
		return memberMapper.updatePhone(memberVO);
	}

	@Override
	@Transactional(value = "transactionManager")
	public int deleteMember(String memberId) {
		MemberVO memberVO = memberMapper.select(memberId);
		if (memberVO.getMemberRole() == 1 && giftCardMapper.countRemainingGiftCardsByMemberId(memberId) == 0) { // 일반회원이고 잔액이 남은 기프트카드가 없을 경우
			membershipMapper.deleteMembership(memberId);
		} else if (giftCardMapper.countRemainingGiftCardsByMemberId(memberId) != 0) { // 잔액이 남은 기프트카드가 있을 경우
			return 2;
		}
		return memberMapper.delete(memberId);
	}

	@Override
	public int dupCheckId(String memberId) {
		// 회원 아이디 중복 체크
		return memberMapper.dupCheckId(memberId);
	}

	@Override
	public int dupCheckEmail(String email) {
		// 이메일 중복 체크
		return memberMapper.dupCheckEmail(email);
	}

	@Override
	public int dupCheckPhone(String phone) {
		// 전화번호 중복 체크
		return memberMapper.dupCheckPhone(phone);
	}

	@Override
	public String findEmailById(String memberId) {
		return memberMapper.findEmailById(memberId);
	}

	@Override
	public String findPhoneById(String memberId) {
		return memberMapper.findPhoneById(memberId);
	}

	@Override
	public int dupCheckIdAndEmail(String memberId, String email) {
		return memberMapper.isExistMemberByIdAndEmail(memberId, email);
	}

	@Override
	public int checkRole(String memberId) {
		return memberMapper.selectRole(memberId);
	}

	@Override
	public int checkGrade(String memberId) {
		return memberMapper.selectGrade(memberId);
	}
}
