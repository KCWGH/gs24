package com.gs24.website.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gs24.website.domain.MemberVO;
import com.gs24.website.persistence.MemberMapper;

@Service
public class MemberServiceImple implements MemberService {

	@Autowired
	private MemberMapper memberMapper;

<<<<<<< HEAD
<<<<<<< HEAD
   @Override
   public int register(MemberVO memberVO) {
      // �쉶�썝 媛��엯 泥섎━
      return memberMapper.insertUser(memberVO);
   }

   @Override
   public int login(String memberId, String password) {
      // 濡쒓렇�씤 泥섎━
      return memberMapper.login(memberId, password);
   }

   @Override
   public MemberVO getMember(String memberId) {
      // �쉶�썝 �젙蹂� 議고쉶
      return memberMapper.select(memberId);
   }

   @Override
   public String findId(String email) {
      // �씠硫붿씪�쓣 �씠�슜�빐 �븘�씠�뵒 李얘린
      return memberMapper.findId(email);
   }

   @Override
   public int updateMember(MemberVO memberVO) {
      // �쉶�썝 �젙蹂� �닔�젙
      return memberMapper.update(memberVO);
   }

   @Override
   public int deleteMember(String memberId) {
      // �쉶�썝 �깉�눜 泥섎━
      return memberMapper.delete(memberId);
   }
   @Override
   public int dupCheckId(String memberId) {
      // �쉶�썝 �븘�씠�뵒 以묐났 泥댄겕
      return memberMapper.dupCheckId(memberId); // MemberMapper�쓽 dupCheckId �샇異�
   }

   @Override
   public int dupCheckEmail(String email) {
      // �씠硫붿씪 以묐났 泥댄겕
      return memberMapper.dupCheckEmail(email); // MemberMapper�쓽 dupCheckEmail �샇異�
   }

   @Override
   public int dupCheckPhone(String phone) {
      // �쟾�솕踰덊샇 以묐났 泥댄겕
      return memberMapper.dupCheckPhone(phone); // MemberMapper�쓽 dupCheckPhone �샇異�
   }

=======
	@Override
	public int register(MemberVO memberVO) {
		String memberId = memberVO.getMemberId();
		String email = memberVO.getEmail();
		String phone = memberVO.getPhone();
		if (dupCheckId(memberId) == 0 && dupCheckEmail(email) == 0 && dupCheckPhone(phone) == 0) {
			return memberMapper.insertUser(memberVO);
		}
		return 0;
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
	public int updateMemberPassword(MemberVO memberVO) {
		return memberMapper.updatePassword(memberVO);
	}

=======
	@Override
	public int register(MemberVO memberVO) {
		String memberId = memberVO.getMemberId();
		String email = memberVO.getEmail();
		String phone = memberVO.getPhone();
		if (dupCheckId(memberId) == 0 && dupCheckEmail(email) == 0 && dupCheckPhone(phone) == 0) {
			return memberMapper.insertUser(memberVO);
		}
		return 0;
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
	public int updateMemberPassword(MemberVO memberVO) {
		return memberMapper.updatePassword(memberVO);
	}

>>>>>>> ccdce2e7a9f17201e6bb89e46d18852272cf8bfd
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
<<<<<<< HEAD

	@Override
	public String findPhoneById(String memberId) {
		return memberMapper.findPhoneById(memberId);
	}

	@Override
	public int dupCheckIdAndEmail(String memberId, String email) {
		return memberMapper.isExistMemberByIdAndEmail(memberId, email);
	}

>>>>>>> ccdce2e7a9f17201e6bb89e46d18852272cf8bfd
=======

	@Override
	public String findPhoneById(String memberId) {
		return memberMapper.findPhoneById(memberId);
	}

	@Override
	public int dupCheckIdAndEmail(String memberId, String email) {
		return memberMapper.isExistMemberByIdAndEmail(memberId, email);
	}

>>>>>>> ccdce2e7a9f17201e6bb89e46d18852272cf8bfd
}
