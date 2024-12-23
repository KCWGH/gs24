package com.gs24.website.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gs24.website.service.ImgFoodService;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping(value = "/imgfood")
@Log4j
public class ImgFoodController {
	
	@Autowired
	private ImgFoodService imgFoodService;
	
	@GetMapping("/Get/{foodId}")
	public ResponseEntity<byte[]> getImage(@PathVariable int foodId){
		
		String path = imgFoodService.getImgFoodById(foodId).getImgFoodPath();
		
		File file = new File(path);
		
		ResponseEntity<byte[]> result = null;
		
		try {
			HttpHeaders header = new HttpHeaders();
			header.add("Content-type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file),header,HttpStatus.OK);
			
		} catch (IOException e) {
			log.info(e.getMessage());
		}
<<<<<<< HEAD

		log.info(imgFoodVO);


		// uploadImgFoodUtil.deleteFile(uploadPath, chgName +"."+
		// uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));
=======
		
		return result;
>>>>>>> 20091d038ecd342f64f0e1bcb2640eb581d47778
	}
}
