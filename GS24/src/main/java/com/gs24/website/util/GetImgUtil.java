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
	 * @Param �Ű������� �̹��� ��θ� �־��ָ� �ȴ�. ex)
	 * C:\Users\sdedu\Desktop\gsproject\GS24\src\main\webapp\ImgFood\FoodNO8.png
	 * ��Ʈ�ѷ����� �ؿ��� ���� ����ϸ� �ȴ�. 
	 * public ResponseEntity<byte[]> �޼ҵ��̸�() throws IOException{ 
	 * �߰��� �ʿ��� ����
	 * ResponseEntity<byte[]> entity = GetImgUtil.getImage(���ϰ��); return entity; }
	 * jsp���� <img>�±׿� src="../Controller Mapping URL/Method Mapping URL" �� ����ϸ� ����
	 * ���� �����մϴ�. �߰��� QueryString�� ����ؼ� �ּ� ��ü�� URL�� ������ Ȱ�뵵 �����մϴ�.
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
