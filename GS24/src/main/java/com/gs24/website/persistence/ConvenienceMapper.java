package com.gs24.website.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gs24.website.domain.ConvenienceVO;

@Mapper
public interface ConvenienceMapper {
	int insertConvenience(ConvenienceVO convenienceVO);

	List<ConvenienceVO> selectAllConvenience();

	int selectConvenienceIdByOwnerId(String ownerId);
}
