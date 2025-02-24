package com.gs24.website.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gs24.website.domain.OwnerVO;

@Mapper
public interface OwnerMapper {
	int insertOwner(OwnerVO ownerVO);

	OwnerVO selectOwnerByOwnerId(String ownerId);

	String selectOwnerIdByEmail(String email);

	String selectEmailByOwnerId(String ownerId);

	String selectPhoneByOwnerId(String ownerId);

	int updateOwnerPassword(OwnerVO ownerVO);

	int updateOwnerPassword(@Param("ownerId") String ownerId, @Param("password") String password);

	int updateOwnerEmail(OwnerVO ownerVO);

	int updateOwnerPhone(OwnerVO ownerVO);

	int deleteOwnerByOwnerId(String ownerId);

	int countOwnerByOwnerId(String ownerId);

	int countOwnerByEmail(String email);

	int countOwnerByPhone(String phone);

	int countOwnerByOwnerIdAndEmail(@Param("ownerId") String ownerId, @Param("email") String email);

	List<OwnerVO> selectOwnerVOList();

	List<OwnerVO> selectOwnerListByOwnerId(String ownerId);

	String[] selectActivationRequestedOwners();

	int requestActivation(String ownerId);

	int activateOwner(String ownerId);

}
