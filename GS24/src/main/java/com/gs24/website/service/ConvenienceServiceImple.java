package com.gs24.website.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gs24.website.domain.ConvenienceFoodVO;
import com.gs24.website.domain.ConvenienceVO;
import com.gs24.website.domain.FoodVO;
import com.gs24.website.persistence.ConvenienceFoodMapper;
import com.gs24.website.persistence.ConvenienceMapper;
import com.gs24.website.persistence.FoodMapper;
import com.gs24.website.util.Pagination;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ConvenienceServiceImple implements ConvenienceService {

	@Autowired
	private ConvenienceMapper convenienceMapper;

	@Autowired
	private ConvenienceFoodMapper convenienceFoodMapper;
	 
	@Autowired
	private FoodMapper foodMapper;
	 
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
	
	@Override
	public List<FoodVO> getFoodDetailsByOwnerId(String ownerId) {
		 // ownerId로 해당 편의점 정보 조회
        ConvenienceVO convenience = convenienceMapper.selectConvenienceByOwnerId(ownerId);
        if (convenience == null) {
            return Collections.emptyList();
        }

        // convenienceId로 연결된 음식 목록 조회
        List<ConvenienceFoodVO> convenienceFoods = convenienceFoodMapper.selectFoodsByConvenienceId(convenience.getConvenienceId());
        List<FoodVO> foodDetails = new ArrayList<>();

        for (ConvenienceFoodVO convenienceFood : convenienceFoods) {
            // 각 음식에 대해 상세 정보 조회
            FoodVO food = foodMapper.selectFoodById(convenienceFood.getFoodId());
            foodDetails.add(food);
        }

        return foodDetails;
	}

}
