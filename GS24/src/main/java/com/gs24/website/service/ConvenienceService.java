package com.gs24.website.service;

import java.util.List;

import com.gs24.website.domain.ConvenienceVO;
import com.gs24.website.util.Pagination;

public interface ConvenienceService {
	int createConvenience(ConvenienceVO convenienceVO);
	
	List<ConvenienceVO> getAllConvenience(Pagination pagination);
	
	int countAllEnabledConvenience();
	
	int getConvenienceIdByOwnerId(String ownerId);
}
