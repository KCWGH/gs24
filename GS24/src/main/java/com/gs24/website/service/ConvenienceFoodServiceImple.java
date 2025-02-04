package com.gs24.website.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gs24.website.domain.ConvenienceFoodVO;
import com.gs24.website.domain.ReviewVO;
import com.gs24.website.persistence.ConvenienceFoodMapper;
import com.gs24.website.persistence.ConvenienceMapper;
import com.gs24.website.persistence.ReviewMapper;

import lombok.extern.java.Log;

@Service
@Log
public class ConvenienceFoodServiceImple implements ConvenienceFoodService {
	
	@Autowired
	private ConvenienceFoodMapper convenienceFoodMapper;
	
	@Autowired
	private ReviewMapper reviewMapper;
	
	@Override
	public int createConvenienceFood(ConvenienceFoodVO convenienceFoodVO) {
		log.info("createConvenienceFood()");
		
		int result = convenienceFoodMapper.insertConvenienceFood(convenienceFoodVO);
		
		return result;
	}

	@Override
	public List<ConvenienceFoodVO> getAllConvenienceFood() {
		log.info("getAllConvenienceFood()");
		
		List<ConvenienceFoodVO> list = convenienceFoodMapper.selectAllConvenienceFood();
		
		return list;
	}

	@Override
	public List<ConvenienceFoodVO> getConvenienceFoodByConvenienceId(int convenienceId) {
		log.info("getConvenienceFoodByConvenienceId()");
		
		List<ConvenienceFoodVO> list = convenienceFoodMapper.selectConvenienceFoodByConvenienceId(convenienceId);
		
		return list;
	}

	@Override
	public ConvenienceFoodVO getDetailConvenienceFoodByFoodId(int foodId) {
		log.info("getDetailConvenienceFoodByFoodId()");
		
		ConvenienceFoodVO convenienceFoodVO = convenienceFoodMapper.selectDetailConvenienceFoodByFoodId(foodId);
		
		return convenienceFoodVO;
	}

	@Override
	public List<ReviewVO> getReviewsByFoodId(int foodId) {
		log.info("getReviewsByFoodId()");
		
		List<ReviewVO> list = reviewMapper.selectReviewByFoodId(foodId);
		
		return list;
	}

}
