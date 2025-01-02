package com.gs24.website.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.extern.log4j.Log4j;

@Log4j
public class GetImgUtil {
	public static ResponseEntity<byte[]> getImage(String filePath) throws IOException{
		log.info("getImage()");
		
		File file = new File(filePath);
		if(!file.isFile()) {
			return null;
		}
		
		InputStream in = null;
		
		in = new FileInputStream(file);

		ResponseEntity<byte[]> entity = null;
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.add("Content-type", Files.probeContentType(file.toPath()));

		entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.OK);
		
		return entity;
	}
}
