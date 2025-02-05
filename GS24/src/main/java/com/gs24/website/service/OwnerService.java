package com.gs24.website.service;

import java.util.List;

import com.gs24.website.domain.OwnerVO;

public interface OwnerService {

	int registerOwner(OwnerVO ownerVO);

	OwnerVO getOwner(String ownerId);

	String findOwnerIdByEmail(String email);

	String findEmailByOwnerId(String ownerId);

	String findPhoneByOwnerId(String ownerId);

	int dupCheckOwnerId(String ownerId);

	int dupCheckOwnerEmail(String email);

	int dupCheckOwnerPhone(String phone);

	int dupCheckOwnerByOwnerIdAndOwnerEmail(String ownerId, String email);

	int updateOwnerPassword(String ownerId, String password);

	int updateOwnerEmail(OwnerVO ownerVO);

	int updateOwnerPhone(OwnerVO ownerVO);

	int deleteOwner(String ownerId);
	
	List<OwnerVO> getOwnerVOList();
		
	List<OwnerVO> getOwnerListByUsername(String ownerId);


}
