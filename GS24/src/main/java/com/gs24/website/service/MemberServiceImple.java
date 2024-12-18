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

}
