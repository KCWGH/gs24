package com.gs24.website.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gs24.website.domain.ImgVO;
import com.gs24.website.persistence.ImgFoodMapper;
import com.gs24.website.persistence.ImgMapper;
import com.gs24.website.persistence.ImgReviewMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ImgServiceImple implements ImgService{
	
	@Autowired
	private ImgMapper imgMapper;
	
	@Autowired
	private ImgFoodMapper imgFoodMapper;
	
	@Autowired
	private ImgReviewMapper imgReviewMapper;
	
	@Override
	public int getNextReviewId() {
		log.info("getNextReviewId()");
		int nextReviewId = imgMapper.selectNextReviewId();
		return nextReviewId;
	}

	@Override
	public int getNextFoodId() {
		log.info("getNextFoodId()");
		int nextFoodId = imgMapper.selectNextFoodId();
		return nextFoodId;
	}

	@Override
	public ImgVO getImgReviewById(int imgId) {
		ImgVO imgReviewVO = imgReviewMapper.selectImgReviewById(imgId);
		return imgReviewVO;
	}

	@Override
	public ImgVO getImgFoodById(int imgId) {
		ImgVO imgFoodVO = imgFoodMapper.selectImgFoodById(imgId);
		return imgFoodVO;
	}
	
}
