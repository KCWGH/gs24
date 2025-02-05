package com.gs24.website.persistence;

import org.apache.ibatis.annotations.Mapper;

import com.gs24.website.domain.AdminVO;

@Mapper
public interface AdminMapper {
	AdminVO selectAdminByAdminId(String adminId);
}
