package com.gs24.website.controller;

<<<<<<< HEAD
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gs24.website.domain.ImgFoodVO;
import com.gs24.website.service.ImgFoodService;
=======
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.gs24.website.domain.ImgFoodVO;
import com.gs24.website.util.uploadImgFoodUtil;
>>>>>>> 5f0e7c57d0a4abf29e5d76e4b4e2974567c8a0d7

import lombok.extern.log4j.Log4j;

@Controller
<<<<<<< HEAD
@RequestMapping("/ImgFood")
@Log4j
public class ImgFoodController{
	
	@Autowired
	private ImgFoodService imgFoodService;
	
	
	@GetMapping
	public ResponseEntity<byte[]> getImage(Integer foodId) throws Exception{
		log.info("getImage()");
		log.info(foodId);
		ImgFoodVO imgFoodVO = imgFoodService.getImgFoodById(foodId);
		log.info(imgFoodVO);
		
		File file = new File(imgFoodVO.getImgFoodPath());
		
		InputStream in = null;
		
		in = new FileInputStream(file);
		
		ResponseEntity<byte[]> entity = null;
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.add("Content-type", Files.probeContentType(file.toPath()));
		
		entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.OK);
		
		return entity;
=======
@RequestMapping(value = "/imgfood")
@Log4j
public class ImgFoodController {

	@Autowired
	private String uploadPath;

	@GetMapping("/register")
	public void registerGET() {
		log.info("registerGET");
	}

	@PostMapping("/register")
	public void registerPOST(ImgFoodVO imgFoodVO) {
		log.info("registerPOST() ����");
		MultipartFile file = imgFoodVO.getFile();
		log.info("���� �̸� : " + file.getOriginalFilename());
		log.info("���� ũ�� : " + file.getSize());

		// �̰� DB�� �����Ǵ� foodId �̰ɷ� �����ϸ鼭 �ϸ� �ɵ�
		String chgName = "7����ǰ";
		// ���� ����
		boolean a = uploadImgFoodUtil.saveFile(uploadPath, file,
				chgName + "." + uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));

		// ���� ���� �̸� ����
		imgFoodVO.setImgFoodRealName(uploadImgFoodUtil.subStrName(file.getOriginalFilename()));
		// ���� ���� �̸� ����
		imgFoodVO.setImgFoodChgName(chgName);
		// ���� Ȯ���� ����
		imgFoodVO.setImgFoodExtension(uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));
		// ���� ��� ����
		imgFoodVO.setImgFoodPath(uploadPath + uploadImgFoodUtil.makeDir() + chgName + "."
				+ uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));

		if (a) {
			// ������ ������ insert
			log.info("������ �����Ͽ� update");
		} else {
			// ������ ������ update
			log.info("������ �������� �ʾ� insert");
		}

		log.info(imgFoodVO);

		// uploadImgFoodUtil.deleteFile(uploadPath, chgName +"."+
		// uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));
>>>>>>> 5f0e7c57d0a4abf29e5d76e4b4e2974567c8a0d7
	}
	
}
