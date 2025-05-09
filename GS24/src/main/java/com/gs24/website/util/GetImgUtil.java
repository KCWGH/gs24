package com.gs24.website.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import lombok.extern.log4j.Log4j;

@Log4j
public class GetImgUtil {
	
	public static ResponseEntity<byte[]> getImage(String filePath){
		log.info("getImage()");
		
		File file = new File(filePath);
		if(!file.isFile()) {
			return null;
		}
		
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;
		
		try {
			in = new FileInputStream(file);
			
			HttpHeaders headers = new HttpHeaders();
			String contentType = Files.probeContentType(file.toPath());
			headers.setContentType(MediaType.parseMediaType(contentType));
			
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		}
		
		return entity;
	}
}
