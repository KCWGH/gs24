package com.gs24.website.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gs24.website.domain.ImgVO;
import com.gs24.website.service.ImgService;
import com.gs24.website.util.GetImgUtil;
import com.gs24.website.util.uploadImgUtil;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("/image")
@Log4j
public class ImgRESTController {
	
	@Autowired
	private String uploadPath;
	
	@Autowired
	private ImgService imgService;
	
	@PostMapping("/")
	public ResponseEntity<List<ImgVO>> createImage(MultipartFile[] files, int foreignId, String type){
		log.info("createImage()");
		List<ImgVO> entity = new ArrayList<ImgVO>();
		
		log.info("foreignId : " + foreignId + " type : " + type);
		
		String dir = "";
		if(type.equals("review")) {
			if(foreignId == 0)
				foreignId = imgService.getNextReviewId();
			dir = "ReviewNO" + foreignId;
		} else if(type.equals("food")) {
			if(foreignId == 0)
				foreignId = imgService.getNextFoodId();
			dir = "FoodNO" + foreignId;
		}
		
		for(MultipartFile file : files) {
			String realName = uploadImgUtil.subStrName(file.getOriginalFilename());
			String chgName = UUID.randomUUID().toString();
			String extension = uploadImgUtil.subStrExtension(file.getOriginalFilename());
			
			uploadImgUtil.saveFile(uploadPath, file, dir, chgName, extension);
			
			ImgVO imgVO = new ImgVO();
			
			imgVO.setForeignId(foreignId);
			imgVO.setImgRealName(realName);
			imgVO.setImgChgName(chgName);
			imgVO.setImgExtension(extension);
			imgVO.setImgPath(uploadImgUtil.makeDir(dir));
			
			entity.add(imgVO);
		}
		
		return new ResponseEntity<List<ImgVO>>(entity, HttpStatus.OK);
		
	}
	
	@GetMapping("/display")
	public ResponseEntity<byte[]> getReviewImage(String path, String chgName , String extension){
		log.info("getReviewImage");
		
		String fullPath = uploadPath + File.separator + path + chgName + "." + extension;
		
		log.info("fullPath = " + fullPath);
		
		ResponseEntity<byte[]> entity = GetImgUtil.getImage(fullPath);
		
		return entity;
	}
	
	@PostMapping("/delete")
	public ResponseEntity<Integer> delete(String path, String chgName, String extension){
		log.info("delete()");
		
		String fullPath = uploadPath + File.separator + path + chgName + "." + extension;
		
		uploadImgUtil.deleteFile(fullPath);
		
		return new ResponseEntity<Integer>(1, HttpStatus.OK);
	}
	
	@PostMapping("/remove")
	public ResponseEntity<Integer> remove(String path){
		log.info("remove()");
		
		if(path == null) {
			return new ResponseEntity<Integer>(1, HttpStatus.OK);
		}
		
		log.info("path : " + path);
		
		String fullPath = uploadPath + File.separator + path;
		
		File forder = new File(fullPath);
		
		log.info("isDirectory : " + forder.isDirectory());
		
		File[] fileList = forder.listFiles();
		
		System.gc();
		for(int i=0; i < fileList.length; i++) {
			fileList[i].delete();
		}
		
		forder.delete();
		
		return new ResponseEntity<Integer>(1, HttpStatus.OK);
	}
	
	@GetMapping("/reviewImage")
	public ResponseEntity<byte[]> getReviewImage(int imgId){
		log.info("getReviewImage()");
		
		ImgVO vo = imgService.getImgReviewById(imgId);
		
		String fullPath = uploadPath + File.separator + vo.getImgPath() + vo.getImgChgName() + "." + vo.getImgExtension();
		
		ResponseEntity<byte[]> entity = GetImgUtil.getImage(fullPath);
		
		return entity;
	}
	
	@GetMapping("/foodImage")
	public ResponseEntity<byte[]> getFoodImage(int imgFoodId){
		log.info("getFoodImage()");
		
		ImgVO vo = imgService.getImgFoodById(imgFoodId);
		log.info(vo);
		
		String fullPath = uploadPath + File.separator + vo.getImgPath() + vo.getImgChgName() + "." + vo.getImgExtension();
		
		ResponseEntity<byte[]> entity = GetImgUtil.getImage(fullPath);
		
		return entity;
	}
}
