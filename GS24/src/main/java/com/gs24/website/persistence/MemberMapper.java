package com.gs24.website.persistence;

import org.apache.ibatis.annotations.Mapper;

import com.gs24.website.domain.MemberVO;

@Mapper
public interface MemberMapper {
	int insertUser(MemberVO memberVO);

	MemberVO select(String memberId);
}
