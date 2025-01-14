package com.gs24.website.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gs24.website.domain.ImgFoodVO;
import com.gs24.website.domain.ImgReviewVO;
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

	@Override
	public ImgReviewVO getImgReviewById(int imgId) {
		ImgReviewVO imgReviewVO = imgReviewMapper.selectImgReviewById(imgId);
		return imgReviewVO;
	}

	@Override
	public ImgFoodVO getImgFoodById(int imgFoodId) {
		ImgFoodVO imgFoodVO = imgFoodMapper.selectImgFoodById(imgFoodId);
		return imgFoodVO;
	}
	
}
