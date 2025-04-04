package com.gs24.website.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gs24.website.domain.OwnerVO;
import com.gs24.website.persistence.ConvenienceMapper;
import com.gs24.website.persistence.MemberMapper;
import com.gs24.website.persistence.OwnerMapper;
import com.gs24.website.persistence.PreorderMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class OwnerServiceImple implements OwnerService {

	@Autowired
	private MemberMapper memberMapper;

	@Autowired
	private OwnerMapper ownerMapper;

	@Autowired
	private ConvenienceMapper convenienceMapper;
	
	@Autowired
	private PreorderMapper preorderMapper;

	@Lazy
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public int registerOwner(OwnerVO ownerVO) {
		String ownerId = ownerVO.getOwnerId();
		String password = ownerVO.getPassword();
		String encodedPassword = passwordEncoder.encode(password);
		ownerVO.setPassword(encodedPassword);
		String email = ownerVO.getEmail();
		String phone = ownerVO.getPhone();
		if (memberMapper.countMemberByMemberId(ownerId) == 0 && dupCheckOwnerId(ownerId) == 0
				&& dupCheckOwnerEmail(email) == 0 && dupCheckOwnerPhone(phone) == 0) {

			int result = ownerMapper.insertOwner(ownerVO);

			return result;
		}

		return 0;
	}

	@Override
	public OwnerVO getOwner(String ownerId) {
		return ownerMapper.selectOwnerByOwnerId(ownerId);
	}

	@Override
	public String findOwnerIdByEmail(String email) {
		return ownerMapper.selectOwnerIdByEmail(email);
	}

	@Override
	public String findEmailByOwnerId(String ownerId) {
		return ownerMapper.selectEmailByOwnerId(ownerId);
	}

	@Override
	public String findPhoneByOwnerId(String ownerId) {
		return ownerMapper.selectPhoneByOwnerId(ownerId);
	}

	@Override
	public int dupCheckOwnerId(String ownerId) {
		return ownerMapper.countOwnerByOwnerId(ownerId);
	}

	@Override
	public int dupCheckOwnerEmail(String email) {
		return ownerMapper.countOwnerByEmail(email);
	}

	@Override
	public int dupCheckOwnerPhone(String phone) {
		return ownerMapper.countOwnerByPhone(phone);
	}

	@Override
	public int dupCheckOwnerByOwnerIdAndOwnerEmail(String ownerId, String email) {
		return ownerMapper.countOwnerByOwnerIdAndEmail(ownerId, email);
	}

	@Override
	public int updateOwnerPassword(String ownerId, String password) {
		String encodedPassword = passwordEncoder.encode(password);
		password = encodedPassword;
		return ownerMapper.updateOwnerPassword(ownerId, password);
	}

	@Override
	public int updateOwnerEmail(OwnerVO ownerVO) {
		return ownerMapper.updateOwnerEmail(ownerVO);
	}

	@Override
	public int updateOwnerPhone(OwnerVO ownerVO) {
		return ownerMapper.updateOwnerPhone(ownerVO);
	}

	@Override
	public int deleteOwner(String ownerId) {
		int convenienceId = convenienceMapper.selectConvenienceIdByOwnerId(ownerId);
		if(preorderMapper.countNotPickUpPreordersByConvenienceId(convenienceId) == 0) {			
			return ownerMapper.deleteOwnerByOwnerId(ownerId);
		}
		return 0;
	}

	@Override
	public List<OwnerVO> getOwnerVOList() {
		List<OwnerVO> list = ownerMapper.selectOwnerVOList();
		return list;
	}

	@Override
	public List<OwnerVO> getOwnerListByUsername(String ownerId) {

		return ownerMapper.selectOwnerListByOwnerId(ownerId);
	}

	@Override
	public int requestActivationOwner(String ownerId) {
		return ownerMapper.requestActivation(ownerId);
	}

	@Override
	public int reActivateOwner(String ownerId) {
		convenienceMapper.insertConvenience(ownerId);
		return ownerMapper.activateOwner(ownerId);
	}

	@Override
	public String[] selectActivationRequestedOwners() {
		return ownerMapper.selectActivationRequestedOwners();
	}

	@Override
	public String[] selectUnauthorizedOwners() {
		return ownerMapper.selectUnauthorizedOwners();
	}

}
