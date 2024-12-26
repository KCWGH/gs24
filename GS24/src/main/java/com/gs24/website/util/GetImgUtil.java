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
	
	
	
	/*
	 * @Param 매개변수로 이미지 경로를 넣어주면 된다. ex)
	 * C:\Users\sdedu\Desktop\gsproject\GS24\src\main\webapp\ImgFood\FoodNO8.png
	 * 컨트롤러에서 밑에와 같이 사용하면 된다. 
	 * public ResponseEntity<byte[]> 메소드이름() throws IOException{ 
	 * 추가로 필요한 내용
	 * ResponseEntity<byte[]> entity = GetImgUtil.getImage(파일경로); return entity; }
	 * jsp에서 <img>태그에 src="../Controller Mapping URL/Method Mapping URL" 로 사용하면 쉽게
	 * 적용 가능합니다. 추가로 QueryString을 사용해서 주소 자체를 URL로 보내는 활용도 가능합니다.
	 */
	/**
     * use easly to print local image file
     * 
     * @param filePath : absolute file path | ex) C:\Users\sdedu\Desktop\test.png
     * @return image file data
     */
	public static ResponseEntity<byte[]> getImage(String filePath) throws IOException{
		log.info("getImage()");
		
		File file = new File(filePath);
		if(!file.isFile()) {
			log.info("해당 경로에 파일이 존재하지 않습니다.");
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
