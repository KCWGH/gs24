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
        log.info("registerPOST() 실행");

        MultipartFile file = imgFoodVO.getFile();
        log.info("파일 이름 : " + file.getOriginalFilename());
        log.info("파일 크기 : " + file.getSize());

        String chgName = "7번음식";
        boolean result = uploadImgFoodUtil.saveFile(uploadPath, file,
                chgName + "." + uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));

        imgFoodVO.setImgFoodRealName(uploadImgFoodUtil.subStrName(file.getOriginalFilename()));
        imgFoodVO.setImgFoodChgName(chgName);
        imgFoodVO.setImgFoodExtension(uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));
        imgFoodVO.setImgFoodPath(uploadPath + uploadImgFoodUtil.makeDir() + chgName + "." 
                + uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));

        if (result) {
            log.info("이미지 저장 성공 후 update");
        } else {
            log.info("이미지 저장 실패 후 insert");
        }

        log.info(imgFoodVO);
    }
}
