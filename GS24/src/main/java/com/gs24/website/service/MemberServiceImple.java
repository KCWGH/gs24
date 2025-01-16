package com.gs24.website.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gs24.website.domain.MemberVO;
import com.gs24.website.persistence.MemberMapper;

@Service
public class MemberServiceImple implements MemberService {

	@Autowired
	private MemberMapper memberMapper;

	@Lazy
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public int register(MemberVO memberVO) {
		String memberId = memberVO.getMemberId();
		String password = memberVO.getPassword();
		String encodedPassword = passwordEncoder.encode(password);
		memberVO.setPassword(encodedPassword);
		String email = memberVO.getEmail();
		String phone = memberVO.getPhone();
		if (dupCheckId(memberId) == 0 && dupCheckEmail(email) == 0 && dupCheckPhone(phone) == 0) {
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
		// 회원 정보 조회
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
	public int deleteMember(String memberId) {
		// 회원 탈퇴 처리
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

}
