package com.gs24.website.util;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gs24.website.domain.ImgFoodVO;
import com.gs24.website.persistence.ImgFoodMapper;

import lombok.extern.log4j.Log4j;

@Component
@Log4j
public class ImageCheckTask {
	
	@Autowired
	private ImgFoodMapper imgFoodMapper;
	
	@Autowired
    private String uploadPath;
	
	public void deleteFoodImage() {
		log.warn("---------------------------");
		log.warn("delete image task run");
		
		List<ImgFoodVO> imgFoodList = imgFoodMapper.selectOldFood();
		
		if(imgFoodList.size() == 0) {
			return;
		}
		
		List<String> savedList = imgFoodList.stream().filter(this::isImage).map(this::toChgName).collect(Collectors.toList());
		
		log.warn(savedList);
		
		File targetDir = Paths.get(uploadPath, imgFoodList.get(0).getImgFoodPath()).toFile();
		
		
		
		//TODO : 지금은 DB에 ImgFoodPath가 .png 등의 파일 형태로 저장되어 있지만 나중에 폴더 명으로 한다면 바뀌어야 한다.
		log.info("isDirectory : " + targetDir.isDirectory());
		
		log.info("isFile : " + targetDir.isFile());
	}
	
	public String toChgName(ImgFoodVO imgFoodVO) {
		return imgFoodVO.getImgFoodChgName();
	}
	
	public boolean isImage(ImgFoodVO imgFoodVO) {
		String extension = imgFoodVO.getImgFoodExtension().toLowerCase();
		
		if(extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png") || extension.equals("gif")) {
			return true;			
		} else {
			return false;
		}
	}
}
