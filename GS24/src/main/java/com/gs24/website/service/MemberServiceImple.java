package com.gs24.website.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gs24.website.domain.MemberVO;
import com.gs24.website.persistence.MemberMapper;

@Service
public class MemberServiceImple implements MemberService {

	@Autowired
	private MemberMapper memberMapper;

	@Override
	public int register(MemberVO memberVO) {
		// 회원 가입 처리
		return memberMapper.insertUser(memberVO);
	}

	@Override
	public int login(String memberId, String password) {
		// 로그인 처리
		return memberMapper.login(memberId, password);
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
<<<<<<< Updated upstream
	public int updateMemberPassword(MemberVO memberVO) {
		return memberMapper.updatePassword(memberVO);
	}

	@Override
	public int updateMemberPassword(String memberId, String password) {
		return memberMapper.updatePassword(memberId, password);
	}

	@Override
	public int updateMemberEmail(MemberVO memberVO) {
		return memberMapper.updateEmail(memberVO);
	}

	@Override
	public int updateMemberPhone(MemberVO memberVO) {
		return memberMapper.updatePhone(memberVO);
=======
	public int updateMember(MemberVO memberVO) {
		// 회원 정보 수정
		return memberMapper.update(memberVO);
>>>>>>> Stashed changes
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String findPhoneById(String memberId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateMemberPassword(MemberVO memberVO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateMemberPassword(String memberId, String password) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateMemberEmail(MemberVO memberVO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateMemberPhone(MemberVO memberVO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int dupCheckIdAndEmail(String memberId, String email) {
		// TODO Auto-generated method stub
		return 0;
	}
<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
}
