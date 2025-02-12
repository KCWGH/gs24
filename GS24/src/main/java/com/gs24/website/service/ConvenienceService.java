package com.gs24.website.service;

import java.util.List;

import com.gs24.website.domain.ConvenienceVO;

public interface ConvenienceService {
	int createConvenience(ConvenienceVO convenienceVO);
	
	List<ConvenienceVO> getAllConvenience();
	
	int getConvenienceIdByOwnerId(String ownerId);
}
