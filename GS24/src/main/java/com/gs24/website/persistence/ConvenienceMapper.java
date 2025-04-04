package com.gs24.website.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gs24.website.domain.ConvenienceVO;
import com.gs24.website.util.Pagination;

@Mapper
public interface ConvenienceMapper {
	int insertConvenience(String ownerId);

	List<ConvenienceVO> selectAllEnabledConvenience(Pagination pagination);

	int countAllEnabledConvenience();

	int selectConvenienceIdByOwnerId(String ownerId);
	
	ConvenienceVO selectConvenienceByOwnerId(String ownerId);
	
	String selectAddressByConvenienceId(int convenienceId);
}