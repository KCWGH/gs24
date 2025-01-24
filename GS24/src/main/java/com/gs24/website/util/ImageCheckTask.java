package com.gs24.website.util;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gs24.website.domain.ImgVO;
import com.gs24.website.persistence.ImgFoodMapper;
import com.gs24.website.persistence.ImgReviewMapper;

import lombok.extern.log4j.Log4j;

@Component
@Log4j
public class ImageCheckTask {
	
	@Autowired
	private ImgFoodMapper imgFoodMapper;
	
	@Autowired
	private ImgReviewMapper imgReviewMapper;
	
	@Autowired
    private String uploadPath;
	
	public void deleteReviewImage() {
		log.warn("---------------------------");
		log.warn("delete review image task run");
		
		List<ImgVO> imgList = imgReviewMapper.selectOldReview();
		
		if(imgList.size() == 0) {
			log.warn("delete review image task exit");
			return;
		}
		
		List<String> savedList = imgList.stream().map(this::toChgName).collect(Collectors.toList());
		
		
		log.warn("savedList : " + savedList);
		
		File targetDir = Paths.get(uploadPath, imgList.get(0).getImgPath()).toFile();
		
		log.info("isDirectory : " + targetDir.isDirectory());
		
		log.info("isFile : " + targetDir.isFile());
		
		File[] removeFiles = targetDir.listFiles(file -> savedList.contains(file.getName()) == false);
		
		for(int i=0; i<removeFiles.length; i++) {
			log.info( "delete " + i+"th file : " + removeFiles[i].getName());
			removeFiles[i].delete();
		}
		targetDir.delete();
	}
	
	@Scheduled(cron = "0 33 10 * * *")
	public void deleteFoodImage() {
		log.warn("---------------------------");
		log.warn("delete food image task run");
		
		List<ImgVO> imgList = imgFoodMapper.selectOldFood();
		
		if(imgList.size() == 0) {
			log.warn("delete food image task exit");
			return;
		}
		
		List<String> savedList = imgList.stream().map(this::toChgName).collect(Collectors.toList());
		
		
		log.warn("savedList : " + savedList);
		
		File targetDir = Paths.get(uploadPath, imgList.get(0).getImgPath()).toFile();
		
		log.info("isDirectory : " + targetDir.isDirectory());
		
		log.info("isFile : " + targetDir.isFile());
		
		File[] foodFiles = targetDir.listFiles(file -> savedList.contains(file.getName()) == false);
		
		for(int i=0; i<foodFiles.length; i++) {
			log.info( "delete " + i+"th file : " + foodFiles[i].getName());
			foodFiles[i].delete();
		}
		targetDir.delete();
	}
	
	public String toChgName(ImgVO imgVO) {
		return imgVO.getImgChgName() + "." + imgVO.getImgExtension();
	}
	
	public boolean isImage(ImgVO imgVO) {
		String extension = imgVO.getImgExtension().toLowerCase();
		
		if(extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png") || extension.equals("gif")) {
			return true;			
		} else {
			return false;
		}
	}
}
