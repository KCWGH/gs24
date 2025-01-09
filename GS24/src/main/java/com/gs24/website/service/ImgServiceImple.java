package com.gs24.website.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gs24.website.persistence.ImgMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ImgServiceImple implements ImgService{
	
	@Autowired
	private ImgMapper imgMapper;
	
	@Override
	public long getNextReviewId() {
		log.info("getNextReviewId()");
		long nextReviewId = imgMapper.selectNextReviewId();
		return nextReviewId;
	}

	@Override
	public long getNextFoodId() {
		log.info("getNextFoodId()");
		long nextFoodId = imgMapper.selectNextFoodId();
		return nextFoodId;
	}
	
}
