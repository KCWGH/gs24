package com.gs24.website.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gs24.website.domain.ConvenienceVO;
import com.gs24.website.persistence.ConvenienceMapper;
import com.gs24.website.util.Pagination;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ConvenienceServiceImple implements ConvenienceService {

	@Autowired
	private ConvenienceMapper convenienceMapper;

	@Override
	public int createConvenience(ConvenienceVO convenienceVO) {
		int result = convenienceMapper.insertConvenience(convenienceVO);
		return result;
	}

	@Override
	public List<ConvenienceVO> getAllConvenience(Pagination pagination) {
		List<ConvenienceVO> list = convenienceMapper.selectAllEnabledConvenience(pagination);
		return list;
	}

	@Override
	public int countAllEnabledConvenience() {
		return convenienceMapper.countAllEnabledConvenience();
	}

	@Override
	public int getConvenienceIdByOwnerId(String ownerId) {
		int result = convenienceMapper.selectConvenienceIdByOwnerId(ownerId);
		return result;
	}

}
