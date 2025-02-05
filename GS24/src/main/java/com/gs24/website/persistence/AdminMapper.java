package com.gs24.website.persistence;

import org.apache.ibatis.annotations.Mapper;

import com.gs24.website.domain.AdminVO;

@Mapper
public interface AdminMapper {
	
	int insertAdmin(AdminVO adminVO);
	
	AdminVO selectAdminByAdminId(String adminId);
}
