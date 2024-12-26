package com.gs24.website.controller;

<<<<<<< Updated upstream
import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
=======
import org.springframework.beans.factory.annotation.Autowired;
>>>>>>> Stashed changes
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
<<<<<<< Updated upstream

import com.gs24.website.domain.ImgFoodVO;
import com.gs24.website.service.ImgFoodService;
import com.gs24.website.util.GetImgUtil;
=======
import org.springframework.web.multipart.MultipartFile;

import com.gs24.website.domain.ImgFoodVO;
import com.gs24.website.util.uploadImgFoodUtil;
>>>>>>> Stashed changes

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/ImgFood")
@Log4j
public class ImgFoodController{

<<<<<<< Updated upstream
	@Autowired
	private ImgFoodService imgFoodService;
	
	@Autowired
	private String uploadPath;
	
	@GetMapping
	public ResponseEntity<byte[]> getFoodImage(Integer foodId) throws IOException{
		log.info("getFoodImage()");
		log.info(foodId);
		ImgFoodVO imgFoodVO = imgFoodService.getImgFoodById(foodId);
		
		String path = uploadPath + File.separator + imgFoodVO.getImgFoodPath();
		
		ResponseEntity<byte[]> entity = GetImgUtil.getImage(path);
		
		return entity;	
	}
	
	@GetMapping("/regist")
	public ResponseEntity<byte[]>foodRegister(String filePath) throws IOException{
		log.info("foodRegister()");
		log.info("File Path : " + filePath);
		
		ResponseEntity<byte[]> entity = GetImgUtil.getImage(filePath);
		
		return entity;
		
	}
}
=======
    @Autowired
    private String uploadPath;

    // 이미지 음식 등록 페이지
    @GetMapping("/register")
    public void registerGET() {
        log.info("registerGET()");
    }

    // 이미지 음식 등록 처리
    @PostMapping("/register")
    public void registerPOST(ImgFoodVO imgFoodVO) {
        log.info("registerPOST()");

        MultipartFile file = imgFoodVO.getFile();
        log.info("파일 이름: " + file.getOriginalFilename());
        log.info("파일 크기: " + file.getSize());

        // 파일명 변경 및 저장
        String chgName = "food_" + System.currentTimeMillis(); // 파일명에 현재 시간 추가
        boolean isSaved = uploadImgFoodUtil.saveFile(uploadPath, file, 
                chgName + "." + uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));

        // 파일 정보 세팅
        imgFoodVO.setImgFoodRealName(uploadImgFoodUtil.subStrName(file.getOriginalFilename()));
        imgFoodVO.setImgFoodChgName(chgName);
        imgFoodVO.setImgFoodExtension(uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));
        imgFoodVO.setImgFoodPath(uploadPath + uploadImgFoodUtil.makeDir() + chgName + "." 
                + uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));

        if (isSaved) {
            log.info("파일 저장 성공, DB에 정보 업데이트");
        } else {
            log.info("파일 저장 실패");
        }

        log.info("등록된 이미지 정보: " + imgFoodVO);

        // 필요한 경우, 파일 삭제 (주석 처리된 부분)
        // uploadImgFoodUtil.deleteFile(uploadPath, chgName + "." + uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));
    }
}
>>>>>>> Stashed changes
