package com.gs24.website.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gs24.website.domain.AdminVO;
import com.gs24.website.persistence.AdminMapper;

@Service
public class AdminServiceImple implements AdminService {

	@Autowired
	private AdminMapper adminMapper;

	@Lazy
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public int registerAdmin(AdminVO adminVO) {
		String password = adminVO.getPassword();
		String encodedPassword = passwordEncoder.encode(password);
		adminVO.setPassword(encodedPassword);
		return adminMapper.insertAdmin(adminVO);
	}

}