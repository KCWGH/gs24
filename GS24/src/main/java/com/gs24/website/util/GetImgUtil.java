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
	
	// 매개변수 예)C:\Users\sdedu\Desktop\gsproject\GS24\src\main\webapp\ImgFood\FoodNO8.png
	// 컨트롤러에서 밑에 메소드 추가하면 된다.
	// Getmapping("")
	//  public ResponseEntity<byte[]> 메소드 이름(){ 
	//	추가로 필요한 내용
	//	ResponseEntity<byte[]> entity = GetImgUtil.getImage(파일 경로);
	//	return entity;
	//	}
	// jsp <img> src=../컨트롤러 매핑 URL/메소드패핑 URL?file
	public static ResponseEntity<byte[]> getImage(String filePath) throws IOException{
		log.info("getImage()");
		
		File file = new File(filePath);
		if(!file.isFile()) {
			log.info("�ش� ��ο� ������ �������� �ʽ��ϴ�.");
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
