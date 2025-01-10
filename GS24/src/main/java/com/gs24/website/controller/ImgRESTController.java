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

import com.gs24.website.service.ImgService;
import com.gs24.website.util.GetImgUtil;
import com.gs24.website.util.uploadImgUtil;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("/Image")
@Log4j
public class ImgRESTController {
	
	@Autowired
	private String uploadPath;
	
	@Autowired
	private ImgService imgService;
	
	@PostMapping
	public ResponseEntity<List<String>> createImage(MultipartFile[] files, String type) {
		log.info("createImage()");
		log.info(type);
		List<String> entity = new ArrayList<>();
		for(int i=0; i<files.length; i++) {
			log.info(i + "th file : " + files[i].getOriginalFilename());
			String chgName = UUID.randomUUID().toString();
			String dir = "";
			if(type.equals("food")) {
				dir = "FoodNO" + imgService.getNextFoodId();
			} else if(type.equals("review")) {
				dir = "ReviewNO" + imgService.getNextReviewId();
			}
			
			String extention = uploadImgUtil.subStrExtension(files[i].getOriginalFilename());
			
			uploadImgUtil.saveFile(uploadPath, files[i], dir, chgName, extention);
			
			entity.add(chgName + "." + extention);
		}
		
		log.info(entity);
		
		return new ResponseEntity<List<String>>(entity, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<byte[]> getImage(String path, String type){
		log.info("getImage");
		
		String dir = "";
		if(type.equals("food")) {
			dir = "FoodNO" + imgService.getNextFoodId();
		} else if(type.equals("review")) {
			dir = "ReviewNO" + imgService.getNextReviewId();
		}
		
		String realPath = uploadPath + File.separator + uploadImgUtil.makeDir(dir) + File.separator +  path;
		
		ResponseEntity<byte[]> entity = GetImgUtil.getImage(realPath);
		
		return entity;
	}
	
	@PostMapping("/delete")
	public ResponseEntity<Integer> delete(String path, String type){
		log.info("delete()");
		log.info(path);
		String dir = "";
		if(type.equals("food")) {
			dir = "FoodNO" + imgService.getNextFoodId();
		} else if(type.equals("review")) {
			dir = "ReviewNO" + imgService.getNextReviewId();
		}
		
		String chgName = uploadImgUtil.subStrName(path);
		String extention = uploadImgUtil.subStrExtension(path);
		
		uploadImgUtil.deleteFile(uploadPath, dir, chgName, extention);
		
		return null;
	}
}
