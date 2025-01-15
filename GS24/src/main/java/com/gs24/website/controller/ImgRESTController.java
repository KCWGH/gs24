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

import com.gs24.website.domain.ImgFoodVO;
import com.gs24.website.domain.ImgReviewVO;
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
	
	@PostMapping("/review")
	public ResponseEntity<List<ImgReviewVO>> createReviewImage(MultipartFile[] files, int reviewId) {
		log.info("createReviewImage()");
		List<ImgReviewVO> entity = new ArrayList<ImgReviewVO>();
		
		if(reviewId == 0) {
			log.info("신규 리뷰 작성입니다.");
			reviewId = (int)imgService.getNextReviewId();
		}		
		String dir = "ReviewNO" + reviewId;
		
		for(int i=0; i<files.length; i++) {
			log.info(i + "th file : " + files[i].getOriginalFilename());
			String realName = uploadImgUtil.subStrName(files[i].getOriginalFilename());
			
			String chgName = UUID.randomUUID().toString();
			
			String extension = uploadImgUtil.subStrExtension(files[i].getOriginalFilename());
			
			uploadImgUtil.saveFile(uploadPath, files[i], dir, chgName, extension);
		
			ImgReviewVO imgReviewVO = new ImgReviewVO();
			
			imgReviewVO.setImgReviewRealName(realName);
			imgReviewVO.setImgReviewChgName(chgName);
			imgReviewVO.setImgReviewExtension(extension);
			imgReviewVO.setImgReviewPath(uploadImgUtil.makeDir(dir));
			imgReviewVO.setReviewId(reviewId);
			
			entity.add(imgReviewVO);
		}
		
		log.info(entity);
		
		return new ResponseEntity<List<ImgReviewVO>>(entity, HttpStatus.OK);
	}
	
	@PostMapping("/food")
	public ResponseEntity<List<ImgFoodVO>> createFoodImage(MultipartFile[] files) {
		log.info("createFoodImage()");
		List<ImgFoodVO> list = new ArrayList<>();
		
		long foodId = imgService.getNextFoodId();
		
		String dir = "FoodNO" + foodId;
		
		for(int i=0; i<files.length; i++) {
			log.info(i + "th file : " + files[i].getOriginalFilename());
			
			String chgName = UUID.randomUUID().toString();
			
			String realName = uploadImgUtil.subStrName(files[i].getOriginalFilename());
					
			String extention = uploadImgUtil.subStrExtension(files[i].getOriginalFilename());
			
			uploadImgUtil.saveFile(uploadPath, files[i], dir, chgName, extention);
			
			ImgFoodVO imgFoodVO = new ImgFoodVO();
			
			imgFoodVO.setImgFoodChgName(chgName);
			imgFoodVO.setImgFoodRealName(realName);
			imgFoodVO.setImgFoodExtension(extention);
			imgFoodVO.setImgFoodPath(uploadImgUtil.makeDir(dir));
			imgFoodVO.setFoodId((int)foodId);
			
			list.add(imgFoodVO);
		}
		
		return new ResponseEntity<List<ImgFoodVO>>(list, HttpStatus.OK);
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
	public ResponseEntity<byte[]> getReviewImage(int imgReviewId){
		log.info("getReviewImage()");
		
		ImgReviewVO vo = imgService.getImgReviewById(imgReviewId);
		
		String fullPath = uploadPath + File.separator + vo.getImgReviewPath() + vo.getImgReviewChgName() + "." + vo.getImgReviewExtension();
		
		ResponseEntity<byte[]> entity = GetImgUtil.getImage(fullPath);
		
		return entity;
	}
	
	@GetMapping("/foodImage")
	public ResponseEntity<byte[]> getFoodImage(int imgFoodId){
		log.info("getFoodImage()");
		
		ImgFoodVO vo = imgService.getImgFoodById(imgFoodId);
		
		String fullPath = uploadPath + File.separator + vo.getImgFoodPath() + vo.getImgFoodChgName() + "." + vo.getImgFoodExtension();
		
		ResponseEntity<byte[]> entity = GetImgUtil.getImage(fullPath);
		
		return entity;
	}
}
