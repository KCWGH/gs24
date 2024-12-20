package com.gs24.website.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.gs24.website.domain.ImgFoodVO;
import com.gs24.website.util.uploadImgFoodUtil;

import lombok.extern.log4j.Log4j;

@Controller
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
	}
}
